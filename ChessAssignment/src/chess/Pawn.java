package chess;

public class Pawn extends ReturnPiece {
    public Pawn(PieceType type, PieceFile file, int rank) {
        this.pieceType = type;
        this.pieceFile = file;
        this.pieceRank = rank;
    }

    @Override
    public boolean validateMove(PieceFile targetFile, int targetRank) {
        // Simplified example: Pawns move straight forward
        // Additional logic needed for first move, capturing, en passant, promotion, etc.
        if(this.pieceFile == targetFile && (this.pieceRank == targetRank - 1 || this.pieceRank == targetRank + 1)) {
            return true;
        }
        return false;
    }
}
