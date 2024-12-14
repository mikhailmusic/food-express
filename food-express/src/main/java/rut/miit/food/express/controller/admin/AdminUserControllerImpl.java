package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminUserController;
import food.express.contracts.form.UserAdminEditForm;
import food.express.contracts.form.UserSearchForm;
import food.express.contracts.viewmodel.user.UserAdminEditViewModel;
import food.express.contracts.viewmodel.user.UserListViewModel;
import food.express.contracts.viewmodel.user.UserViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.user.UserAdminUpdateDto;
import rut.miit.food.express.dto.user.UserDto;
import rut.miit.food.express.service.UserService;
import rut.miit.food.express.util.PageWrapper;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserControllerImpl extends BaseControllerImpl implements AdminUserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    @GetMapping
    public String listUsers(@ModelAttribute("form") UserSearchForm form, Model model){
        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 20;
        form = new UserSearchForm(searchTerm, page, size);

        PageWrapper<UserDto> wrapper = userService.getAllUsers(searchTerm, page, size);

        List<UserViewModel> userViewModels = wrapper.content().stream()
                .map(dto -> new UserViewModel(dto.id(), dto.firstName(), dto.phoneNumber(), dto.address(), dto.birthDate(), dto.login(), dto.role()))
                .toList();
        UserListViewModel viewModel = new UserListViewModel(
                createBaseViewModel("Пользователи"),
                userViewModels, wrapper.totalPages()
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "user-list";
    }

    @Override
    @GetMapping("/{username}/edit-profile")
    public String editUserProfile(@PathVariable String username, Model model){
        UserDto dto = userService.getUser(username);
        UserAdminEditViewModel viewModel = new UserAdminEditViewModel(
                createBaseViewModel("Изменение профиля"), dto.login(), userService.getUserRoles()
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new UserAdminEditForm(username, dto.role()));
        return "user-admin-edit";
    }

    @Override
    @PostMapping("/{username}/edit-profile")
    public String editUserProfile(@PathVariable String username, @Valid @ModelAttribute("form") UserAdminEditForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            UserAdminEditViewModel viewModel = new UserAdminEditViewModel(
                    createBaseViewModel("Изменение профиля"), username, userService.getUserRoles()
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "user-edit";
        }
        userService.userAdminUpdate(new UserAdminUpdateDto(form.username(), form.role()));
        return "redirect:/admin/users";
    }
}
