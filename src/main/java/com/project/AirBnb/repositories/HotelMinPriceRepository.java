package com.project.AirBnb.repositories;

import com.project.AirBnb.dto.HotelPriceDTO;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.entities.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long > {

    @Query("""
            SELECT new com.project.AirBnb.dto.HotelPriceDTO(i.hotel, AVG(i.price))
            FROM HotelMinPrice i
            WHERE LOWER(i.hotel.city) = LOWER(:city)
                AND i.date BETWEEN :startDate AND :endDate
                AND i.hotel.isActive = true
            GROUP BY i.hotel
            HAVING COUNT(DISTINCT i.date) = :dateCount
            """)
    Page<HotelPriceDTO> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
