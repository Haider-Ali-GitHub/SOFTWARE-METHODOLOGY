package chess;

public class Pawn extends Pieces {
    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }


    @Override
    public boolean validMove(int newRow, int newCol) {
        return true; //fix later
    }

    /*public String getColor() { //duno if i need this yet
        return color;
    } */
    
}
