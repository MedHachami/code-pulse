package com.yc.code_pulse_api.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitHubUserDTO {
    private String login;
    private String name;
    private String avatarUrl;

}
