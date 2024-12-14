package rut.miit.food.express.util;

import java.util.List;

public class PaginationHelper {
    public static <D> PageWrapper<D> getPage(List<D> dtoList, int page, int size) {
        int start = (page - 1) * size;
        int end = Math.min(start + size, dtoList.size());
        int totalPages = (int) Math.ceil((double) dtoList.size() / size);

        if (start >= dtoList.size()) {
            return new PageWrapper<>(List.of(), totalPages);
        }

        List<D> pageContent = dtoList.subList(start, end);
        return new PageWrapper<>(pageContent, totalPages);
    }
}
