import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.processUserChoices();
    }

    public void displayMenu() {
        System.out.println("---------------------------------------");
        System.out.println("Please select one of the options below:");
        System.out.println("1. Start new game");
        System.out.println("0. Exit");
    }

    public void processUserChoices() {
        int userChoice;
        Scanner scanner = new Scanner(System.in);
        Choice choice = new Choice();
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
                    choice.startNewGame();
                    break;
                case 0:
                    choice.exit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        } while(userChoice!=0);
    }
}
