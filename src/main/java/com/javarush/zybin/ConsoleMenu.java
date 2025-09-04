package com.javarush.zybin;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

public class ConsoleMenu {
    private final Cipher cipher;
    private final Scanner scanner;
    private boolean isRunner;
    private String userName;

    public ConsoleMenu() {
        this.cipher = new Cipher();
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
            if (key < Cipher.ALPHABET.length && key > 0) {
                cipher.setKey(key);
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
                if (content == null) {
                    System.out.println("Error: cannot read file: " + filePath);
                    return;
                }
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("text.txt");
                content = FileManager.readFile(defaultPath.toString());
                if (content == null) {
                    System.out.println("Error: default file not found: " + defaultPath);
                    return;
                }
            }
            String encryptedContent = cipher.encrypt(content, cipher.getKey());
            if (encryptedContent == null) {
                System.out.println("Error: encryption failed");
                return;
            }
            System.out.println("Enter the path to save the file:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("encryptedText.txt").toString();
            }
            FileManager.writeFile(savePath, encryptedContent);

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
                if (content == null) {
                    System.out.println("Error: cannot read file: " + filePath);
                    return;
                }
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("encryptedText.txt");
                content = FileManager.readFile(defaultPath.toString());
                if (content == null) {
                    System.out.println("Error: default file not found: " + defaultPath);
                    return;
                }
            }
            String decrypted = cipher.decrypt(content, cipher.getKey());
            if (decrypted == null) {
                System.out.println("Error: decryption failed");
                return;
            }
            System.out.println("Enter the path to save the file:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("decodedText.txt").toString();
            }
            FileManager.writeFile(savePath, decrypted);

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
                if (content == null) {
                    System.out.println("Error: cannot read file: " + filePath);
                    return;
                }
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("encryptedText.txt");
                content = FileManager.readFile(defaultPath.toString());
                if (content == null) {
                    System.out.println("Error: default file not found: " + defaultPath);
                    return;
                }
            }
            System.out.println("Write the path to the dictionary file, or the choice will occur by default");
            String filePathDictionary = scanner.nextLine().trim();
            String contentDictionary;
            if (!filePathDictionary.isEmpty()) {
                contentDictionary = FileManager.readFile(filePathDictionary);
                if (contentDictionary == null) {
                    System.out.println("Error: cannot read file: " + filePathDictionary);
                    return;
                }
            } else {
                Path defaultPath = GettingPath.getCurrentDir().resolve("dict.txt");
                contentDictionary = FileManager.readFile(defaultPath.toString());
                if (contentDictionary == null) {
                    System.out.println("Error: default file not found: " + defaultPath);
                    return;
                }
            }
            Set<String> dictionary = cipher.dictionaryProcessing(contentDictionary);
            String bruteForce = cipher.bruteForce(content, dictionary);
            if (bruteForce == null) {
                System.out.println("Error: decryption failed");
                return;
            }
            System.out.println("Enter the path to save the file:");
            String savePath = scanner.nextLine().trim();

            if (savePath.isEmpty()) {
                savePath = GettingPath.getCurrentDir().resolve("decodedBruteForce.txt").toString();
            }
            FileManager.writeFile(savePath, bruteForce);
        } catch (IOException e) {
            System.out.println("Error during decryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void returnDisplayMainMenu() {
        displayMainMenu();
    }

    public void exitMenu() {
        System.out.println("Goodbye, " + userName + "!");
        System.out.println("Completion of work...");
        isRunner = false;
    }

}
