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
 * classe de base pour tout les entités de l'app
 * fournis tout les champs communs pour les entités comme le tenant_id et les dates de création et de modification

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
     * L'id du tenant au quel appartien l'entité. NON NULLABLE pour garantir que chaque enregistrement appartient à un tenant
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
     * Méthode appelée avant la persistance pour définir le tenant_id.
     */


    @PrePersist
    public void prePersist() {
       this.tenantId = TenantContext.getTenantID();
       this.createdAt = LocalDateTime.now();
         this.updatedAt = LocalDateTime.now();
        }


    /**
     * Méthode appelée avant la mise à jour pour actualiser updated_at.
     */

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



}








