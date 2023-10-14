package chess;

public class King extends ReturnPiece {
    private boolean hasMoved;
    public King(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        this.hasMoved = false;
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

    public boolean canCastle(PieceFile desFile, int destRank) {
        if (this.pieceType != PieceType.WK && this.pieceType != PieceType.BK) {
            return false;
        }

        // Check if king has movedbefore   
        if (this.hasMoved) {
            return false;
        }

        // check if the dest square is 2 squares away left or right
        int fileDifference = desFile.ordinal() - this.pieceFile.ordinal();
        if (Math.abs(fileDifference) != 2) {
            return false;
        }

        // check if the dest square is on the same rank
        if (destRank != this.pieceRank) {
            return false;
        }

        // check if the squares between the king and rook are empty
        PieceFile rookFile;
        if (fileDifference > 0) {
            rookFile = PieceFile.h;
        } else {
            rookFile = PieceFile.a;
        }

        // isnt working for some reason
        // ReturnPiece rook = getPiece(rookFile, this.pieceRank);

        return true; // HERE TO MAKE COMPILER HAPPY
    }
}
