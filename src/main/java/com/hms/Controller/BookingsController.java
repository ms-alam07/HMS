package com.hms.Controller;

import com.hms.Entity.Bookings;
import com.hms.Entity.Property;
import com.hms.Payload.BookingsDto;
import com.hms.Repository.PropertyRepository;
import com.hms.Service.BookingsService;
import com.hms.Service.PDFRequestService;
import com.hms.Service.RoomsAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/hms/bookings")
public class BookingsController {

    private final RoomsAvailabilityService roomsAvailabilityService;
    private final PropertyRepository propertyRepository;
    private final BookingsService bookingsService;

    public BookingsController(RoomsAvailabilityService roomsAvailabilityService,
                              PropertyRepository propertyRepository,
                              BookingsService bookingsService
                              ) {
        this.roomsAvailabilityService = roomsAvailabilityService;
        this.propertyRepository = propertyRepository;
        this.bookingsService = bookingsService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody BookingsDto bookingDto,
                                        @RequestParam LocalDate fromDate,
                                        @RequestParam LocalDate toDate,
                                        @RequestParam Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if (property.isEmpty()) {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }

        try {
            bookingDto.setProperty(property.get());
            bookingDto.setFromDate(fromDate);
            bookingDto.setToDate(toDate);

            Bookings savedBooking = bookingsService.addBooking(bookingDto, fromDate, toDate);
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
