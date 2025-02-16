package com.yc.code_pulse_api.dto.res;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepositoryDTO {
    private Long id;
    private String name;
    private String fullName;
    private String owner;
    private String avatarUrl;
    private String htmlUrl;
    private String defaultBranch;
    private String language;
}
