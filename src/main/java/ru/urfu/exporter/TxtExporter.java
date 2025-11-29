package ru.urfu.exporter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Экспортёр текста в TXT
 *
 * @author Daniil Mezev
 */
@Component("txt")
public class TxtExporter implements Exporter {
    @Override
    public void export(String path, String content) throws IOException {
        Files.writeString(Path.of(path), content);
    }
}

