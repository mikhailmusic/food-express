package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminCategoryController;
import food.express.contracts.form.CategoryCreateForm;
import food.express.contracts.viewmodel.CreateViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.category.CategoryAddDto;
import rut.miit.food.express.service.DishCategoryService;

import java.security.Principal;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryControllerImpl extends BaseControllerImpl implements AdminCategoryController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private DishCategoryService categoryService;

    @Autowired
    public void setCategoryService(DishCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("form") CategoryCreateForm form, BindingResult bindingResult, Model model, Principal principal) {
        LOG.info("User '{}' is attempting to add a new category: {}", principal.getName(), form.name());

        if (bindingResult.hasErrors()) {
            CreateViewModel viewModel = new CreateViewModel(
                    createBaseViewModel("Добавление категории")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "category-add";
        }
        categoryService.addCategory(new CategoryAddDto(form.name()));
        LOG.info("User '{}' successfully added category: {}", principal.getName(), form);
        return "redirect:/categories";
    }

    @Override
    @GetMapping("/add")
    public String addCategory(Model model, Principal principal) {
        LOG.info("User '{}' is accessing the add category form", principal.getName());

        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Добавление категории")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CategoryCreateForm(""));
        return "category-add";
    }

}
