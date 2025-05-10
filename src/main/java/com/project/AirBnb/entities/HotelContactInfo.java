package com.project.AirBnb.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class HotelContactInfo {
    private String address;
    private String phoneNumber;
    private String location;
    private String email;
}
