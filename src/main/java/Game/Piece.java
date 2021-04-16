package Game;

import PieceType.Empty;

import java.util.ArrayList;

public class Piece {
    protected final char label;
    protected final char colour;
    protected int row;
    protected int col;

    public Piece(char label, int[] location) {
        this.label = label;
        colour = getColourOfLabel(label, location);
        row = location[0];
        col = location[1];
    }

    public static char getColourOfLabel(char label, int[] location) {
        if (label >= 65 && label <= 90) {
            return 'w';
        } else if (label >= 97 && label <= 122) {
            return 'b';
        } else {
            //set the colour for an empty piece
            if (location[0] % 2 == 0) {
                if (location[1] % 2 == 0) {
                    return 'w';
                } else {
                    return 'b';
                }
            } else {
                if (location[1] % 2 == 0) {
                    return 'b';
                } else {
                    return 'w';
                }
            }
        }
    }

    public static boolean isEmptyPiece(char label) {
        if (label >= 65 && label <= 90) {
            return false;
        } else return label < 97 || label > 122;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getLabel() {
        return label;
    }

    public char getColour() {
        return colour;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ArrayList<int[]> generateMovesForPiece(Piece[][] board, boolean isInCheck) {
        return null;
    }

    public static char reverseColour(char colour) {
        if (colour == 'w') {
            return 'b';
        } else {
            return 'w';
        }
    }

    protected void filterOutInvalidMoves(Piece[][] board, ArrayList<int[]> moves) {
        //if move contains same colour piece that isn't empty remove all following moves
        moves.removeIf(move -> move[0] >= 8 || move[0] < 0 || move[1] >= 8 || move[1] < 0);
        boolean remove = false;
        ArrayList<int[]> movesToDelete = new ArrayList<>();
        for (int[] move : moves) {
            if (remove) {
                movesToDelete.add(move);
            } else if (board[move[0]][move[1]].getColour() != colour && !Piece.isEmptyPiece(board[move[0]][move[1]].getLabel())) {
                remove = true;
            } else if (board[move[0]][move[1]].getColour() == colour && !Piece.isEmptyPiece(board[move[0]][move[1]].getLabel())) {
                movesToDelete.add(move);
                remove = true;
            }
        }

        for (int[] moveToDelete : movesToDelete) {
            moves.remove(moveToDelete);
        }
    }

    protected void removeInvalidMoves(Piece[][] boardArray, ArrayList<int[]> moves, Piece piece) {
        //if move does not prevent check then remove it
        int tmpRow = row;
        int tmpCol = col;
        ArrayList<int[]> movesToDelete = new ArrayList<>();
        for (int[] move : moves) {
            //move piece
            boardArray[row][col] = new Empty('8', new int[]{row, col});
            boardArray[move[0]][move[1]] = piece;
            this.setRow(move[0]);
            this.setCol(move[1]);
            //if still in check remove the move
            if (Board.isInCheck(colour, boardArray)) {
                movesToDelete.add(move);
            }
            boardArray[row][col] = new Empty('8', new int[]{row, col});
            boardArray[tmpRow][tmpCol] = piece;
            this.setRow(tmpRow);
            this.setCol(tmpCol);
        }

        for (int[] moveToDelete : movesToDelete) {
            moves.remove(moveToDelete);
        }
    }

    protected void filterOutInvalidMovesShorter(Piece[][] board, ArrayList<int[]> moves) {
        //if move contains same colour piece that isn't empty remove all following moves
        moves.removeIf(move -> move[0] >= 8 || move[0] < 0 || move[1] >= 8 || move[1] < 0);
        ArrayList<int[]> movesToDelete = new ArrayList<>();
        for (int[] move : moves) {
            if (board[move[0]][move[1]].getColour() == colour && !Piece.isEmptyPiece(board[move[0]][move[1]].getLabel())) {
                movesToDelete.add(move);
            }
        }
        for (int[] moveToDelete : movesToDelete) {
            moves.remove(moveToDelete);
        }
    }
}
