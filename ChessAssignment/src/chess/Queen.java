package chess;

public class Queen extends Pieces {
    public Queen(String color, char file, int rank) {
        super(color, file, rank);
    }


    @Override
    public boolean validMove(char newFile, int newRank) {
        // TODO Auto-generated method stub
        return false;
    }
}
