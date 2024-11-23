package com.nilsw13.springreact.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



/**
 * Cette classe gère le contexte du tenant pour chaque requête HTTP.
 * Elle utilise ThreadLocal pour garantir l'isolation entre les différentes requêtes,
 * même si elles sont traitées simultanément.
 */



@Slf4j
@Component
public class TenantContext {

    /**
     * ThreadLocal stocke une copie distincte de la variable pour chaque thread.
     * Cela garantit que même si plusieurs requêtes sont traitées simultanément,
     * chacune aura son propre tenantId isolé.
     */

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    /**
     * Définit le tenantId pour le thread actuel.
     * @param tenantId
     */

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to : {} ", tenantId);
        CURRENT_TENANT.set(tenantId);

    }

    /**
     * Récupère le tenantId pour le thread actuel.
     * @return l'id du tenant ou null si aucun n'est défini.
     */



    public static String getTenantID(){
        String tenantId = CURRENT_TENANT.get();
        log.debug("Getting tenantId : {} ", tenantId);
        return tenantId;

    }

    /**
     * Supprime l'ID du tenant du thread actuel.
     * Important pour éviter les fuites de mémoire dans les applications web.
     */
    public static void clear() {
        log.debug("Clearing tenant ID");
        CURRENT_TENANT.remove();
    }

}
