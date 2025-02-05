package com.nilsw13.springreact.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CustomUserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private String password;
    private String tenantId;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private CustomUserPrincipal(Long id, String email, String tenantId,
                                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.tenantId = tenantId;
        this.authorities = authorities;
    }

    /**
     * Create a CustomUserPrincipal from a User and OAuth2 attributes
     */
    public static CustomUserPrincipal create(User user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getTenantId(),
                authorities
        );
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    /**
     * Define the OAuth2 attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     *  OAuth2User implementation
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    /**
     * UserDetails implementation
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
