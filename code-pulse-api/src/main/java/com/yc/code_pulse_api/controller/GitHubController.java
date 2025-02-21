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

import com.yc.code_pulse_api.dto.res.GitHubUserDTO;
import com.yc.code_pulse_api.dto.res.RepositoryDTO;
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

  
    // Repository contents and files
    @GetMapping("/repos/{owner}/{repo}/contents")
    public ResponseEntity<?> getRepositoryContents(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(required = false) String path,
            @RequestParam(required = false) String ref) {
                // return ResponseEntity.ok("aa");

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

    
   
}