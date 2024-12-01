package rut.miit.food.express.controller;

import food.express.contracts.controller.CategoryController;
import food.express.contracts.viewmodel.category.CategoryListViewModel;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.miit.food.express.service.DishCategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryControllerImpl extends BaseControllerImpl implements CategoryController {
    private DishCategoryService categoryService;

    @Autowired
    public void setCategoryService(DishCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public String listCategories(Model model) {
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map( dto -> new CategoryViewModel(dto.id(), dto.name())).toList();

        CategoryListViewModel viewModel = new CategoryListViewModel(
                createBaseViewModel("Категории блюд"),
                categories
        );
        model.addAttribute("model", viewModel);
        return "category-list";
    }

}
