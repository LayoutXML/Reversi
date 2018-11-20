import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

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
            int[] scores = board.calculateScore();
            System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
        }
    }

    /**
     * Method that saves the game to a file
     */
    public void saveGame() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (gameStarted) {
                FileOutputStream fileOutputStream;
                PrintWriter printWriter;
                try {
                    String fileName = chooser.getSelectedFile().getAbsolutePath();
                    if (!fileName.substring(fileName.length() - 4).equals(".txt")) {
                        fileName += ".txt";
                    }
                    fileOutputStream = new FileOutputStream(fileName);
                    printWriter = new PrintWriter(fileOutputStream);

                    printWriter.println((isPlayerOneTurn ? 1 : 0) + " " + (isHumanPlayingHuman ? 1 : 0) + " " + (isComputerPlayerOne ? 1 : 0) + " " + (opponentStuck ? 1 : 0));
                    printWriter.print(board.returnBoardAsString());

                    printWriter.close();
                    JOptionPane.showMessageDialog(new JFrame(), "File saved successfully.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error writing file " + e);
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "No games have been started - nothing to save.");
            }
        }
    }

    /**
     * Method that laods a game from a file
     */
    public boolean loadGame() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game file", "txt");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            int input = 1;
            File file = new File(chooser.getSelectedFile().getAbsolutePath());
            if (file.exists()) {
                FileReader fileReader;
                BufferedReader bufferedReader;
                try {
                    fileReader = new FileReader(chooser.getSelectedFile().getAbsolutePath());
                    bufferedReader = new BufferedReader(fileReader);

                    gameStarted = true;

                    input = bufferedReader.read();
                    if (input == '1') {
                        isPlayerOneTurn = true;
                    } else if (input == '0') {
                        isPlayerOneTurn = false;
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error loading file.");
                        return false;
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        isHumanPlayingHuman = true;
                    } else if (input == '0') {
                        isHumanPlayingHuman = false;
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error loading file.");
                        return false;
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        isComputerPlayerOne = true;
                    } else if (input == '0') {
                        isComputerPlayerOne = false;
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error loading file.");
                        return false;
                    }

                    do {
                        input = bufferedReader.read();
                    } while ((char) input == ' ' || (char) input == '\n');
                    if (input == '1') {
                        opponentStuck = true;
                    } else if (input == '0') {
                        opponentStuck = false;
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error loading file.");
                        return false;
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
                    JOptionPane.showMessageDialog(new JFrame(), "File loaded successfully.");

                    if (!isHumanPlayingHuman && ((isPlayerOneTurn && isComputerPlayerOne) || (!isPlayerOneTurn && !isComputerPlayerOne))) {
                        int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
                        performComputerMove(availableMoves);
                        int[] scores = board.calculateScore();
                        System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
                    }

                    return true;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error loading file. " + e);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "File does not exist.");
                return false;
            }
        }
        return false;
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
        int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);

        if (availableMoves.length == 0) {
            if (opponentStuck) {
                gameStarted = false;
                int outcome = board.winner();
                switch (outcome) {
                    case 1:
                        JOptionPane.showMessageDialog(new JFrame(), "No available moves. Winner is Player 1 (" + Board.playerOne + ").");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(new JFrame(), "No available moves. Winner is Player 2 (" + Board.playerTwo + ").");
                        System.out.println();
                        break;
                    default:
                        JOptionPane.showMessageDialog(new JFrame(), "No available moves. It's a tie.");
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
        }
    }

    public void printWhoseTurn() {
        char playerSymbol = isPlayerOneTurn ? Board.playerOne : Board.playerTwo;
        if (gameStarted) {
            if (isPlayerOneTurn) {
                System.out.println("Player's 1 (" + playerSymbol + ") turn.");
            } else {
                System.out.println("Player's 2 (" + playerSymbol + ") turn.");
            }
        }
    }

    public void runGame(int x, int y) {

        int[][] availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);

        if (availableMoves.length != 0) {
            if (board.checkIfAvailable(x, y, isPlayerOneTurn)) {
                performHumanMove(x, y);
                int[] scores = board.calculateScore();
                System.out.println("Player 1 (#) score is: " + scores[0] + "\nPlayer 2 (O) score is: " + scores[1] + "\n");
            }
        }

        if (!isHumanPlayingHuman && ((isPlayerOneTurn && isComputerPlayerOne) || (!isPlayerOneTurn && !isComputerPlayerOne))) {
            availableMoves = board.getAllAvailableMoves(isPlayerOneTurn);
            performComputerMove(availableMoves);
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
