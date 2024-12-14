package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.review.ReviewAddDto;
import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.Review;
import rut.miit.food.express.exception.OrderNotFoundException;
import rut.miit.food.express.repository.OrderRepository;
import rut.miit.food.express.repository.ReviewRepository;
import rut.miit.food.express.service.ReviewService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @CacheEvict(value = "reviews", allEntries = true)
    public void leaveReview(ReviewAddDto reviewDto) {
        Order order = orderRepository.findById(reviewDto.orderId()).orElseThrow(() -> new OrderNotFoundException(reviewDto.orderId()));
        Review review = new Review(reviewDto.text(), reviewDto.rating(), order, order.getUser());
        reviewRepository.save(review);
    }

    @Override
    @Cacheable("reviews")
    public List<ReviewDto> reviewsForRestaurant(Integer restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        return reviews.stream().sorted(Comparator.comparing(Review::getDate).reversed()).map(this::toDto).collect(Collectors.toList());
    }


    private ReviewDto toDto(Review review) {
        if (review == null) {
            return null;
        }
        return new ReviewDto(review.getText(), review.getRating(), review.getDate(), review.getUser().getFirstName());
    }
}
