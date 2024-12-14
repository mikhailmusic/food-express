package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminDishController;
import food.express.contracts.form.DishCreateForm;
import food.express.contracts.form.DishEditForm;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import food.express.contracts.viewmodel.dish.DishInputViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.dto.dish.DishUpdateDto;
import rut.miit.food.express.service.DishCategoryService;
import rut.miit.food.express.service.DishService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/dishes")
public class AdminDishControllerImpl extends BaseControllerImpl implements AdminDishController {
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
    @GetMapping("/{restaurantId}/add-dish")
    public String addDish(@PathVariable Integer restaurantId, Model model) {
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
        DishInputViewModel viewModel = new DishInputViewModel(
                createBaseViewModel("Добавление блюда"), categories
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new DishCreateForm("", "", BigDecimal.ONE, 1, 1, "", null, restaurantId));
        return "dish-add";
    }

    @Override
    @PostMapping("/{restaurantId}/add-dish")
    public String addDish(@PathVariable Integer restaurantId, @Valid @ModelAttribute("form") DishCreateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<CategoryViewModel> categories = categoryService.getAllCategories()
                    .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
            DishInputViewModel viewModel = new DishInputViewModel(
                    createBaseViewModel("Добавление блюда"), categories
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "dish-add";
        }
        DishAddDto dto = new DishAddDto(form.name(), form.description(), form.price(), form.weight(),
                form.calories(), form.imageURL(), form.restaurantId(), form.categoryId());
        dishService.addDish(dto);
        return "redirect:/restaurants/" + restaurantId;
    }

    @Override
    @PostMapping("/{id}/edit")
    public String editDish(@PathVariable Integer id, @Valid @ModelAttribute("form") DishEditForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<CategoryViewModel> categories = categoryService.getAllCategories()
                    .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
            DishInputViewModel viewModel = new DishInputViewModel(
                    createBaseViewModel("Изменение информации"), categories
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "restaurant-edit";
        }
        DishUpdateDto dto = new DishUpdateDto(form.id(), form.name(), form.description(), form.price(), form.weight(),
                form.calories(), form.imageURL(), form.isVisible(), form.categoryId());
        dishService.modifyDish(dto);
        return "redirect:/dishes/" + id;

    }

    @Override
    @GetMapping("/{id}/edit")
    public String editDish(@PathVariable Integer id, Model model) {
        DishDto dishDto = dishService.getDishDetails(id);
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
        DishInputViewModel viewModel = new DishInputViewModel(
                createBaseViewModel("Изменение информации"), categories
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new DishEditForm(dishDto.id(), dishDto.name(), dishDto.description(),
                dishDto.price(), dishDto.weight(), dishDto.calories(), dishDto.imageURL(), dishDto.isVisible(), dishDto.categoryId()));
        return "dish-edit";
    }

}