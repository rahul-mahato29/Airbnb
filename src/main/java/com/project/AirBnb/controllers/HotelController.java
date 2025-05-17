package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.services.HotelService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createNewHotel(@RequestBody HotelDTO hotelDTO){
        log.info("Attempting to create a new hotel with name : {}", hotelDTO.getName());
        HotelDTO hotel = hotelService.createNewHotel(hotelDTO);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotel(){
        List<HotelDTO> hotel = hotelService.getAllHotel();
        return ResponseEntity.ok(hotel);
    }

    @GetMapping(path = "/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long hotelId){
        HotelDTO hotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }
}
