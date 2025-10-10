package com.project.AirBnb.services;

import com.project.AirBnb.entities.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
