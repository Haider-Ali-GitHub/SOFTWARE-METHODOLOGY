package chess;

public class King extends ReturnPiece {
    public King(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a king
        if (this.pieceType != PieceType.WK && this.pieceType != PieceType.BK) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Ensure the move is only one square in any direction
        if (Math.abs(fileDifference) <= 1 && Math.abs(rankDifference) <= 1) {
            return true;
        }

        return false;
    }
}
