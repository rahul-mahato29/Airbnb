package com.project.AirBnb.services.Impl;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.entities.Room;
import com.project.AirBnb.exceptions.ResourceNotFoundException;
import com.project.AirBnb.repositories.HotelRepository;
import com.project.AirBnb.repositories.RoomRepository;
import com.project.AirBnb.services.HotelService;
import com.project.AirBnb.services.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

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
    public List<HotelDTO> getAllHotel() {
        log.info("Getting all hotels");
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels
                .stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting the hotel with Id : {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotelById(Long hotelId, HotelDTO updatedDetails) {
        log.info("Updating the hotel with ID : {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        modelMapper.map(updatedDetails, hotel); //updating with new details (source --> destination)
        hotel.setId(hotelId);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long hotelId) {
        log.info("Deleting the hotel with ID : {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        //delete all future inventories for this hotel, not deleting the hotel directly only delete inventory
        for(Room room: hotel.getRoom()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID : {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        hotel.setIsActive(true);

        //Todo: create inventory for all the rooms for this hotel (Doubt)
        for(Room room: hotel.getRoom()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }
}
