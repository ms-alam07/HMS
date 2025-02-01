package com.hms.Controller;

import com.hms.Payload.PropertyDto;
import com.hms.Service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hms/property")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Add a new property
    @PostMapping("/addProperty")
    public ResponseEntity<?> addProperty(@RequestBody PropertyDto dto) {
        try {
            PropertyDto property = propertyService.addProperty(dto);
            return new ResponseEntity<>(property, HttpStatus.CREATED);  // Changed to 201 CREATED for new resources
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all properties
    @GetMapping("/getAllProperties")
    public ResponseEntity<?> getAllProperties() {
        try {
            List<PropertyDto> properties = propertyService.getAllProperties();
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get property by id
    @GetMapping("/getProperty/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id) {
        try {
            PropertyDto property = propertyService.getPropertyById(id);
            if (property != null) {
                return new ResponseEntity<>(property, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update property by id
    @PutMapping("/updateProperty/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyDto dto) {
        try {
            PropertyDto updatedProperty = propertyService.updateProperty(id, dto);
            if (updatedProperty != null) {
                return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete property by id
    @DeleteMapping("/deleteProperty/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            boolean isDeleted = propertyService.deleteProperty(id);
            if (isDeleted) {
                return new ResponseEntity<>("Property deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
