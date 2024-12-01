package rut.miit.food.express.dto.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;

public record RestaurantAddDto(
        String name,
        String address,
        String description,
        String phoneNumber,
        LocalTime openTime,
        LocalTime closeTime,
        BigDecimal minOrderAmount
) {}