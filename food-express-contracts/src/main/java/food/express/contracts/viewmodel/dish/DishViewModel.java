package food.express.contracts.viewmodel.dish;

import java.math.BigDecimal;

public record DishViewModel(
        Integer id,
        String name,
        BigDecimal price,
        Integer weight,
        Integer calories,
        String imageURL,
        Boolean isVisible
) {}
