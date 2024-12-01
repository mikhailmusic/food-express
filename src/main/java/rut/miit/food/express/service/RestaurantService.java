package rut.miit.food.express.service;

import org.springframework.data.domain.Page;
import rut.miit.food.express.dto.restaurant.RestaurantAddDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.dto.restaurant.RestaurantRatingDto;

import java.util.List;

public interface RestaurantService {
    Integer registerRestaurant(RestaurantAddDto restaurantDto);
    void changeRestaurantInfo(RestaurantDto restaurantDto);
    RestaurantDto getRestaurantDetails(Integer id);
    List<RestaurantDto> availableRestaurants();
    Page<RestaurantDto> availableRestaurants(String searchQuery, int page, int size);
    List<RestaurantRatingDto> ratingRestaurants();

//    void closeRestaurant(Integer id);
}
