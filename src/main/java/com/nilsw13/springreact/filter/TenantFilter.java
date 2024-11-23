package com.nilsw13.springreact.filter;


import com.nilsw13.springreact.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre qui intercepte toutes les requêtes HTTP pour extraire et gérer l'ID du tenant.
 * Ce filtre s'exécute avant la logique métier pour garantir que le contexte du tenant
 * est toujours disponible.
 */




@Slf4j
@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-TenantID";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            //extraction du tenantId depuis l'en-tête de la requête

            String tenantId = request.getHeader(TENANT_HEADER);
            log.debug("Received request for tenant: {}", tenantId);

            if (isPublicEndPoint(request.getRequestURI())) {
                //si l'endpoint est public, on ne vérifie pas le tenantId
                filterChain.doFilter(request, response);
                return;
            }

            //validation du tenantId

           if(tenantId == null || tenantId.isEmpty()) {
               log.error("No tenant ID found in the request headers");
               response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tenant ID is required");
                return;
           }

           // Définition du tenant dans le contexte
            TenantContext.setTenantId(tenantId);
           log.debug("Set tenant context for tenant: {}", tenantId);
            filterChain.doFilter(request, response);

        } finally {
            TenantContext.clear();
            log.debug("Cleared tenant context");
        }
    }



    /**
     * Vérifie si l'endpoint est public ou non.
     * @param uri l'URI de la requête
     * @return true si l'endpoint est public, false sinon
     */


    private boolean isPublicEndPoint(String requestUri) {
        return requestUri.contains("/public") ||
                requestUri.contains("/auth") ||
                requestUri.contains("/actuator") ||
                requestUri.contains("/error");

    }



}
