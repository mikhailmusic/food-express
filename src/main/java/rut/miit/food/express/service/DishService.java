package rut.miit.food.express.service;

import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.dto.dish.DishUpdateDto;

import java.util.List;


public interface DishService {
    void addDish(DishAddDto dishDto);
    void modifyDish(DishUpdateDto dishDto);
    DishDto getDishDetails(Integer id);
    PageWrapper<DishDto> getDishes(String name, Integer categoryId, boolean isEnable, int page, int size);
    List<DishByCategoryDto> dishesByRestaurant(Integer restaurantId, boolean isEnable);
    List<DishByCategoryDto> popularDish();

}
