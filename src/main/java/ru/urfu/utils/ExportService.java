package ru.urfu.utils;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для экспорта документов в различные форматы
 */
public class ExportService {

    private final Map<String, Exporter> exporters = new HashMap<>();

    public ExportService() {
        exporters.put("pdf", new PdfExporter());
        //добавляем сюда все экспортеры
    }

    /**
     * Экспортировать документ в указанный формат
     *
     * @param path
     * @param content
     * @param format
     * @throws IOException
     * @throws DocumentException
     */
    public void export(String path, String content, String format) throws IOException, DocumentException {
        Exporter exporter = exporters.get(format.toLowerCase());
        exporter.export(path, content);
    }
}
