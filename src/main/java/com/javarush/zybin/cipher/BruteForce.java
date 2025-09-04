package com.javarush.zybin.cipher;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.zybin.cipher.Alphabet.ALPHABET;

public class BruteForce {

    private int bestKey = -1;
    private final Decrypt decrypt;

    public BruteForce() {
        this.decrypt = new Decrypt();
    }

    public int getBestKey() {
        return bestKey;
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

    public String action(String text, Set<String> dictionary) {

        int maxMatches = 0;
        String bestDecryption = "";

        System.out.println(" \uD83D\uDE80 Starting brute force attack...");
        System.out.println("=".repeat(60));

        for (int key = 0; key <= ALPHABET.length - 1; key++) {
            String decryption = decrypt.action(text, key);
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
