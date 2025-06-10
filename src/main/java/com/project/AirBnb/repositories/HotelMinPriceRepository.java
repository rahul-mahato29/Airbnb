package com.project.AirBnb.repositories;

import com.project.AirBnb.entities.HotelMinPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long > {
}
