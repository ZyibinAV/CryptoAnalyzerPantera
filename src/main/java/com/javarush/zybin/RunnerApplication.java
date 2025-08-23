package com.javarush.zybin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

public class RunnerApplication {
    public static final char[] ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ,.!? ".toCharArray();

    public static void main(String[] args) {
        int key = 50;

        Path path = Paths.get("C:\\Users\\ZybinAV\\Desktop\\test\\text.txt");
        List<String> textFile;
        try {
            textFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String line : textFile) {
            String encryptedLine = encrypt(line, key);
            System.out.println("Original: " + line);
            System.out.println("Encrypted: " + encryptedLine);
        }


    }

    public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int index = -1;
            int newIndex;
            for (int i = 0; i < ALPHABET.length; i++) {
                if (ALPHABET[i] == character) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                newIndex = (index + key) % ALPHABET.length;
                if (newIndex < 0) {
                    newIndex += ALPHABET.length;
                }
                result.append(ALPHABET[newIndex]);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}

