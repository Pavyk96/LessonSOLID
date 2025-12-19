package ru.urfu.exporter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Экспортёр текста в PDF.
 */
@Component
public class PdfExporter implements Exporter {

    @Override
    public String format() {
        return "pdf";
    }

    @Override
    public void export(String outputPath, String content)
            throws DocumentException, IOException {

        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, outputStream);

            pdf.open();
            pdf.add(new Paragraph(content));
            pdf.close();
        }
    }
}