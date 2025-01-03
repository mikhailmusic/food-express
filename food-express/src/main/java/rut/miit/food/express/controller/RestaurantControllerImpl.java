package rut.miit.food.express.controller;

import food.express.contracts.controller.RestaurantController;


import food.express.contracts.form.DishFilterForm;
import food.express.contracts.form.RestaurantSearchForm;
import food.express.contracts.viewmodel.dish.DishByCategoryViewModel;
import food.express.contracts.viewmodel.dish.DishViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantInfoViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantListViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantReviewViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantViewModel;
import food.express.contracts.viewmodel.review.ReviewViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.dto.dish.DishByCategoryDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.service.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantControllerImpl extends BaseControllerImpl implements RestaurantController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private RestaurantService restaurantService;
    private DishService dishService;
    private ReviewService reviewService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @GetMapping
    public String availableRestaurants(@Valid @ModelAttribute("form") RestaurantSearchForm form, Model model, Principal principal) {
        LOG.info("User '{}' is searching for available restaurants: search term: '{}', page: {}, size: {}",
                getUsername(principal), form.searchTerm(), form.page(), form.size());

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 12;
        form = new RestaurantSearchForm(searchTerm, page, size);

        PageWrapper<RestaurantDto> wrapper = restaurantService.availableRestaurants(searchTerm, page, size);
        List<RestaurantViewModel> restaurantViewModels = wrapper.content().stream().map(this::toViewModel).toList();
        RestaurantListViewModel viewModel = new RestaurantListViewModel(
                createBaseViewModel("Рестораны"),
                restaurantViewModels, wrapper.totalPages()
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "restaurant-list";
    }


    @Override
    @GetMapping("/{id}")
    public String restaurantDetails(@PathVariable Integer id, @Valid @ModelAttribute("form") DishFilterForm form, Model model, Principal principal) {
        LOG.info("User '{}' is viewing details for restaurant with ID: {}", getUsername(principal), id);

        boolean enableDish = form.enableDish() != null ? form.enableDish() : true;
        form = new DishFilterForm(enableDish);

        RestaurantDto restaurantDto = restaurantService.getRestaurantDetails(id);
        List<DishByCategoryViewModel> dishViewModels = new ArrayList<>();
        for (DishByCategoryDto dto : dishService.dishesByRestaurant(id, enableDish)) {
            dishViewModels.add(new DishByCategoryViewModel(dto.id(), dto.name(),
                    dto.dishes().stream().map(dishDto -> new DishViewModel(dishDto.id(), dishDto.name(), dishDto.price(),
                    dishDto.weight(), dishDto.calories(), dishDto.imageURL(), dishDto.isVisible())).toList(),
                    dto.count()));
        }
        RestaurantInfoViewModel viewModel = new RestaurantInfoViewModel(
                createBaseViewModel(restaurantDto.name()),
                toViewModel(restaurantDto), dishViewModels
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "restaurant-details";
    }

    @Override
    @GetMapping("/{id}/reviews")
    public String restaurantWithReviews(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("User '{}' is viewing reviews for restaurant with ID: {}", getUsername(principal), id);

        RestaurantDto restaurantDto = restaurantService.getRestaurantDetails(id);
        List<ReviewViewModel> reviewViewModels = reviewService.reviewsForRestaurant(id)
                .stream().map(dto -> new ReviewViewModel(dto.rating(), dto.text(), dto.date(), dto.userFirstName())).toList();
        RestaurantReviewViewModel viewModel = new RestaurantReviewViewModel(
                createBaseViewModel("Отзывы ресторана"),
                toViewModel(restaurantDto), reviewViewModels);

        model.addAttribute("model", viewModel);
        return "reviews-restaurant";
    }

    private RestaurantViewModel toViewModel(RestaurantDto dto) {
        if (dto == null) {
            return null;
        }
        return new RestaurantViewModel(dto.id(), dto.name(), dto.address(), dto.description(), dto.phoneNumber(),
                dto.openTime(), dto.closeTime(), dto.minOrderAmount()
        );
    }
}
