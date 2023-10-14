package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {	
	
	enum Player { white, black }
	private static Player currentPlayer;
	// private static ArrayList<ReturnPiece> board; 

	// ArrayList<ReturnPiece> board = new ArrayList<>();
	public static ReturnPlay board; 




	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

    //...
	board.message = null; 

	if (move.trim().equalsIgnoreCase("resign")) {
		board.message = (currentPlayer == Player.white) ? 
		ReturnPlay.Message.RESIGN_BLACK_WINS : 
		ReturnPlay.Message.RESIGN_WHITE_WINS;
		return board;
	}
    String[] parts = move.split(" ");

	// if (parts.length != 3 ) {
	// 	// error case
	// 	// ReturnPlay rp = new ReturnPlay();
	// 	board.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 	return board;
    // }
	 // Check for special moves

    PieceFile sourceFile = PieceFile.valueOf(parts[0].substring(0, 1));
    int sourceRank = Integer.parseInt(parts[0].substring(1, 2));

    PieceFile destFile = PieceFile.valueOf(parts[1].substring(0, 1));
    int destRank = Integer.parseInt(parts[1].substring(1, 2));
    

    ReturnPiece movingPiece = null; 
    for (ReturnPiece piece : board.piecesOnBoard) {
        if (piece.pieceFile == sourceFile && piece.pieceRank == sourceRank) {
            movingPiece = piece;
            break;
        }
    }

    // Check if no piece was found or it's not the current player's piece
    if (movingPiece == null || (currentPlayer == Player.white && movingPiece.pieceType.toString().startsWith("B")) ||
        (currentPlayer == Player.black && movingPiece.pieceType.toString().startsWith("W"))) {
            board.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return board;
    }

    // Further validations for legality of moves, checks, and checkmates would go here
	if (isValidMove(movingPiece, destFile, destRank)) {
		// Check if there is a piece on the destination square
		ReturnPiece targetPiece = getPieceAt(destFile, destRank);
		if (targetPiece != null) {
			// If the piece at the destination is the same color as movingPiece, the move is illegal
			if (isSameColor(movingPiece, targetPiece)) {
				board.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return board;
			} else {
				// Otherwise, capture the piece at the destination
				board.piecesOnBoard.remove(targetPiece);
			}
		}
	
	// Update movingPiece's position
	movingPiece.pieceFile = destFile;
	movingPiece.pieceRank = destRank;

	if (move.endsWith(" draw?"))  {
		board.message = ReturnPlay.Message.DRAW;
		return board;
	}

	if (isCheckmate((currentPlayer == Player.white) ? Player.black : Player.white)) {
	    board.message = (currentPlayer == Player.white) ? 
                    ReturnPlay.Message.CHECKMATE_WHITE_WINS : 
                    ReturnPlay.Message.CHECKMATE_BLACK_WINS;
	}
	} else {
		board.message = ReturnPlay.Message.ILLEGAL_MOVE;
		return board;
	}
	
    // Switch the current player
    currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;	
    return board;
}

public static ReturnPiece getPieceAt(PieceFile file, int rank) {
    for (ReturnPiece piece : board.piecesOnBoard) {
        if (piece.pieceFile == file && piece.pieceRank == rank) {
            return piece;
        }
    }
    return null;
}

private static boolean isSameColor(ReturnPiece piece1, ReturnPiece piece2) {
    return piece1.pieceType.toString().charAt(0) == piece2.pieceType.toString().charAt(0);
}

public static boolean isCheck(Player player) {
    ReturnPiece king = null;
    
    // Find the king
    for (ReturnPiece piece : board.piecesOnBoard) {
        if ((piece.pieceType == PieceType.WK && player == Player.white) ||
            (piece.pieceType == PieceType.BK && player == Player.black)) {
            king = piece;
            break;
        }
    }

    // Check if any of the opponent's pieces can attack the king
    for (ReturnPiece piece : board.piecesOnBoard) {
        if ((piece.pieceType.toString().startsWith("W") && player == Player.black) ||
            (piece.pieceType.toString().startsWith("B") && player == Player.white)) {
            if (isValidMove(piece, king.pieceFile, king.pieceRank)) {
                return true; // Player is in check
            }
        }
    }
    return false; // Player is not in check
}

public static boolean isCheckmate(Player player) {
    // If the player is not in check, it cannot be checkmate
    if (!isCheck(player)) {
        return false;
    }
    
    // Go through all the player's pieces and see if any have a legal move that removes the check
    for (ReturnPiece piece : board.piecesOnBoard) {
        if ((piece.pieceType.toString().startsWith("W") && player == Player.white) ||
            (piece.pieceType.toString().startsWith("B") && player == Player.black)) {
            PieceFile originalFile = piece.pieceFile;
            int originalRank = piece.pieceRank;
            
            // Check each square on the board to see if moving this piece there would remove the check
            for (PieceFile file : PieceFile.values()) {
                for (int rank = 1; rank <= 8; rank++) {
                    if (isValidMove(piece, file, rank)) {
                        // Simulate the move
                        piece.pieceFile = file;
                        piece.pieceRank = rank;
                        
                        // Check if this move would remove the check
                        if (!isCheck(player)) {
                            // Undo the move
                            piece.pieceFile = originalFile;
                            piece.pieceRank = originalRank;
                            return false; // Found a legal move that removes check
                        }
                        
                        // Undo the move
                        piece.pieceFile = originalFile;
                        piece.pieceRank = originalRank;
                    }
                }
            }
        }
    }
    
    // If no such move was found, it's checkmate
    return true;
}


private static boolean isValidMove(ReturnPiece piece, PieceFile destFile, int destRank) {
    // Utilize specific piece logic for validation based on PieceType
    switch (piece.pieceType) {
        case WP, BP:
            return ((Pawn) piece).isValidMove(destFile, destRank);
        case WN, BN:
            return ((Knight) piece).isValidMove(destFile, destRank);
        case WB, BB:
            return ((Bishop) piece).isValidMove(destFile, destRank);
        case WR, BR:
            return ((Rook) piece).isValidMove(destFile, destRank);
        case WQ, BQ:
            return ((Queen) piece).isValidMove(destFile, destRank);
        case WK, BK:
            return ((King) piece).isValidMove(destFile, destRank);
        default:
            throw new IllegalArgumentException("Unexpected value: " + piece.pieceType);
    }
}

	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		if (board == null) {
			board = new ReturnPlay();
			board.piecesOnBoard = new ArrayList<>();
		} else {
			board.piecesOnBoard.clear();
		}
		// Pawns
		for (PieceFile file : PieceFile.values()) {
			board.piecesOnBoard.add(new Pawn(PieceType.WP, file, 2));
			board.piecesOnBoard.add(new Pawn(PieceType.BP, file, 7));
		}
	
		// Other pieces
		PieceType[] whitePieceTypes = 
			{PieceType.WR, PieceType.WN, PieceType.WB, PieceType.WQ, PieceType.WK, PieceType.WB, PieceType.WN, PieceType.WR};
		PieceType[] blackPieceTypes = 
			{PieceType.BR, PieceType.BN, PieceType.BB, PieceType.BQ, PieceType.BK, PieceType.BB, PieceType.BN, PieceType.BR};
	
		for (int i = 0; i < whitePieceTypes.length; i++) {
			PieceType whiteType = whitePieceTypes[i];
			PieceType blackType = blackPieceTypes[i];
	
			// Assuming a constructor similar to Pawn's for all piece classes
			switch (whiteType) {
				case WR -> board.piecesOnBoard.add(new Rook(whiteType, PieceFile.values()[i], 1));
				case WN -> board.piecesOnBoard.add(new Knight(whiteType, PieceFile.values()[i], 1));
				case WB -> board.piecesOnBoard.add(new Bishop(whiteType, PieceFile.values()[i], 1));
				case WQ -> board.piecesOnBoard.add(new Queen(whiteType, PieceFile.values()[i], 1));
				case WK -> board.piecesOnBoard.add(new King(whiteType, PieceFile.values()[i], 1));
				// Similarly for other white piece types...
				default -> throw new IllegalArgumentException("Unexpected value: " + whiteType);
			}
	
			switch (blackType) {
				case BR -> board.piecesOnBoard.add(new Rook(blackType, PieceFile.values()[i], 8));
				case BN -> board.piecesOnBoard.add(new Knight(blackType, PieceFile.values()[i], 8));
				case BB -> board.piecesOnBoard.add(new Bishop(blackType, PieceFile.values()[i], 8));
				case BQ -> board.piecesOnBoard.add(new Queen(blackType, PieceFile.values()[i], 8));
				case BK ->board.piecesOnBoard.add(new King(blackType, PieceFile.values()[i], 8));
				// Similarly for other black piece types...
				default -> throw new IllegalArgumentException("Unexpected value: " + blackType);
			}
		}
		currentPlayer = Player.white;
	}
	

}
