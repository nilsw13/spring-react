package com.nilsw13.springreact.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;



    private String picture;

     @Column(name = "Google_id")
    private String googleId;

     @Column(name = "email_verified")
    private boolean emailVerified;


}
