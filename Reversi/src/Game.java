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
            System.out.println("Player "+playerSymbol);

            userInputX = scanner.nextInt();
            userInputY = scanner.nextInt();

            board.placePiece(userInputX, userInputY, isPlayerOneTurn);

            isPlayerOneTurn = !isPlayerOneTurn;

        } while (userInputX!=-1);

    }
}
