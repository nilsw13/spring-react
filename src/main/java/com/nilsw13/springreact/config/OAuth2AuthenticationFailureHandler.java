package com.nilsw13.springreact.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("app.oauth2.redirect-uri=http://localhost:8080/oauth2/redirect")
    private String frontendUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        log.error("OAuth2 authentication failed: {}", exception.getMessage());

        String targetUrl = UriComponentsBuilder
                .fromUriString(frontendUrl)
                .path("/login")
                .queryParam("error", exception.getLocalizedMessage())
                .build()
                .toUriString();

        // Si la réponse n'a pas déjà été envoyée
        if (!response.isCommitted()) {
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            log.warn("Response has already been committed. Unable to redirect to {}", targetUrl);
        }
    }

    /**
     * Nettoie l'URL pour éviter les redirections malveillantes
     */
    private String sanitizeTargetUrl(String url) {
        // Vérifie si l'URL est celle de votre frontend
        if (!url.startsWith(frontendUrl)) {
            log.warn("Invalid redirect URL: {}", url);
            return frontendUrl + "/login";
        }
        return url;
    }

    /**
     * Construit un message d'erreur approprié pour l'utilisateur
     */
    private String buildErrorMessage(AuthenticationException exception) {
        String message = exception.getMessage();
        // Évite d'exposer des détails techniques sensibles
        if (message.contains("technical")) {
            return "Une erreur est survenue lors de l'authentification";
        }
        return message;
    }
}