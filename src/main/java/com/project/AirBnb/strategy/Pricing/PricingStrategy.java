package com.project.AirBnb.strategy.Pricing;

import com.project.AirBnb.entities.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
