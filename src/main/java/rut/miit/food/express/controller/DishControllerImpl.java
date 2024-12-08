package rut.miit.food.express.controller;

import food.express.contracts.controller.DishController;
import food.express.contracts.form.DishSearchForm;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import food.express.contracts.viewmodel.dish.DishInfoViewModel;
import food.express.contracts.viewmodel.dish.DishListViewModel;
import food.express.contracts.viewmodel.dish.DishViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.PageWrapper;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.service.DishCategoryService;
import rut.miit.food.express.service.DishService;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishControllerImpl extends BaseControllerImpl implements DishController {
    private DishService dishService;
    private DishCategoryService categoryService;

    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @Autowired
    public void setCategoryService(DishCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping("/{id}")
    public String dishDetails(@PathVariable Integer id, Model model) {
        DishDto dto = dishService.getDishDetails(id);
        DishInfoViewModel viewModel = new DishInfoViewModel(
                createBaseViewModel(dto.name()),
                new DishViewModel(dto.id(), dto.name(), dto.price(), dto.weight(), dto.calories(), dto.imageURL()),
                dto.description(), dto.categoryName(), dto.restaurantName(), dto.restaurantId()
        );

        model.addAttribute("model", viewModel);
        return "dish-details";

    }

    @Override
    @GetMapping
    public String listDishes(@ModelAttribute DishSearchForm form, Model model) {
        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 12;
        form = new DishSearchForm(searchTerm, form.categoryId(), page, size);

        PageWrapper<DishDto> wrapper = dishService.getDishes(searchTerm, form.categoryId(), page, size);
        List<DishViewModel> dishViewModels = wrapper.content().stream()
                .map(dto -> new DishViewModel(dto.id(), dto.name(), dto.price(), dto.weight(), dto.calories(), dto.imageURL())).toList();
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();

        DishListViewModel viewModel = new DishListViewModel(
                createBaseViewModel("Блюда"),
                dishViewModels, categories, wrapper.totalPages()
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "dish-list";
    }
}
