package com.nilsw13.springreact.service;

import com.nilsw13.springreact.model.CustomUserPrincipal;
import com.nilsw13.springreact.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2 authentication success");

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        // Génère le token avec le tenant ID
        String token = tokenProvider.generateToken(authentication);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        if (response.isCommitted()) {
            log.warn("Response has already been committed");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}