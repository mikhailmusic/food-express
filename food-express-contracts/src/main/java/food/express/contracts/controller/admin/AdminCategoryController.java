package food.express.contracts.controller.admin;

import food.express.contracts.controller.BaseController;
import food.express.contracts.form.CategoryCreateForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/categories")
public interface AdminCategoryController extends BaseController {

    @PostMapping("/add")
    String addCategory(@Valid @ModelAttribute("form") CategoryCreateForm form, BindingResult bindingResult, Model model);

    @GetMapping("/add")
    String addCategory(Model model);

}
