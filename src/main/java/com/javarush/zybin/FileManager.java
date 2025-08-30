package com.javarush.zybin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager {

    static String readFile(String path) {
        try {
            Path inputPath = Paths.get(path);
            List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
            return String.join(System.lineSeparator(), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void writeFile (String path, String content) {
        try {
            Path outputPath = Paths.get(path);
            if (outputPath.getParent() == null) {
                Files.createDirectories(outputPath.getParent());
            } else {
                    Files.writeString(outputPath, content,
                            StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
