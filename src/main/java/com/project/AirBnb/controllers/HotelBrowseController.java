package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.HotelDTO;
import com.project.AirBnb.dto.HotelInfoDTO;
import com.project.AirBnb.dto.HotelPriceDTO;
import com.project.AirBnb.dto.HotelSearchRequest;
import com.project.AirBnb.services.HotelService;
import com.project.AirBnb.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping(path = "/search")
    public ResponseEntity<Page<HotelPriceDTO>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest) {
        var page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/{hotelId}/info")
    public ResponseEntity<HotelInfoDTO> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
