package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.BookingDTO;
import com.project.AirBnb.dto.BookingRequest;
import com.project.AirBnb.dto.GuestDTO;
import com.project.AirBnb.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDTO> initialiseBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDTO> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDTO> guestDTOList) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDTOList));
    }

    @PostMapping("/{bookingId}/payment")
    public ResponseEntity<Map<String, String >> initiatePayment(@PathVariable Long bookingId) {
        String sessionUrl = bookingService.initialisePayment(bookingId);
        return ResponseEntity.ok(Map.of("sessionUrl", sessionUrl));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
