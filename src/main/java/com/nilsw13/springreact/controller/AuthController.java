package com.nilsw13.springreact.controller;


import com.nilsw13.springreact.model.CustomUserPrincipal;
import com.nilsw13.springreact.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserPrincipal principal) throws Exception {
        return ResponseEntity.ok(userService.getCurrentUser(principal.getId()));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(Map.of(
                "email", principal.getEmail(),
                "name", principal.getName(),
                "tenantId", principal.getTenantId()
        ));
    }
}
