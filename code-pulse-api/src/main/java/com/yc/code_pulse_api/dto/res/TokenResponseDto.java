package com.yc.code_pulse_api.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String scope;
}