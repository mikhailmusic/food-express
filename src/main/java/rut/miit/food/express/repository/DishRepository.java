package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Dish;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.List;

public interface DishRepository extends
        CreateRepository<Dish, Integer>, ReadRepository<Dish, Integer>, UpdateRepository<Dish, Integer> {

    List<Dish> findByNameContaining(String namePart);

}

