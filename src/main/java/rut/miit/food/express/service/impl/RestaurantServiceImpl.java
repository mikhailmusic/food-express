package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.restaurant.RestaurantAddDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.dto.restaurant.RestaurantRatingDto;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.entity.Review;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.NotFoundException;
import rut.miit.food.express.exception.ValidationException;
import rut.miit.food.express.repository.RestaurantRepository;
import rut.miit.food.express.service.RestaurantService;

import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Integer registerRestaurant(RestaurantAddDto dto) {
        // TODO: телефон, адрес
        if (restaurantRepository.findByName(dto.name()).isEmpty()) {
            Restaurant restaurant = new Restaurant(dto.name(), dto.address(), dto.description(), dto.phoneNumber(), dto.openTime(), dto.closeTime(), dto.minOrderAmount());
            return restaurantRepository.save(restaurant).getId();
        } else {
            throw new ValidationException("Restaurant with this name already exists");
        }
    }

    @Override
    public void changeRestaurantInfo(RestaurantDto dto) {
        // TODO: телефон, адрес
        Restaurant restaurant = restaurantRepository.findById(dto.id()).orElseThrow(() -> new NotFoundException("Restaurant Not Found: " + dto.id()));
        Optional<Restaurant> restaurantEqualsName = restaurantRepository.findByName(dto.name());
        if (restaurantEqualsName.isEmpty() || restaurantEqualsName.get().getId().equals(dto.id())) {
            restaurant.setName(dto.name());
            restaurant.setAddress(dto.address());
            restaurant.setDescription(dto.description());
            restaurant.setPhoneNumber(dto.phoneNumber());
            restaurant.setOpenTime(dto.openTime());
            restaurant.setCloseTime(dto.closeTime());
            restaurant.setMinOrderAmount(dto.minOrderAmount());
            restaurantRepository.save(restaurant);
        } else {
            throw new ValidationException("Restaurant with this name already exists");
        }
    }

    @Override
    public RestaurantDto getRestaurantDetails(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant Not Found: " + id));
        return toDto(restaurant);
    }

    @Override
    public Page<RestaurantDto> availableRestaurants(String searchQuery, int page, int size) {
        List<RestaurantDto> restaurantDtos = restaurantRepository.findByNameContaining(searchQuery).stream()
                .filter(restaurant -> restaurant.checkIsOpenNow()).map(this::toDto).toList();

        Pageable pageable = PageRequest.of(page - 1, size);
        int start = (page - 1) * size;
        int end = Math.min(start + size, restaurantDtos.size());

        if (start >= restaurantDtos.size()) {
            return new PageImpl<>(List.of(), pageable, restaurantDtos.size());
        }

        List<RestaurantDto> pageContent = restaurantDtos.subList(start, end);
        return new PageImpl<>(pageContent, pageable, restaurantDtos.size());
    }

    @Override
    public List<RestaurantDto> availableRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().filter(restaurant -> restaurant.checkIsOpenNow()).map(this::toDto).toList();
    }

    @Override
    public List<RestaurantRatingDto> ratingRestaurants() {
        List<RestaurantRatingDto> dtoList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAllWithOrders()) {        // EAGER
            List<Order> orders = restaurant.getOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.DELIVERED).toList();
            float averageRating = (float) orders.stream().map(Order::getReview).filter(Objects::nonNull).mapToInt(Review::getRating)
                    .average().orElse(0.0);
            int countOrder = orders.size();
            int countReview = (int) orders.stream().map(Order::getReview).filter(Objects::nonNull).count();
            if (countReview < 1) continue;
            RestaurantRatingDto dto = new RestaurantRatingDto(restaurant.getId(), restaurant.getName(), countOrder, countReview, Math.round(averageRating * 10) / 10.0f);
            dtoList.add(dto);
        }
        return dtoList.stream().sorted(Comparator.comparingDouble(RestaurantRatingDto::averageRating).reversed()).limit(5).toList();
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
