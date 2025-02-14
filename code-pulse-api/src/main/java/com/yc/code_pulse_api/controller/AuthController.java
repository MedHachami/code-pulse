package com.yc.code_pulse_api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yc.code_pulse_api.dto.res.ErrorResponse;
import com.yc.code_pulse_api.dto.res.TokenResponseDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuth2AuthorizedClientService clientService;

    @GetMapping("/token")
    public ResponseEntity<?> getToken(Authentication authentication) {
        // Check if user is authenticated at all
        if (authentication == null) {
            return ResponseEntity
                .status(401)
                .body(new ErrorResponse(
                    "Authentication required",
                    "Please complete the OAuth flow by visiting: /oauth2/authorization/github",
                    "ERR_NOT_AUTHENTICATED"
                ));
        }

        // Check if it's OAuth2 authentication
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return ResponseEntity
                .status(401)
                .body(new ErrorResponse(
                    "Invalid authentication type",
                    "Please complete the OAuth flow by visiting: /oauth2/authorization/github",
                    "ERR_INVALID_AUTH_TYPE"
                ));
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        
        try {
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName()
            );

            if (client == null || client.getAccessToken() == null) {
                return ResponseEntity
                    .status(401)
                    .body(new ErrorResponse(
                        "No valid token found",
                        "Token not found or expired. Please re-authenticate at: /oauth2/authorization/github",
                        "ERR_NO_TOKEN"
                    ));
            }

            return ResponseEntity.ok(TokenResponseDto.builder()
                .accessToken(client.getAccessToken().getTokenValue())
                .tokenType("Bearer")
                .expiresIn(client.getAccessToken().getExpiresAt().getEpochSecond())
                .scope(String.join(" ", client.getAccessToken().getScopes()))
                .build());

        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body(new ErrorResponse(
                    "Token retrieval failed",
                    "An error occurred while retrieving the token. Please try re-authenticating.",
                    "ERR_TOKEN_RETRIEVAL"
                ));
        }
    }
}
