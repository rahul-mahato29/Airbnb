package com.project.AirBnb.services;

import com.project.AirBnb.dto.BookingDTO;
import com.project.AirBnb.dto.BookingRequest;

public interface BookingService {
    BookingDTO initialiseBooking(BookingRequest bookingRequest);
}
