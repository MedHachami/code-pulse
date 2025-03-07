package com.yc.code_pulse_api.dto.res.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContentDTO {
    private String name;
    private String path;
    private String sha;
    private Long size;
    private String url;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    @JsonProperty("git_url")
    private String gitUrl;
    
    @JsonProperty("download_url")
    private String downloadUrl;
    
    private String type;
    private String content;
    private String encoding;
}
