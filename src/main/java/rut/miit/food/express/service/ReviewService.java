package rut.miit.food.express.service;

import rut.miit.food.express.dto.review.ReviewAddDto;
import rut.miit.food.express.dto.review.ReviewDto;

import java.util.List;

public interface ReviewService {
    void leaveReview(ReviewAddDto reviewDto);
    List<ReviewDto> reviewsForRestaurant(Integer id);

}
