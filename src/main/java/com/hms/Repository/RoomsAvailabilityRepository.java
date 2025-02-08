package com.hms.Repository;

import com.hms.Entity.RoomsAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsAvailabilityRepository extends JpaRepository<RoomsAvailability, Long> {
    @Query("SELECT r FROM RoomsAvailability r WHERE r.roomType = :roomType"+ " AND r.date BETWEEN :fromDate AND :toDate")
    List<RoomsAvailability> searchAvailability(@Param("fromDate")LocalDate fromDate, @Param("toDate")LocalDate toDate,@Param("roomType") String roomType);
}