package chess;

public class Knight extends ReturnPiece {
    public Knight(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {

        if (this.pieceType != PieceType.WN && this.pieceType != PieceType.BN) {
            return false;
        }

        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        if ((Math.abs(fileDifference) == 2 && Math.abs(rankDifference) == 1) || (Math.abs(fileDifference) == 1 && Math.abs(rankDifference) == 2)) {
            return true;
        }

        return false;
    }
}
