package com.reversi;

import java.util.List;
import java.util.Map;

public class Player implements Movable {
    private final PieceColor friendlyColor;
    protected int currentScore = 0;

    protected boolean canMove = true;

    public Player(PieceColor friendlyColor) {
        this.friendlyColor = friendlyColor;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public PieceColor getFriendlyColor() {
        return friendlyColor;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public void move(ReversiBoard board, Coordinate coordinate,
                     Map<Coordinate, List<Square>> availableMoves) {
        board.getRank(8 - coordinate.getRankPosition()).
                get(Game.LETTERS_AS_NUMBERS.get(coordinate.getFilePosition())).
                setPiece(new Piece(this.friendlyColor));
        Game.removeVisualization(board, availableMoves);
        Game.changeOpponentsPiecesColor(availableMoves.get(coordinate), this.friendlyColor);
    }

    public void setCurrentScore(ReversiBoard board, PieceColor color) {
        currentScore = 0;
        for (int i = 0; i < board.getBoard().size(); ++i) {
            List<Square> rank = board.getRank(i);
            for (Square square : rank) {
                if (square.getPiece() != null && square.getPiece().getPieceColor() == color) {
                    ++currentScore;
                }
            }
        }
    }

    @Override
    public String toString() {
        if (friendlyColor == PieceColor.GREEN) {
            return "Green";
        }
        return "White";
    }
}
