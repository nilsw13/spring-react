package com.nilsw13.springreact.filter;


import com.nilsw13.springreact.model.CustomUserPrincipal;
import com.nilsw13.springreact.model.User;
import com.nilsw13.springreact.repository.UserRepository;
import com.nilsw13.springreact.service.CustomOAuth2UserService;
import com.nilsw13.springreact.tenant.TenantContext;
import com.nilsw13.springreact.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String email = tokenProvider.getUserEmailFromToken(jwt);
                String tenantId = tokenProvider.getTenantIdFromToken(jwt);

                // Define the tenantId in the TenantContext
                TenantContext.setTenantId(tenantId);

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                // Créer les attributs
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("sub", user.getGoogleId());
                attributes.put("name", user.getName());
                attributes.put("email", user.getEmail());
                attributes.put("picture", user.getPicture());
                attributes.put("email_verified", true);
                attributes.put("tenant_id", user.getTenantId());

                // Créer le CustomUserPrincipal avec les attributs
                CustomUserPrincipal userPrincipal = CustomUserPrincipal.create(user, attributes);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }









    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}