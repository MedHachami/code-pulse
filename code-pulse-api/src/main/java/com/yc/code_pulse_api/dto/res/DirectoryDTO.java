package com.yc.code_pulse_api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryDTO {
    private String name;
    private String path;
    private String type; // "dir"
    private String url;  // API URL to fetch directory contents
}
