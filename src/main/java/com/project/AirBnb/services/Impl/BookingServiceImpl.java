package com.project.AirBnb.services.Impl;

import com.project.AirBnb.dto.BookingDTO;
import com.project.AirBnb.dto.BookingRequest;
import com.project.AirBnb.entities.*;
import com.project.AirBnb.entities.enums.BookingStatus;
import com.project.AirBnb.exceptions.ResourceNotFoundException;
import com.project.AirBnb.repositories.BookingRepository;
import com.project.AirBnb.repositories.HotelRepository;
import com.project.AirBnb.repositories.InventoryRepository;
import com.project.AirBnb.repositories.RoomRepository;
import com.project.AirBnb.services.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDTO initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialising booking for hotel : {}, room : {}, date : {} to {}",bookingRequest.getHotelId(),
                bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository
                .findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+bookingRequest.getHotelId()));

        Room room = roomRepository
                .findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount()
        );

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }

        log.info("Checking : {}", bookingRequest.getRoomsCount());
        //Reserve the room/ update the booked count of inventories
        for(Inventory inventory : inventoryList) {
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        //Create/Initialise the booking
        User user = new User();
        user.setId(1l); //TODO: REMOVE DUMMY USER

        //TODO: Calculate dynamic price

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(new User())
                .roomCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }
}

