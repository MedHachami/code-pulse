package com.yc.code_pulse_api.dto.res;

import java.util.Date;

public class CommitDTO {
    private String sha;
    private String message;
    private GitHubUserDTO author;
    private Date date;
}
