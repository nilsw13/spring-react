package com.nilsw13.springreact.dto;

import com.nilsw13.springreact.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private String tenantId;

    // Constructeur pour créer un DTO à partir de l'entité User
    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPicture(user.getPicture());
        dto.setTenantId(user.getTenantId());
        return dto;
    }
}
