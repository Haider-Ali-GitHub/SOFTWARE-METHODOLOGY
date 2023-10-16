package chess;

public class Queen extends ReturnPiece {

    public Queen(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {

        if (this.pieceType != PieceType.WQ && this.pieceType != PieceType.BQ) {
            return false;
        }

    
        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        // Moving vertically
        if (fileDifference == 0 && rankDifference != 0) {
            // Check if the path is clear
            int step = (rankDifference > 0) ? 1 : -1;
            for(int i = 1; i < Math.abs(rankDifference); i++) {
                if(Chess.getPieceAt(this.pieceFile, this.pieceRank + i * step) != null) {
                    return false; // A piece is blocking the way
                }
            }
            return true;
        }

        // Moving horizontally
        if (rankDifference == 0 && fileDifference != 0) {
            int step = (fileDifference > 0) ? 1 : -1;
            for(int i = 1; i < Math.abs(fileDifference); i++) {
                if(Chess.getPieceAt(PieceFile.values()[this.pieceFile.ordinal() + i * step], this.pieceRank) != null) {
                    return false; 
                }
            }
            return true;
        }

        // Moving diagonally
        if (Math.abs(fileDifference) == Math.abs(rankDifference)) {
            // Check if the path is clear
            int rankStep = (rankDifference > 0) ? 1 : -1;
            int fileStep = (fileDifference > 0) ? 1 : -1;
            for(int i = 1; i < Math.abs(rankDifference); i++) {
                if(Chess.getPieceAt(PieceFile.values()[this.pieceFile.ordinal() + i * fileStep], this.pieceRank + i * rankStep) != null) {
                    return false; // A piece is blocking the way
                }
            }
            return true;
        }

        return false;
    }
}
