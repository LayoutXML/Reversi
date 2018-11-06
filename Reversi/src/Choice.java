import java.util.InputMismatchException;
import java.util.Scanner;

public class Choice {

    private boolean isPlayerOneTurn = true;
    private boolean gameEnded = false;
    private boolean gameStarted = false;
    private boolean opponentStuck = false;
    private boolean isHumanPlayingHuman;
    private boolean isComputerPlayerOne;
    private Board board;

    @SuppressWarnings("Duplicates")
    public void startNewGameHvH() {
        int userInputX=0, userInputY=0;
        isPlayerOneTurn = true;
        gameEnded = false;
        gameStarted = true;
        opponentStuck = false;
        isHumanPlayingHuman = true;

        board = new Board();
        Scanner scanner = new Scanner(System.in);

        do {
            char playerSymbol = isPlayerOneTurn ? board.playerOne : board.playerTwo;

            board.printBoard();
            int[] scores = board.calculateScore();
            System.out.println("Player 1 (#) score is: "+scores[0]+"\nPlayer 2 (O) score is: "+scores[1]+"\n");

            int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
            if (availableMoves.length==0) {
                if (opponentStuck) {
                    gameEnded = true;
                    gameStarted = false;
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
                System.out.println("Available moves ("+availableMoves.length+"):");
                for (int i=0; i<availableMoves.length; i++) {
                    System.out.println((availableMoves[i][0]+1) + " " + (availableMoves[i][1]+1));
                }

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

    @SuppressWarnings("Duplicates")
    public void startNewGameHvC() {
        int userInputX=0, userInputY=0;
        isPlayerOneTurn = true;
        gameEnded = false;
        gameStarted = true;
        opponentStuck = false;
        isHumanPlayingHuman = false;

        board = new Board();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Which player would you like computer to play? Enter 1 or 2: ");
            try {
                userInputX = scanner.nextInt();
                if (userInputX==1) {
                    isComputerPlayerOne = true;
                } else if (userInputX==2) {
                    isComputerPlayerOne = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input entered. Please try again.");
                scanner = new Scanner(System.in);
            }
        } while (userInputX!=1 && userInputX!=2);

        userInputX = 0;
        do {
            char playerSymbol = isPlayerOneTurn ? board.playerOne : board.playerTwo;

            board.printBoard();
            int[] scores = board.calculateScore();
            System.out.println("Player 1 (#) score is: "+scores[0]+"\nPlayer 2 (O) score is: "+scores[1]+"\n");

            int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
            if (availableMoves.length==0) {
                if (opponentStuck) {
                    gameEnded = true;
                    gameStarted = false;
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
                if ((isPlayerOneTurn && !isComputerPlayerOne) || (!isPlayerOneTurn && isComputerPlayerOne)) {
                    System.out.println("\nPlayer's " + playerSymbol + " turn. Enter two digits separated by a space (x and y axis). Enter -1 to stop the game.");

                    System.out.println("Available moves:");
                    for (int i=0; i<availableMoves.length; i++) {
                        System.out.println((availableMoves[i][0]+1) + " " + (availableMoves[i][1]+1));
                    }
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
                } else {
                    System.out.println("\nPlayer's " + playerSymbol + " turn. ");

                    if (board.placePieceForComputer(availableMoves, isComputerPlayerOne)) {
                        isPlayerOneTurn = !isPlayerOneTurn;
                    }
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
