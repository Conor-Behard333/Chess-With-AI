package UI;

import Game.ChessEngine;
import Game.Piece;
import Game.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ChessInterface extends Application {
    private ChessEngine chessEngine = new ChessEngine();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        updateUI("White Players turn", 790, 900);
    }

    private void updateUI(String player, int width, int height) {
        Label playerTurn = new Label(player);
        playerTurn.setFont(new Font(50));
        playerTurn.setTranslateX(210);
        playerTurn.setTranslateY(825);

        Group board = updateBoard();
        Line border = getBorder();
        Group window = new Group();
        window.getChildren().addAll(board, border, playerTurn);

        Scene scene = new Scene(window, width, height);
        scene.setFill(Color.web("cebb9e", 1));
        this.stage.setTitle("Chess");
        this.stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/blackvariant/button-ui-system-apps/1024/Chess-icon.png"));
        this.stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private Group updateBoard() {
        Group board = new Group();

        //adds all pieces
        board.getChildren().add(getPieces());

        return board;
    }

    private Group getPieces() {
        Group board = new Group();
        ImageView[][] tiles = new ImageView[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = getPiece('8', i, j);
                board.getChildren().add(tiles[i][j]);
            }
        }

        String state = chessEngine.getBoardState();

        String[] rows = state.split("/");

        int tilesCol = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                char pieceLabel = rows[i].charAt(j);
                //if it is a number add empty pieces for that number
                if (pieceLabel >= 48 && pieceLabel <= 57) {
                    for (int k = 0; k < Integer.parseInt(String.valueOf(pieceLabel)); k++) {
                        tiles[i][tilesCol] = getPiece(pieceLabel, i, tilesCol);
                        board.getChildren().add(tiles[i][tilesCol]);
                        tilesCol++;
                    }
                } else {
                    tiles[i][tilesCol] = getPiece(pieceLabel, i, tilesCol);
                    board.getChildren().add(tiles[i][tilesCol]);
                    tilesCol++;
                }
            }
            tilesCol = 0;
        }

        return board;
    }

    private ImageView getPiece(char label, int row, int col) {
        ImageView piece;
        char colour = Piece.getColourOfLabel(label, new int[]{row, col});
        switch (String.valueOf(label).toLowerCase()) {
            case "r":
                piece = getImageOfPiece(colour, "Rook");
                break;
            case "n":
                piece = getImageOfPiece(colour, "Knight");
                break;
            case "b":
                piece = getImageOfPiece(colour, "Bishop");
                break;
            case "q":
                piece = getImageOfPiece(colour, "Queen");
                break;
            case "k":
                piece = getImageOfPiece(colour, "King");
                break;
            case "p":
                piece = getImageOfPiece(colour, "Pawn");
                break;
            default:
                piece = getImageOfEmptyPiece(colour);
                break;
        }
        piece.setX(col * 100);
        piece.setY(row * 100);
        piece.setId(String.valueOf(label));

        ImageView finalPiece = piece;
        piece.setOnMouseClicked(event -> runGameLogic(finalPiece));

        return piece;
    }

    private ImageView getImageOfEmptyPiece(char colour) {
        ImageView piece;
        if (colour == 'b') {
            piece = new ImageView(new Image(new File("src/main/resources/Tiles/Black.png").toURI().toString()));
        } else {
            piece = new ImageView(new Image(new File("src/main/resources/Tiles/White.png").toURI().toString()));
        }
        piece.setFitHeight(100);
        piece.setFitWidth(100);
        return piece;
    }

    private void runGameLogic(ImageView piece) {

        char label = piece.getId().charAt(0);
        int row = (int) (piece.getY() / 100);
        int col = (int) (piece.getX() / 100);

        char pieceColour = Piece.getColourOfLabel(label, new int[]{row, col});
        Player currentPlayer = chessEngine.getCurrentPlayer();
        //check if the user has already selected a piece or they are selecting a new piece
        if (currentPlayer.getSelectedPiece() == null || (pieceColour == currentPlayer.getColour() && !Piece.isEmptyPiece(label))) {
            //check if this is a valid piece to select
            if (pieceColour == currentPlayer.getColour() && !Piece.isEmptyPiece(label)) {
                currentPlayer.setSelectedPiece(chessEngine.getPiece(row, col));
            }
        } else {
            //generate valid moves for the piece the player selected
            ArrayList<int[]> moves = currentPlayer.getSelectedPiece().generateMoves(chessEngine.getBoard(), true);
            if (currentPlayer.isInCheck()) {
                if (chessEngine.isCurrentPlayerInCheckMate()) {
                    chessEngine.newGame();
                    updateUI(chessEngine.getCurrentPlayer().toString(), 800, 910);
                }
            }
            for (int[] move : moves) {
                //if the location is in the valid move list then move the piece and end turn
                if (move[0] == row && move[1] == col) {
                    //move piece and end turn
                    chessEngine.movePiece(currentPlayer.getSelectedPiece(), move);
                    updateUI(chessEngine.getCurrentPlayer().toString(), 800, 910);
                    chessEngine.endTurn();
                    updateUI(chessEngine.getCurrentPlayer().toString(), 800, 910);
                    chessEngine.inspectForCheck();
                }
            }
        }
    }

    private ImageView getImageOfPiece(char colour, String imageName) {
        ImageView piece;
        if (colour == 'b') {
            piece = new ImageView(new Image(new File("src/main/resources/Piece Icons/Black/" + imageName + ".png").toURI().toString()));
        } else {
            piece = new ImageView(new Image(new File("src/main/resources/Piece Icons/White/" + imageName + ".png").toURI().toString()));
        }
        piece.setFitHeight(100);
        piece.setFitWidth(100);
        return piece;
    }

    private Line getBorder() {
        Line border = new Line();
        border.setStartX(0);
        border.setStartY(800);
        border.setEndX(800);
        border.setEndY(800);
        border.setStrokeWidth(5);
        return border;
    }

}
