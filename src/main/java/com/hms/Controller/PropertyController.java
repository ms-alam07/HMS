package com.hms.Controller;

import com.hms.Entity.Property;
import com.hms.Entity.PropertyImage;
import com.hms.Payload.PropertyDto;
import com.hms.Repository.PropertyImageRepository;
import com.hms.Repository.PropertyRepository;
import com.hms.Service.BucketService;
import com.hms.Service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/hms/property")
public class PropertyController {

    private PropertyService propertyService;
    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private PropertyImageRepository propertyImageRepository;

    public PropertyController(PropertyService propertyService,BucketService bucketService,PropertyRepository propertyRepository,
                              PropertyImageRepository propertyImageRepository) {
        this.propertyService = propertyService;
        this.bucketService=bucketService;
        this.propertyRepository = propertyRepository;
        this.propertyImageRepository = propertyImageRepository;
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



        // Upload property image
        @PostMapping("/upload/property-image/{propertyId}")
        public ResponseEntity<?> uploadPropertyImage(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("myhmsaws") String myhmsaws,
                                                     @PathVariable Long propertyId) {
            Optional<Property> propertyOpt = propertyRepository.findById(propertyId);
            if (propertyOpt.isEmpty()) {
                return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
            }

            String url = bucketService.upload(file, myhmsaws, propertyId);
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setUrl(url);
            propertyImage.setProperty(propertyOpt.get());

            propertyImageRepository.save(propertyImage);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.CREATED);
        }


    // Get all images of a property
        @GetMapping("/images/{propertyId}")
        public ResponseEntity<?> getAllPropertyImages(@PathVariable Long propertyId) {
            List<PropertyImage> images = propertyImageRepository.findAllByPropertyId(propertyId);
            if (images.isEmpty()) {
                return new ResponseEntity<>("No images found for this property", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        }

        // Get a specific image by ID
        @GetMapping("/image/{imageId}")
        public ResponseEntity<?> getPropertyImage(@PathVariable Long imageId) {
            System.out.println("Fetching image with ID: " + imageId);
            Optional<PropertyImage> imageOpt = propertyImageRepository.findById(imageId);

            if (imageOpt.isPresent()) {
                System.out.println("Image found: " + imageOpt.get().getUrl());
                return new ResponseEntity<>(imageOpt.get(), HttpStatus.OK);
            } else {
                System.out.println("Image not found");
                return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
            }
        }


    // Delete an image by ID
        @DeleteMapping("/image/delete/{imageId}")
        public ResponseEntity<?> deletePropertyImage(@PathVariable Long imageId) {
            Optional<PropertyImage> imageOpt = propertyImageRepository.findById(imageId);
            if (imageOpt.isEmpty()) {
                return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
            }

            propertyImageRepository.deleteById(imageId);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        }
    

}
