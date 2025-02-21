package com.yc.code_pulse_api.dto.res.github;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentDTO {
    private String name;
    private String path;
    private String sha;
    private long size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;

   
}
