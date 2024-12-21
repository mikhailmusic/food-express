package food.express.contracts.controller;

import food.express.contracts.form.DishSearchForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/dishes")
public interface DishController extends BaseController {

    @GetMapping("/{id}")
    String dishDetails(@PathVariable Integer id, Model model, Principal principal);

    @GetMapping
    String listDishes(@ModelAttribute("form") DishSearchForm form, Model model, Principal principal);

    @GetMapping("/popular")
    String popular(Model model, Principal principal);
}
