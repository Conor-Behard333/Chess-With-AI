package PieceType;

import Game.Board;
import Game.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {
    public Knight(char colour, int[] location) {
        super(colour, location);
    }

    @Override
    public String toString() {
        return super.colour + "n";
    }

    @Override
    public ArrayList<int[]> generateMovesForPiece(Piece[][] board, boolean isInCheck) {
        Piece[][] boardArray = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);
        ArrayList<int[]> moves = new ArrayList<>();

        moves.add(new int[]{row - 2, col - 1});
        moves.add(new int[]{row - 2, col + 1});

        moves.add(new int[]{row - 1, col + 2});
        moves.add(new int[]{row - 1, col - 2});

        moves.add(new int[]{row + 2, col - 1});
        moves.add(new int[]{row + 2, col + 1});

        moves.add(new int[]{row + 1, col - 2});
        moves.add(new int[]{row + 1, col + 2});

        filterOutInvalidMovesShorter(boardArray, moves);
        if (isInCheck) {
            removeInvalidMoves(boardArray, moves, this);
        }
        return moves;
    }
}
