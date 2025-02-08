package com.hms.Service;

import com.hms.Entity.Bookings;
import com.hms.Entity.Property;
import com.hms.Entity.RoomsAvailability;
import com.hms.Payload.BookingsDto;
import com.hms.Payload.PDFRequestDto;
import com.hms.Repository.BookingsRepository;
import com.hms.Repository.RoomsAvailabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingsService {

    private BookingsRepository bookingsRepository;
    private  RoomsAvailabilityRepository roomsAvailabilityRepository;
    private PDFRequestService pdfRequestService;


    public BookingsService(BookingsRepository bookingsRepository,
                           RoomsAvailabilityRepository roomsAvailabilityRepository,
                           PDFRequestService pdfRequestService) {
        this.bookingsRepository = bookingsRepository;
        this.roomsAvailabilityRepository = roomsAvailabilityRepository;
        this.pdfRequestService = pdfRequestService;


    }

    @Transactional
    public Bookings addBooking(BookingsDto bookingsDto, LocalDate fromDate, LocalDate toDate) {
        List<RoomsAvailability> availableRooms = roomsAvailabilityRepository
                .searchAvailability(fromDate, toDate, bookingsDto.getRoomType());

        if (availableRooms.isEmpty() || availableRooms.stream().allMatch(r -> r.getTotalRooms() == 0)) {
            throw new RuntimeException("No rooms available for the selected dates.");
        }

        // Update availability
        for (RoomsAvailability room : availableRooms) {
            if (room.getTotalRooms() > 0) {
                room.setTotalRooms(room.getTotalRooms() - 1);
                roomsAvailabilityRepository.save(room);
                break; // Deduct one room only
            }
        }

        // Convert DTO to Entity
        Bookings booking = mapToEntity(bookingsDto);

        // Save booking
        Bookings savedBooking = bookingsRepository.save(booking);

        // **Generate PDF after booking is saved**
        PDFRequestDto pdfRequestDto = new PDFRequestDto();
        pdfRequestDto.setFilePath("C:/Users/YourUsername/Desktop/booking_" + savedBooking.getId() + ".pdf");
        pdfRequestDto.setTitle("Booking Confirmation");
        pdfRequestDto.setContent("Booking Details:\nGuest: " + savedBooking.getGuestName() +
                "\nRoom Type: " + savedBooking.getRoomType() +
                "\nFrom: " + savedBooking.getFromDate() +
                "\nTo: " + savedBooking.getToDate());

        pdfRequestService.generatePDF(pdfRequestDto);

        return savedBooking;
    }


    Bookings mapToEntity(BookingsDto dto) {
        Bookings booking = new Bookings();
        booking.setGuestName(dto.getGuestName());
        booking.setNoOfGuest(dto.getNoOfGuest());
        booking.setMobile(dto.getMobile());
        booking.setEmail(dto.getEmail());
        booking.setRoomType(dto.getRoomType());
        booking.setFromDate(dto.getFromDate());
        booking.setToDate(dto.getToDate());
        booking.setProperty(dto.getProperty());
        return booking;
    }
}
