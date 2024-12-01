package rut.miit.food.express.service.impl;

import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.category.CategoryDto;
import rut.miit.food.express.entity.DishCategory;
import rut.miit.food.express.exception.ValidationException;
import rut.miit.food.express.repository.DishCategoryRepository;
import rut.miit.food.express.service.DishCategoryService;

import java.util.List;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {
    private final DishCategoryRepository dishCategoryRepository;

    public DishCategoryServiceImpl(DishCategoryRepository dishCategoryRepository) {
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Override
    public void addCategory(String name) {

        if (dishCategoryRepository.findByName(name).isEmpty()) {
            DishCategory category = new DishCategory(name);
            dishCategoryRepository.save(category);
        }
        else {
            throw new ValidationException("Duplicate category name");
        }
    }

    @Override
    public List<CategoryDto> getCategoriesByRestaurant(Integer restaurantId) {
        List<DishCategory> categories = dishCategoryRepository.findByRestaurantId(restaurantId);
        return categories.stream().map(c -> new CategoryDto(c.getId(), c.getName())).toList();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<DishCategory> categories = dishCategoryRepository.findAll();
        return categories.stream().map(c -> new CategoryDto(c.getId(), c.getName())).toList();
    }

}
