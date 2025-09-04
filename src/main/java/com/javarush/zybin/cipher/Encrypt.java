package com.javarush.zybin.cipher;

import static com.javarush.zybin.cipher.Alphabet.ALPHABET;

public class Encrypt {
    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String action(String text, int key) {
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
