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
            //Extract tenantId from the request headers

            String tenantId = request.getHeader(TENANT_HEADER);
            log.debug("Received request for tenant: {}", tenantId);

            if (isPublicEndPoint(request.getRequestURI())) {
                //if the endpoint is public, no need to check for tenantId
                filterChain.doFilter(request, response);
                return;
            }

            //tenantId is required for all other endpoints

           if(tenantId == null || tenantId.isEmpty()) {
               log.error("No tenant ID found in the request headers");
               response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tenant ID is required");
                return;
           }

           // Define the tenantId in the Context
            TenantContext.setTenantId(tenantId);
           log.debug("Set tenant context for tenant: {}", tenantId);
            filterChain.doFilter(request, response);

        } finally {
            TenantContext.clear();
            log.debug("Cleared tenant context");
        }
    }



    /**
     *Chekc if the endpoint is public
     * @param uri L'url de la requête
     * @return true if the endpoint is public
     */


    private boolean isPublicEndPoint(String requestUri) {
        return requestUri.contains("/public") ||
                requestUri.contains("/auth") ||
                requestUri.contains("/actuator") ||
                requestUri.contains("/error") ||
                requestUri.contains("/test");

    }



}
