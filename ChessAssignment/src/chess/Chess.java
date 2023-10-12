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
	private static ArrayList<ReturnPiece> board; 

	// ArrayList<ReturnPiece> board = new ArrayList<>();


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

    String[] parts = move.split(" ");
    
    if (parts.length != 2) {
        // error case
        ReturnPlay rp = new ReturnPlay();
        rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return rp;
    }

    PieceFile sourceFile = PieceFile.valueOf(parts[0].substring(0, 1));
    int sourceRank = Integer.parseInt(parts[0].substring(1, 2));

    PieceFile destFile = PieceFile.valueOf(parts[1].substring(0, 1));
    int destRank = Integer.parseInt(parts[1].substring(1, 2));
    
    ReturnPlay rp = new ReturnPlay(); 

    ReturnPiece movingPiece = null; 
    for (ReturnPiece piece : board) {
        if (piece.pieceFile == sourceFile && piece.pieceRank == sourceRank) {
            movingPiece = piece;
            break;
        }
    }

    // Check if no piece was found or it's not the current player's piece
    if (movingPiece == null || (currentPlayer == Player.white && movingPiece.pieceType.toString().startsWith("B")) ||
        (currentPlayer == Player.black && movingPiece.pieceType.toString().startsWith("W"))) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }

    // Further validations for legality of moves, checks, and checkmates would go here

    // Validate the move (very basic check, many other validations missing)
    if (isValidMove(movingPiece, destFile, destRank)) {
        // Perform the move, update piece's position
        movingPiece.pieceFile = destFile;
        movingPiece.pieceRank = destRank;

        // Remove any opponent piece at the destination
        board.removeIf(piece -> piece.pieceFile == destFile && piece.pieceRank == destRank);

        // Check for check/checkmate/stalemate/draw scenarios here

    } else {
        rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return rp;
    }

    // Switch the current player
    currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;

    rp.piecesOnBoard = board;
    return rp;
}


	private static boolean isValidMove(ReturnPiece piece, PieceFile destFile, int destRank) {

	}
	
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		// Initialize or clear the board
		if (board != null) {
			board.clear();
		} else {
			board = new ArrayList<>();
		}

		for (PieceFile file : PieceFile.values()) {
			ReturnPiece whitePawn = new ReturnPiece();
			whitePawn.pieceFile = file;
			whitePawn.pieceRank = 2;
			whitePawn.pieceType = PieceType.WP;
			board.add(whitePawn);
	
			ReturnPiece blackPawn = new ReturnPiece();
			blackPawn.pieceFile = file;
			blackPawn.pieceRank = 7;
			blackPawn.pieceType = PieceType.BP;
			board.add(blackPawn);
		}

		PieceType[] pieceTypes = 
		{PieceType.WR, PieceType.WN, PieceType.WB, PieceType.WQ, PieceType.WK, PieceType.WB, PieceType.WN, PieceType.WR};
		PieceType[] blackPieceTypes = 
		{PieceType.BR, PieceType.BN, PieceType.BB, PieceType.BQ, PieceType.BK, PieceType.BB, PieceType.BN, PieceType.BR};
	
		for (int i = 0; i < pieceTypes.length; i++) {
			ReturnPiece whitePiece = new ReturnPiece();
			whitePiece.pieceFile = PieceFile.values()[i];
			whitePiece.pieceRank = 1;
			whitePiece.pieceType = pieceTypes[i];
			board.add(whitePiece);
	
			ReturnPiece blackPiece = new ReturnPiece();
			blackPiece.pieceFile = PieceFile.values()[i];
			blackPiece.pieceRank = 8;
			blackPiece.pieceType = blackPieceTypes[i];
			board.add(blackPiece);
		}
		currentPlayer = Player.white;
	}
	

}
