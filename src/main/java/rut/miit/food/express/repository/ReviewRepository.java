package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Review;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.List;

public interface ReviewRepository extends
        CreateRepository<Review, Integer>, ReadRepository<Review, Integer>, UpdateRepository<Review, Integer> {

    List<Review> findAllByRestaurantId(Integer restaurantId);

}

