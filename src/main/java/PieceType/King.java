package PieceType;

import Game.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
    public King(char colour, int[] location) {
        super(colour, location, 900);
    }

    @Override
    public String toString() {
        return super.colour + "k";
    }

    @Override
    public ArrayList<int[]> generateMoves(Piece[][] board, boolean isInCheck) {
        Piece[][] boardArray = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);
        ArrayList<int[]> moves = new ArrayList<>();

        moves.add(new int[]{row - 1, col});
        moves.add(new int[]{row + 1, col});

        moves.add(new int[]{row, col - 1});
        moves.add(new int[]{row, col + 1});

        moves.add(new int[]{row + 1, col + 1});
        moves.add(new int[]{row + 1, col - 1});

        moves.add(new int[]{row - 1, col - 1});
        moves.add(new int[]{row - 1, col + 1});

        filterOutInvalidMovesShorter(boardArray, moves);
        if (isInCheck) {
            removeInvalidMoves(boardArray, moves, this);
        }
        return moves;
    }

}