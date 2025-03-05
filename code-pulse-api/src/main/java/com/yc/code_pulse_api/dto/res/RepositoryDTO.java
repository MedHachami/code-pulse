package com.yc.code_pulse_api.dto.res;


import com.yc.code_pulse_api.dto.res.github.OwnerDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepositoryDTO {
    private Long id;
    private String name;
    private String full_name;
    private String avatar_url;
    private String html_url;
    private String default_branch;
    private String language;
    private OwnerDTO owner;
}
