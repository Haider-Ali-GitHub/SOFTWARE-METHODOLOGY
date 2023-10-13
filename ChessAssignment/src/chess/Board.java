// package chess;
// import java.util.HashMap;

// public class Board {
//     private HashMap<String, Pieces> board;

//     public Board() {
//         board = new HashMap<>();
//         initBoard();
//     }

//     public void initBoard() {
//         // Place pawns
//         for (char file = 'a'; file <= 'h'; file++) {
//             board.put(file + "2", new Pawn("Black", file, 2));
//             board.put(file + "7", new Pawn("White", file, 7));
//         }

//         // Place rooks
//         board.put("a1", new Rook("White", 'a', 1));
//         board.put("h1", new Rook("White", 'h', 1));
//         board.put("a8", new Rook("Black", 'a', 8));
//         board.put("h8", new Rook("Black", 'h', 8));

//         // Place knights
//         board.put("b1", new Knight("White", 'b', 1));
//         board.put("g1", new Knight("White", 'g', 1));
//         board.put("b8", new Knight("Black", 'b', 8));
//         board.put("g8", new Knight("Black", 'g', 8));

//         // Place bishops
//         board.put("c1", new Bishop("White", 'c', 1));
//         board.put("f1", new Bishop("White", 'f', 1));
//         board.put("c8", new Bishop("Black", 'c', 8));
//         board.put("f8", new Bishop("Black", 'f', 8));

//         // Place queens
//         board.put("d1", new Queen("White", 'd', 1));
//         board.put("d8", new Queen("Black", 'd', 8));

//         // Place kings
//         board.put("e1", new King("White", 'e', 1));
//         board.put("e8", new King("Black", 'e', 8));
//     }

//     // You can add other methods and operations for your chessboard class
// }

