package rut.miit.food.express.dto;

import java.util.List;

public record PageWrapper<T> (
        List<T> content,
        int totalPages
) {}
