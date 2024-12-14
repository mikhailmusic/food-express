package food.express.contracts.controller;

import food.express.contracts.form.UserPasswordChangeForm;
import food.express.contracts.form.UserEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/users")
public interface UserController extends BaseController {

    @PostMapping("/edit-profile")
    String editUserProfile(@Valid @ModelAttribute("form") UserEditForm form, BindingResult result, Principal principal, Model model);

    @PostMapping("/change-password")
    String changePassword(@Valid @ModelAttribute("form") UserPasswordChangeForm form, BindingResult result, Principal principal, Model model);

    @GetMapping("/profile")
    String userProfile(Principal principal, Model model);

    @GetMapping("/edit-profile")
    String editUserProfile(Principal principal, Model model);

    @GetMapping("/change-password")
    String changePassword(Principal principal, Model model);

}
