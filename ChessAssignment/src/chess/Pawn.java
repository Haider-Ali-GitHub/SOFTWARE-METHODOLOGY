package chess;

public class Pawn extends ReturnPiece {

    public Pawn(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        int rankDifference = destRank - this.pieceRank;
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();


        // Check for En Passant
        if (Math.abs(fileDifference) == 1 && rankDifference == 1 && Chess.lastPawnMovedTwoSquares != null) {
            // For white pawns
            if (pieceType == PieceType.WP && Chess.lastPawnMovedTwoSquares.pieceRank == this.pieceRank && Chess.lastPawnMovedTwoSquares.pieceFile == destFile) {
                return true;
            }
            // For black pawns
            else if (pieceType == PieceType.BP && Chess.lastPawnMovedTwoSquares.pieceRank == this.pieceRank && Chess.lastPawnMovedTwoSquares.pieceFile == destFile) {
                return true;
            }
        }
        // Basic forward move
        if (fileDifference == 0) {
            if (pieceType == PieceType.WP && (rankDifference == 1 || 
                (this.pieceRank == 2 && rankDifference == 2))) {
                // Ensure no piece is in the path
                for(int i = 1; i <= rankDifference; i++) {
                    if(Chess.getPieceAt(this.pieceFile, this.pieceRank + i) != null) {
                        return false;
                    }
                }
                return true;
            }
            else if (pieceType == PieceType.BP && (rankDifference == -1 || 
                (this.pieceRank == 7 && rankDifference == -2))) {
                // Ensure no piece is in the path
                for(int i = 1; i <= Math.abs(rankDifference); i++) {
                    if(Chess.getPieceAt(this.pieceFile, this.pieceRank - i) != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        // Capturing move (Diagonal move)
        else if (Math.abs(fileDifference) == 1 && Math.abs(rankDifference) == 1) {
            if (pieceType == PieceType.WP && rankDifference == 1) {
                // Check if there is a piece to capture
                if(Chess.getPieceAt(destFile, destRank) == null ||
                   Chess.getPieceAt(destFile, destRank).pieceType == PieceType.WP) {
                    return false;
                }
                return true;
            }
            else if (pieceType == PieceType.BP && rankDifference == -1) {
                // Check if there is a piece to capture
                if(Chess.getPieceAt(destFile, destRank) == null ||
                   Chess.getPieceAt(destFile, destRank).pieceType == PieceType.BP) {
                    return false;
                }
                return true;
            }
        }

        // If none of the above conditions were met, the move is invalid
        return false;
    }
}
