package com.project.AirBnb.services;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.dto.HotelSearchRequest;
import com.project.AirBnb.entities.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
    Page<HotelDTO> searchHotels(HotelSearchRequest hotelSearchRequest);
}
