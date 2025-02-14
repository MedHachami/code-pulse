package com.yc.code_pulse_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yc.code_pulse_api.dto.req.RepositoryDto;
import com.yc.code_pulse_api.dto.req.UserDto;
import com.yc.code_pulse_api.service.implementation.GitHubService;
import java.util.List;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubService gitHubService;

    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(gitHubService.getCurrentUser());
    }

    @GetMapping("/repositories")
    public ResponseEntity<List<RepositoryDto>> getUserRepositories() {
        return ResponseEntity.ok(gitHubService.getUserRepositories());
    }
}
