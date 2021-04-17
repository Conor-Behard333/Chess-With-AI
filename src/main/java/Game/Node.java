package Game;

import java.util.ArrayList;

public class Node {
    private int evaluation;
    private Piece[][] board;
    private Node parent;
    private ArrayList<Node> children = new ArrayList<>();
    private boolean isRoot;
    private int depth;
    private char colour;

    public Node(Board board, boolean root, int depth, char colour, Node parent) {
        this.board = board.getBoard();
        this.depth = depth;
        this.colour = colour;
        isRoot = root;
        if (!root) {
            this.parent = parent;
        }
        if (depth > 0) {
            ArrayList<Piece[][]> boards = board.generateAllBoardsForTurn(Piece.reverseColour(colour));
            addAllChildren(boards);
        }
    }

    public void addAllChildren(ArrayList<Piece[][]> boards) {
        for (Piece[][] board : boards) {
            children.add(new Node(new Board(encodeBoard(board)), false, depth - 1, Piece.reverseColour(colour), this));
        }
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

    public boolean isRoot() {
        return isRoot;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "evaluation=" + evaluation +
                ", children=" + children.size() +
                ", isRoot=" + isRoot +
                ", depth=" + depth +
                ", colour=" + colour +
                '}';
    }

    public char getColour() {
        return colour;
    }
}
