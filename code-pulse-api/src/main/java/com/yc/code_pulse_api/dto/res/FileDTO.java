package com.yc.code_pulse_api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String name;
    private String path;
    private String sha;
    private Long size;
    private String type;  // "file" or "dir"
    private String downloadUrl;
    private String content;  // Optional, if you want inline editing
    private String encoding; // Exam
}
