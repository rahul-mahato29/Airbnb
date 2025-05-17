package com.project.AirBnb.dto;

import com.project.AirBnb.entities.HotelContactInfo;
import com.project.AirBnb.entities.User;
import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private User owner;
    private Boolean isActive;
}
