package com.javarush.zybin;



public class Runner {

    public static void main(String[] args) {
        int key = 3;

        String textFile = FileManager.readFile("C:\\Users\\ZybinAV\\Desktop\\Projekt\\CryptoAnalyzerPantera\\text\\text.txt");
        //String encrypt = Cipher.encrypt(textFile, key);
        FileManager.writeFile("C:\\Users\\ZybinAV\\Desktop\\test\\encrypt.txt", Cipher.encrypt(textFile, key));
        String dicr = FileManager.readFile("C:\\Users\\ZybinAV\\Desktop\\test\\encrypt.txt");
        FileManager.writeFile("C:\\Users\\ZybinAV\\Desktop\\test\\decrypt.txt", Cipher.decrypt(dicr, key));
    }


}

