package rut.miit.food.express.exception;

public class OrderAccessException extends CustomException {
    public OrderAccessException(String username) {
        super("Order does not belong to the user: " + username);
    }
}
