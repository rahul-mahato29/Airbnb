package com.project.AirBnb.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Embedded
    private HotelContactInfo contactInfo;

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "hotel")
    private List<Room> room;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
