package chess;
import java.util.List;

import chess.Chess.Player;

public class King extends ReturnPiece {
    private boolean hasMoved;

    public King(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        this.hasMoved = false;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a king
        if (this.pieceType != PieceType.WK && this.pieceType != PieceType.BK) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Check if the move is a castling move
        if (Math.abs(fileDifference) == 2 && rankDifference == 0) {
            // Define the current Player variable and initialize it
            Player currentPlayer = (this.pieceType == PieceType.WK) ? Player.white : Player.black;

            // Call the canCastle method to check if the move is valid
            List<ReturnPiece> piecesOnBoard = Chess.getPiecesOnBoard();
            boolean canCastle = canCastle(currentPlayer, destFile, destRank, piecesOnBoard);
            if (canCastle) {
                System.out.println("can castle returned true");
                hasMoved = true;
                if (fileDifference > 0) {
                    swapPieces(PieceFile.h, destRank, PieceFile.f, destRank, destFile, destRank, PieceFile.g, destRank);
                } else {
                    swapPieces(PieceFile.a, destRank, PieceFile.d, destRank, destFile, destRank, PieceFile.c, destRank);
                }
                return true;
            } else {
                System.out.println("can castle returned false");
            }
        }

        // Ensure the move is only one square in any direction
        if (Math.abs(fileDifference) <= 1 && Math.abs(rankDifference) <= 1) {
            hasMoved = true;
            return true;
        }
        return false;
    }

    public boolean canCastle(Player currentPlayer, PieceFile destFile, int destRank, List<ReturnPiece> piecesOnBoard) {
    
        // check if the king is not in check
        if (Chess.isInCheck(currentPlayer)) {
            return false;
        }

        // Check if the king has moved before
        if (this.hasMoved) {
            return false;
        }

        // check if the dest square is 2 squares away left or right
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        if (Math.abs(fileDifference) != 2) {
            return false;
        }

        // check if the dest square is on the same rank
        if (destRank != this.pieceRank) {
            return false;
        }

        // check if the squares between the king and rook are emtpy
        int direction = (fileDifference > 0) ? 1 : -1;
        int currentFile = this.pieceFile.ordinal() + direction;
        while (currentFile != destFile.ordinal()) {
            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceFile.ordinal() == currentFile && piece.pieceRank == destRank) {
                    return false;
                }
            }
            currentFile += direction;
        }
        hasMoved = true;
        return true;
    }
    // DUNNO IF WE NEED THIS OR IF IT WORKS DOWN HERE DELETE IF NEEDED
    public void swapPieces(PieceFile kingStartFile, int kingStartRank, PieceFile kingEndFile, int kingEndRank, PieceFile rookStartFile, int rookStartRank, PieceFile rookEndFile, int rookEndRank) {
        // swap the king
        ReturnPiece king = Chess.getPieceAt(kingStartFile, kingStartRank);
        king.pieceFile = kingEndFile;
        king.pieceRank = kingEndRank;

        // swap the rook
        ReturnPiece rook = Chess.getPieceAt(rookStartFile, rookStartRank);
        rook.pieceFile = rookEndFile;
        rook.pieceRank = rookEndRank;
    }
}
