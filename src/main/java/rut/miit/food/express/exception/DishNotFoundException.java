package rut.miit.food.express.exception;

public class DishNotFoundException extends EntityNotFoundException {
    public DishNotFoundException(String message) {
        super("Dish not found: " + message);
    }
    public DishNotFoundException(Integer id) {
        super("Dish with id " + id + " not found");
    }
}
