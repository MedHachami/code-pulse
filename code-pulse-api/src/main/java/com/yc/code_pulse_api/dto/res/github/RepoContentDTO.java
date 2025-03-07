package com.yc.code_pulse_api.dto.res.github;

import lombok.Data;
import java.util.List;

@Data
public class RepoContentDTO {
    private final String repository;
    private final String owner;
    private final String ref;
    private final List<ContentDTO> contents;

    public RepoContentDTO(String repository, String owner, String ref, List<ContentDTO> contents) {
        this.repository = repository;
        this.owner = owner;
        this.ref = ref;
        this.contents = contents;
    }
}
