package chess;

public class Rook extends ReturnPiece {
    public boolean hasMoved;
    public Rook(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        this.hasMoved = false;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {

        if (this.pieceType != PieceType.WR && this.pieceType != PieceType.BR) {
            return false;
        }

        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Moving vertically 
        if(fileDifference == 0 && rankDifference != 0) {
            int step = rankDifference > 0 ? 1 : -1;
            for(int i = 1; i < Math.abs(rankDifference); i++) {
                // Check if there is a piece in the path
                if(Chess.getPieceAt(this.pieceFile, this.pieceRank + i * step) != null) {
                    return false;
                }
            }
            return true;
        }

        // Moving horizontally 
        if(rankDifference == 0 && fileDifference != 0) {
            int step = fileDifference > 0 ? 1 : -1;
            for(int i = 1; i < Math.abs(fileDifference); i++) {
                if(Chess.getPieceAt(PieceFile.values()[this.pieceFile.ordinal() + i * step], this.pieceRank) != null) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
