package chess;

public abstract class Pieces {
    public String color;
    public char file;
    public int rank;

    // pass in color, char file, AND int rank, not row and col
    //letter is file and number is rank

    public Pieces(String color, char file, int rank) {
        this.color = color;
        this.file = file;
        this.rank = rank; 
    }

    public abstract boolean validMove(char newFile, int newRank);

    public void move(char newFile, int newRank) {
        if (validMove(newFile, newRank)) {
            this.file = newFile;
            this.rank = newRank;
        } else {
            // Handle invalid move here
            System.out.println("Invalid move");
        }
    }

    public String getColor() {
        return color;
    }
}
