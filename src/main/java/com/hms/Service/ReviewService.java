package com.hms.Service;

import com.hms.Entity.Review;
import com.hms.Entity.User;
import com.hms.Payload.ReviewDto;
import com.hms.Repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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

    public ReviewDto addReview(ReviewDto dto, long propertyId, Long userId) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        Review savedReview = reviewRepository.save(review);
        return mapToDto(savedReview);
    }

    public List<ReviewDto> getReviewsByProperty(long propertyId) {
        return reviewRepository.findByPropertyId(propertyId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto updateReview(long reviewId, ReviewDto dto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return mapToDto(reviewRepository.save(review));
    }

    public void deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewDto> getReviewsByUserId(User user) {
        return reviewRepository.findByUsername(user).stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());
    }
}