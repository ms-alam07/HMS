package com.hms.Service;

import com.hms.Entity.Country;
import com.hms.Payload.CountryDto;
import com.hms.Repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Map CountryDto to Country entity
    private Country mapToEntity(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        return country;
    }

    // Map Country entity to CountryDto
    private CountryDto mapToDto(Country country) {
        CountryDto dto = new CountryDto();
        dto.setName(country.getName());
        return dto;
    }

    // Add a new country
    public CountryDto addCountry(CountryDto dto) {
        Country country = mapToEntity(dto);
        Country savedCountry = countryRepository.save(country);
        return mapToDto(savedCountry);
    }

    // Get all countries
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get country by ID
    public CountryDto getCountryById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.map(this::mapToDto).orElse(null);
    }

    // Update country by ID
    public CountryDto updateCountry(Long id, CountryDto dto) {
        Optional<Country> existingCountry = countryRepository.findById(id);
        if (existingCountry.isPresent()) {
            Country country = existingCountry.get();
            country.setName(dto.getName());
            Country updatedCountry = countryRepository.save(country);
            return mapToDto(updatedCountry);
        }
        return null; // Country not found
    }

    // Delete country by ID
    public boolean deleteCountry(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return true;
        }
        return false; // Country not found
    }
}
