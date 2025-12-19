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
     * Возвращает идентификатор поддерживаемого формата
     *
     * @return строковый идентификатор формата
     */
    String getFormat();

    /**
     * Экспортирует содержимое документа в файл по указанному пути
     *
     * @param path путь к выходному файлу
     * @param content содержимое документа
     *
     * @throws IOException ошибка записи файла
     * @throws DocumentException ошибка формирования
     */
    void export(String path, String content) throws IOException, DocumentException;
}
