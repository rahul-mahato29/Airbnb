package com.project.AirBnb.services;

import com.project.AirBnb.entities.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
}
