package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends
        CreateRepository<Restaurant, Integer>, ReadRepository<Restaurant, Integer>, UpdateRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);
    List<Restaurant> findByNameContaining(String name);
    List<Restaurant> findAllWithOrders();

}

