package com.project.AirBnb.dto;

import com.project.AirBnb.entities.HotelContactInfo;

public class HotelDTO {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private Boolean isActive;
}
