package rut.miit.food.express.util;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationUtil {
    <T> boolean isValid(T object);
    <T> Set<ConstraintViolation<T>> violations(T object);

}
