package com.project.AirBnb.services;

import com.project.AirBnb.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    RoomDTO createNewRoom(Long hotelId, RoomDTO roomDTO);

    RoomDTO getRoomById(Long roomId);

    List<RoomDTO> getAllRoomInHotel(Long hotelId);

    RoomDTO updateRoomById(Long roomId, RoomDTO roomDTO);

    void deleteRoomById(Long roomId);
}
