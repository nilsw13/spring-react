package com.nilsw13.springreact.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



/**
 * This class manages the tenant context for each HTTP request.
 * It uses ThreadLocal to ensure isolation between different requests,
 * even when they are processed simultaneously.
 */



@Slf4j
@Component
public class TenantContext {

    /**
     * ThreadLocal stores a separate copy of the variable for each thread.
     * This ensures that even when multiple requests are processed simultaneously,
     * each will have its own isolated tenantId.
     */

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    /**
     * Define tenantId for the current thread.
     * @param tenantId
     */

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to : {} ", tenantId);
        CURRENT_TENANT.set(tenantId);

    }

    /**
     * Gets the tenantId for the current thread.
     * @return the tenant id or null if none is defined.
     */



    public static String getTenantID(){
        String tenantId = CURRENT_TENANT.get();
        log.debug("Getting tenantId : {} ", tenantId);
        return tenantId;

    }

    /**
     * Removes the tenant ID from the current thread.
     * Important to prevent memory leaks in web applications.
     */
    public static void clear() {
        log.debug("Clearing tenant ID");
        CURRENT_TENANT.remove();
    }

}
