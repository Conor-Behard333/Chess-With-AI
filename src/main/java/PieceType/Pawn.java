package PieceType;

import Game.Board;
import Game.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    public Pawn(char colour, int[] location) {
        super(colour, location);
    }

    @Override
    public String toString() {
        return super.colour + "p";
    }

    @Override
    public ArrayList<int[]> generateMovesForPiece(Piece[][] board, boolean isInCheck) {
        Piece[][] boardArray = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);
        ArrayList<int[]> moves = new ArrayList<>();
        if (colour == 'w') {
            if (row == 6) {
                //can move 2 places forward
                moves.add(new int[]{row - 1, col});
                moves.add(new int[]{row - 2, col});
            } else {
                //can move 1 place forward
                moves.add(new int[]{row - 1, col});
            }
            moves.add(new int[]{row - 1, col + 1});
            moves.add(new int[]{row - 1, col - 1});
        } else {
            //if player is black
            if (row == 1) {
                //can move 2 places forward
                moves.add(new int[]{row + 1, col});
                moves.add(new int[]{row + 2, col});
            } else {
                //can move 1 place forward
                moves.add(new int[]{row + 1, col});
            }
            moves.add(new int[]{row + 1, col + 1});
            moves.add(new int[]{row + 1, col - 1});
        }
        //remove any moves out of bounds
        moves.removeIf(move -> move[0] >= 8 || move[0] < 0 || move[1] >= 8 || move[1] < 0);

        moves.removeIf(move -> (Piece.isEmptyPiece(boardArray[move[0]][move[1]].getLabel()) && move[1] != col) || (move[1] == col && !Piece.isEmptyPiece(boardArray[move[0]][move[1]].getLabel())));

        if (isInCheck) {
            removeInvalidMoves(boardArray, moves, this);
        }
        return moves;
    }

}