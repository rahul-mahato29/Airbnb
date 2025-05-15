package com.project.AirBnb.services;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.exceptions.ResourceNotFoundException;
import com.project.AirBnb.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDTO createNewHotel(HotelDTO hotelDTO) {
        log.info("Creating a new hotel with name : {}", hotelDTO.getName());
        Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
        hotel.setIsActive(false);    //initially
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with id : {}", hotelDTO.getId());
        return modelMapper.map(hotel, HotelDTO.class);   //converting back to DTO, because we have to return DTO to user
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting the hotel with Id : {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        return modelMapper.map(hotel, HotelDTO.class);
    }
}
