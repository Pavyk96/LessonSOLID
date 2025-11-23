package ru.urfu.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * Экспортёр текста в PDF.
 */
public class PdfExporter implements Exporter {

    @Override
    public void export(String outputPath, String content)
            throws DocumentException, java.io.IOException {

        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, outputStream);

            pdf.open();
            pdf.add(new Paragraph(content));
            pdf.close();
        }
    }
}