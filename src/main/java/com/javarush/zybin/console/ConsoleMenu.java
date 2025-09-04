package com.javarush.zybin.console;

import com.javarush.zybin.cipher.BruteForce;
import com.javarush.zybin.cipher.Decrypt;
import com.javarush.zybin.cipher.Encrypt;
import com.javarush.zybin.file.FileManager;
import com.javarush.zybin.file.GettingPath;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

import static com.javarush.zybin.cipher.Alphabet.ALPHABET;

public class ConsoleMenu {
    private final Encrypt encrypt;
    private final Decrypt decrypt;
    private final BruteForce bruteForce;
    private final Scanner scanner;
    private boolean isRunner;
    private String userName;

    public ConsoleMenu() {
        this.encrypt = new Encrypt();
        this.decrypt = new Decrypt();
        this.bruteForce = new BruteForce();
        this.scanner = new Scanner(System.in);
        this.isRunner = true;
        this.userName = "Guest";
    }

    public boolean isRunner() {
        return isRunner;
    }

    public String getUserName() {
        return userName;
    }

    public void displayMainMenu() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         Main menu            ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ 1. Greetings                 ║");
        System.out.println("║ 2. Change the user name      ║");
        System.out.println("║ 3. Select the operation      ║");
        System.out.println("║ 0. Exit                      ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("Select the option (0-3): ");
    }

    public void operationMenu() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       Operation menu         ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ 1. Encrypt text file         ║");
        System.out.println("║ 2. Decrypt text file         ║");
        System.out.println("║ 3. Decrypt text brute force  ║");
        System.out.println("║ 0. Back                      ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("Select the option (0-3): ");
    }

    public int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please, enter a number between 0 and 3!");
            scanner.next();
            System.out.print("Select the option (0-3): ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void showGreeting() {
        System.out.println("\uD83D\uDC4B Hello, " + userName + "! Thank you for choosing our product");
    }

    public void changeUserName() {
        System.out.println("Enter your name: ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            userName = newName;
            System.out.println("Name has been successfully changed to: " + userName);
        } else {
            System.out.println("Incorrect input method!");
        }
    }

    public void handleEncryption() {
        try {
            System.out.println("Enter the encryption key (1-80)");
            int key = scanner.nextInt();
            if (key < ALPHABET.length && key > 0) {
                encrypt.setKey(key);
            } else {
                System.out.println("The key cannot be more than 80 or negative");
                handleEncryption();
            }
            scanner.nextLine();
            System.out.println("Write the path to the file or the choice will occur by default");
            String filePath = scanner.nextLine().trim();
            String content;
            if (!filePath.isEmpty()) {
                content = FileManager.readFile(filePath);
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("text.txt");
                content = FileManager.readFile(defaultPath.toString());
            }
            String encryptedContent = encrypt.action(content, encrypt.getKey());
            if (encryptedContent == null) {
                System.out.println("Error: encryption failed");
                return;
            }
            System.out.println("Enter the path to save the file or save will occur by default:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("encryptedText.txt").toString();
            }
            FileManager.writeFile(savePath, encryptedContent);
            System.out.println(userName + "! The encryption and saving the file has been successful\uD83D\uDE0E");

        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleDecryption() {
        try {
            System.out.println("Write the path to the file or the choice will occur by default");
            String filePath = scanner.nextLine().trim();
            String content;
            if (!filePath.isEmpty()) {
                content = FileManager.readFile(filePath);
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("encryptedText.txt");
                content = FileManager.readFile(defaultPath.toString());
            }
            String decrypted = decrypt.action(content, encrypt.getKey());
            if (decrypted == null) {
                System.out.println("Error: decryption failed");
                return;
            }
            System.out.println("Enter the path to save the file or save will occur by default:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("decodedText.txt").toString();
            }
            FileManager.writeFile(savePath, decrypted);
            System.out.println(userName + "! Decryption and saving the file is successful\uD83D\uDE0E");

        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleBruteForce() {
        try {
            System.out.println("Write the path to the file or the choice will occur by default");
            String filePath = scanner.nextLine().trim();
            String content;
            if (!filePath.isEmpty()) {
                content = FileManager.readFile(filePath);
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("encryptedText.txt");
                content = FileManager.readFile(defaultPath.toString());
            }
            System.out.println("Write the path to the dictionary file, or the choice will occur by default");
            String filePathDictionary = scanner.nextLine().trim();
            String contentDictionary;
            if (!filePathDictionary.isEmpty()) {
                contentDictionary = FileManager.readFile(filePathDictionary);
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("dict.txt");
                contentDictionary = FileManager.readFile(defaultPath.toString());
            }
            Set<String> dictionary = bruteForce.dictionaryProcessing(contentDictionary);
            String result = bruteForce.action(content, dictionary);
            if (result == null) {
                System.out.println("Error: decryption failed");
                return;
            }
            System.out.println("Enter the path to save the file or save will occur by default:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("decodedBruteForce.txt").toString();
            }
            FileManager.writeFile(savePath, result);
            System.out.println(userName +
                    "! The decoding by the Brute Force and saving the file was successful\uD83D\uDE0E \n Correct encryption key: " +
                    bruteForce.getBestKey());
        } catch (IOException e) {
            System.out.println("Error during decryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void returnDisplayMainMenu() {
        displayMainMenu();
    }

    public void exitMenu() {
        System.out.println("Goodbye, " + userName + "!\uD83D\uDC4B");
        System.out.println("Completion of work...");
        isRunner = false;
    }

}
