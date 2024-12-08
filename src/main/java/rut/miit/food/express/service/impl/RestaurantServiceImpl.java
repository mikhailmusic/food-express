package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.dto.restaurant.RestaurantAddDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.dto.restaurant.RestaurantRatingDto;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.RestaurantNotFoundException;
import rut.miit.food.express.repository.RestaurantRepository;
import rut.miit.food.express.service.RestaurantService;
import rut.miit.food.express.util.PaginationHelper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Integer registerRestaurant(RestaurantAddDto dto) {
        Set<String> existingNames = restaurantRepository.findAllNames();

        Restaurant restaurant = new Restaurant(dto.name(), dto.address(), dto.description(), dto.phoneNumber(), dto.openTime(), dto.closeTime(), dto.minOrderAmount(), existingNames);
        return restaurantRepository.save(restaurant).getId();
    }

    @Override
    public void changeRestaurantInfo(RestaurantDto dto) {
        Set<String> existingNames = restaurantRepository.findAllNames();
        Restaurant restaurant = restaurantRepository.findById(dto.id()).orElseThrow(() -> new RestaurantNotFoundException(dto.id()));

        restaurant.setName(dto.name(), existingNames);
        restaurant.setAddress(dto.address());
        restaurant.setDescription(dto.description());
        restaurant.setPhoneNumber(dto.phoneNumber());
        restaurant.setOpenTime(dto.openTime());
        restaurant.setCloseTime(dto.closeTime());
        restaurant.setMinOrderAmount(dto.minOrderAmount());
        restaurantRepository.save(restaurant);

    }

    @Override
    public RestaurantDto getRestaurantDetails(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        return toDto(restaurant);
    }

    @Override
    @Cacheable("restaurants")
    public PageWrapper<RestaurantDto> availableRestaurants(String searchQuery, int page, int size) {
        List<RestaurantDto> restaurantDtos = restaurantRepository.findByNameContaining(searchQuery).stream()
                .filter(restaurant -> restaurant.checkIsOpenNow()).map(this::toDto).toList();

        return PaginationHelper.getPage(restaurantDtos, page, size);
    }

    @Override
    public List<RestaurantDto> availableRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().filter(restaurant -> restaurant.checkIsOpenNow()).map(this::toDto).toList();
    }

    @Override
    @Cacheable("top-restaurants")
    public List<RestaurantRatingDto> ratingRestaurants() {
        List<RestaurantRatingDto> dtoList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAllWithOrders()) {   // EAGER - беру все сразу - 1 запрос

            int orderCount = 0;
            int reviewCount = 0;
            float totalRating = 0;
            for (Order order : restaurant.getOrders()) {
                if (order.getStatus() == OrderStatus.DELIVERED) {
                    orderCount++;

                    if (order.getReview() != null) {
                        reviewCount++;
                        totalRating += order.getReview().getRating();
                    }
                }
            }
            if (reviewCount == 0) continue;
            float averageRating = totalRating / reviewCount;
            RestaurantRatingDto dto = new RestaurantRatingDto(restaurant.getId(), restaurant.getName(), orderCount,
                    reviewCount, Math.round(averageRating * 10) / 10.0f);
            dtoList.add(dto);
        }
        return dtoList.stream().sorted(Comparator.comparingDouble(RestaurantRatingDto::averageRating).reversed())
                .limit(5).collect(Collectors.toList());
    }

    private RestaurantDto toDto(Restaurant rest) {
        if (rest == null) {
            return null;
        }
        return new RestaurantDto(rest.getId(), rest.getName(), rest.getAddress(),
                rest.getDescription(), rest.getPhoneNumber(), rest.getOpenTime(),
                rest.getCloseTime(), rest.getMinOrderAmount()
        );
    }

}
