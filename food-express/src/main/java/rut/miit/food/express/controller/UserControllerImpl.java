package rut.miit.food.express.controller;

import food.express.contracts.controller.UserController;
import food.express.contracts.form.UserEditForm;
import food.express.contracts.form.UserPasswordChangeForm;
import food.express.contracts.viewmodel.EditViewModel;
import food.express.contracts.viewmodel.user.UserProfileViewModel;
import food.express.contracts.viewmodel.user.UserViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.user.UserChangePasswordDto;
import rut.miit.food.express.dto.user.UserUpdateDto;
import rut.miit.food.express.dto.user.UserDto;
import rut.miit.food.express.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserControllerImpl extends BaseControllerImpl implements UserController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/edit-profile")
    public String editUserProfile(@Valid @ModelAttribute("form") UserEditForm form, BindingResult result, Principal principal, Model model){
        LOG.info("User '{}' is editing their profile with new data: firstName, phoneNumber, address", principal.getName());

        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Редактирование профиля")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "user-edit";
        }
        UserUpdateDto dto = new UserUpdateDto(principal.getName(), form.firstName(), form.phoneNumber(), form.address());
        userService.updateUserInfo(dto);
        LOG.info("User '{}' successfully updated their profile", principal.getName());
        return "redirect:/users/profile";

    }

    @Override
    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("form") UserPasswordChangeForm form, BindingResult result, Principal principal, Model model){
        LOG.info("User '{}' is changing their password", principal.getName());
        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Изменение пароля")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "user-password";
        }

        UserChangePasswordDto dto = new UserChangePasswordDto(principal.getName(), form.oldPassword(), form.newPassword(), form.confirmPassword());
        userService.updateUserPassword(dto);
        LOG.info("User '{}' successfully changed their password", principal.getName());
        return "redirect:/users/profile";

    }

    @Override
    @GetMapping("/profile")
    public String userProfile(Principal principal, Model model){
        LOG.info("User '{}' is viewing their profile", principal.getName());
        UserDto dto = userService.getUser(principal.getName());
        UserProfileViewModel viewModel = new UserProfileViewModel(
                createBaseViewModel("Профиль"),
                new UserViewModel(dto.id(), dto.firstName(), dto.phoneNumber(), dto.address(), dto.birthDate(), dto.login(), dto.role())
        );
        model.addAttribute("model", viewModel);
        return "user-details";

    }

    @Override
    @GetMapping("/edit-profile")
    public String editUserProfile(Principal principal, Model model){
        LOG.info("User '{}' is requesting to edit their profile", principal.getName());
        UserDto dto = userService.getUser(principal.getName());
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Редактирование профиля")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserEditForm(dto.login(), dto.firstName(), dto.phoneNumber(), dto.address()));
        return "user-edit";

    }

    @Override
    @GetMapping("/change-password")
    public String changePassword(Principal principal, Model model){
        LOG.info("User '{}' is requesting to change their password", principal.getName());
        UserDto dto = userService.getUser(principal.getName());
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Изменение пароля")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserPasswordChangeForm(dto.login(), "", "", ""));
        return "user-password";

    }

}
