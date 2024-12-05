package rut.miit.food.express.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super("User not found: " + message);
    }
    public UserNotFoundException(Integer id) {
        super("User with id " + id + " not found");
    }
}
