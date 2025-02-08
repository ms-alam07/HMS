package com.hms.Service;

import com.hms.Entity.Property;
import com.hms.Entity.User;
import com.hms.Payload.PropertyDto;
import com.hms.Payload.ReviewDto;
import com.hms.Payload.UserDto;
import com.hms.Repository.PropertyRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    Property mapToEntity(PropertyDto dto){
            Property property = new Property();
            property.setName(dto.getName());
            property.setNoOfGuests(dto.getNoOfGuests());
            property.setNoOfBedrooms(dto.getNoOfBedrooms());
            property.setNoOfBathrooms(dto.getNoOfBathrooms());
            property.setPrice(dto.getPrice());
            return property;
        }
        PropertyDto mapToDto(Property property){
            PropertyDto dto = new PropertyDto();
            dto.setName(property.getName());
            dto.setNoOfGuests(property.getNoOfGuests());
            dto.setNoOfBedrooms(property.getNoOfBedrooms());
            dto.setNoOfBathrooms(property.getNoOfBathrooms());
            dto.setPrice(property.getPrice());
            return dto;
        }

    public PropertyDto addProperty(PropertyDto dto) {
            Property property = mapToEntity(dto);
           Property pro = propertyRepository.save(property);
           PropertyDto propertyDto = mapToDto(pro);
           return propertyDto;
    }

    // Get all properties
    public List<PropertyDto> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get property by ID
    public PropertyDto getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null); // Return null if not found
    }

    // Update property by ID
    public PropertyDto updateProperty(Long id, PropertyDto dto) {
        Property existingProperty = propertyRepository.findById(id).orElse(null);
        if (existingProperty != null) {
            existingProperty.setName(dto.getName());
            existingProperty.setNoOfGuests(dto.getNoOfGuests());
            existingProperty.setNoOfBedrooms(dto.getNoOfBedrooms());
            existingProperty.setNoOfBathrooms(dto.getNoOfBathrooms());
            existingProperty.setPrice(dto.getPrice());
            Property updatedProperty = propertyRepository.save(existingProperty);
            return mapToDto(updatedProperty);
        }
        return null; // Return null if property not found
    }

    // Delete property by ID
    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true; // Property deleted successfully
        }
        return false; // Property not found
    }



}
