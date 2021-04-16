package PieceType;

import Game.Piece;

public class Empty extends Piece {
    public Empty(char colour, int[] location) {
        super(colour, location);
    }

    @Override
    public String toString() {
        return colour + "e";
    }
}
