import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
/**
 * Game class that handles main game logic - starting and resuming games, running the game, saving and loading files
 */
public class Game {

    private boolean isPlayerOneTurn = true;
    public boolean gameStarted = false;
    private boolean opponentStuck = false;
    private boolean isHumanPlayingHuman = true;
    private boolean isComputerPlayerOne = false;
    private Board board;
    private String fileName = "save.txt";

    /**
     * Method that prepares board and other variables to play a game
     */
    public void startNewGameHvH() {
        isPlayerOneTurn = true;
        gameStarted = true;
        opponentStuck = false;
        this.isHumanPlayingHuman = true;
        board = new Board();
    }

    public void startNewGameHvC(boolean isComputerPlayerOne) {
        isPlayerOneTurn = true;
        gameStarted = true;
        opponentStuck = false;
        this.isHumanPlayingHuman = false;
        this.isComputerPlayerOne = isComputerPlayerOne;
        board = new Board();
        if (this.isComputerPlayerOne) {
            int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
            performComputerMove(availableMoves);
            board.printBoard();
            int[] scores = board.calculateScore();
            System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
        }
    }

    /**
     * Method that saves the game to a file
     */
    public void saveGame() {
        if (gameStarted) {
            File file = new File(fileName);
            int input = 1;
            if (file.exists()) {
                System.out.println("This will override existing file. Enter 1 to continue, 0 to abort.");
                Scanner scanner = new Scanner(System.in);
                do {
                    try {
                        input = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Incorrect input entered. Please try again.");
                        scanner = new Scanner(System.in);
                    }
                } while (input!=1 && input!=0);
            }
            if (input==1) {
                FileOutputStream fileOutputStream;
                PrintWriter printWriter;
                try {
                    fileOutputStream = new FileOutputStream(fileName);
                    printWriter = new PrintWriter(fileOutputStream);

                    printWriter.println((isPlayerOneTurn ? 1 : 0) + " " + (isHumanPlayingHuman ? 1 : 0) + " " + (isComputerPlayerOne ? 1 : 0) + " " + (opponentStuck ? 1 : 0));
                    printWriter.print(board.returnBoardAsString());

                    printWriter.close();
                    System.out.println("File successfully saved.");
                } catch (IOException e) {
                    System.out.println("Error writing file " + e);
                }
            }
        } else {
            System.out.println("No paused games found.");
        }
    }

    /**
     * Method that laods a game from a file
     */
    public void loadGame() {
        //TODO: CHOOSE FILE NAME (FOR SAVE AS WELL)
        int input = 1;
        if (gameStarted) {
            System.out.println("This will override already started game. Enter 1 to continue, 0 to abort.");
            Scanner scanner = new Scanner(System.in);
            do {
                try {
                    input = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect input entered. Please try again.");
                    scanner = new Scanner(System.in);
                }
            } while (input!=1 && input!=0);
        }
        if (input==1) {
            File file = new File(fileName);
            if (file.exists()) {
                FileReader fileReader;
                BufferedReader bufferedReader;
                try {
                    fileReader = new FileReader(fileName);
                    bufferedReader = new BufferedReader(fileReader);

                    gameStarted = true;

                    input = bufferedReader.read();
                    if (input == '1') {
                        isPlayerOneTurn = true;
                    } else if (input == '0') {
                        isPlayerOneTurn = false;
                    } else {
                        System.out.println("Error");
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        isHumanPlayingHuman = true;
                    } else if (input == '0') {
                        isHumanPlayingHuman = false;
                    } else {
                        System.out.println("Error");
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        isComputerPlayerOne = true;
                    } else if (input == '0') {
                        isComputerPlayerOne = false;
                    } else {
                        System.out.println("Error");
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        opponentStuck = true;
                    } else if (input == '0') {
                        opponentStuck = false;
                    } else {
                        System.out.println("Error");
                    }

                    char[][] boardArray = new char[Board.boardWidth][Board.boardHeight];
                    for (int i = 0; i < Board.boardHeight; i++) {
                        for (int j = 0; j < Board.boardWidth; j++) {
                            do {
                                input = bufferedReader.read();
                            } while ((char) input == ' ' || (char) input == '\n' || (char) input == '\r');
                            boardArray[j][i] = (char) input;
                        }
                    }
                    board = new Board(boardArray);

                    bufferedReader.close();
                    System.out.println("File successfully loaded.");

                    /*if (isHumanPlayingHuman) {
                        playGameHvH();
                    } else {
                        playGameHvC();
                    }*/
                } catch (IOException e) {
                    System.out.println("Error reading file.");
                }
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    private void performHumanMove(int x, int y) {
        if (board.placePiece(x, y, isPlayerOneTurn)) {
            isPlayerOneTurn = !isPlayerOneTurn;
            opponentStuck = false;
        }
    }

    private void performComputerMove(int[][] availableMoves) {
        if (board.placePieceForComputer(availableMoves, isComputerPlayerOne)) {
            isPlayerOneTurn = !isPlayerOneTurn;
        } else {
            opponentStuck = true;
            System.out.println("No available moves for a computer.");
            isPlayerOneTurn = !isPlayerOneTurn;
        }
    }

    public void checkIfGameOver() {
        int[][]  availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);

        if (availableMoves.length==0) {
            if (opponentStuck) {
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
                if (!isHumanPlayingHuman && ((isPlayerOneTurn && isComputerPlayerOne) || (!isPlayerOneTurn && !isComputerPlayerOne))) {
                    availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
                    performComputerMove(availableMoves);
                }
                checkIfGameOver();
            }
        } else {
            for (int i=0; i<availableMoves.length; i++) {
                System.out.println((availableMoves[i][0]+1) + " " + (availableMoves[i][1]+1));
            }
        }
    }

    public void printWhoseTurn() {
        char playerSymbol = isPlayerOneTurn ? Board.playerOne : Board.playerTwo;
        if (gameStarted) {
            if (isPlayerOneTurn) {
                System.out.println("Player's 1 ("+playerSymbol+") turn.");
            } else {
                System.out.println("Player's 2 ("+playerSymbol+") turn.");
            }
        }
    }

    public void runGame(int x, int y) {

        int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);

        if (availableMoves.length!=0) {
            if (board.checkIfAvailable(x,y,isPlayerOneTurn)) {
                performHumanMove(x, y);
                board.printBoard();
                int[] scores = board.calculateScore();
                System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
            }
        }

        if (!isHumanPlayingHuman && ((isPlayerOneTurn && isComputerPlayerOne) || (!isPlayerOneTurn && !isComputerPlayerOne))) {
            availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
            performComputerMove(availableMoves);
            board.printBoard();
            int[] scores = board.calculateScore();
            System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
        }

        checkIfGameOver();

        printWhoseTurn();
    }

    public char[][] returnBoard() {
        return board.returnBoardArray();
    }

}
