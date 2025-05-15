package com.project.AirBnb.services;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.entities.Hotel;

public interface HotelService {
    HotelDTO createNewHotel(HotelDTO hotelDTO);
    HotelDTO getHotelById(Long id);
}
