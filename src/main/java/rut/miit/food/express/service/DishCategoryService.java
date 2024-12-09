package rut.miit.food.express.service;

import rut.miit.food.express.dto.category.CategoryDto;

import java.util.List;

public interface DishCategoryService {
    void addCategory(String name);
    List<CategoryDto> getAllCategories();

}
