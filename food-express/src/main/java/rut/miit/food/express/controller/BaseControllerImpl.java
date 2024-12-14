package rut.miit.food.express.controller;

import food.express.contracts.controller.BaseController;
import food.express.contracts.viewmodel.BaseViewModel;

public abstract class BaseControllerImpl implements BaseController {

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
