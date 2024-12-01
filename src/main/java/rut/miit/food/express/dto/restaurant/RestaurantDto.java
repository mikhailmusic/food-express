package rut.miit.food.express.dto.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;

public record RestaurantDto(
        Integer id,
        String name,
        String address,
        String description,
        String phoneNumber,
        LocalTime openTime,
        LocalTime closeTime,
        BigDecimal minOrderAmount
) {}