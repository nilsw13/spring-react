package com.nilsw13.springreact.service;

import com.nilsw13.springreact.dto.UserDTO;
import com.nilsw13.springreact.model.User;

import com.nilsw13.springreact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));
        return UserDTO.fromUser(user);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User createUser(String email, String name, String picture, String googleId) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPicture(picture);
        user.setGoogleId(googleId);
        user.setTenantId(generateUniqueTenantId()); // Generate a unique tenant ID
        user.setEmailVerified(true); // L'email verified beacause it comes from Google

        return userRepository.save(user);
    }

    private String generateUniqueTenantId() {
        return UUID.randomUUID().toString();
    }
}
