package rut.miit.food.express.exception;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(String message) {
        super("DishCategory not found: " + message);
    }
    public CategoryNotFoundException(Integer id) {
        super("DishCategory with id " + id + " not found");
    }
}
