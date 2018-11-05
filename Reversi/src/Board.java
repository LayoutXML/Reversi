public class Board {

    private char[][] board;
    private final int boardWidth = 8;
    private final int boardHeight = 8;
    private final char playerOne = 'O';
    private final char playerTwo = '#';

    public Board() {
        board = new char[boardWidth][boardHeight];
        clearBoard();
    }

    public void clearBoard() {
        for (int i=0; i<boardWidth; i++) {
            for (int j=0; j<boardHeight; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void setSide(int x, int y, boolean isPlayerOne) {
        if (isPlayerOne) {
            board[x][y] = playerOne;
        } else {
            board[x][y] = playerTwo;
        }
    }

    public void setMultipleSides(int x1, int y1, int x2, int y2, boolean isPlayerOne) {
        //TODO: Call from checker, only if valid moves already
        if (x1==x2) {
            //Change column
            if (y1>y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int i=y1; i<=y2; i++) {
                setSide(x1,i,isPlayerOne);
            }
        } else if (y1==y2) {
            //Change row
            if (x1>x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            for (int i=x1; i<=x2; i++) {
                setSide(i,y1,isPlayerOne);
            }
        } else {
            if (x1<x2 && y1<y2) {
                // \
                for (int i=0; i<y2-y1; i++) {
                    setSide(x1+i,y1+i,isPlayerOne);
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
                    setSide(x1+i,y1+i,isPlayerOne);
                }
            } else if (x1<x2 && y1>y2) {
                // /
                for (int i=0; i<y2-y1; i++) {
                    setSide(x1+i,y1-i,isPlayerOne);
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
                    setSide(x1+i,y1-i,isPlayerOne);
                }
            }
        }
    }
}
