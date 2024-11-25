package com.nilsw13.springreact.service;

import com.nilsw13.springreact.model.CustomUserPrincipal;
import com.nilsw13.springreact.model.User;
import com.nilsw13.springreact.repository.UserRepository;
import com.nilsw13.springreact.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String picture = oauth2User.getAttribute("picture");
            String googleId = oauth2User.getAttribute("sub");

            // Recherche de l'utilisateur existant
            Optional<User> userOptional = userRepository.findByEmail(email);

            User user;
            if (userOptional.isPresent()) {
                // Utilisateur existant
                user = userOptional.get();
                // Met à jour le TenantContext avec le tenant existant
                TenantContext.setTenantId(user.getTenantId());
                // Met à jour les infos utilisateur
                user.setName(name);
                user.setPicture(picture);
            } else {
                // Nouvel utilisateur
                String newTenantId = UUID.randomUUID().toString();
                // Définit le nouveau tenant
                TenantContext.setTenantId(newTenantId);

                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPicture(picture);
                user.setGoogleId(googleId);
                user.setTenantId(newTenantId);
                user.setEmailVerified(true);
            }

            user = userRepository.save(user);

            // Crée le principal avec le tenant ID
            return CustomUserPrincipal.create(user, oauth2User.getAttributes());

        } catch (Exception ex) {
            log.error("Error during OAuth2 authentication", ex);
            throw new RuntimeException("Failed to process OAuth2 user", ex);
        }
    }
}