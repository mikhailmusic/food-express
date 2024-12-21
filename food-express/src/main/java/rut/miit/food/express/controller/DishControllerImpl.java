package rut.miit.food.express.controller;

import food.express.contracts.controller.DishController;
import food.express.contracts.form.DishSearchForm;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import food.express.contracts.viewmodel.dish.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.dto.dish.DishDto;
import rut.miit.food.express.service.DishCategoryService;
import rut.miit.food.express.service.DishService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishControllerImpl extends BaseControllerImpl implements DishController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
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
    public String dishDetails(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("User '{}' is viewing details for dish with ID: {}", getUsername(principal), id);
        DishDto dto = dishService.getDishDetails(id);
        DishInfoViewModel viewModel = new DishInfoViewModel(
                createBaseViewModel(dto.name()),
                new DishViewModel(dto.id(), dto.name(), dto.price(), dto.weight(), dto.calories(), dto.imageURL(), dto.isVisible()),
                dto.description(), dto.categoryName(), dto.restaurantName(), dto.restaurantId()
        );
        model.addAttribute("model", viewModel);
        return "dish-details";

    }

    @Override
    @GetMapping
    public String listDishes(@ModelAttribute("form") DishSearchForm form, Model model, Principal principal) {
        LOG.info("User '{}' is searching for dishes: search term: '{}', category: '{}', page: {}, size: {}",
                getUsername(principal), form.searchTerm(), form.categoryId(), form.page(), form.size());
        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 12;
        boolean enableDish = form.enableDish() != null ? form.enableDish() : true;
        form = new DishSearchForm(searchTerm, form.categoryId(), enableDish, page, size);

        PageWrapper<DishDto> wrapper = dishService.getDishes(searchTerm, form.categoryId(), enableDish, page, size);
        List<DishViewModel> dishViewModels = wrapper.content().stream()
                .map(dto -> new DishViewModel(dto.id(), dto.name(), dto.price(), dto.weight(), dto.calories(), dto.imageURL(), dto.isVisible())).toList();
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();

        DishListViewModel viewModel = new DishListViewModel(
                createBaseViewModel("Блюда"), dishViewModels, categories, wrapper.totalPages()
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "dish-list";
    }

    @Override
    @GetMapping("/popular")
    public String popular(Model model, Principal principal) {
        LOG.info("User '{}' is viewing popular dishes", getUsername(principal));
        List<DishByCategoryViewModel> dishViewModels = new ArrayList<>();
        for (DishByCategoryDto dto : dishService.popularDish()) {
            dishViewModels.add(new DishByCategoryViewModel(dto.id(), dto.name(),
                    dto.dishes().stream().map(dishDto -> new DishViewModel(dishDto.id(), dishDto.name(), dishDto.price(), dishDto.weight(), dishDto.calories(), dishDto.imageURL(), dishDto.isVisible())).toList(),
                    dto.count()));
        }
        DishTopViewModel viewModel = new DishTopViewModel(
                createBaseViewModel("Популярное"), dishViewModels
        );
        model.addAttribute("model", viewModel);
        return "popular";
    }
}
