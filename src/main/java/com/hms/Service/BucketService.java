package com.hms.Service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    public AmazonS3 amazonS3;

    public String upload(MultipartFile file, String myhmsaws) {
        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file to upload");
        }

        File conyFile = null;
        try {
            // Create a temporary file with the original file name
            conyFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(conyFile);   // Uploading the file to conyFile from MultipartFile

            // Upload the file to S3
            amazonS3.putObject(myhmsaws, conyFile.getName(), conyFile);

            // Return the URL of the uploaded file
            return amazonS3.getUrl(myhmsaws, conyFile.getName()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading the file: " + e.getMessage());
        } finally {
            // Clean up: Delete the temporary file after upload
            if (conyFile != null && conyFile.exists()) {
                conyFile.delete();
            }
        }
    }
}