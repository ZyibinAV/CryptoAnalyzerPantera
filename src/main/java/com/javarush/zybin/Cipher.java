package com.javarush.zybin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Cipher {

    public static final char[] ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ,.!? 123456789".toCharArray();
    private int key;
    private int bestKey = -1;

    public int getKey() {
        return key;
    }

    public int getBestKey() {
        return bestKey;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String encrypt(String text, int key) {
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

    public String decrypt(String text, int key) {
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
                newIndex = (index - key) % ALPHABET.length;
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


    public Set<String> dictionaryProcessing(String dictionaryText) throws IOException {
        Set<String> dictionary = new HashSet<>();

        String[] words = dictionaryText.toLowerCase().split("\\P{L}+");
        for (String word : words) {
            if (word.length() > 2 && !word.matches(".*\\d.*")) {
                dictionary.add(word);
            }
        }
        return dictionary;
    }

    private static int countMatches(String text, Set<String> dictionary) {
        int matches = 0;
        String[] words = text.toLowerCase().split("\\P{L}+");
        for (String word : words) {
            if (word.length() > 2 && dictionary.contains(word)) {
                matches++;
            }
        }
        return matches;
    }

    public String bruteForce(String text, Set<String> dictionary) {

        int maxMatches = 0;
        String bestDecryption = "";

        System.out.println("Starting brute force attack...");
        System.out.println("=".repeat(60));

        for (int key = 0; key <= ALPHABET.length - 1; key++) {
            String decryption = decrypt(text, key);
            int matches = countMatches(decryption, dictionary);
            String preview = decryption.length() > 50 ?
                    decryption.substring(0, 50) + "..." : decryption;

            System.out.printf("Key %2d: %-50s | Matches: %d%n", key, preview, matches);

            if (matches > maxMatches) {
                maxMatches = matches;
                bestKey = key;
                bestDecryption = decryption;
            }
        }
        System.out.println("=".repeat(60));
        System.out.println("Best key found: " + bestKey + " with " + maxMatches + " matches");
        return bestDecryption;
    }
}
