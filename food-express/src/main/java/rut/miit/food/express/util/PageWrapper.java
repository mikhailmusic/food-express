package rut.miit.food.express.util;

import java.util.List;

public record PageWrapper<T> (
        List<T> content,
        int totalPages
) {}
