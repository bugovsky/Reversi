package com.reversi;

import java.util.List;
import java.util.Map;

public final class Bot extends Player implements Movable {

    public Bot(PieceColor friendlyColor) {
        super(friendlyColor);
    }

    @Override
    public void move(ReversiBoard board, Coordinate coordinate,
                     Map<Coordinate, List<Square>> availableMoves) {
        super.move(board, coordinate, availableMoves);

    }

    public Coordinate findBestMove(Map<Coordinate, List<Square>> availableMoves) {
        double maxPoints = 0.0;
        Coordinate bestCoordinate = availableMoves.keySet().iterator().next();
        for (Coordinate coordinate : availableMoves.keySet()) {
            double points = 0.0;
            for (Square square : availableMoves.get(coordinate)) {
                points += getValueOfLockedSquare(square);
            }
            points += getValueOfMove(coordinate);
            if (points > maxPoints) {
                maxPoints = points;
                bestCoordinate = coordinate;
            }
        }
        return bestCoordinate;
    }

    private double getValueOfLockedSquare(Square lockedSquare) {
        double points;
        if (lockedSquare.getCoordinate().getFilePosition() == 'A' ||
                lockedSquare.getCoordinate().getFilePosition() == 'H' ||
                lockedSquare.getCoordinate().getRankPosition() == '0' ||
                lockedSquare.getCoordinate().getRankPosition() == '8') {
            points = 2.0;
        } else {
            points = 1.0;
        }
        return points;
    }

    private double getValueOfMove(Coordinate moveCoordinate) {
        double points;
        if ((moveCoordinate.getFilePosition() == 'A' && moveCoordinate.getRankPosition() == 0) ||
                (moveCoordinate.getFilePosition() == 'A' && moveCoordinate.getRankPosition() == 8) ||
                (moveCoordinate.getFilePosition() == 'H' && moveCoordinate.getRankPosition() == 0) ||
                (moveCoordinate.getFilePosition() == 'H' && moveCoordinate.getRankPosition() == 8)) {
            points = 0.8;
        } else if (moveCoordinate.getFilePosition() == 'A' ||
                moveCoordinate.getFilePosition() == 'H' ||
                moveCoordinate.getRankPosition() == '0' ||
                moveCoordinate.getRankPosition() == '8') {
            points = 0.4;
        } else {
            points = 0;
        }
        return points;
    }
}
