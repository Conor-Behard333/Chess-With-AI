package Game;

public class Player {
    protected char colour;
    private Piece selectedPiece = null;
    private Piece selectedLocation;
    private boolean isInCheck = false;

    Player(char colour) {
        this.colour = colour;
    }

    public char getColour() {
        return colour;
    }

    public void setColour(char colour) {
        this.colour = colour;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Piece getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Piece selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    @Override
    public String toString() {
        if (colour == 'b') {
            return "Black Players turn";
        } else {
            return "White Players turn";
        }
    }

    public void check() {
        isInCheck = true;
    }
}
