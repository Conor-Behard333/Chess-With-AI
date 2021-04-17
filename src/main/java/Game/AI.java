package Game;

import java.util.Arrays;

public class AI extends Player {

    AI(char colour) {
        super(colour);
    }

    public Board playTurn(Board currentBoard) {
        int depth = 3;
        Node root = new Node(currentBoard, true, depth, Piece.reverseColour(getColour()), null);

        Node response = minimax(root, depth, false);
        System.out.println(response);
        for (Piece[] x : response.getBoard()) {
            System.out.println(Arrays.toString(x));
        }
        System.out.println();

        return new Board(encodeBoard(response.getBoard()));
    }

    public Node minimax(Node node, int depth, boolean maximizingPlayer) {
        //STOP IF IN CHECK
        if (depth == 0 || Board.isInCheck(Piece.reverseColour(node.getColour()), node.getBoard())) {
            node.setEvaluation(evaluateBoard(node.getBoard(), Piece.reverseColour(node.getColour())));
            return node;
        }
        if (maximizingPlayer) {
            int maxEval = -90000000;
            Node bestChild = null;
            for (Node child : node.getChildren()) {
                Node evaluatedNode = minimax(child, depth - 1, false);
                if (evaluatedNode.getEvaluation() > maxEval) {
                    maxEval = evaluatedNode.getEvaluation();
                    child.setEvaluation(evaluatedNode.getEvaluation());
                    bestChild = child;
                }
            }
            return bestChild;
        } else {
            int minEval = 90000000;
            Node bestChild = null;
            for (Node child : node.getChildren()) {
                Node evaluatedNode = minimax(child, depth - 1, true);
                if (evaluatedNode.getEvaluation() < minEval) {
                    minEval = evaluatedNode.getEvaluation();
                    child.setEvaluation(evaluatedNode.getEvaluation());
                    bestChild = child;
                }
            }
            return bestChild;
        }
    }

    public int evaluateBoard(Piece[][] board, char colour) {
        int eval = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //get all the possible moves of the opponent
                if (board[i][j].getColour() == 'w' && !Piece.isEmptyPiece(board[i][j].getLabel())) {
                    if (colour == 'w') {
                        eval += board[i][j].VALUE;
                    } else {
                        eval -= board[i][j].VALUE;
                    }
                }
                if (board[i][j].getColour() == 'b' && !Piece.isEmptyPiece(board[i][j].getLabel())) {
                    if (colour == 'w') {
                        eval -= board[i][j].VALUE;
                    } else {
                        eval += board[i][j].VALUE;
                    }
                }
            }
        }
        return eval;
    }

    public String encodeBoard(Piece[][] board) {
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
        return newState.toString();
    }
}
