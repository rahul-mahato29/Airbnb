package com.project.AirBnb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "inventory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_hotel_room_date",
                        columnNames = {"hotel_id", "room_id", "date"}
                )
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; //basePrice * surgeFactor

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean isClosed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
