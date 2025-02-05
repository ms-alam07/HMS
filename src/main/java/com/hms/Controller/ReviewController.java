package com.hms.Controller;

import com.hms.Entity.User;
import com.hms.Payload.ReviewDto;
import com.hms.Service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hms/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto dto, @RequestParam long propertyId,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.addReview(dto, propertyId, user));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProperty(@PathVariable long propertyId) {
        return ResponseEntity.ok(reviewService.getReviewsByProperty(propertyId));
    }

    @GetMapping("/user-reviews")
    public ResponseEntity<List<ReviewDto>> getUserReviews(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(user));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable long reviewId, @RequestBody ReviewDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, dto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}