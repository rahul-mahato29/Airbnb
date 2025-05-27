package com.project.AirBnb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate startDate; //check-in time
    private LocalDate endDate;   //check-out time
    private Integer roomCount;   //no-of-room user wants

    private Integer page=0;
    private Integer size=10;
}
