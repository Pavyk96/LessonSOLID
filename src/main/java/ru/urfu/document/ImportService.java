package ru.urfu.document;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Сервис для импорта документов из файлов
 *
 * @author Mezev Daniil
 */
@Service
public class ImportService {

    private final DocumentService documentService;

    public ImportService(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Импортирует текстовый файл и добавляет его как документ в память.
     *
     * @param pathStr путь к txt файлу
     * @throws IOException если файл не найден или не удаётся прочитать
     */
    public void importTxt(String pathStr) throws IOException {
        Path path = Path.of(pathStr);

        if (!Files.exists(path)) {
            throw new IOException("Файл не найден: " + path);
        }

        String content = Files.readString(path);
        String name = path.getFileName().toString();

        documentService.createDocument(name, content);
        System.out.println("Документ импортирован: " + name);
    }
}
