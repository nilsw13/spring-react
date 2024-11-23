package com.nilsw13.springreact.tenant;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * Aspect qui applique automatiquement le filtre de tenant sur toutes les requêtes JPA.
 * Cet aspect garantit que toutes les requêtes à la base de données incluent
 * automatiquement la clause WHERE tenant_id = current_tenant_id.
 */




@Slf4j
@Aspect
@Component
public class TenantAspect {


    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Point de coupe qui s'applique à toutes les méthodes des repositories JPA.
     * @param joinPoint Le point de jonction de l'aspect
     */


    @Before("execution(* org.springframework.data.repository.Repository+.save*(..))")
    public void beforeSaveOperation(JoinPoint joinPoint) {
       String tenantId = TenantContext.getTenantID();
         log.debug("Applying tenantId to : {} ",
                 tenantId, joinPoint.getSignature().getName());

         if (tenantId == null) {
             org.hibernate.Session session = entityManager.unwrap(Session.class);
             session.enableFilter("tenantFilter")
                     .setParameter("tenantId", tenantId);

             log.debug("Enabled tenant filter for session : {} ", session);
         }

    }






}
