package com.nilsw13.springreact.model;


import com.nilsw13.springreact.tenant.TenantContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


/**
 * Base entity for all entities in the application that extends BaseEntity.
 * Give all entities the tenant_id, created_at and updated_at fields.

 *  **/


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Tenant id for multi-tenancy.
     * **/

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    /**
     * Call this method before persisting an entity to set the tenant_id and created_at fields.
     */


    @PrePersist
    public void prePersist() {
       this.tenantId = TenantContext.getTenantID();
       this.createdAt = LocalDateTime.now();
         this.updatedAt = LocalDateTime.now();
        }


    /**
     * Method to update the updated_at field before updating an entity.
     */

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



}








