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

            // Search for the user in the database
            Optional<User> userOptional = userRepository.findByEmail(email);

            User user;
            if (userOptional.isPresent()) {

                user = userOptional.get();
                // Update TenantContext with the tenant ID
                TenantContext.setTenantId(user.getTenantId());
                // Update User infos
                user.setName(name);
                user.setPicture(picture);
            } else {
                // New user
                String newTenantId = UUID.randomUUID().toString();
                // Define the new Tenant
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

            // CREATE PRINCIPAL WITH TENANTID
            return CustomUserPrincipal.create(user, oauth2User.getAttributes());

        } catch (Exception ex) {
            log.error("Error during OAuth2 authentication", ex);
            throw new RuntimeException("Failed to process OAuth2 user", ex);
        }
    }
}