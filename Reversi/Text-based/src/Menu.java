import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Menu class that displays a game menu, reads and validates user input (choice) and calls appropriate method in Game object
 */
public class Menu {

    /**
     * Main menu that creates a menu object
     * @param args args
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.processUserChoices();
    }

    /**
     * Method that displays game menu
     */
    public void displayMenu() {
        System.out.println("---------------------------------------");
        System.out.println("Please select one of the options below:");
        System.out.println("1. Start new game (human vs human)");
        System.out.println("2. Start new game (human vs computer)");
        System.out.println("3. Resume paused game");
        System.out.println("4. Save stopped game");
        System.out.println("5. Load saved game");
        System.out.println("0. Exit");
    }

    /**
     * Method that reads user input (menu option choice), validates the option and calls appropriate methods in Game object
     */
    public void processUserChoices() {
        int userChoice;
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        do {
            displayMenu();
            try {
                userChoice = scanner.nextInt();
                System.out.println("---------------------------------------");
            }
            catch (InputMismatchException e) {
                userChoice=-1;
                scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                System.out.println("Invalid input. Please try again");
            }
            switch (userChoice) {
                case 1:
                    game.startNewGame(true);
                    break;
                case 2:
                    game.startNewGame(false);
                    break;
                case 3:
                    game.resumeGame();
                    break;
                case 4:
                    game.saveGame();
                    break;
                case 5:
                    game.loadGame();
                    break;
                case 0:
                    game.exit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        } while(userChoice!=0);
    }
}
