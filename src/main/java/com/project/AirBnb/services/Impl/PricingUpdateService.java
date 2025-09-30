package com.project.AirBnb.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingUpdateService {

    // Scheduler to update the inventory & HotelMinPrice tables every hours

//    private final HotelRepository hotelRepository;
//    private final InventoryRepository inventoryRepository;
//    private final HotelMinPriceRepository hotelMinPriceRepository;
//
//    public void updatePrices() {
//        int page = 0;
//        int batchsize = 100;
//
//        while(true) {
//            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchsize));
//            if(hotelPage.isEmpty()) {
//                break;
//            }
//
//            //start from here
//
//            page++;
//        }
//    }
}
