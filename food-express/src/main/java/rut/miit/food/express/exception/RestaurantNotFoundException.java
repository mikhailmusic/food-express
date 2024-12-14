package rut.miit.food.express.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
    public RestaurantNotFoundException(String message) {
        super("Restaurant not found: " + message);
    }
    public RestaurantNotFoundException(Integer id) {
        super("Restaurant with id " + id + " not found");
    }
}
