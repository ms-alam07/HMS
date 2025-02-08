package com.hms.Service;

import com.hms.Entity.RoomsAvailability;
import com.hms.Repository.RoomsAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomsAvailabilityService {

    private final RoomsAvailabilityRepository roomsAvailabilityRepository;

    public RoomsAvailabilityService(RoomsAvailabilityRepository roomsAvailabilityRepository) {
        this.roomsAvailabilityRepository = roomsAvailabilityRepository;
    }

    public List<RoomsAvailability> searchRooms(LocalDate fromDate, LocalDate toDate, String roomType) {
        return roomsAvailabilityRepository.searchAvailability(fromDate, toDate, roomType);

    }
}
