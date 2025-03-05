package com.yc.code_pulse_api.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentLoader {
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
