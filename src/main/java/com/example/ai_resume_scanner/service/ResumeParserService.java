package com.example.ai_resume_scanner.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeParserService {

    public String extractTextFromPdf(MultipartFile file) throws Exception {

        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);
        document.close();

        return text.replaceAll("\\s+", " ").trim();
    }
}
