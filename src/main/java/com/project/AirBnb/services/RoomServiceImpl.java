package com.project.AirBnb.services;

import com.project.AirBnb.dto.RoomDTO;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.entities.Room;
import com.project.AirBnb.exceptions.ResourceNotFoundException;
import com.project.AirBnb.repositories.HotelRepository;
import com.project.AirBnb.repositories.RoomRepository;
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
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDTO createNewRoom(Long hotelId, RoomDTO roomDTO) {
        log.info("Creating a new room in hotel with ID : {}", roomDTO.getId());
        //checking hotel exists or not, then will proceed
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        //once the room is created & hotel is active, create the inventory of this room
        if(hotel.getIsActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Getting room with ID : {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> getAllRoomInHotel(Long hotelId) {
        log.info("Getting All rooms of hotel with hotelID : {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        List<Room> rooms = hotel.getRoom();
        return rooms.stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDTO updateRoomById(Long roomId, RoomDTO updatedDetails) {
        log.info("Updating the room with ID: {}",roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        modelMapper.map(updatedDetails, room);
        room = roomRepository.save(room);
        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the hotel with ID : {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));

        //Issue: facing issue in deleting the room (will check later)***
        roomRepository.deleteById(roomId);
        //delete all future inventory for this room
        inventoryService.deleteFutureInventories(room);
    }
}
