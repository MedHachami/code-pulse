package com.yc.code_pulse_api.service.implementation;




import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yc.code_pulse_api.dto.req.RepositoryDto;
import com.yc.code_pulse_api.dto.req.UserDto;


// import lombok.Value;
@Service
public class GitHubService {
    private final OAuth2AuthorizedClientService clientService;
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(OAuth2AuthorizedClientService clientService , RestTemplate restTemplate){
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    // @Value("${github.api.base-url:https://api.github.com}")
    private String githubApiBaseUrl = "github.api.base-url:https://api.github.com";

    private String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            throw new IllegalStateException("User not authenticated with OAuth2");
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
            oauthToken.getAuthorizedClientRegistrationId(),
            oauthToken.getName()
        );

        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("No authorized client found");
        }

        return client.getAccessToken().getTokenValue();
    }

    public UserDto getCurrentUser() {
        HttpHeaders headers = createAuthenticatedHeaders();
        RequestEntity<Void> request = new RequestEntity<>(
            headers,
            HttpMethod.GET,
            URI.create(githubApiBaseUrl + "/user")
        );
        
        return restTemplate.exchange(request, UserDto.class).getBody();
    }

    public List<RepositoryDto> getUserRepositories() {
        HttpHeaders headers = createAuthenticatedHeaders();
        RequestEntity<Void> request = new RequestEntity<>(
            headers,
            HttpMethod.GET,
            URI.create(githubApiBaseUrl + "/user/repos")
        );

        return restTemplate.exchange(
            request,
            new ParameterizedTypeReference<List<RepositoryDto>>() {}
        ).getBody();
    }

    private HttpHeaders createAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.set("Accept", "application/vnd.github.v3+json");
        return headers;
    }
}