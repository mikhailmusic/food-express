package rut.miit.food.express.controller;

import food.express.contracts.controller.HomeController;
import food.express.contracts.viewmodel.dish.DishByCategoryViewModel;
import food.express.contracts.viewmodel.dish.DishTopViewModel;
import food.express.contracts.viewmodel.dish.DishViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantRatingListViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantRatingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.service.DishService;
import rut.miit.food.express.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeControllerImpl extends BaseControllerImpl implements HomeController {
    private  RestaurantService restaurantService;
    private DishService dishService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    @GetMapping
    public String home(Model model) {
        List<RestaurantRatingViewModel> ratingViewModels = restaurantService.ratingRestaurants()
                .stream().map(dto -> new RestaurantRatingViewModel(dto.id(), dto.name(), dto.countOrder(), dto.countReview(), dto.averageRating())).toList();

        RestaurantRatingListViewModel viewModel = new RestaurantRatingListViewModel(
                createBaseViewModel("Food-Express"),
                ratingViewModels
        );
        model.addAttribute("model", viewModel);
        return "home";
    }

    @Override
    @GetMapping("/popular")
    public String popular(Model model) {
        List<DishByCategoryViewModel> dishViewModels = new ArrayList<>();
        for (DishByCategoryDto dto : dishService.popularDish()) {
            dishViewModels.add(new DishByCategoryViewModel(dto.id(), dto.name(),
                    dto.dishes().stream().map(dishDto -> new DishViewModel(dishDto.id(), dishDto.name(), dishDto.price(), dishDto.weight(), dishDto.calories(), dishDto.imageURL(), dishDto.isVisible())).toList(),
                    dto.count()));
        }
        DishTopViewModel viewModel = new DishTopViewModel(
                createBaseViewModel("Популярное"),
                dishViewModels
        );
        model.addAttribute("model", viewModel);
        return "popular";
    }

}
