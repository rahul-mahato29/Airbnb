package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.BookingDTO;
import com.project.AirBnb.dto.BookingRequest;
import com.project.AirBnb.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/booking")
@Slf4j
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDTO> initialiseBooking(@RequestBody BookingRequest bookingRequest) {`
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }
}
