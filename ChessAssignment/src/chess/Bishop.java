package chess;

public class Bishop extends ReturnPiece {
    public Bishop(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a bishop
        if (this.pieceType != PieceType.WB && this.pieceType != PieceType.BB) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Ensure the move is along a diagonal (i.e., abs(fileDifference) == abs(rankDifference))
        if (Math.abs(fileDifference) == Math.abs(rankDifference)) {
            return true;
        }

        return false;
    }
}
