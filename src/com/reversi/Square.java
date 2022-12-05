package com.reversi;

public final class Square {

    private Piece piece;
    private final Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setSquareColor(BackgroundColor squareColor) {
        this.squareColor = squareColor;
    }

    private BackgroundColor squareColor;

    public Square(BackgroundColor squareColor, char filePosition, int rankPosition) {
        this.squareColor = squareColor;
        piece = null;
        coordinate = new Coordinate(filePosition, rankPosition);
    }

    public Square(BackgroundColor squareColor, PieceColor pieceColor, char filePosition, int rankPosition) {
        this.squareColor = squareColor;
        this.piece = new Piece(pieceColor);
        coordinate = new Coordinate(filePosition, rankPosition);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (this.piece == null) {
            this.piece = piece;
        }
    }

    @Override
    public String toString() {
        if (piece != null) {
            return squareColor.getColorCode() + piece.getPieceColor().getColorCode() +
                    " " + piece + " " + BackgroundColor.RESET.getColorCode();
        } else {
            return squareColor.getColorCode() + "   " + BackgroundColor.RESET.getColorCode();
        }

    }
}
