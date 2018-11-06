import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Choice {

    private boolean isPlayerOneTurn = true;
    private boolean gameEnded = false;
    private boolean gameStarted = false;
    private boolean opponentStuck = false;
    private boolean isHumanPlayingHuman = true;
    private boolean isComputerPlayerOne = false;
    private Board board;
    private String fileName = "save.txt";

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
            char playerSymbol = isPlayerOneTurn ? Board.playerOne : Board.playerTwo;

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
                            System.out.println("No available moves. Winner is Player 1 ("+Board.playerOne+").");
                            break;
                        case 2:
                            System.out.println("No available moves. Winner is Player 2 ("+Board.playerTwo+").");
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
            char playerSymbol = isPlayerOneTurn ? Board.playerOne : Board.playerTwo;

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
                            System.out.println("No available moves. Winner is Player 1 ("+Board.playerOne+").");
                            break;
                        case 2:
                            System.out.println("No available moves. Winner is Player 2 ("+Board.playerTwo+").");
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

    public void saveGame() {
        //TODO: CHECK IF OVERRIDING A FILE
        //TODO: ADD A MESSAGE WHEN A FILE IS SAVED/LOADED
        if (gameStarted) {
            FileOutputStream fileOutputStream;
            PrintWriter printWriter;
            try {
                fileOutputStream = new FileOutputStream(fileName);
                printWriter = new PrintWriter(fileOutputStream);


                printWriter.println((isPlayerOneTurn ? 1 : 0)+" "+(isHumanPlayingHuman ? 1 : 0)+" "+(isComputerPlayerOne ? 1 : 0)+" "+(opponentStuck ? 1 : 0));
                printWriter.print(board.returnBoard());

                printWriter.close();
            } catch (IOException e) {
                System.out.println("Error writing file "+e);
            }
        } else {
            System.out.println("No stopped games found.");
        }
    }

    public void loadGame() {
        //TODO: NOTIFY IF OVERRIDING EXISTING GAME (gameStarted)
        //TODO: CHECK IF BOOL = 0
        //TODO: CHECK IF FILE EXISTS
        //TODO: CHOOSE FILE NAME (FOR SAVE AS WELL)
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            gameStarted = true;

            int input = bufferedReader.read();
            if (input=='1') {
                isPlayerOneTurn = true;
            } else {
                isPlayerOneTurn = false;
            }

            do {
                input = bufferedReader.read();
            } while((char)input==' ' || (char)input=='\n');
            if (input=='1') {
                isHumanPlayingHuman = true;
            } else {
                System.out.println(input);
                isHumanPlayingHuman = false;
            }

            do {
                input = bufferedReader.read();
            } while((char)input==' ' || (char)input=='\n');
            if (input=='1') {
                isComputerPlayerOne = true;
            } else {
                isComputerPlayerOne = false;
            }

            do {
                input = bufferedReader.read();
            } while((char)input==' ' || (char)input=='\n');
            if (input=='1') {
                opponentStuck = true;
            } else {
                opponentStuck = false;
            }

            System.out.println(isPlayerOneTurn+" "+isHumanPlayingHuman+" "+isComputerPlayerOne+" "+opponentStuck);

            char[][] boardArray = new char[Board.boardWidth][Board.boardHeight];
            for (int i=0; i<Board.boardHeight; i++) {
                for (int j=0; j<Board.boardWidth; j++) {
                    do {
                        input = bufferedReader.read();
                    } while((char)input==' ' || (char)input=='\n' || (char)input=='\r');
                    boardArray[j][i] = (char) input;
                }
            }
            board = new Board(boardArray);
            board.printBoard();

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * exit method prints the goodbye text
     */
    public void exit() {
        System.out.println("Goodbye...");
    }

}
