package rut.miit.food.express.controller;

import food.express.contracts.controller.UserController;
import food.express.contracts.form.UserEditForm;
import food.express.contracts.form.UserPasswordChangeForm;
import food.express.contracts.viewmodel.EditViewModel;
import food.express.contracts.viewmodel.user.UserProfileViewModel;
import food.express.contracts.viewmodel.user.UserViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.user.UserChangePasswordDto;
import rut.miit.food.express.dto.user.UserUpdateDto;
import rut.miit.food.express.dto.user.UserDto;
import rut.miit.food.express.service.UserService;


@Controller
@RequestMapping("/users")
public class UserControllerImpl extends BaseControllerImpl implements UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/{id}/edit")
    public String editUserProfile(@PathVariable Integer id, @Valid @ModelAttribute("form") UserEditForm form, BindingResult result, Model model){
        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Редактирование профиля")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "user-edit";
        }
        UserUpdateDto dto = new UserUpdateDto(id, form.firstName(), form.phoneNumber(), form.address());
        userService.updateUserInfo(dto);
        return "redirect:/admin/users";

    }

    @Override
    @PostMapping("/{id}/change-password")
    public String changePassword(@PathVariable Integer id, @Valid @ModelAttribute("form") UserPasswordChangeForm form, BindingResult result, Model model){
        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Изменение пароля")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "user-password";
        }

        UserChangePasswordDto dto = new UserChangePasswordDto(id, form.oldPassword(), form.newPassword(), form.confirmPassword());
        userService.updateUserPassword(dto);
        return "redirect:/admin/users/";

    }

    @Override
    @GetMapping("/{id}")
    public String userProfile(@PathVariable Integer id, Model model){
        UserDto dto = userService.getUser(id);
        UserProfileViewModel viewModel = new UserProfileViewModel(
                createBaseViewModel("Профиль"),
                new UserViewModel(dto.id(), dto.firstName(), dto.phoneNumber(), dto.address(), dto.birthDate(), dto.login())
        );
        model.addAttribute("model", viewModel);
        return "user-details";

    }

    @Override
    @GetMapping("/{id}/edit")
    public String editUserProfile(@PathVariable Integer id, Model model){
        UserDto dto = userService.getUser(id);
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Редактирование профиля")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserEditForm(dto.id(), dto.firstName(), dto.phoneNumber(), dto.address()));
        return "user-edit";

    }

    @Override
    @GetMapping("/{id}/change-password")
    public String changePassword(@PathVariable Integer id, Model model){
        UserDto dto = userService.getUser(id);
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Изменение пароля")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserPasswordChangeForm(dto.id(), "", "", ""));
        return "user-password";

    }

}
