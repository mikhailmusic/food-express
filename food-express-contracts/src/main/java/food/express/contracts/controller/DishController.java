package food.express.contracts.controller;

import food.express.contracts.form.DishSearchForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dishes")
public interface DishController extends BaseController {

    @GetMapping("/{id}")
    String dishDetails(@PathVariable Integer id, Model model);

    @GetMapping
    String listDishes(@ModelAttribute DishSearchForm form, Model model);

    @GetMapping("/popular")
    String popular(Model model);
}
