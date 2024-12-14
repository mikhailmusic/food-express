package food.express.contracts.viewmodel.order;

public record OrderItemViewModel(
        Integer id,
        Integer dishId,
        Integer count,
        String dishName,
        String imageURL,
        Boolean isVisible
) {}
