package com.yc.code_pulse_api.dto.res;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDTO {
    private String name;
    // private ContentDTO commit;
    private String downloadUrl;


}
