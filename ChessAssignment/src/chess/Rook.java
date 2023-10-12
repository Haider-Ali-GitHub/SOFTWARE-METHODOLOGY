package chess;

public class Rook extends ReturnPiece {
    public Rook(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Ensure the piece being moved is a rook
        if (this.pieceType != PieceType.WR && this.pieceType != PieceType.BR) {
            return false;
        }

        // Check the differences in file and rank between the current and destination squares
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Moving vertically (files are the same, ranks are different)
        if(fileDifference == 0 && rankDifference != 0) {
            return true;
        }

        // Moving horizontally (ranks are the same, files are different)
        if(rankDifference == 0 && fileDifference != 0) {
            return true;
        }

        return false;
    }
}
