package Game;

import PieceType.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private String currentState;
    Piece[][] board = new Piece[8][8];

    public Board() {
        currentState = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        decodeFEN(currentState);
    }

    public Board(String state) {
        currentState = state;
        decodeFEN(state);
    }

    private void decodeFEN(String state) {
        String[] rows = state.split("/");
        int boardCol = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                char pieceLabel = rows[i].charAt(j);
                //if it is a number add empty pieces for that number
                if (pieceLabel >= 48 && pieceLabel <= 57) {
                    for (int k = 0; k < Integer.parseInt(String.valueOf(pieceLabel)); k++) {
                        board[i][boardCol] = getPiece(pieceLabel, i, boardCol);
                        boardCol++;
                    }
                } else {
                    board[i][boardCol] = getPiece(pieceLabel, i, boardCol);
                    boardCol++;
                }
            }
            boardCol = 0;
        }
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece getPiece(char code, int i, int j) {
        int[] location = {i, j};
        if (String.valueOf(code).equalsIgnoreCase("r")) {
            return new Rook(code, location);
        } else if (String.valueOf(code).equalsIgnoreCase("n")) {
            return new Knight(code, location);
        } else if (String.valueOf(code).equalsIgnoreCase("b")) {
            return new Bishop(code, location);
        } else if (String.valueOf(code).equalsIgnoreCase("q")) {
            return new Queen(code, location);
        } else if (String.valueOf(code).equalsIgnoreCase("k")) {
            return new King(code, location);
        } else if (String.valueOf(code).equalsIgnoreCase("p")) {
            return new Pawn(code, location);
        } else {
            return new Empty(code, location);
        }
    }

    public String getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            string.append(Arrays.toString(board[i])).append("\n");
        }
        return string.toString();
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void movePiece(Piece selectedPiece, int[] newLocation) {
        board[selectedPiece.getRow()][selectedPiece.getCol()] = new Empty('8', new int[]{selectedPiece.getRow(), selectedPiece.getCol()});
        board[newLocation[0]][newLocation[1]] = selectedPiece;
        selectedPiece.setRow(newLocation[0]);
        selectedPiece.setCol(newLocation[1]);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void updateState() {
        StringBuilder newState = new StringBuilder();
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Piece.isEmptyPiece(board[i][j].getLabel())) {
                    count++;
                } else if (count > 0) {
                    newState.append(count).append(board[i][j].getLabel());
                    count = 0;
                } else {
                    newState.append(board[i][j].getLabel());
                }
            }
            if (count > 0) {
                newState.append(count);
            }
            if (i != 7) {
                newState.append("/");
            }
            count = 0;
        }
        currentState = newState.toString();
    }

    public static boolean isInCheck(char playerColour, Piece[][] board) {
        ArrayList<int[]> allMoves = new ArrayList<>();
        int[] kingLocation = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //find the king of the current player
                if (board[i][j].getColour() == playerColour && String.valueOf(board[i][j].label).equalsIgnoreCase("k")) {
                    kingLocation = new int[]{board[i][j].row, board[i][j].col};
                }

                //get all the possible moves of the opponent
                if (board[i][j].getColour() != playerColour && !Piece.isEmptyPiece(board[i][j].getLabel())) {
                    allMoves.addAll(board[i][j].generateMoves(board, false));
                }
            }
        }


        for (int[] move : allMoves) {
           if (kingLocation != null){
               if (move[0] == kingLocation[0] && move[1] == kingLocation[1]) {
                   return true;
               }
           }
        }
        return false;
    }

    public boolean isInCheckMate(Player currentPlayer) {
        ArrayList<int[]> moves = new ArrayList<>();
        char playerColour = currentPlayer.getColour();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //get all the possible moves of the opponent
                if (board[i][j].getColour() == playerColour && !Piece.isEmptyPiece(board[i][j].getLabel())) {
                    moves.addAll(board[i][j].generateMoves(board, true));
                }
            }
        }
        return moves.isEmpty();
    }

    public ArrayList<Piece[][]> generateAllBoardsForTurn(char playerColour) {
        ArrayList<Piece[][]> boards = new ArrayList<>();

//        System.out.println(this);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getColour() == playerColour && !Piece.isEmptyPiece(board[row][col].getLabel())) {

                    //get moves for that piece
                    ArrayList<int[]> moves = board[row][col].generateMoves(board, true);
//                    System.out.println("moves for piece " + board[row][col]);
                    //create a new board for each of the moves
                    for (int[] move : moves) {

//                        System.out.println("row: " + row + ", col: " + col);
//                        System.out.println(move[0] + ", " + move[1]);

                        Piece[][] newBoard = new Piece[8][8];
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (newBoard[i][j] == null) {
                                    if (i == move[0] && j == move[1]) {
                                        newBoard[i][j] = board[row][col];
                                        newBoard[row][col] = new Empty('8', new int[]{row, col});
                                    } else {
                                        newBoard[i][j] = board[i][j];
                                    }
                                }
                            }
                        }
                        boards.add(newBoard);


                    }
//                    System.out.println();
                }
            }
        }
        return boards;
    }

    private Piece[][] getCopyOfBoard(Piece[][] board) {
        Piece[][] boardCopy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, 8);
        }
        return boardCopy;
    }


}
