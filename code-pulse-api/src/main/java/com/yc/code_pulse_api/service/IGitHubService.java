package com.yc.code_pulse_api.service;

import java.util.List;

import com.yc.code_pulse_api.dto.res.GitHubUserDTO;
import com.yc.code_pulse_api.dto.res.RepositoryDTO;
import com.yc.code_pulse_api.dto.res.github.RepoContentDTO;

public interface IGitHubService {
    public GitHubUserDTO getCurrentUser();
    public List<RepositoryDTO> getUserRepositories(int page, int size);
    public RepositoryDTO getRepository(String owner, String repo);
    public RepoContentDTO getRepositoryContents(String owner, String repo, String path, String ref);
    public String getFileContent(String owner, String repo, String path, String ref);


}
