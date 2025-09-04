package com.javarush.zybin.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileManager {

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

    public static String readFile(String path) {
        validatePath(path, "File reading");

        try {
            Path inputPath = Paths.get(path);

            if (!Files.exists(inputPath)) {
                throw new FileNotFoundException("File not found: " + path, null);
            }

            if (Files.isDirectory(inputPath)) {
                throw new InvalidPathException("The specified path is a directory, not a file: " + path);
            }

            List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
            return String.join(System.lineSeparator(), lines);

        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("The file does not exist: " + path, e);
        } catch (AccessDeniedException e) {
            throw new FileReadException("No access for reading file: " + path, e);
        } catch (IOException e) {
            throw new FileReadException("Error input-output when reading a file: " + path, e);
        }
    }

    public static void writeFile(String path, String content) {
        validatePath(path, "File recording");

        try {
            Path outputPath = Paths.get(path);
            Path parentDir = outputPath.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                try {
                    Files.createDirectories(parentDir);
                } catch (IOException e) {
                    throw new FileWriteException("Failed to create a directory for the path: " + path, e);
                }
            }

            if (Files.exists(outputPath) && !Files.isWritable(outputPath)) {
                throw new AccessDeniedException("No right to write to the file: " + path);
            }

            Files.writeString(outputPath, content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);

        } catch (AccessDeniedException e) {
            throw new FileWriteException("No access for file recording: " + path, e);
        } catch (IOException e) {
            throw new FileWriteException("Error input-output when writing a file: " + path, e);
        }
    }

    private static void validatePath(String path, String operation) {
        if (path == null || path.trim().isEmpty()) {
            throw new InvalidPathException("The path cannot be empty for surgery: " + operation);
        }

        if (path.contains("\0")) {
            throw new InvalidPathException("The path contains unacceptable symbols: " + path);
        }
    }

}
