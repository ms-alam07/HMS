package com.hms.Controller;

import com.hms.Repository.PropertyRepository;
import com.hms.Service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/hms/image")
public class ImageController {

    private BucketService bucketService;

    public ImageController(BucketService bucketService) {
        this.bucketService = bucketService;
    }


    @PostMapping(path = "/upload/file/{myhmsaws}/property/{propertyId}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @PathVariable String myhmsaws,
                                        @PathVariable Long propertyId,
                                        @RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> System.out.println(key + ": " + value)); // Debug headers

        String imageUrl = bucketService.upload(file, myhmsaws, propertyId);
        return new ResponseEntity<>(imageUrl, HttpStatus.CREATED);
    }


}
