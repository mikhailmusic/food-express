package rut.miit.food.express.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.category.CategoryDto;
import rut.miit.food.express.entity.DishCategory;
import rut.miit.food.express.repository.DishCategoryRepository;
import rut.miit.food.express.service.DishCategoryService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {
    private final DishCategoryRepository categoryRepository;

    public DishCategoryServiceImpl(DishCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void addCategory(String name) {
        Set<String> existingNames = categoryRepository.findAllNames();
        DishCategory category = new DishCategory(name, existingNames);
        categoryRepository.save(category);

    }

    @Override
    public List<CategoryDto> getCategoriesByRestaurant(Integer restaurantId) {
        List<DishCategory> categories = categoryRepository.findByRestaurantId(restaurantId);
        return categories.stream().map(c -> new CategoryDto(c.getId(), c.getName())).toList();
    }

    @Override
    @Cacheable("categories")
    public List<CategoryDto> getAllCategories() {
        List<DishCategory> categories = categoryRepository.findAll();
        return categories.stream().map(c -> new CategoryDto(c.getId(), c.getName())).collect(Collectors.toList());
    }

}
