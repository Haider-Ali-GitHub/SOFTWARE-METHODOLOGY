package chess;

public class King extends ReturnPiece {
    public boolean hasMoved; // This should be set to true after the King moves for the first time.

    public King(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
        this.hasMoved = false; 
    }

    public boolean isValidMove(PieceFile destFile, int destRank) {
        // Normal movement check for King
        int fileDifference = Math.abs(destFile.ordinal() - this.pieceFile.ordinal());
        int rankDifference = Math.abs(destRank - this.pieceRank);

        if (fileDifference <= 1 && rankDifference <= 1) {
            return true; // Normal one-square move
        }

        // Additional conditions for castling
        if (!this.hasMoved && rankDifference == 0 && fileDifference == 2) {
            // Ensure the move is two squares to the left or right for castling
            boolean isKingSide = (destFile == PieceFile.g); // or use the file of your rook at the kingside
            boolean isQueenSide = (destFile == PieceFile.c); // or use the file of your rook at the queenside

            if (isKingSide || isQueenSide) {
                // The actual movement will be handled in the play method or another method that handles castling.
                return true; // This is potentially a valid castling move.
            }
        }

        return false; // If none of the conditions are met, it's an invalid move.
    }
}
