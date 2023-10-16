package chess;

public class Bishop extends ReturnPiece {
    public Bishop(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {

        if (this.pieceType != PieceType.WB && this.pieceType != PieceType.BB) {
            return false;
        }

        int fileDifference = destFile.ordinal() - this.pieceFile.ordinal();
        int rankDifference = destRank - this.pieceRank;

        if (Math.abs(fileDifference) != Math.abs(rankDifference)) {
            return false;
        }

        int fileDirection = (fileDifference > 0) ? 1 : -1;
        int rankDirection = (rankDifference > 0) ? 1 : -1;
        PieceFile currentFile = this.pieceFile;
        int currentRank = this.pieceRank;
        
        while (true) {
            int newFileOrdinal = currentFile.ordinal() + fileDirection;
            int newRank = currentRank + rankDirection;

            if (newFileOrdinal == destFile.ordinal() && newRank == destRank) {
                break;
            }

            if (newFileOrdinal < 0 || newFileOrdinal >= PieceFile.values().length || newRank < 1 || newRank > 8) {
                return false;
            }

            currentFile = PieceFile.values()[newFileOrdinal];
            currentRank = newRank;

            if (Chess.getPieceAt(currentFile, currentRank) != null) {
                return false;
            }
        }

        return true;
    }
}
