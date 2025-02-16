package com.yc.code_pulse_api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class GitHubUserDTO {
    private String login;
    private String name;
    private String avatarUrl;

}
