package ru.urfu.exporter;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис для экспорта документов в различные форматы.
 */
@Service
public class ExportService {

    private final Map<String, Exporter> exporters;

    public ExportService(List<Exporter> exporters) {
        this.exporters = exporters.stream()
                .collect(Collectors.toMap(
                        e -> e.format().toLowerCase(),
                        Function.identity(),
                        (a, b) -> {
                            throw new IllegalStateException("Дубликат экспортера для формата: " + a.format());
                        }
                ));
    }

    /**
     * Экспортирует документ в указанный формат
     *
     * @param path путь к выходному файлу
     * @param content содержимое документа
     * @param format формат экспорта
     *
     * @throws IllegalArgumentException если формат не поддерживается
     * @throws IOException ошибка записи файла
     * @throws DocumentException ошибка формирования PDF
     */
    public void export(String path, String content, String format) throws IOException, DocumentException {
        Exporter exporter = exporters.get(format.toLowerCase());
        if (exporter == null) {
            throw new IllegalArgumentException("Неподдерживаемый формат экспорта: " + format);
        }
        exporter.export(path, content);
    }
}
