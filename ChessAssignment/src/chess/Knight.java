package chess;

public class Knight extends Pieces {
    public Knight(String color, int row, int col) {
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
