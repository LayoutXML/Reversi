import java.util.InputMismatchException;
import java.util.Scanner;

public class Choice {

    private boolean isPlayerOneTurn = true;
    private boolean gameEnded = false;
    private boolean gameStarted = false;
    private boolean opponentStuck = false;
    private Board board;

    public void startNewGame() {
        int userInputX=0, userInputY=0;
        isPlayerOneTurn = true;
        gameEnded = false;
        gameStarted = true;
        opponentStuck = false;

        board = new Board();
        Scanner scanner = new Scanner(System.in);

        do {
            char playerSymbol = isPlayerOneTurn ? board.playerOne : board.playerTwo;

            board.printBoard();
            if (!board.printAllAvailable(isPlayerOneTurn)) {
                if (opponentStuck) {
                    gameEnded = true;
                    int outcome = board.winner();
                    switch (outcome) {
                        case 1:
                            System.out.println("No available moves. Winner is Player 1 ("+board.playerOne+").");
                            break;
                        case 2:
                            System.out.println("No available moves. Winner is Player 2 ("+board.playerTwo+").");
                            break;
                        default:
                            System.out.println("No available moves. It's a tie.");
                            break;

                    }
                } else {
                    opponentStuck = true;
                    System.out.println("No available moves.");
                    isPlayerOneTurn = !isPlayerOneTurn;
                }
            } else {
                opponentStuck = false;
                System.out.println("\nPlayer's " + playerSymbol + " turn. Enter two digits separated by a space (x and y axis). Enter -1 to stop the game.");

                try {
                    userInputX = scanner.nextInt();
                    if (userInputX != -1) {
                        userInputY = scanner.nextInt();

                        userInputX--;
                        userInputY--;
                        if (userInputX >= 0 && userInputX < 8 && userInputY >= 0 && userInputY < 8) {
                            if (board.placePiece(userInputX, userInputY, isPlayerOneTurn)) {
                                isPlayerOneTurn = !isPlayerOneTurn;
                            }
                        } else {
                            System.out.println("Coordinates must be positive and lower than 9.");
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect coordinates entered. Please try again.");
                    scanner = new Scanner(System.in);
                }
            }

        } while (userInputX!=-1 && !gameEnded);

    }

    /**
     * exit method prints the goodbye text
     */
    public void exit() {
        System.out.println("Goodbye...");
    }

}
