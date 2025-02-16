package com.yc.code_pulse_api.dto.res.github;

public class ContentDTO {
    private String name;
    private String path;
    private String sha;
    private long size;
    private String url;
    private String html_url;
    private String git_url;
    private String downloadUrl;
    private String type;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return html_url;
    }

    public void setHtmlUrl(String html_url) {
        this.html_url = html_url;
    }

    public String getGitUrl() {
        return git_url;
    }

    public void setGitUrl(String git_url) {
        this.git_url = git_url;
    }

    public String getDownloadUrl() {
        return downloadUrl	;
    }

    public void setDownloadUrl(String downloadUrl	) {
        this.downloadUrl = downloadUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
