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

		/* FILL IN THIS METHOD */

		String[] parts = move.split(" "); 
		
		if (parts.length != 2) {
			// error case
			ReturnPlay rp = new ReturnPlay();
			rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return rp;
		}

	    // Extracting source and destination from the move string
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

		// Validate the move (very basic check, many other validations missing)
		if (isValidMove(movingPiece, destFile, destRank)) {
			movingPiece.pieceFile = destFile;
			movingPiece.pieceRank = destRank;
			// Remove any piece at the destination
			board.removeIf(piece -> piece.pieceFile == destFile && piece.pieceRank == destRank);
		} else {
			rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return rp;
		}
	
		// Switch the current player
		currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
	
		rp.piecesOnBoard = board;
		return rp;
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		// return null;
	}

	private static boolean isValidMove(ReturnPiece piece, PieceFile destFile, int destRank) {
		switch (piece.pieceType) {
			case WP:
				// Basic pawn move for white, allowing 2-square move from starting position
				return (piece.pieceRank == destRank - 1 && piece.pieceFile == destFile) || 
					   (piece.pieceRank == 2 && destRank == 4 && piece.pieceFile == destFile);
			case BP:
				// Basic pawn move for black, allowing 2-square move from starting position
				return (piece.pieceRank == destRank + 1 && piece.pieceFile == destFile) || 
					   (piece.pieceRank == 7 && destRank == 5 && piece.pieceFile == destFile);
			case WN:
			case BN:
				// Basic knight move
				int rankDifference = Math.abs(piece.pieceRank - destRank);
				int fileDifference = Math.abs(piece.pieceFile.ordinal() - destFile.ordinal());
				return (rankDifference == 1 && fileDifference == 2) || (rankDifference == 2 && fileDifference == 1);
			case WR:
			case BR:
				// Basic rook move (not accounting for pieces in the path)
				return (piece.pieceFile == destFile || piece.pieceRank == destRank);
			case WB:
			case BB:
				// Basic bishop move (not accounting for pieces in the path)
				return Math.abs(piece.pieceRank - destRank) == Math.abs(piece.pieceFile.ordinal() - destFile.ordinal());
			case WQ:
			case BQ:
				// Basic queen move (not accounting for pieces in the path)
				int rankDiff = Math.abs(piece.pieceRank - destRank);
				int fileDiff = Math.abs(piece.pieceFile.ordinal() - destFile.ordinal());
				return (piece.pieceFile == destFile || piece.pieceRank == destRank || rankDiff == fileDiff);
			case WK:
			case BK:
				// Basic king move
				return Math.abs(piece.pieceRank - destRank) <= 1 && Math.abs(piece.pieceFile.ordinal() - destFile.ordinal()) <= 1;
			default:
				return false;
		}
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


		//Knights 
		// Initialize white knights
		ReturnPiece whiteKnight1 = new ReturnPiece();
		whiteKnight1.pieceFile = PieceFile.b;
		whiteKnight1.pieceRank = 1;
		whiteKnight1.pieceType = PieceType.WN;
		board.add(whiteKnight1);

		ReturnPiece whiteKnight2 = new ReturnPiece();
		whiteKnight2.pieceFile = PieceFile.g;
		whiteKnight2.pieceRank = 1;
		whiteKnight2.pieceType = PieceType.WN;
		board.add(whiteKnight2);

		// Initialize black knights
		ReturnPiece blackKnight1 = new ReturnPiece();
		blackKnight1.pieceFile = PieceFile.b;
		blackKnight1.pieceRank = 8;
		blackKnight1.pieceType = PieceType.BN;
		board.add(blackKnight1);

		ReturnPiece blackKnight2 = new ReturnPiece();
		blackKnight2.pieceFile = PieceFile.g;
		blackKnight2.pieceRank = 8;
		blackKnight2.pieceType = PieceType.BN;
		board.add(blackKnight2);

		//Bishop 
		// Initialize white bishops
		ReturnPiece whiteBishop1 = new ReturnPiece();
		whiteBishop1.pieceFile = PieceFile.c;
		whiteBishop1.pieceRank = 1;
		whiteBishop1.pieceType = PieceType.WB;
		board.add(whiteBishop1);

		ReturnPiece whiteBishop2 = new ReturnPiece();
		whiteBishop2.pieceFile = PieceFile.f;
		whiteBishop2.pieceRank = 1;
		whiteBishop2.pieceType = PieceType.WB;
		board.add(whiteBishop2);

		// Initialize black bishops
		ReturnPiece blackBishop1 = new ReturnPiece();
		blackBishop1.pieceFile = PieceFile.c;
		blackBishop1.pieceRank = 8;
		blackBishop1.pieceType = PieceType.BB;
		board.add(blackBishop1);

		ReturnPiece blackBishop2 = new ReturnPiece();
		blackBishop2.pieceFile = PieceFile.f;
		blackBishop2.pieceRank = 8;
		blackBishop2.pieceType = PieceType.BB;
		board.add(blackBishop2);


		//Queens 
		// Initialize white queen
		ReturnPiece whiteQueen = new ReturnPiece();
		whiteQueen.pieceFile = PieceFile.d;
		whiteQueen.pieceRank = 1;
		whiteQueen.pieceType = PieceType.WQ;
		board.add(whiteQueen);

		// Initialize black queen
		ReturnPiece blackQueen = new ReturnPiece();
		blackQueen.pieceFile = PieceFile.d;
		blackQueen.pieceRank = 8;
		blackQueen.pieceType = PieceType.BQ;
		board.add(blackQueen);

		//Kings 
		// Initialize white king
		ReturnPiece whiteKing = new ReturnPiece();
		whiteKing.pieceFile = PieceFile.e;
		whiteKing.pieceRank = 1;
		whiteKing.pieceType = PieceType.WK;
		board.add(whiteKing);

		// Initialize black king
		ReturnPiece blackKing = new ReturnPiece();
		blackKing.pieceFile = PieceFile.e;
		blackKing.pieceRank = 8;
		blackKing.pieceType = PieceType.BK;
		board.add(blackKing);
		

		// Set the current player to white
		currentPlayer  = Player.white;
	}
}
