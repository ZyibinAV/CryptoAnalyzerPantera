package com.javarush.zybin;

public class ConsoleMenuRunner {
    ConsoleMenu consoleMenu = new ConsoleMenu();

    public void start() {
        System.out.println("=======Welcome to the cipher of Caesar in the menu=======");

        while (consoleMenu.isRunner()) {
            consoleMenu.displayMainMenu();
            int choice = consoleMenu.getUserChoice();

            switch (choice) {
                case 1:
                    consoleMenu.showGreeting();
                    break;
                case 2:
                    consoleMenu.changeUserName();
                    break;
                case 3:
                    consoleMenu.operationMenu();
                    int subChoice = consoleMenu.getUserChoice();
                    switch (subChoice) {
                        case 1:
                            consoleMenu.handleEncryption();
                            break;
                        case 2:
                            consoleMenu.handleDecryption();
                            break;
                        case 3:
                            consoleMenu.handleBruteForce();
                            break;
                        case 0:
                            consoleMenu.returnDisplayMainMenu();
                        default:
                            System.out.println(consoleMenu.getUserName() + ", the wrong choice");
                            break;
                    }
                    break;
                case 0:
                    consoleMenu.exitMenu();
                    break;
                default:
                    System.out.println(consoleMenu.getUserName() + ", wrong choice, please repeat the choice from 0 to 3");
            }
        }
    }
}
