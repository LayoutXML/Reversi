public class Board {

    private char[][] board;
    private final int boardWidth = 8;
    private final int boardHeight = 8;
    private final char playerOne = '#';
    private final char playerTwo = 'O';
    private int numberOfTimesFunctionWasCalled;

    public Board() {
        board = new char[boardWidth][boardHeight];
        clearBoard();
    }

    public Board(char[][] board) {
        this.board = board;
    }

    public void clearBoard() {
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void printBoard() {
        System.out.println("");
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i=0; i<boardHeight; i++) {
            System.out.print(i);
            for (int j=0; j<boardWidth; j++) {
                System.out.print(" "+board[j][i]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void setSinglePieceSide(int x, int y, boolean isPlayerOne) {
        if (isPlayerOne) {
            board[x][y] = playerOne;
        } else {
            board[x][y] = playerTwo;
        }
    }

    public void setMultiplePiecesSides(int x1, int y1, int x2, int y2, boolean isPlayerOne) {
        //TODO: Call from checker, only if valid moves already
        if (x1==x2) {
            //Change column
            if (y1>y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int i=y1; i<=y2; i++) {
                setSinglePieceSide(x1,i,isPlayerOne);
            }
        } else if (y1==y2) {
            //Change row
            if (x1>x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            for (int i=x1; i<=x2; i++) {
                setSinglePieceSide(i,y1,isPlayerOne);
            }
        } else {
            if (x1<x2 && y1<y2) {
                // \
                for (int i=0; i<y2-y1; i++) {
                    setSinglePieceSide(x1+i,y1+i,isPlayerOne);
                }
            } else if (x1>x2 && y1>y2) {
                // \
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
                for (int i=0; i<y2-y1; i++) {
                    setSinglePieceSide(x1+i,y1+i,isPlayerOne);
                }
            } else if (x1<x2 && y1>y2) {
                // /
                for (int i=0; i<y2-y1; i++) {
                    setSinglePieceSide(x1+i,y1-i,isPlayerOne);
                }
            } else if (x1>x2 && y1<y2) {
                // /
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
                for (int i=0; i<y2-y1; i++) {
                    setSinglePieceSide(x1+i,y1-i,isPlayerOne);
                }
            }
        }
    }

    public void placePiece(int x, int y, boolean isPlayerOne) {
        numberOfTimesFunctionWasCalled = 0;
    }

    public void printAllAvailable(boolean isPlayerOne) {
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                if (checkIfAvailable(i, j, isPlayerOne))
                    System.out.println(i+" "+j);
            }
        }
    }

    public boolean checkIfAvailable(int x, int y, boolean isPlayerOne) {
        boolean isAvailable = false;

        numberOfTimesFunctionWasCalled = 0;
        if (check0(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check1(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check2(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check3(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check4(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check5(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check6(x,y,isPlayerOne)) isAvailable=true;

        numberOfTimesFunctionWasCalled = 0;
        if (check7(x,y,isPlayerOne)) isAvailable=true;

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

}
