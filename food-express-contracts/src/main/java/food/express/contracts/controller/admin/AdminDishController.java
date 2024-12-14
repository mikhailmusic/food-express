package food.express.contracts.controller.admin;

import food.express.contracts.controller.BaseController;
import food.express.contracts.form.DishCreateForm;
import food.express.contracts.form.DishEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/dishes")
public interface AdminDishController extends BaseController {

    @GetMapping("/{restaurantId}/add-dish")
    String addDish(@PathVariable Integer restaurantId, Model model);

    @PostMapping("/{restaurantId}/add-dish")
    String addDish(@PathVariable Integer restaurantId, @Valid @ModelAttribute("form") DishCreateForm form, BindingResult result, Model model);

    @PostMapping("/{id}/edit")
    String editDish(@PathVariable Integer id, @Valid @ModelAttribute("form") DishEditForm form, BindingResult result, Model model);

    @GetMapping("/{id}/edit")
    String editDish(@PathVariable Integer id, Model model);

}
