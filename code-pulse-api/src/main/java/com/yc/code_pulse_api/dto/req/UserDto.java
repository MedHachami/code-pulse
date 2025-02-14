package com.yc.code_pulse_api.dto.req;

public record UserDto(
    Long id,
    String login,
    String name,
    String email,
    String avatarUrl,
    String bio
) {}
