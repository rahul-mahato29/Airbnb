package com.project.AirBnb.repositories;

import com.project.AirBnb.entities.Inventory;
import com.project.AirBnb.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByRoom(Room room);
}

