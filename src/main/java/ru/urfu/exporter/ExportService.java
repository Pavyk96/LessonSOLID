package ru.urfu.exporter;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import ru.urfu.document.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.nio.file.Files;


/**
 * Сервис для экспорта документов в различные форматы.
 */
@Service
public class ExportService {

    private static final Path OUTPUT_DIR = Path.of(System.getProperty("user.home"), "lessonSOLID");

    private final Map<String, Exporter> exporters;

    public ExportService(List<Exporter> exporters) {
        this.exporters = exporters.stream()
                .collect(Collectors.toMap(
                        e -> e.getFormat().toLowerCase(),
                        Function.identity(),
                        (a, b) -> {
                            throw new IllegalStateException("Дубликат экспортера для формата: " + a.getFormat());
                        }
                ));
    }

    /**
     * Экспортирует документ в указанный формат в директорию приложения.
     *
     * @param document документ для экспорта
     * @param format формат экспорта
     *
     * @return путь к созданному файлу
     *
     * @throws IllegalArgumentException если формат не поддерживается
     * @throws IOException ошибка создания директории или записи файла
     * @throws DocumentException ошибка формирования PDF
     */
    public Path export(Document document, String format) throws IOException, DocumentException {
        Exporter exporter = exporters.get(format.toLowerCase());
        if (exporter == null) {
            throw new IllegalArgumentException("Неподдерживаемый формат экспорта: " + format);
        }

        Files.createDirectories(OUTPUT_DIR);

        String ext = format.toLowerCase();
        Path outputPath = OUTPUT_DIR.resolve(document.name() + "." + ext);

        exporter.export(outputPath.toString(), document.content());

        return outputPath;
    }
}
