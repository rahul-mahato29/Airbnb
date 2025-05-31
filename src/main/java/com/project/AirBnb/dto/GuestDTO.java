package com.project.AirBnb.dto;

import com.project.AirBnb.entities.User;
import com.project.AirBnb.entities.enums.Gender;
import lombok.Data;

@Data
public class GuestDTO {

    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private User user;
}
