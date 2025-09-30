package com.project.AirBnb.services.Impl;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.dto.HotelPriceDTO;
import com.project.AirBnb.dto.HotelSearchRequest;
import com.project.AirBnb.entities.Hotel;
import com.project.AirBnb.entities.Inventory;
import com.project.AirBnb.entities.Room;
import com.project.AirBnb.repositories.HotelMinPriceRepository;
import com.project.AirBnb.repositories.InventoryRepository;
import com.project.AirBnb.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        //Inventory for 1year
        for(; !today.isAfter(endDate); today=today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .isClosed(false)
                    .build();

            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("Deleting the inventories for room with id : {}", room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDTO> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city, from {} to {}", hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());

        long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;

        //Business-Logic : 90 days
        Page<HotelPriceDTO> hotelPage = hotelMinPriceRepository.findHotelsWithAvailableInventory(
                hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate(),
                hotelSearchRequest.getRoomsCount(),
                dateCount,
                pageable
        );
//        return hotelPage.map((elements) -> modelMapper.map(elements, HotelPriceDTO.class));
        return hotelPage;
    }
}
