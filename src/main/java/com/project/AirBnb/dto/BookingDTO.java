package com.project.AirBnb.dto;

import com.project.AirBnb.entities.Guest;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.entities.Room;
import com.project.AirBnb.entities.User;
import com.project.AirBnb.entities.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {

    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private BookingStatus bookingStatus;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomCount;
    private Set<GuestDTO> guests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
