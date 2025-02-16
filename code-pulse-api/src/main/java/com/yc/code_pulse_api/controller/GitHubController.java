package com.yc.code_pulse_api.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.yc.code_pulse_api.dto.req.RepositoryDto;
import com.yc.code_pulse_api.dto.req.UserDto;
import com.yc.code_pulse_api.dto.res.AuthenticationResultDTO;
import com.yc.code_pulse_api.dto.res.BranchDTO;
import com.yc.code_pulse_api.dto.res.CommitDTO;
import com.yc.code_pulse_api.dto.res.GitHubUserDTO;
import com.yc.code_pulse_api.dto.res.IssueDTO;
import com.yc.code_pulse_api.dto.res.PullRequestDTO;
import com.yc.code_pulse_api.dto.res.RepositoryDTO;
import com.yc.code_pulse_api.dto.res.SearchResultDTO;
import com.yc.code_pulse_api.dto.res.github.RepoContentDTO;
import com.yc.code_pulse_api.service.implementation.GitHubService;
import java.util.List;@RestController
@RequestMapping("/api/github")
public class GitHubController {
    private final GitHubService gitHubService;
    
    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }
    
    // User information
    @GetMapping("/user")
    public ResponseEntity<GitHubUserDTO> getCurrentUser() {
        return ResponseEntity.ok(gitHubService.getCurrentUser());
    }
    
    // Repositories
    @GetMapping("/repos")
    public ResponseEntity<List<RepositoryDTO>> getUserRepositories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(gitHubService.getUserRepositories(page, size));
    }
    
    @GetMapping("/repos/{owner}/{repo}")
    public ResponseEntity<RepositoryDTO> getRepository(
            @PathVariable String owner,
            @PathVariable String repo) {
        return ResponseEntity.ok(gitHubService.getRepository(owner, repo));
    }
    
    // Repository contents and files
    @GetMapping("/repos/{owner}/{repo}/contents")
    public ResponseEntity<?> getRepositoryContents(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(required = false) String path,
            @RequestParam(required = false) String ref) {
        try {
            RepoContentDTO repoContent = gitHubService.getRepositoryContents(owner, repo, path, ref);
            if (repoContent.getContents() == null || repoContent.getContents().isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(repoContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching repository contents: " + e.getMessage());
        }
    }


    
    @GetMapping("/repos/{owner}/{repo}/content/{path}")
    public ResponseEntity<String> getFileContent(
            @PathVariable String owner,
            @PathVariable String repo,
            @PathVariable String path,
            @RequestParam(required = false) String ref) {
        try {
            String content = gitHubService.getFileContent(owner, repo, path, ref);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
    // Branches
    // @GetMapping("/repos/{owner}/{repo}/branches")
    // public ResponseEntity<List<BranchDTO>> getBranches(
    //         @PathVariable String owner,
    //         @PathVariable String repo) {
    //     return ResponseEntity.ok(gitHubService.getBranches(owner, repo));
    // }
    
    // @GetMapping("/repos/{owner}/{repo}/branches/{branch}")
    // public ResponseEntity<BranchDTO> getBranch(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @PathVariable String branch) {
    //     return ResponseEntity.ok(gitHubService.getBranch(owner, repo, branch));
    // }
    
    // // Commits
    // @GetMapping("/repos/{owner}/{repo}/commits")
    // public ResponseEntity<List<CommitDTO>> getCommits(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @RequestParam(required = false) String sha,
    //         @RequestParam(required = false) String path,
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "10") int size) {
    //     return ResponseEntity.ok(gitHubService.getCommits(owner, repo, sha, path, page, size));
    // }
    
    // @GetMapping("/repos/{owner}/{repo}/commits/{sha}")
    // public ResponseEntity<CommitDTO> getCommit(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @PathVariable String sha) {
    //     return ResponseEntity.ok(gitHubService.getCommit(owner, repo, sha));
    // }
  

    // // Pull requests
    // @GetMapping("/repos/{owner}/{repo}/pulls")
    // public ResponseEntity<List<PullRequestDTO>> getPullRequests(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @RequestParam(defaultValue = "open") String state,
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "10") int size) {
    //     return ResponseEntity.ok(gitHubService.getPullRequests(owner, repo, state, page, size));
    // }
    
    // @GetMapping("/repos/{owner}/{repo}/pulls/{number}")
    // public ResponseEntity<PullRequestDTO> getPullRequest(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @PathVariable int number) {
    //     return ResponseEntity.ok(gitHubService.getPullRequest(owner, repo, number));
    // }
    
    // // Issues
    // @GetMapping("/repos/{owner}/{repo}/issues")
    // public ResponseEntity<List<IssueDTO>> getIssues(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @RequestParam(defaultValue = "open") String state,
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "10") int size) {
    //     return ResponseEntity.ok(gitHubService.getIssues(owner, repo, state, page, size));  
    // }
    
    // // Search functionality
    // @GetMapping("/search/code")
    // public ResponseEntity<SearchResultDTO> searchCode(
    //         @RequestParam String q,
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "10") int size) {
    //     return ResponseEntity.ok(gitHubService.searchCode(q, page, size));
    // }
    
    // // OAuth endpoints (if not handled by Spring Security)
    // @GetMapping("/oauth/login")
    // public ResponseEntity<String> getAuthorizationUrl() {
    //     return ResponseEntity.ok(gitHubService.getAuthorizationUrl());
    // }
    
    // @GetMapping("/oauth/callback")
    // public ResponseEntity<AuthenticationResultDTO> handleOAuthCallback(
    //         @RequestParam String code,
    //         @RequestParam String state) {
    //     return ResponseEntity.ok(gitHubService.processOAuthCallback(code, state));
    // }
    
    // Webhook management
    // @PostMapping("/repos/{owner}/{repo}/hooks")
    // public ResponseEntity<WebhookDTO> createWebhook(
    //         @PathVariable String owner,
    //         @PathVariable String repo,
    //         @RequestBody WebhookCreateRequest request) {
    //     return ResponseEntity.ok(gitHubService.createWebhook(owner, repo, request));
    // }
}