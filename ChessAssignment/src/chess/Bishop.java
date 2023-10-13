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
        if (Math.abs(fileDifference) != Math.abs(rankDifference)) {
            return false;
        }

        // Determine the direction of movement in file and rank
        int fileDirection = (fileDifference > 0) ? 1 : -1;
        int rankDirection = (rankDifference > 0) ? 1 : -1;

        // Check for pieces in the path
        PieceFile currentFile = this.pieceFile;
        int currentRank = this.pieceRank;
        while (true) {
            // Move to the next square along the path
            currentFile = PieceFile.values()[currentFile.ordinal() + fileDirection];
            currentRank += rankDirection;

            // Break if we've reached the destination
            if (currentFile == destFile && currentRank == destRank) {
                break;
            }

            // If there is a piece in the square, the move is not valid
            if (Chess.getPieceAt(currentFile, currentRank) != null) {
                return false;
            }
        }

        return true;
    }
}

