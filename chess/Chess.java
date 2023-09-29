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

		/* FILL IN THIS METHOD */


		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return null;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		ArrayList<ReturnPiece> board = new ArrayList<>();


		//Pawns 
		for (PieceFile file : PieceFile.values())
		{
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

		//ROOKS 
		// Initialize white rooks
		ReturnPiece whiteRook1 = new ReturnPiece();
		whiteRook1.pieceFile = PieceFile.a;
		whiteRook1.pieceRank = 1;
		whiteRook1.pieceType = PieceType.WR;
		board.add(whiteRook1);

		ReturnPiece whiteRook2 = new ReturnPiece();
		whiteRook2.pieceFile = PieceFile.h;
		whiteRook2.pieceRank = 1;
		whiteRook2.pieceType = PieceType.WR;
		board.add(whiteRook2);

		// Initialize black rooks
		ReturnPiece blackRook1 = new ReturnPiece();
		blackRook1.pieceFile = PieceFile.a;
		blackRook1.pieceRank = 8;
		blackRook1.pieceType = PieceType.BR;
		board.add(blackRook1);

		ReturnPiece blackRook2 = new ReturnPiece();
		blackRook2.pieceFile = PieceFile.h;
		blackRook2.pieceRank = 8;
		blackRook2.pieceType = PieceType.BR;
		board.add(blackRook2);


		
	
	}
}
