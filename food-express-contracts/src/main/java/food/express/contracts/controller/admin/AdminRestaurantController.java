package food.express.contracts.controller.admin;

import food.express.contracts.controller.BaseController;
import food.express.contracts.form.RestaurantCreateForm;
import food.express.contracts.form.RestaurantEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin/restaurants")
public interface AdminRestaurantController extends BaseController {

    @PostMapping("/add")
    String addRestaurant(@Valid @ModelAttribute("form") RestaurantCreateForm form, BindingResult result, Model model, Principal principal);

    @PostMapping("/{id}/edit")
    String editRestaurant(@PathVariable Integer id, @Valid @ModelAttribute("form") RestaurantEditForm form, BindingResult result, Model model, Principal principal);

    @GetMapping("/{id}/edit")
    String editRestaurant(@PathVariable Integer id, Model model, Principal principal);

    @GetMapping("/add")
    String addRestaurant(Model model, Principal principal);

}
