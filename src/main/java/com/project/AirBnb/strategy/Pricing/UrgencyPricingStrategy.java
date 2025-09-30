package com.project.AirBnb.strategy.Pricing;

import com.project.AirBnb.entities.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        LocalDate today = LocalDate.now();
        if(!inventory.getDate().isBefore(today) && !inventory.getDate().isBefore(today.plusDays(7))) {
            price = price.multiply(BigDecimal.valueOf(1.5));
        }

        return price;
    }
}
