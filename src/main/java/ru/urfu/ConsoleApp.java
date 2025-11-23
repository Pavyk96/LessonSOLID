package ru.urfu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.urfu.utils.ConsoleService;

import java.util.Scanner;

/**
 * Основной класс консольного приложения.
 * Реализует ввод команд и взаимодействие с сервисами.
 */
@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {

    private final ConsoleService consoleService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(ConsoleService consoleService) {
        this.consoleService = consoleService;
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
     * Старт программы
     */
    @Override
    public void run(String... args) {
        System.out.println("=== Консольное приложение ===");

        while (true) {
            System.out.println("\nКоманды: import, list, create, export, exit");
            System.out.print("> ");
            String cmd = scanner.nextLine().trim();

            switch (cmd) {
                case "create" -> consoleService.createDocument(scanner);
                case "import" -> consoleService.importDocument(scanner);
                case "list" -> consoleService.listDocuments();
                case "export" -> consoleService.exportDocument(scanner);
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }
}
