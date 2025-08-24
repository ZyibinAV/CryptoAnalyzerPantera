package com.javarush.zybin;

public class Cipher {

    private static final char[] ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ,.!? ".toCharArray();
    public int key;

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



    public static String decrypt(String text, int key) {
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
}
