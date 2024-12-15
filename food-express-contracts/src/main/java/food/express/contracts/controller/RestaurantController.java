package food.express.contracts.controller;

import food.express.contracts.form.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/restaurants")
public interface RestaurantController extends BaseController {

    @GetMapping
    String availableRestaurants(@ModelAttribute("form") RestaurantSearchForm form, Model model, Principal principal);

    @GetMapping("/{id}")
    String restaurantDetails(@PathVariable Integer id, @ModelAttribute("form") DishFilterForm form, Model model, Principal principal);

    @GetMapping("/{id}/reviews")
    String restaurantWithReviews(@PathVariable Integer id, Model model, Principal principal);

}
