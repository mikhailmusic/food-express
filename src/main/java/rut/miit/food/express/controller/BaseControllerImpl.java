package rut.miit.food.express.controller;

import food.express.contracts.controller.BaseController;
import food.express.contracts.viewmodel.BaseViewModel;

public abstract class BaseControllerImpl implements BaseController {

    public BaseViewModel createBaseViewModel(String title) {
        String username = getCurrentUsername();
        return new BaseViewModel(title, username);
    }

    private String getCurrentUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            return authentication.getName();
//        }
        return "anonymous";
    }
}
