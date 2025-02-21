package com.yc.code_pulse_api.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepositoryDto {
    private Long id;
    private String name;
    private String fullName;
    private String description;
    private boolean isPrivate;
    private String htmlUrl;
    private String cloneUrl;
    private String defaultBranch;
}