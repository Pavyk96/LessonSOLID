package ru.urfu.exporter;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Интерфейс для экспорта документов в различные форматы
 *
 * @author Daniil Mezev
 */
public interface Exporter {

    /**
     * Экспортировать содержимое в указанный файл
     */
    void export(String path, String content) throws IOException, DocumentException;
}
