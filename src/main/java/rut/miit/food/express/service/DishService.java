package rut.miit.food.express.service;

import rut.miit.food.express.dto.PageWrapper;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.dto.dish.DishUpdateDto;

import java.util.List;


public interface DishService {
    void addDish(DishAddDto dishDto);
    void modifyDish(DishUpdateDto dishDto);
    DishDto getDishDetails(Integer id);
    PageWrapper<DishDto> getDishes(String name, Integer categoryId, int page, int size);
    List<DishByCategoryDto> dishesByRestaurant(Integer restaurantId);
    List<DishByCategoryDto> popularDish();

//    void updateDishAvailability(Integer id);
}
