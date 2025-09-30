package com.project.AirBnb.strategy.Pricing;

import com.project.AirBnb.entities.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
