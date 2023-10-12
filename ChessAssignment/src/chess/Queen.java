package chess;

public class Queen extends ReturnPiece {
    public Queen(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a queen
        if (this.pieceType != PieceType.WQ && this.pieceType != PieceType.BQ) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Moving vertically
        if(fileDifference == 0 && rankDifference != 0) {
            return true;
        }

        // Moving horizontally
        if(rankDifference == 0 && fileDifference != 0) {
            return true;
        }

        // Moving diagonally
        if (Math.abs(fileDifference) == Math.abs(rankDifference)) {
            return true;
        }

        return false;
    }
}
