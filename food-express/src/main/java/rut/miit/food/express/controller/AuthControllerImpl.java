package rut.miit.food.express.controller;

import food.express.contracts.controller.AuthController;
import food.express.contracts.form.UserRegisterForm;
import food.express.contracts.viewmodel.CreateViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.miit.food.express.dto.user.UserAddDto;
import rut.miit.food.express.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthControllerImpl extends BaseControllerImpl implements AuthController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("form") UserRegisterForm form, BindingResult bindingResult, Model model){
        LOG.info("User anonymous is attempting to register: login='{}'", form.login());
        if (bindingResult.hasErrors()) {
            CreateViewModel viewModel = new CreateViewModel(
                    createBaseViewModel("Регистрация")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "register";
        }

        UserAddDto dto = new UserAddDto(form.firstName(), form.address(), form.birthDate(), form.login(), form.password(), form.confirmPassword());
        userService.registerUser(dto);
        LOG.info("User '{}' successfully registered", form.login());
        return "redirect:/auth/login";
    }

    @Override
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        LOG.info("Displaying registration form for anonymous user");
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Регистрация")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserRegisterForm("", "",null, "","", ""));
        return "register";
    }

    @Override
    @GetMapping("/login")
    public String showLoginForm(Model model){
        LOG.info("Displaying login form for anonymous user");
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Вход")
        );
        model.addAttribute("model", viewModel);
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {
        LOG.warn("Failed login attempt with username: '{}'", username);

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/auth/login";
    }
}
