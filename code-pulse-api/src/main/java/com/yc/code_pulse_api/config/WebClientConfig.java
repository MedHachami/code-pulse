package com.yc.code_pulse_api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebClientConfig implements WebMvcConfigurer {
    private static final Logger log = LogManager.getLogger(WebClientConfig.class);

    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json";
    private static final String GITHUB_API_VERSION = "2022-11-28";

    @Bean
    public WebClient webClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> {
                configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder());
                configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder());
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024); // 16MB buffer
            })
            .build();

        return WebClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader("Accept", "application/json")
            .exchangeStrategies(strategies)
            .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
