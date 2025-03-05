package com.yc.code_pulse_api.dto.req;

import lombok.Data;

@Data
public class GithubContentDto {
    private String name;
    private String path;
    private String sha;
    private long size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;
    private String content;
    private String encoding;
} 