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

        // Basic forward move
        if (fileDifference == 0) {
            if (pieceType == PieceType.WP && (rankDifference == 1 || 
                (this.pieceRank == 2 && rankDifference == 2))) {
                return true;
            }
            else if (pieceType == PieceType.BP && (rankDifference == -1 || 
                (this.pieceRank == 7 && rankDifference == -2))) {
                return true;
            }
        }

        // Capturing move (Diagonal move)
        else if (Math.abs(fileDifference) == 1) {
            if (pieceType == PieceType.WP && rankDifference == 1) {
                return true;
            }
            else if (pieceType == PieceType.BP && rankDifference == -1) {
                return true;
            }
        }

        // If none of the above conditions were met, the move is invalid
        return false;
    }
}
