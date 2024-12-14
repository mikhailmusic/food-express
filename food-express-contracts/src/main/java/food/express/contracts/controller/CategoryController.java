package food.express.contracts.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
public interface CategoryController extends BaseController {

    @GetMapping
    String listCategories(Model model);

}
