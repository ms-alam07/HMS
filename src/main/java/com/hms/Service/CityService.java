package com.hms.Service;

import com.hms.Entity.City;
import com.hms.Payload.CityDto;
import com.hms.Repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // Map CityDto to City entity
     City mapToEntity(CityDto dto) {
        City city = new City();
        city.setName(dto.getName());
        return city;
    }

    // Map City entity to CityDto
    CityDto mapToDto(City city) {
        CityDto dto = new CityDto();
        dto.setName(city.getName());
        return dto;
    }

    // Add a new city
    public CityDto addCity(CityDto dto) {
        City city = mapToEntity(dto);
        City savedCity = cityRepository.save(city);
        return mapToDto(savedCity);
    }

    // Get all cities
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get city by ID
    public CityDto getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.map(this::mapToDto).orElse(null);
    }

    // Update city by ID
    public CityDto updateCity(Long id, CityDto dto) {
        Optional<City> existingCity = cityRepository.findById(id);
        if (existingCity.isPresent()) {
            City city = existingCity.get();
            city.setName(dto.getName());
            City updatedCity = cityRepository.save(city);
            return mapToDto(updatedCity);
        }
        return null; // City not found
    }

    // Delete city by ID
    public boolean deleteCity(Long id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false; // City not found
    }
}
