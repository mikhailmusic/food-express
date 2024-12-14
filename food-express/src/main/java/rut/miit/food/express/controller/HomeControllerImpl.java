package rut.miit.food.express.controller;

import food.express.contracts.controller.HomeController;
import food.express.contracts.viewmodel.restaurant.RestaurantRatingListViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantRatingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rut.miit.food.express.service.RestaurantService;

import java.util.List;

@Controller
public class HomeControllerImpl extends BaseControllerImpl implements HomeController {
    private  RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
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


}
