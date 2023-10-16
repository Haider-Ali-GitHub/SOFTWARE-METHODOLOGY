package chess;

import java.util.ArrayList;
import java.util.List;

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
	public static ReturnPiece lastPawnMovedTwoSquares = null;
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

	// if (parts.length != 2 ) {
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
	
	if (movingPiece instanceof King && !((King) movingPiece).hasMoved) {
		boolean isCastlingMove = move.equals("e1 g1") || move.equals("e1 c1") || move.equals("e8 g8") || move.equals("e8 c8");
		if (isCastlingMove) {
			boolean canCastle = attemptCastling((King) movingPiece, move);
			if (canCastle) {
				((King) movingPiece).hasMoved = true;  
				// Switch the current player and continue the game.
				currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
				return board; // Castling was successful, and the game continues.
			} else {
				board.message = ReturnPlay.Message.ILLEGAL_MOVE;
				return board; 
			}
		}
	}


	if (movingPiece instanceof Pawn && Math.abs(destRank - sourceRank) == 2) {
		lastPawnMovedTwoSquares = movingPiece;
	} 
 
	if (isValidMove(movingPiece, destFile, destRank)) {

	if (movingPiece instanceof Pawn && 
    lastPawnMovedTwoSquares != null &&
    lastPawnMovedTwoSquares.pieceRank == sourceRank && 
    lastPawnMovedTwoSquares.pieceFile == destFile &&
    Math.abs(destFile.ordinal() - sourceFile.ordinal()) == 1 && 
    Math.abs(destRank - sourceRank) == 1) {
    
    // Remove the pawn captured En Passant
    removePieceAt(destFile, sourceRank);  // Adjust rank depending on the pawn's color
}
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

	// Add Pawn Promotion logic
	if(movingPiece.pieceType == PieceType.WP && destRank == 8 || 
		movingPiece.pieceType == PieceType.BP && destRank == 1) {
	 	if(parts.length >= 3) { // Check if a promotion piece type is provided
		 	try {
					String promoTypeString = parts[2].toUpperCase();
					if(promoTypeString.length() == 1 && "RNBQ".contains(promoTypeString)) {
						PieceType promoType = PieceType.valueOf(
							(movingPiece.pieceType == PieceType.WP ? "W" : "B") + promoTypeString);
							 movingPiece.pieceType = promoType;
					}else {
							 board.message = ReturnPlay.Message.ILLEGAL_MOVE;
							 return board;
					}
				} 	catch(IllegalArgumentException e) {
				  		board.message = ReturnPlay.Message.ILLEGAL_MOVE;
						return board;
					}
		} else {
				// Default to Queen if no promotion type is provided
				movingPiece.pieceType = movingPiece.pieceType == PieceType.WP ? PieceType.WQ : PieceType.BQ;
			}
		}
		// Update movingPiece's position
		movingPiece.pieceFile = destFile;
		movingPiece.pieceRank = destRank;

            // After making the move, check if the opponent is in check
            Player opponent = (currentPlayer == Player.white) ? Player.black : Player.white;
			if (isInCheck(opponent)) {
				// If opponent has no legal moves, it's checkmate
				if (!hasLegalMoves(opponent)) {
					board.message = (opponent == Player.white) ? 
									 ReturnPlay.Message.CHECKMATE_BLACK_WINS :
									 ReturnPlay.Message.CHECKMATE_WHITE_WINS;
				} else {
					// Otherwise, it's just a check
					board.message = ReturnPlay.Message.CHECK;
				}
			}

		if (move.endsWith(" draw?"))  {
		board.message = ReturnPlay.Message.DRAW;
		return board;
		}
	
	} else {
		board.message = ReturnPlay.Message.ILLEGAL_MOVE;
		return board;
		}
    // Switch the current player
    currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;	
    return board;
}


private static boolean attemptCastling(King king, String move) {
    // Determine if it's king's side or queen's side castling based on the move
    boolean isKingsSide = move.endsWith("g1") || move.endsWith("g8");

    // Neither the king nor the rook has moved.
    if (king.hasMoved) return false;

    // Find the rook involved in the castling
    PieceFile rookFile = isKingsSide ? PieceFile.h : PieceFile.a; 
    int rank = king.pieceRank; // same rank as the king
    Rook rook = null;
    for (ReturnPiece piece : board.piecesOnBoard) {
        if (piece instanceof Rook && piece.pieceFile == rookFile && piece.pieceRank == rank) {
            rook = (Rook) piece;
            break;
        }
    }
    if (rook == null || rook.hasMoved) return false;

    // There are no pieces between the king and the rook.
    PieceFile[] filesBetween = isKingsSide ? new PieceFile[]{PieceFile.f, PieceFile.g} : new PieceFile[]{PieceFile.b, PieceFile.c, PieceFile.d};
    for (PieceFile file : filesBetween) {
        if (getPieceAt(file, rank) != null) {
            return false; // There is a piece between the king and the rook
        }
    }

    // The king is not in check, the squares it passes through are not attacked, and the square it moves to is not attacked.
    if (isInCheck(currentPlayer)) {
        return false; // Cannot castle while in check
    }

    //Check if the squares the king passes through are under attack
    for (PieceFile file : filesBetween) {
        // Temporarily move the king to the new position
        king.pieceFile = file;
        // Check if the king would be in check in the new position
        if (isInCheck(currentPlayer)) {
            king.pieceFile = isKingsSide ? PieceFile.e : (currentPlayer == Player.white ? PieceFile.e : PieceFile.e); // Reset to original position
            return false; // Cannot move through or into check
        }
    }

    // If all conditions are met, move the King and the Rook to their new positions
    king.pieceFile = isKingsSide ? PieceFile.g : PieceFile.c;
    rook.pieceFile = isKingsSide ? PieceFile.f : PieceFile.d;

    // Update their moved status
    king.hasMoved = true;
    rook.hasMoved = true;

    return true; // Castling move was successful
}


    public static  boolean isInCheck(Player currentPlayer) {
        // Find the position of the King of the current player
        PieceType kingType = (currentPlayer == Player.white) ? PieceType.WK : PieceType.BK;
        ReturnPiece king = findKing(kingType);
        
        // Get all opponent pieces
        List<ReturnPiece> opponentPieces = getOpponentPieces(currentPlayer);

        // Check if any opponent piece can move to the King’s position
        for (ReturnPiece piece : opponentPieces) {
            if (canMoveToPosition(piece, king.pieceFile, king.pieceRank)) {
                return true; // King is in check
            }
        }
        return false; // King is not in check
    }

    private  static ReturnPiece findKing(PieceType kingType) {
        for (ReturnPiece piece : board.piecesOnBoard) {
            if (piece.pieceType == kingType) {
                return piece;
            }
        }
        return null; // King not found (should never happen in a valid game state)
    }

    private  static List<ReturnPiece> getOpponentPieces(Player currentPlayer) {
        List<ReturnPiece> opponentPieces = new ArrayList<>();
        for (ReturnPiece piece : board.piecesOnBoard) {
            if ((currentPlayer == Player.white && piece.pieceType.name().charAt(0) == 'B') ||
                (currentPlayer == Player.black && piece.pieceType.name().charAt(0) == 'W')) {
                opponentPieces.add(piece);
            }
        }
        return opponentPieces;
    }

	private static boolean simulateMove(ReturnPiece piece, PieceFile destFile, int destRank) {
		// Store old position
		PieceFile oldFile = piece.pieceFile;
		int oldRank = piece.pieceRank;
	
		// Simulate move
		piece.pieceFile = destFile;
		piece.pieceRank = destRank;
	
		// Check if move puts/leaves the king in check
		boolean ownKingInCheck = isInCheck(currentPlayer);
	
		// Undo move
		piece.pieceFile = oldFile;
		piece.pieceRank = oldRank;
	
		return ownKingInCheck;
	}

	public static boolean hasLegalMoves(Player player) {
		boolean kingInCheck = isInCheck(player);
		for (ReturnPiece piece : board.piecesOnBoard) {
			// Iterate through each piece of the current player
			if ((player == Player.white && piece.pieceType.name().charAt(0) == 'W') ||
				(player == Player.black && piece.pieceType.name().charAt(0) == 'B')) {
				// Check every possible destination square
				for (PieceFile file : PieceFile.values()) {
					for (int rank = 1; rank <= 8; rank++) {
						// If the move is valid and doesn't put the player in check, return true
						// System.out.println(" piece : " + piece.pieceType + " trying to move to file: " + file + " rank: " + rank);

						// If the king is in check, validate that the move alleviates the check
						if (isValidMove(piece, file, rank)) {
							if (kingInCheck) {
								if (simulateMove(piece, file, rank)) {
									continue;  // Skip this move if it doesn’t alleviate the check
								}
							} else {
								if (!simulateMove(piece, file, rank)) {
									return true;  // This move is legal and doesn’t put the player in check
								}
							}
						}
					}
				}
			}
		}
		// No legal moves were found
		return false;
	}
	
	
    private static boolean canMoveToPosition(ReturnPiece piece, ReturnPiece.PieceFile destFile, int destRank) {
        if (piece instanceof King) {
            return ((King) piece).isValidMove(destFile, destRank);
        } else if (piece instanceof Queen) {
            return ((Queen) piece).isValidMove(destFile, destRank);
        } else if (piece instanceof Bishop) {
            return ((Bishop) piece).isValidMove(destFile, destRank);
        } else if (piece instanceof Rook) {
            return ((Rook) piece).isValidMove(destFile, destRank);
        } else if (piece instanceof Knight) {
            return ((Knight) piece).isValidMove(destFile, destRank);
        } else if (piece instanceof Pawn) {
            return ((Pawn) piece).isValidMove(destFile, destRank);
        }
        // If piece type is unrecognized or unhandled, return false as a safe fallback
        return false;
    }
    

private static void removePieceAt(PieceFile file, int rank) {
    ReturnPiece pieceToRemove = null;
    for (ReturnPiece piece : board.piecesOnBoard) {
        if (piece.pieceFile == file && piece.pieceRank == rank) {
            pieceToRemove = piece;
            break;
        }
    }
    if (pieceToRemove != null) {
        board.piecesOnBoard.remove(pieceToRemove);
    }
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


private static boolean isValidMove(ReturnPiece piece, PieceFile destFile, int destRank) {
    // Utilize specific piece logic for validation based on PieceType
	if (simulateMove(piece, destFile, destRank)) {
        return false;  // Move is not valid as it would put/leave own king in check
    }
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
