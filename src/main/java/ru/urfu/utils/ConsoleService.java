package ru.urfu.utils;

import ru.urfu.document.Document;
import ru.urfu.document.DocumentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import com.itextpdf.text.DocumentException;
import java.util.Scanner;

/**
 * Сервис для реализации логики команд
 *
 * @author Daniil Mezev
 */
public class ConsoleService {

    private final DocumentService documentService;
    private final ExportService exportService;

    public ConsoleService(DocumentService documentService, ExportService exportService) {
        this.documentService = documentService;
        this.exportService = exportService;
    }

    /**
     * Создаёт новый документ.
     */
    public void createDocument(Scanner scanner) {
        System.out.print("Введите имя документа: ");
        String name = scanner.nextLine().trim();

        System.out.println("Введите содержимое документа (пустая строка — завершить ввод):");

        StringBuilder content = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            content.append(line).append(System.lineSeparator());
        }

        documentService.createDocument(name, content.toString());
        System.out.println("Документ создан и сохранён в памяти.");
    }

    /**
     * Импортирует документ из текстового файла.
     */
    public void importDocument(Scanner scanner) {
        System.out.print("Введите путь к txt файлу: ");
        String path = scanner.nextLine();

        try {
            documentService.importTxt(path);
            System.out.println("Документ успешно импортирован.");
        } catch (IOException e) {
            System.out.println("Ошибка импорта: " + e.getMessage());
        }
    }

    /**
     * Выводит список всех документов.
     */
    public void listDocuments() {
        List<Document> documents = documentService.list();
        if (documents.isEmpty()) {
            System.out.println("Документов нет");
            return;
        }
        int i = 0;
        for (Document doc : documents) {
            System.out.println(i + ": " + doc.name());
            i++;
        }
    }

    /**
     * Экспортирует документ в указанный формат.
     */
    public void exportDocument(Scanner scanner) {
        System.out.print("Введите номер документа: ");
        int index = Integer.parseInt(scanner.nextLine());

        Optional<Document> documentOptional = documentService.getDocument(index);
        if (documentOptional.isEmpty()) {
            System.out.println("Нет документа с таким номером.");
            return;
        }

        Document document = documentOptional.get();

        //добавить новые оповещение о новых импортах
        System.out.print("Введите формат (txt/pdf/docx): ");
        String format = scanner.nextLine().trim().toLowerCase();

        try {
            Files.createDirectories(Path.of(System.getProperty("user.home"), "lessonSOLID"));
        } catch (IOException e) {
            System.out.println("Ошибка создания директории: " + e);
            return;
        }

        Path outputPath = Path.of(System.getProperty("user.home"), "lessonSOLID").resolve(document.name() + "." + format);

        try {
            exportService.export(outputPath.toString(), document.content(), format);
            System.out.println("Экспорт выполнен: " + outputPath);
        } catch (IOException | DocumentException e) {
            System.out.println("Ошибка экспорта: " + e.getMessage());
        }
    }
}
