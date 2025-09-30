package com.project.AirBnb.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Enabled
@Data
public class HotelMinPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;  //cheapest room price on a particular day

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
