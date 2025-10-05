package com.project.AirBnb.dto;

import com.project.AirBnb.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
}
