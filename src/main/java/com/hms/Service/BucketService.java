package com.hms.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.util.UUID;

@Service
public class BucketService {

    private final S3Client s3Client;

    public BucketService(S3Client s3Client)
    {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile file, String myhmsaws, Long propertyId) {
        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file to upload");
        }

        // Generate unique file name inside a property-specific folder
        String fileName = "property_" + propertyId + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(myhmsaws)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            // Return S3 URL
            return "https://" + myhmsaws + ".s3.amazonaws.com/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error uploading the file: " + e.getMessage(), e);
        }
    }

}
