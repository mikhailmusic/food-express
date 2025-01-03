package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.review.ReviewAddDto;
import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.Review;
import rut.miit.food.express.entity.User;
import rut.miit.food.express.exception.OrderAccessException;
import rut.miit.food.express.exception.OrderNotFoundException;
import rut.miit.food.express.exception.UserNotFoundException;
import rut.miit.food.express.repository.OrderRepository;
import rut.miit.food.express.repository.ReviewRepository;
import rut.miit.food.express.repository.UserRepository;
import rut.miit.food.express.service.ReviewService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(value = "reviews", allEntries = true)
    public void leaveReview(ReviewAddDto dto) {
        Order order = orderRepository.findById(dto.orderId()).orElseThrow(() -> new OrderNotFoundException(dto.orderId()));
        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFoundException(dto.username()));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderAccessException(dto.username());
        }

        Review review = new Review(dto.text(), dto.rating(), order, user);
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
