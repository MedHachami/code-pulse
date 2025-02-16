package com.yc.code_pulse_api.service.implementation;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.yc.code_pulse_api.dto.res.GitHubUserDTO;
import com.yc.code_pulse_api.dto.res.RepositoryDTO;
import com.yc.code_pulse_api.dto.res.github.ContentDTO;
import com.yc.code_pulse_api.dto.res.github.RepoContentDTO;
import com.yc.code_pulse_api.exception.*;

import reactor.core.publisher.Mono;

@Service
public class GitHubService {
    private static final Logger log = LogManager.getLogger(GitHubService.class);
    
    private final WebClient webClient;
    
    public GitHubService(@Qualifier("githubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }
    
    // Get authenticated user info
    public GitHubUserDTO getCurrentUser() {
        return webClient.get()
            .uri("/user")
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)
            .bodyToMono(GitHubUserDTO.class)
            .block();
    }
    
    // Get user repositories
    public List<RepositoryDTO> getUserRepositories(int page, int size) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/user/repos")
                .queryParam("page", page)
                .queryParam("per_page", size)
                .queryParam("sort", "updated")
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)

            .bodyToFlux(RepositoryDTO.class)
            .collectList()
            .block();
    }
    
    // Get repository by owner and name
    public RepositoryDTO getRepository(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)

            .bodyToMono(RepositoryDTO.class)
            .block();
    }
    
    // Get repository contents
    public RepoContentDTO getRepositoryContents(String owner, String repo, String path, String ref) {
        log.debug("Fetching contents for repo: {}/{} at path: {} with ref: {}", owner, repo, path, ref);

        List<ContentDTO> contentList = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/repos/{owner}/{repo}/contents{path}")
                .queryParamIfPresent("ref", Optional.ofNullable(ref))
                .build(owner, repo, path != null ? "/" + path : ""))
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)

            .bodyToFlux(ContentDTO.class)
            .collectList()
            .block();

        return new RepoContentDTO(repo, owner, ref != null ? ref : "main", contentList);
    }

    // Get raw file content
    public String getFileContent(String owner, String repo, String path, String ref) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/{owner}/{repo}/contents/{path}")
                        .queryParamIfPresent("ref", Optional.ofNullable(ref))
                        .build(owner, repo, path))  
                .header(HttpHeaders.ACCEPT, "application/vnd.github.v3.raw")
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)

                .bodyToMono(String.class)
                .block();
    }
    
    // Improved error handling
    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
            .flatMap(body -> {
                int statusCode = response.statusCode().value();
                log.error("GitHub API error: {} - {}", statusCode, body);

                return switch (statusCode) {
                    case 401 -> Mono.error(new UnauthorizedException("Authentication failed: " + body));
                    case 403 -> Mono.error(new ForbiddenException("Access denied: " + body));
                    case 404 -> Mono.error(new ResourceNotFoundException("Resource not found: " + body));
                    case 422 -> Mono.error(new ValidationException("Validation failed: " + body));
                    case 429 -> Mono.error(new RateLimitedException("Rate limit exceeded: " + body));
                    default -> Mono.error(new GitHubApiException("API error: " + statusCode + " - " + body));
                };
            });
    }
}
