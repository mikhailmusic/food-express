package rut.miit.food.express;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rut.miit.food.express.service.DishService;

import java.util.stream.IntStream;

@SpringBootTest
class CacheTest {
    @Autowired
    DishService dishService;

    @Test
    void testCachePerformance() {
        int testIterations = 100;

        long cacheTime = IntStream.range(0, testIterations)
                .mapToLong(i -> {
                    long start = System.nanoTime();
                    dishService.getDishes("", null, true, 1, 100);
                    return System.nanoTime() - start;
                })
                .sum() / testIterations;

        System.out.println("Среднее время с кешем: " + cacheTime / 1_000_000 + " мс");
    }
}
