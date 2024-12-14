package rut.miit.food.express.service;

import rut.miit.food.express.dto.category.CategoryAddDto;
import rut.miit.food.express.dto.category.CategoryDto;

import java.util.List;

public interface DishCategoryService {
    void addCategory(CategoryAddDto dto);
    List<CategoryDto> getAllCategories();

}
