package com.yc.code_pulse_api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
public class WebClientConfig {
    private static final Logger log = LogManager.getLogger(WebClientConfig.class);


    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json";
    private static final String GITHUB_API_VERSION = "2022-11-28";

    @Bean
    public WebClient githubWebClient() {
        String githubToken = System.getenv("GITHUB_TOKEN");
        if (githubToken == null || githubToken.isBlank()) {
            log.warn("GITHUB_TOKEN is not set!");
        }

        return WebClient.builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubToken)
            .defaultHeader(HttpHeaders.ACCEPT, GITHUB_ACCEPT_HEADER)
            .defaultHeader("X-GitHub-Api-Version", GITHUB_API_VERSION)
            .filter((request, next) -> {
                log.debug("Sending request: {} {}", request.method(), request.url());
                return next.exchange(request);
            })
            .build();
    }
}
