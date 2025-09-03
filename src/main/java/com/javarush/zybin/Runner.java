package com.javarush.zybin;



public class Runner {

    public static void main(String[] args) {
        Cipher cipher = new Cipher();
        ConsoleMenu consoleMenu = new ConsoleMenu(cipher);
        consoleMenu.handleEncryption();
        consoleMenu.handleBruteForce();

    }

}

