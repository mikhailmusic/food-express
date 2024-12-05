package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminUserController;
import food.express.contracts.viewmodel.user.UserListViewModel;
import food.express.contracts.viewmodel.user.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.service.UserService;

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
    public String listUsers(Model model){
        List<UserViewModel> userViewModels = userService.getAllUsers().stream()
                .map(dto -> new UserViewModel(dto.id(), dto.firstName(), dto.phoneNumber(), dto.address(), dto.birthDate(), dto.login()))
                .toList();
        UserListViewModel viewModel = new UserListViewModel(
                createBaseViewModel("Пользователи"),
                userViewModels
        );
        model.addAttribute("model", viewModel);
        return "user-list";
    }
}
