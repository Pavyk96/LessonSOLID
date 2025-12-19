package ru.urfu.exporter;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Класс пример, как можно добавить новый экспорт в 1 действие
 *
 * @author Daniil Mezev
 */
@Component
public class DocxExporter implements Exporter {

    @Override
    public String format() {
        return "docx";
    }

    @Override
    public void export(String path, String content) throws IOException {
        // реализация экспорта в DOCX
    }
}

