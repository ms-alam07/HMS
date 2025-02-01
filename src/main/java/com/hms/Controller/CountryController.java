package com.hms.Controller;

import com.hms.Payload.CountryDto;
import com.hms.Service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hms/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // Add a new country
    @PostMapping("/addCountry")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto dto) {
        try {
            CountryDto country = countryService.addCountry(dto);
            return new ResponseEntity<>(country, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all countries
    @GetMapping("/getAllCountries")
    public ResponseEntity<?> getAllCountries() {
        try {
            List<CountryDto> countries = countryService.getAllCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get country by id
    @GetMapping("/getCountry/{id}")
    public ResponseEntity<?> getCountry(@PathVariable Long id) {
        try {
            CountryDto country = countryService.getCountryById(id);
            if (country != null) {
                return new ResponseEntity<>(country, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update country by id
    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @RequestBody CountryDto dto) {
        try {
            CountryDto updatedCountry = countryService.updateCountry(id, dto);
            if (updatedCountry != null) {
                return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete country by id
    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        try {
            boolean isDeleted = countryService.deleteCountry(id);
            if (isDeleted) {
                return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
