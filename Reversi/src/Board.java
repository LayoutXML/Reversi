public class Board {

    private char[][] board;
    private final int boardWidth = 8;
    private final int boardHeight = 8;
    public final char playerOne = '#';
    public final char playerTwo = 'O';
    private int numberOfTimesFunctionWasCalled;

    public Board() {
        board = new char[boardWidth][boardHeight];
        clearBoard();
    }

    public Board(char[][] board) {
        this.board = board;
    }

    public int winner() {
        int playerOneScore = 0, playerTwoScore = 0;
        for (int i=0; i<boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (board[i][j]==playerOne) {
                    playerOneScore++;
                } else if (board[i][j]==playerTwo) {
                    playerTwoScore++;
                }
            }
        }
        if (playerOneScore>playerTwoScore) {
            return 1;
        } else if (playerTwoScore>playerOneScore) {
            return 2;
        } else {
            return 3;
        }
    }

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

    public boolean placePiece(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        boolean canBePlaced = false;

        numberOfTimesFunctionWasCalled = 0;
        if (check0(x,y,isPlayerOne)) {
            set0(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check1(x,y,isPlayerOne)) {
            set1(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check2(x,y,isPlayerOne)) {
            set2(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check3(x,y,isPlayerOne)) {
            set3(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check4(x,y,isPlayerOne)) {
            set4(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check5(x,y,isPlayerOne)) {
            set5(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check6(x,y,isPlayerOne)) {
            set6(x,y,isPlayerOne);
            canBePlaced = true;
        }

        numberOfTimesFunctionWasCalled = 0;
        if (check7(x,y,isPlayerOne)) {
            set7(x,y,isPlayerOne);
            canBePlaced = true;
        }

        if (canBePlaced) {
            board[x][y] = playerSymbol;
        } else {
            System.out.println("You cannot place your piece here. Please enter new coordinates.");
        }

        return canBePlaced;
    }

    public boolean printAllAvailable(boolean isPlayerOne) {
        boolean atLeastOneAvailableMove = false;
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                if (checkIfAvailable(i, j, isPlayerOne)) {
                    if (!atLeastOneAvailableMove) {
                        System.out.println("Available moves:");
                    }
                    System.out.println((i+1) + " " + (j+1));
                    atLeastOneAvailableMove = true;
                }
            }
        }
        return atLeastOneAvailableMove;
    }

    public boolean checkIfAvailable(int x, int y, boolean isPlayerOne) {
        boolean isAvailable = false;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;

        if (board[x][y]!=playerSymbol && board[x][y]!=opponentSymbol) {
            numberOfTimesFunctionWasCalled = 0;
            if (check0(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check1(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check2(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check3(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check4(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check5(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check6(x, y, isPlayerOne)) isAvailable = true;

            numberOfTimesFunctionWasCalled = 0;
            if (check7(x, y, isPlayerOne)) isAvailable = true;
        }

        return isAvailable;
    }

    /*
    0  1  2
     \ | /
    7 -.- 3
     / | \
    6  5  4
     */
    public boolean check0(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x>0 && y>0) {
            if (board[x-1][y-1]==opponentSymbol) {
                existsSameColorInDirection = check0(x-1,y-1,isPlayerOne);
            } else if (board[x-1][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set0(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x>0 && y>0) {
            if (board[x-1][y-1]==opponentSymbol) {
                set0(x-1,y-1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check1(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y>0) {
            if (board[x][y-1]==opponentSymbol) {
                existsSameColorInDirection = check1(x,y-1,isPlayerOne);
            } else if (board[x][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set1(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y>0) {
            if (board[x][y-1]==opponentSymbol) {
                set1(x,y-1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check2(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x<boardWidth-1 && y>0) {
            if (board[x+1][y-1]==opponentSymbol) {
                existsSameColorInDirection = check2(x+1,y-1,isPlayerOne);
            } else if (board[x+1][y-1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set2(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x<boardWidth-1 && y>0) {
            if (board[x+1][y-1]==opponentSymbol) {
                set2(x+1,y-1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check3(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x<boardWidth-1) {
            if (board[x+1][y]==opponentSymbol) {
                existsSameColorInDirection = check3(x+1,y,isPlayerOne);
            } else if (board[x+1][y]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set3(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x<boardWidth-1) {
            if (board[x+1][y]==opponentSymbol) {
                set3(x+1,y,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check4(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1 && x<boardWidth-1) {
            if (board[x+1][y+1]==opponentSymbol) {
                existsSameColorInDirection = check4(x+1,y+1,isPlayerOne);
            } else if (board[x+1][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set4(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1 && x<boardWidth-1) {
            if (board[x+1][y+1]==opponentSymbol) {
                set4(x+1,y+1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check5(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1) {
            if (board[x][y+1]==opponentSymbol) {
                existsSameColorInDirection = check5(x,y+1,isPlayerOne);
            } else if (board[x][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set5(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1) {
            if (board[x][y+1]==opponentSymbol) {
                set5(x,y+1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check6(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (y<boardHeight-1 && x>0) {
            if (board[x-1][y+1]==opponentSymbol) {
                existsSameColorInDirection = check6(x-1,y+1,isPlayerOne);
            } else if (board[x-1][y+1]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set6(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (y<boardHeight-1 && x>0) {
            if (board[x-1][y+1]==opponentSymbol) {
                set6(x-1,y+1,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

    public boolean check7(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled++;
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        boolean existsSameColorInDirection = false;
        if (x>0) {
            if (board[x-1][y]==opponentSymbol) {
                existsSameColorInDirection = check7(x-1,y,isPlayerOne);
            } else if (board[x-1][y]==playerSymbol && numberOfTimesFunctionWasCalled!=1){
                existsSameColorInDirection = true;
            }
        }
        return existsSameColorInDirection;
    }

    public void set7(int x, int y, boolean isPlayerOne) {
        char playerSymbol = isPlayerOne ? playerOne : playerTwo;
        char opponentSymbol = isPlayerOne ? playerTwo : playerOne;
        if (x>0) {
            if (board[x-1][y]==opponentSymbol) {
                set7(x-1,y,isPlayerOne);
            }
        }
        board[x][y]=playerSymbol;
    }

}
