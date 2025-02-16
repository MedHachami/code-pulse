package com.yc.code_pulse_api.dto.res.github;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class RepoContentDTO {
    private String repository;
    private String owner;
    private String branch;
    private List<ContentDTO> contents;

  

    public RepoContentDTO(String repository, String owner, String branch, List<ContentDTO> contents) {
        this.repository = repository;
        this.owner = owner;
        this.branch = branch;
        this.contents = contents;
    }

    // Getters & Setters
    public String getRepository() { return repository; }
    public void setRepository(String repository) { this.repository = repository; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public List<ContentDTO> getContents() { return contents; }
    public void setContents(List<ContentDTO> contents) { this.contents = contents; }
}
