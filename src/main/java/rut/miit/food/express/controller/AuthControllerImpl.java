package rut.miit.food.express.controller;

import food.express.contracts.controller.AuthController;
import food.express.contracts.form.UserRegisterForm;
import food.express.contracts.viewmodel.CreateViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.user.UserAddDto;
import rut.miit.food.express.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthControllerImpl extends BaseControllerImpl implements AuthController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("form") UserRegisterForm form, BindingResult bindingResult, Model model){
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
        return "redirect:/auth/login";
    }

    @Override
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
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
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Вход")
        );
        model.addAttribute("model", viewModel);
        return "login";
    }

}
