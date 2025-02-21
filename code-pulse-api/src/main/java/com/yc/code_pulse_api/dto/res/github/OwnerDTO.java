package com.yc.code_pulse_api.dto.res.github;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerDTO {
    private Long id;
    private String login;
    private String avatar_url;
    private String html_url;
}
