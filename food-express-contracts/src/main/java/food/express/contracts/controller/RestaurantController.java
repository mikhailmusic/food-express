package food.express.contracts.controller;

import food.express.contracts.form.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurants")
public interface RestaurantController extends BaseController {

    @GetMapping
    String availableRestaurants(@ModelAttribute("form") RestaurantSearchForm form, Model model);

    @GetMapping("/{id}")
    String restaurantDetails(@PathVariable Integer id, @ModelAttribute("form") DishFilterForm form, Model model);

    @GetMapping("/{id}/reviews")
    String restaurantWithReviews(@PathVariable Integer id, Model model);

}
