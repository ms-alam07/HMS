package com.hms.Controller;

import com.hms.Payload.CityDto;
import com.hms.Service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hms/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Add a new city
    @PostMapping("/addCity")
    public ResponseEntity<?> addCity(@RequestBody CityDto dto) {
        try {
            CityDto city = cityService.addCity(dto);
            return new ResponseEntity<>(city, HttpStatus.CREATED); // 201 CREATED
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    // Get all cities
    @GetMapping("/getAllCities")
    public ResponseEntity<?> getAllCities() {
        try {
            List<CityDto> cities = cityService.getAllCities();
            return new ResponseEntity<>(cities, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    // Get city by ID
    @GetMapping("/getCity/{id}")
    public ResponseEntity<?> getCity(@PathVariable Long id) {
        try {
            CityDto city = cityService.getCityById(id);
            if (city != null) {
                return new ResponseEntity<>(city, HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND); // 404 NOT_FOUND
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    // Update city by ID
    @PutMapping("/updateCity/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id, @RequestBody CityDto dto) {
        try {
            CityDto updatedCity = cityService.updateCity(id, dto);
            if (updatedCity != null) {
                return new ResponseEntity<>(updatedCity, HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND); // 404 NOT_FOUND
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    // Delete city by ID
    @DeleteMapping("/deleteCity/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        try {
            boolean isDeleted = cityService.deleteCity(id);
            if (isDeleted) {
                return new ResponseEntity<>("City deleted successfully", HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND); // 404 NOT_FOUND
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }
}
