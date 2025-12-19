package ru.urfu;


import com.itextpdf.text.DocumentException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.urfu.document.Document;
import ru.urfu.document.DocumentService;
import ru.urfu.document.ImportService;
import ru.urfu.exporter.ExportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Основной класс консольного приложения.
 * Реализует ввод команд и взаимодействие с сервисами.
 */
@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {

    public static final Path OUTPUT_DIR = Path.of(System.getProperty("user.home"), "lessonSOLID");

    private final Scanner scanner = new Scanner(System.in);
    private final DocumentService documentService;
    private final ExportService exportService;
    private final ImportService importService;

    public ConsoleApp(DocumentService documentService,
                      ExportService exportService,
                      ImportService importService) {
        this.documentService = documentService;
        this.exportService = exportService;
        this.importService = importService;
    }

    /**
     * Точка входа приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

    /**
     * Запуск консольного UI.
     *
     * @param args аргументы командной строки
     */
    @Override
    public void run(String... args) {
        System.out.println("=== Консольное приложение ===");

        while (true) {
            System.out.println("\nКоманды: import, list, create, export, exit");
            System.out.print("> ");

            String cmd = scanner.nextLine().trim();

            switch (cmd) {
                case "create" -> handleCreate();
                case "import" -> handleImport();
                case "list" -> handleList();
                case "export" -> handleExport();
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    /**
     * Обработчик команды create - читает имя и содержимое документа из консоли и создаёт документ в памяти
     */
    private void handleCreate() {
        System.out.print("Введите имя документа: ");
        String name = scanner.nextLine().trim();

        System.out.println("Введите содержимое документа (пустая строка - завершить ввод):");
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
     * Обработчик команды import - читает путь к txt и запускает импорт
     */
    private void handleImport() {
        System.out.print("Введите путь к txt файлу: ");
        String path = scanner.nextLine().trim();

        try {
            importService.importTxt(path);
            System.out.println("Документ успешно импортирован.");
        } catch (IOException e) {
            System.out.println("Ошибка импорта: " + e.getMessage());
        }
    }

    /**
     * Обработчик команды list - выводит список всех документов
     */
    private void handleList() {
        List<Document> documents = documentService.list();
        if (documents.isEmpty()) {
            System.out.println("Документов нет");
            return;
        }

        for (int i = 0; i < documents.size(); i++) {
            System.out.println(i + ": " + documents.get(i).name());
        }
    }

    /**
     * Обработчик команды export - читает индекс документа и формат, выполняет экспорт в папку ~/lessonSOLID
     */
    private void handleExport() {
        System.out.print("Введите номер документа: ");
        int index = Integer.parseInt(scanner.nextLine());

        Optional<Document> documentOptional = documentService.getDocument(index);
        if (documentOptional.isEmpty()) {
            System.out.println("Нет документа с таким номером.");
            return;
        }

        System.out.print("Введите формат экспорта: ");
        String format = scanner.nextLine().trim().toLowerCase();

        try {
            Files.createDirectories(OUTPUT_DIR);
        } catch (IOException e) {
            System.out.println("Ошибка создания директории: " + e.getMessage());
            return;
        }

        Document document = documentOptional.get();
        Path outputPath = OUTPUT_DIR.resolve(document.name() + "." + format);

        try {
            exportService.export(outputPath.toString(), document.content(), format);
            System.out.println("Экспорт выполнен: " + outputPath);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException | DocumentException e) {
            System.out.println("Ошибка экспорта: " + e.getMessage());
        }
    }
}
