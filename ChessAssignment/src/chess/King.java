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

        int fileDifference = Math.abs(destFile.ordinal() - this.pieceFile.ordinal());
        int rankDifference = Math.abs(destRank - this.pieceRank);

        if (fileDifference <= 1 && rankDifference <= 1) {
            return true; // Normal one-square move
        }

        // Additional conditions for castling
        if (!this.hasMoved && rankDifference == 0 && fileDifference == 2) {
            boolean isKingSide = (destFile == PieceFile.g); 
            boolean isQueenSide = (destFile == PieceFile.c); 

            if (isKingSide || isQueenSide) {
                return true; 
            }
        }
        
        return false; 
    }
}
