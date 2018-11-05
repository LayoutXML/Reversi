import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        char[][] newBoard = {{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'}, {'.','.','.','O','#','.','.','.'},
                {'.','.','.','#','O','.','.','.'}, {'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'},{'.','.','.','.','.','.','.','.'}};
        int userInputX=0, userInputY=0;
        boolean isPlayerOneTurn = true;
        Board board = new Board(newBoard);
        Scanner scanner = new Scanner(System.in);

        do {
            char playerSymbol = isPlayerOneTurn ? board.playerOne : board.playerTwo;

            board.printBoard();
            board.printAllAvailable(isPlayerOneTurn);
            System.out.println("\nPlayer's "+playerSymbol+" turn. Enter two digits separated by a space (x and y axis). Enter -1 to stop the game.");

            try {
                userInputX = scanner.nextInt();
                if (userInputX!=-1) {
                    userInputY = scanner.nextInt();

                    if (userInputX >= 0 && userInputX < 8 && userInputY >= 0 && userInputY < 8) {
                        if (board.placePiece(userInputX, userInputY, isPlayerOneTurn)) {
                            isPlayerOneTurn = !isPlayerOneTurn;
                        }
                    } else {
                        System.out.println("Coordinates must be positive and lower than 8.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect coordinates entered. Please try again.");
                scanner = new Scanner(System.in);
            }

        } while (userInputX!=-1);

    }
}
