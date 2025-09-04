package com.javarush.zybin.cipher;

public class Alphabet {
    public static final char[] ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ,.!? 123456789"
            .toCharArray();

    public static int getLength() {
        return ALPHABET.length;
    }
}
