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

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oauth2User);
        } catch (Exception ex) {
            log.error("Error processing OAuth2 user", ex);
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        // Extraction des informations de l'utilisateur Google
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");
        String googleId = oauth2User.getAttribute("sub");

        // Recherche de l'utilisateur dans la base de données
        User user = userRepository.findByEmail(email)
                .map(existingUser -> updateExistingUser(existingUser, name, picture))
                .orElseGet(() -> registerNewUser(email, name, picture, googleId));

        return CustomUserPrincipal.create(user, oauth2User.getAttributes());
    }

    private User updateExistingUser(User user, String name, String picture) {
        user.setName(name);
        user.setPicture(picture);
        return userRepository.save(user);
    }

    private User registerNewUser(String email, String name, String picture, String googleId) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPicture(picture);
        user.setGoogleId(googleId);
        // Par défaut, utiliser le tenant du contexte ou un tenant par défaut
        user.setTenantId(TenantContext.getTenantID());
        return userRepository.save(user);
    }
}