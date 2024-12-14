package food.express.contracts.controller;

import food.express.contracts.form.UserRegisterForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthController {
    @PostMapping("/register")
    String registerUser(@Valid @ModelAttribute("form") UserRegisterForm form, BindingResult bindingResult, Model model);

    @GetMapping("/register")
    String showRegistrationForm(Model model);

    @GetMapping("/login")
    String showLoginForm(Model model);


}
