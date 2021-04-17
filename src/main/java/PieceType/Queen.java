package PieceType;

import Game.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece {
    public Queen(char colour, int[] location) {
        super(colour, location, 90);
    }

    @Override
    public String toString() {
        return super.colour + "q";
    }

    @Override
    public ArrayList<int[]> generateMoves(Piece[][] board, boolean isInCheck) {
        Piece[][] boardArray = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);
        ArrayList<int[]> moves = new ArrayList<>();
        ArrayList<int[]> movesUp = new ArrayList<>();
        ArrayList<int[]> movesLeft = new ArrayList<>();
        ArrayList<int[]> movesDown = new ArrayList<>();
        ArrayList<int[]> movesRight = new ArrayList<>();

        ArrayList<int[]> movesUpRight = new ArrayList<>();
        ArrayList<int[]> movesUpLeft = new ArrayList<>();
        ArrayList<int[]> movesDownLeft = new ArrayList<>();
        ArrayList<int[]> movesDownRight = new ArrayList<>();

        for (int i = 1; i <= row; i++) {//up
            movesUp.add(new int[]{row - i, col});
        }
        filterOutInvalidMoves(boardArray, movesUp);
        moves.addAll(movesUp);

        for (int i = 1; i <= col; i++) {//left
            movesLeft.add(new int[]{row, col - i});
        }
        filterOutInvalidMoves(boardArray, movesLeft);
        moves.addAll(movesLeft);

        for (int i = 1; i < 8 - row; i++) {//down
            movesDown.add(new int[]{row + i, col});
        }
        filterOutInvalidMoves(boardArray, movesDown);
        moves.addAll(movesDown);

        for (int i = 1; i < 8 - col; i++) {//right
            movesRight.add(new int[]{row, col + i});
        }
        filterOutInvalidMoves(boardArray, movesRight);
        moves.addAll(movesRight);

        for (int i = 1; i <= row; i++) {//up right (white)
            movesUpRight.add(new int[]{row - i, col + i});
        }
        filterOutInvalidMoves(boardArray, movesUpRight);
        moves.addAll(movesUpRight);

        for (int i = 1; i <= col; i++) {//up left
            movesUpLeft.add(new int[]{row - i, col - i});
        }
        filterOutInvalidMoves(boardArray, movesUpLeft);
        moves.addAll(movesUpLeft);

        for (int i = 1; i < 8 - row; i++) {//down left
            movesDownLeft.add(new int[]{row + i, col + i});
        }
        filterOutInvalidMoves(boardArray, movesDownLeft);
        moves.addAll(movesDownLeft);

        for (int i = 1; i < 8 - row; i++) {//down right
            movesDownRight.add(new int[]{row + i, col - i});
        }
        filterOutInvalidMoves(boardArray, movesDownRight);
        moves.addAll(movesDownRight);

        if (isInCheck) {
            removeInvalidMoves(boardArray, moves, this);
        }

        return moves;
    }
}
