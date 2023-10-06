package chess;

public abstract class Pieces {
    private String color;
    private int row;
    private int col;


    public Pieces(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col; 
    }

    public abstract boolean validMove(int newRow, int newCol);

    public void move(int newRow, int newCol) {
        if (validMove(newRow, newCol)) {
            this.row = newRow;
            this.col = newCol;
        } else {
            // put stuff here later
        }
    }

}
