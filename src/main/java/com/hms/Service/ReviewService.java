package com.hms.Service;

import com.hms.Entity.Property;
import com.hms.Entity.Review;
import com.hms.Entity.User;
import com.hms.Payload.ReviewDto;
import com.hms.Repository.PropertyRepository;
import com.hms.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    ReviewDto mapToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }

    Review mapToEntity(ReviewDto dto) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }

    public ReviewDto addReview(ReviewDto dto, long propertyId, User user) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        Review review = reviewRepository.findByPropertyAndUser(property, user);
        if (review != null) {
            review.setRating(dto.getRating());
            review.setComment(dto.getComment());
            Review savedReview = reviewRepository.save(review);
            return mapToDto(savedReview);
        } else {
            Review newReview = mapToEntity(dto);
            newReview.setProperty(property);
            newReview.setUser(user);
            Review savedReview = reviewRepository.save(newReview);
            return mapToDto(savedReview);
        }
    }

    public List<ReviewDto> getReviewsByProperty(long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        return reviewRepository.findByProperty(property).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto updateReview(long reviewId, ReviewDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return mapToDto(reviewRepository.save(review));
    }

    public void deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewDto> getReviewsByUserId(User user) {
        return reviewRepository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}