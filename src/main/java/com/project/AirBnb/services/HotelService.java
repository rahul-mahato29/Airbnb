package com.project.AirBnb.services;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.dto.HotelInfoDTO;

import java.util.List;

public interface HotelService {
    HotelDTO createNewHotel(HotelDTO hotelDTO);

    List<HotelDTO> getAllHotel();

    HotelDTO getHotelById(Long id);

    HotelDTO updateHotelById(Long hotelId, HotelDTO hotelDTO);

    void deleteHotelById(Long hotelId);

    void activateHotel(Long hotelId);

    HotelInfoDTO getHotelInfoById(Long hotelId);
}
