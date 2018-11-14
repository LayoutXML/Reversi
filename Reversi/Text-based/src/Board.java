import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("Duplicates")
/**
 * Board class that contains the board, essential information about it and has methods to validate and place pieces
 */
public class Board {

    private char[][] board;
    public static final int boardWidth = 8; //static because we need to use it when board object is not initialized, i.e. when loading file
    public static final int boardHeight = 8;
    public static final char playerOne = '#';
    public static final char playerTwo = 'O';
    private int numberOfTimesFunctionWasCalled;

    //Methods for working with board directly

    /**
     * Constructor method that creates an empty board
     */
    public Board() {
        board = new char[boardWidth][boardHeight];
        clearBoard();
    }

    /**
     * Constructor method that creates a board from a given one
     * @param board 2d char array - a game board
     */
    public Board(char[][] board) {
        this.board = board;
    }

    /**
     * Method that prepares a board for a new game - empties the board and places the starting pieces in the center
     */
    public void clearBoard() {
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                board[i][j] = '.';
            }
        }
        board[3][3] = 'O';
        board[3][4] = '#';
        board[4][3] = '#';
        board[4][4] = 'O';
    }

    /**
     * Method that prints the board to the screen
     */
    public void printBoard() {
        System.out.print("\n");
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i=0; i<boardHeight; i++) {
            System.out.print(i+1);
            for (int j=0; j<boardWidth; j++) {
                System.out.print(" "+board[j][i]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Method that returns the board as string (without coordinates)
     * @return
     */
    public String returnBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int i=0; i<boardHeight; i++) {
            for (int j=0; j<boardWidth; j++) {
                boardString.append(board[j][i]);
                if (j+1<boardWidth) {
                    boardString.append(" ");
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    //Method for working with scores directly

    /**
     * Method that returns who is winning
     * @return integer 1 if player1 is winning, 2 if player2 is winning, 3 if scores are equal (tie)
     */
    public int winner() {
        int[] scores = calculateScore();
        if (scores[0]>scores[1]) {
            return 1;
        } else if (scores[1]>scores[0]) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Method that returns player scores
     * @return int array with 2 elements - index 0 contains player1 score, index 1 contains player2 score
     */
    public int[] calculateScore() {
        int[] scores = {0,0};
        for (int i=0; i<boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (board[i][j]==playerOne) {
                    scores[0]++;
                } else if (board[i][j]==playerTwo) {
                    scores[1]++;
                }
            }
        }
        return scores;
    }

    /**
     * Method that returns an array with all available moves for a player
     * @param isPlayerOne true if return player1 available moves, false if player2
     * @return 2d array with all available moves for a player
     */
    public int[][] getAllAvailableMoves(boolean isPlayerOne) {
        ArrayList<Integer> availableMovesX = new ArrayList<>();
        ArrayList<Integer> availableMovesY = new ArrayList<>();
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                if (checkIfAvailable(i, j, isPlayerOne)) {
                    availableMovesX.add(i);
                    availableMovesY.add(j);
                }
            }
        }
        int[][] availableMovesArray = new int[availableMovesX.size()][2];
        for (int i=0; i<availableMovesX.size(); i++) {
            availableMovesArray[i][0] = availableMovesX.get(i);
            availableMovesArray[i][1] = availableMovesY.get(i);
        }
        return availableMovesArray;
    }

    /**
     * Method that checks an individual board coordinate if a piece can be places there for a given player
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @return true if a piece can be placed in this place
     */
    public boolean checkIfAvailable(int x, int y, boolean isPlayerOne) {
        boolean isAvailable = false;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;

        if (board[x][y]!=playerSymbol && board[x][y]!=opponentSymbol) {
            numberOfTimesFunctionWasCalled = 0;
            if (check0(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check1(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check2(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check3(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check4(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check5(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check6(x, y, isPlayerOne, board)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check7(x, y, isPlayerOne, board)) isAvailable = true;
        }

        return isAvailable;
    }

    /**
     * Method that places the price in the given coordinate and flips appropriate pieces in all directions
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @return true if the piece was successfully places, false if a piece cannot be places in this coordinate
     */
    public boolean placePiece(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        boolean canBePlaced = false;

        numberOfTimesFunctionWasCalled = 0;
        if (check0(x,y,isPlayerOne, board)) {
            set0(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check1(x,y,isPlayerOne, board)) {
            set1(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check2(x,y,isPlayerOne, board)) {
            set2(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check3(x,y,isPlayerOne, board)) {
            set3(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check4(x,y,isPlayerOne, board)) {
            set4(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check5(x,y,isPlayerOne, board)) {
            set5(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check6(x,y,isPlayerOne, board)) {
            set6(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check7(x,y,isPlayerOne, board)) {
            set7(x,y,isPlayerOne, board);
            canBePlaced = true;
        }

        if (canBePlaced) {
            board[x][y] = playerSymbol;
        } else {
            System.out.println("You cannot place your piece here. Please enter new coordinates.");
        }

        return canBePlaced;
    }

    /*
    Methods for checking and placing pieces in 8 available directions:
    0  1  2
     \ | /
    7 -.- 3
     / | \
    6  5  4
     */

    /**
     * Checks if a piece can be placed here (direction rules apply) - NW direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check0(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x>0 && y>0) {
            if (board[x-1][y-1]==opponentSymbol) {
                existsSameColorInDirection = check0(x-1,y-1,isPlayerOne, board);
            } else if (board[x-1][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - NW direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set0(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x>0 && y>0) {
            if (board[x-1][y-1]==opponentSymbol) {
                set0(x-1,y-1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - N direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check1(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y>0) {
            if (board[x][y-1]==opponentSymbol) {
                existsSameColorInDirection = check1(x,y-1,isPlayerOne, board);
            } else if (board[x][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - N direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set1(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y>0) {
            if (board[x][y-1]==opponentSymbol) {
                set1(x,y-1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - NE direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check2(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x<boardWidth-1 && y>0) {
            if (board[x+1][y-1]==opponentSymbol) {
                existsSameColorInDirection = check2(x+1,y-1,isPlayerOne, board);
            } else if (board[x+1][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - NE direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set2(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x<boardWidth-1 && y>0) {
            if (board[x+1][y-1]==opponentSymbol) {
                set2(x+1,y-1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - E direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check3(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x<boardWidth-1) {
            if (board[x+1][y]==opponentSymbol) {
                existsSameColorInDirection = check3(x+1,y,isPlayerOne, board);
            } else if (board[x+1][y]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - E direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set3(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x<boardWidth-1) {
            if (board[x+1][y]==opponentSymbol) {
                set3(x+1,y,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - SE direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check4(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1 && x<boardWidth-1) {
            if (board[x+1][y+1]==opponentSymbol) {
                existsSameColorInDirection = check4(x+1,y+1,isPlayerOne, board);
            } else if (board[x+1][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - SE direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set4(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1 && x<boardWidth-1) {
            if (board[x+1][y+1]==opponentSymbol) {
                set4(x+1,y+1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - S direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check5(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1) {
            if (board[x][y+1]==opponentSymbol) {
                existsSameColorInDirection = check5(x,y+1,isPlayerOne, board);
            } else if (board[x][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - S direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set5(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1) {
            if (board[x][y+1]==opponentSymbol) {
                set5(x,y+1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - SW direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check6(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1 && x>0) {
            if (board[x-1][y+1]==opponentSymbol) {
                existsSameColorInDirection = check6(x-1,y+1,isPlayerOne, board);
            } else if (board[x-1][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - SW direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set6(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1 && x>0) {
            if (board[x-1][y+1]==opponentSymbol) {
                set6(x-1,y+1,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    /**
     * Checks if a piece can be placed here (direction rules apply) - W direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to check availability
     * @return true if a piece can be placed in this place
     */
    public boolean check7(int x, int y, boolean isPlayerOne, char[][] board) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x>0) {
            if (board[x-1][y]==opponentSymbol) {
                existsSameColorInDirection = check7(x-1,y,isPlayerOne, board);
            } else if (board[x-1][y]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    /**
     * Flips pieces to correct side in this direction - W direction
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param board board in which to place piece
     */
    public void set7(int x, int y, boolean isPlayerOne, char[][] board) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x>0) {
            if (board[x-1][y]==opponentSymbol) {
                set7(x-1,y,isPlayerOne, board);
            }
        }
        board[x][y]=playerSymbol;
    }

    //Method for computer

    /**
     * Method that decides which move is the best for a computer (gives the highest score) and places a piece there
     * @param availableMoves 2d int array that contains all available moves for a computer
     * @param isComputerPlayerOne true if computer is player1, false if player2
     * @return true if a piece was placed successfully, false if a piece was not placed anywhere
     */
    public boolean placePieceForComputer(int[][] availableMoves, boolean isComputerPlayerOne) {
        boolean response = false;
        int[] scores = new int[availableMoves.length];
        char[][] imaginaryBoard;
        for (int i=0; i<availableMoves.length; i++) {
            imaginaryBoard = new char[boardWidth][boardWidth];
            for (int j=0; j<boardWidth; j++) {
                imaginaryBoard[j] = Arrays.copyOf(board[j],boardHeight);
            }
            placePieceOnImaginaryBoard(availableMoves[i][0],availableMoves[i][1],isComputerPlayerOne, imaginaryBoard);
            int[] imaginaryScores = calculateScoreOnImaginaryBoard(imaginaryBoard);
            if (isComputerPlayerOne) {
                scores[i] = imaginaryScores[0];
            } else {
                scores[i] = imaginaryScores[1];
            }
            System.out.println("Scores: "+scores[i]);
        }
        int maxScore = 0;
        for (int i=0; i<scores.length; i++) {
            if (scores[i]>maxScore) {
                maxScore = scores[i];
            }
        }
        boolean found = false;
        int i=0;
        while (!found) {
            if (scores[i]==maxScore) {
                found = true;
                response = placePiece(availableMoves[i][0],availableMoves[i][1],isComputerPlayerOne);
                System.out.println("Computer placed a piece on "+(availableMoves[i][0]+1)+" "+(availableMoves[i][1]+1));
            }
            i++;
        }
        return response;
    }

    /**
     * Method that calculates score on a given board (to predict which move gives the highest score)
     * @param imaginaryBoard 2d char array - board
     * @return int array with 2 elements - index 0 contains player1 score, index 1 contains player2 score
     */
    public int[] calculateScoreOnImaginaryBoard(char[][] imaginaryBoard) {
        int[] scores = {0,0};
        for (int i=0; i<boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (imaginaryBoard[i][j]==playerOne) {
                    scores[0]++;
                } else if (imaginaryBoard[i][j]==playerTwo) {
                    scores[1]++;
                }
            }
        }
        return scores;
    }

    /**
     * Method that places a piece on a given board, in order to calculate the score which could be achieved if a piece was to placed here
     * @param x board x coordinates (horizontal)
     * @param y board y coordinates (vertical)
     * @param isPlayerOne true if checking if a player1 can place a piece here, false if player2
     * @param imaginaryBoard board in which to place piece
     * @return true if a piece was placed successfully, false if a piece was not placed anywhere
     */
    public boolean placePieceOnImaginaryBoard(int x, int y, boolean isPlayerOne, char[][] imaginaryBoard) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        boolean canBePlaced = false;

        numberOfTimesFunctionWasCalled = 0;
        if (check0(x,y,isPlayerOne, imaginaryBoard)) {
            set0(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check1(x,y,isPlayerOne, imaginaryBoard)) {
            set1(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check2(x,y,isPlayerOne, imaginaryBoard)) {
            set2(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check3(x,y,isPlayerOne, imaginaryBoard)) {
            set3(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check4(x,y,isPlayerOne, imaginaryBoard)) {
            set4(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check5(x,y,isPlayerOne, imaginaryBoard)) {
            set5(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check6(x,y,isPlayerOne, imaginaryBoard)) {
            set6(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check7(x,y,isPlayerOne, imaginaryBoard)) {
            set7(x,y,isPlayerOne, imaginaryBoard);
            canBePlaced = true;
        }

        if (canBePlaced) {
            imaginaryBoard[x][y] = playerSymbol;
        } else {
            System.out.println("You cannot place your piece here. Please enter new coordinates.");
        }

        return canBePlaced;
    }

}
