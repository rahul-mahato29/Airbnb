package com.project.AirBnb.services;

import com.project.AirBnb.dto.BookingDTO;
import com.project.AirBnb.dto.BookingRequest;
import com.project.AirBnb.dto.GuestDTO;
import com.stripe.model.Event;

import java.util.List;

public interface BookingService {
    BookingDTO initialiseBooking(BookingRequest bookingRequest);

    BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDTOList);

    String initialisePayment(Long bookingId);

    void capturePayments(Event event);
}
