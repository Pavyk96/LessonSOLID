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
@Component
public class TxtExporter implements Exporter {

    @Override
    public String getFormat() {
        return "txt";
    }

    @Override
    public void export(String path, String content) throws IOException {
        Files.writeString(Path.of(path), content);
    }
}

