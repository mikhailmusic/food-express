package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.dto.dish.DishUpdateDto;
import rut.miit.food.express.entity.Dish;
import rut.miit.food.express.entity.DishCategory;
import rut.miit.food.express.entity.OrderItem;
import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.CategoryNotFoundException;
import rut.miit.food.express.exception.DishNotFoundException;
import rut.miit.food.express.exception.RestaurantNotFoundException;
import rut.miit.food.express.repository.DishCategoryRepository;
import rut.miit.food.express.repository.DishRepository;
import rut.miit.food.express.repository.OrderRepository;
import rut.miit.food.express.repository.RestaurantRepository;
import rut.miit.food.express.service.DishService;
import rut.miit.food.express.util.PaginationHelper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishCategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, RestaurantRepository restaurantRepository, DishCategoryRepository categoryRepository, OrderRepository orderRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addDish(DishAddDto dto) {
        DishCategory category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
        Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(dto.restaurantId()));

        Dish dish = new Dish(dto.name(), dto.description(), dto.price(), dto.weight(), dto.calories(),
                dto.imageURL(), restaurant, category);
        dishRepository.save(dish);
    }

    @Override
    public void modifyDish(DishUpdateDto dto) {
        Dish dish = dishRepository.findById(dto.id()).orElseThrow(() -> new DishNotFoundException(dto.id()));
        dish.setName(dto.name());
        dish.setDescription(dto.description());
        dish.setPrice(dto.price());
        dish.setWeight(dto.weight());
        dish.setCalories(dto.calories());
        dish.setImageURL(dto.imageURL());
        if (dto.categoryId() != null) {
            DishCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
            dish.setCategory(category);
        }
        dishRepository.save(dish);

    }

    @Override
    public DishDto getDishDetails(Integer id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
        return toDto(dish);
    }

    @Override
    @Cacheable("dishes")
    public PageWrapper<DishDto> getDishes(String name, Integer categoryId, int page, int size) {
        List<DishDto> dishDtos = dishRepository.findByNameContaining(name, categoryId)
                .stream().map(this::toDto).toList();
        return PaginationHelper.getPage(dishDtos, page, size);

    }

    @Override
    public List<DishByCategoryDto> dishesByRestaurant(Integer restaurantId) {
        List<Dish> dishes = dishRepository.findByRestaurantId(restaurantId);  // EAGER default ManyToOne
        Map<DishCategory, List<Dish>> dishesByCategory = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getCategory));
        return toDtoByCategory(dishesByCategory);
    }

    @Override
    @Cacheable("popular-dishes")
    public List<DishByCategoryDto> popularDish() {
        Map<Dish, Long> dishOrderCount = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getDish, Collectors.summingLong(OrderItem::getCount)
                ));
        Map<DishCategory, List<Dish>> dishesByCategory = dishOrderCount.keySet().stream()
                .collect(Collectors.groupingBy(Dish::getCategory, Collectors.toList()));

        for (Map.Entry<DishCategory, List<Dish>> entry : dishesByCategory.entrySet()) {
            List<Dish> dishes = entry.getValue();
            dishes.sort((dish1, dish2) -> Long.compare(dishOrderCount.get(dish2), dishOrderCount.get(dish1)));
            if (dishes.size() > 4) {
                dishes.subList(4, dishes.size()).clear();
            }
        }
        return toDtoByCategory(dishesByCategory);
    }


    private DishDto toDto(Dish dish) {
        if (dish == null) {
            return null;
        }
        return new DishDto(dish.getId(), dish.getName(), dish.getDescription(),
                dish.getPrice(), dish.getWeight(), dish.getCalories(), dish.getImageURL(),
                dish.getCategory().getId(), dish.getRestaurant().getId(),
                dish.getCategory().getName(), dish.getRestaurant().getName()
        );
    }
    private List<DishByCategoryDto> toDtoByCategory(Map<DishCategory, List<Dish>> dishesByCategory) {
        List<DishByCategoryDto> dishesDtos = dishesByCategory.entrySet().stream()
                .map(entry -> {
                    List<DishDto> dtoList = entry.getValue().stream().map(this::toDto).toList();
                    return new DishByCategoryDto(entry.getKey().getId(), entry.getKey().getName(), dtoList.size(), dtoList);
                }).sorted(Comparator.comparing(DishByCategoryDto::name)).collect(Collectors.toList());
        return dishesDtos;
    }
}
