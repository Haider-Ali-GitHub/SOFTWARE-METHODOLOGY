package chess;

public class Knight extends ReturnPiece {
    public Knight(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a knight
        if (this.pieceType != PieceType.WN && this.pieceType != PieceType.BN) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Check that the move is L-shaped (2 squares one way and 1 square the other)
        if ((Math.abs(fileDifference) == 2 && Math.abs(rankDifference) == 1) || 
            (Math.abs(fileDifference) == 1 && Math.abs(rankDifference) == 2)) {
            return true;
        }

        return false;
    }
}
