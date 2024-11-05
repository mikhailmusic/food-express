package rut.miit.food.express.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    @Autowired
    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> boolean isValid(T object) {
        return validator.validate(object).isEmpty();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> violations(T object) {
        return validator.validate(object);
    }
}
