package Game;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessEngine {
    private Player[] players = {new Player('w'), new AI('b')};
    private Player currentPlayer = players[0];
    private Board board = new Board();

    public Piece getPiece(int row, int col) {
        return board.getPiece(row, col);
    }

    public Piece[][] getBoard() {
        return board.getBoard();
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getBoardState() {
        return board.getCurrentState();
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public void endTurn() {
        currentPlayer.setSelectedPiece(null);
        if (currentPlayer.getColour() == 'w') {
            //AI's turn
//            currentPlayer = players[1];
            board = ((AI) players[1]).playTurn(board);
            if (board == null) {
                newGame();
            }
        } else {
            currentPlayer = players[0];
        }
    }

    public void movePiece(Piece selectedPiece, int[] newLocation) {
        board.movePiece(selectedPiece, newLocation);
        board.updateState();
    }

    public void inspectForCheck() {
        if (Board.isInCheck(currentPlayer.getColour(), board.getBoard())) {
            currentPlayer.check();
        }
    }

    public boolean isCurrentPlayerInCheckMate() {
        return board.isInCheckMate(currentPlayer);
    }

    public void newGame() {
        board = new Board();
        players = new Player[]{new Player('w'), new AI('b')};
        currentPlayer = players[0];
    }
}
