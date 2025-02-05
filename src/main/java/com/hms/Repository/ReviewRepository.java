package com.hms.Repository;

import com.hms.Entity.Property;
import com.hms.Entity.Review;
import com.hms.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProperty(Property property);
  List<Review> findByUser(User user);
  Review findByPropertyAndUser(Property property, User user);
}