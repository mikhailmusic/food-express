package rut.miit.food.express.controller;

import food.express.contracts.controller.BaseController;
import food.express.contracts.viewmodel.BaseViewModel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl implements BaseController {

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

    public String getUsername(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    protected String getErrorMessage(BindingResult result, String formName, String username) {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors().stream()
                    .map(error -> {
                        String field = ((FieldError) error).getField();
                        String value = (String) ((FieldError) error).getRejectedValue();
                        return String.format("%s: '%s' - %s", field, value, error.getDefaultMessage());
                    })
                    .collect(Collectors.joining("; "));
            return String.format("User %s failed to submit %s. Errors: %s", username, formName, errorMessages);
        }
        return "";
    }
}
