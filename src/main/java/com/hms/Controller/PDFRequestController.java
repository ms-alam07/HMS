package com.hms.Controller;

import com.hms.Payload.PDFRequestDto;
import com.hms.Service.PDFRequestService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
public class PDFRequestController {

    private PDFRequestService pdfRequestService;

    public PDFRequestController(PDFRequestService pdfRequestService) {
        this.pdfRequestService = pdfRequestService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePDF(@RequestBody PDFRequestDto requestDto) {
        String response = pdfRequestService.generatePDF(requestDto);
        return ResponseEntity.ok(response);
    }
}
