package rut.miit.food.express.repository;

import rut.miit.food.express.entity.DishCategory;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;

import java.util.List;
import java.util.Optional;

public interface DishCategoryRepository extends
        CreateRepository<DishCategory, Integer>, ReadRepository<DishCategory, Integer> {

    List<DishCategory> findByRestaurantId(Integer restaurantId);
    Optional<DishCategory> findByName(String name);
}
