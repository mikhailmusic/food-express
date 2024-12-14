package rut.miit.food.express.exception;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(String message) {
        super("Order not found: " + message);
    }
    public OrderNotFoundException(Integer id) {
        super("Order with id " + id + " not found");
    }
}
