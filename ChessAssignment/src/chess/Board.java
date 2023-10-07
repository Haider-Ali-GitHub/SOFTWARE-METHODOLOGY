package chess;

public class Board {
    
    private Pieces[][] board;

    public Board() {
        board = new Pieces[8][8];
        initBoard();
    }

    private void initBoard() { // creates the chess board and creates the pieces

        // place pawns
        for (int col = 0; col < board.length; col++) {
            board[1][col] = new Pawn("Black", 1, col);
            board[6][col] = new Pawn("White", 6, col);
        }

        //place rooks
        for (int row : new int[]{0, 7}) { // Loop through rows 0 and 7
            board[row][0] = new Rook(row == 0 ? "Black" : "White", row, 0);
            board[row][7] = new Rook(row == 0 ? "Black" : "White", row, 7);
        }

        // place knights
        for (int row: new int[]{0, 7}) {
            board[row][1] = new Knight(row == 0 ? "Black" : "White", row, 0);
            board[row][6] = new Knight(row == 0 ? "Black" : "White", row, 7);
        }

        // place bishops
        for (int row : new int[]{0, 7}) { // Loop through rows 0 and 7
            board[row][2] = new Bishop(row == 0 ? "Black" : "White", row, 2);
            board[row][5] = new Bishop(row == 0 ? "Black" : "White", row, 5);
        }

        // place queen and king
        for (int row : new int[]{0, 7}) { // Loop through rows 0 and 7
            // Place queens
            board[row][3] = new Queen(row == 0 ? "White" : "Black", row, 3);
            // Place kings
            board[row][4] = new King(row == 0 ? "White" : "Black", row, 4);
        }
    }
    
}
