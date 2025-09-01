package com.javarush.zybin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileManager {

    // Собственные исключения для лучшей семантики
    public static class FileReadException extends RuntimeException {
        public FileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class FileWriteException extends RuntimeException {
        public FileWriteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidPathException extends RuntimeException {
        public InvalidPathException(String message) {
            super(message);
        }
    }

    public static class FileNotFoundException extends RuntimeException {
        public FileNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static String readFile(String path) {
        validatePath(path, "Чтение файла");

        try {
            Path inputPath = Paths.get(path);

            // Проверка существования файла
            if (!Files.exists(inputPath)) {
                throw new FileNotFoundException("Файл не найден: " + path, null);
            }

            // Проверка, что это действительно файл, а не директория
            if (Files.isDirectory(inputPath)) {
                throw new InvalidPathException("Указанный путь является директорией, а не файлом: " + path);
            }

            List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
            return String.join(System.lineSeparator(), lines);

        } catch (InvalidPathException | FileNotFoundException e) {
            // Перебрасываем наши собственные исключения
            throw e;
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("Файл не существует: " + path, e);
        } catch (AccessDeniedException e) {
            throw new FileReadException("Нет прав доступа для чтения файла: " + path, e);
        } catch (IOException e) {
            throw new FileReadException("Ошибка ввода-вывода при чтении файла: " + path, e);
        }
    }

    static void writeFile(String path, String content) {
        validatePath(path, "Запись файла");

        try {
            Path outputPath = Paths.get(path);
            Path parentDir = outputPath.getParent();

            // Создание директорий, если они не существуют
            if (parentDir != null && !Files.exists(parentDir)) {
                try {
                    Files.createDirectories(parentDir);
                } catch (IOException e) {
                    throw new FileWriteException("Не удалось создать директории для пути: " + path, e);
                }
            }

            // Проверка прав на запись
            if (Files.exists(outputPath) && !Files.isWritable(outputPath)) {
                throw new AccessDeniedException("Нет прав на запись в файл: " + path);
            }

            Files.writeString(outputPath, content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);

        } catch (AccessDeniedException e) {
            throw new FileWriteException("Нет прав доступа для записи файла: " + path, e);
        } catch (IOException e) {
            throw new FileWriteException("Ошибка ввода-вывода при записи файла: " + path, e);
        }
    }

    // Валидация пути
    private static void validatePath(String path, String operation) {
        if (path == null || path.trim().isEmpty()) {
            throw new InvalidPathException("Путь не может быть пустым для операции: " + operation);
        }

        if (path.contains("\0")) {
            throw new InvalidPathException("Путь содержит недопустимые символы: " + path);
        }
    }

}
