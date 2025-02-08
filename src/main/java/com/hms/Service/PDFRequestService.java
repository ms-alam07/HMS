package com.hms.Service;

import com.hms.Payload.PDFRequestDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class PDFRequestService {

    public String generatePDF(PDFRequestDto requestDto) {
        try {
            String path = requestDto.getFilePath();

            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph(requestDto.getTitle()).setBold().setFontSize(18));

            document.add(new Paragraph(requestDto.getContent()));

            float[] columnsWidth ={2,1};
            Table table = new Table(columnsWidth);
            table.addHeaderCell("Name");
            table.addHeaderCell("No of Days");

            table.addCell("Saquib,5");
            table.addCell("");
            document.close();
            return "PDF created successfully: " + path;
        } catch (FileNotFoundException e) {
            return "Error creating PDF: " + e.getMessage();
        }
    }
}
