package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.RoomDTO;
import com.project.AirBnb.services.RoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/hotels/{hotelId}/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createNewRoom(@PathVariable Long hotelId,
                                                 @RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomService.createNewRoom(hotelId, roomDTO);
        return new ResponseEntity<>(room,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRoomInHotel(@PathVariable Long hotelId) {
        List<RoomDTO> rooms = roomService.getAllRoomInHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping(path = "/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {
        RoomDTO room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @PutMapping(path = "/{roomId}")
    public ResponseEntity<RoomDTO> updateRoomById(@PathVariable Long roomId,
                                                  @RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomService.updateRoomById(roomId, roomDTO);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping(path = "/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}
