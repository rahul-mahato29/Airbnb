package com.project.AirBnb.dto;

import com.project.AirBnb.entities.Hotel;

import java.math.BigDecimal;

public class RoomDTO {
    private Long id;
    private Hotel hotel;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
