package food.express.contracts.controller.admin;

import food.express.contracts.controller.BaseController;
import food.express.contracts.form.UserAdminEditForm;
import food.express.contracts.form.UserSearchForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin/users")
public interface AdminUserController extends BaseController {

    @GetMapping
    String listUsers(@ModelAttribute("form") UserSearchForm form, Model model, Principal principal);

    @GetMapping("/{username}/edit-profile")
    String editUserProfile(@PathVariable String username, Model model, Principal principal);

    @PostMapping("/{username}/edit-profile")
    String editUserProfile(@PathVariable String username, @Valid @ModelAttribute("form") UserAdminEditForm form, BindingResult result, Model model, Principal principal);
}
