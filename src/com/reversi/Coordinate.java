package com.reversi;

import java.util.Objects;

public final class Coordinate {
    private final char filePosition;
    private final int rankPosition;

    public Coordinate(char filePosition, int rankPosition) {
        this.filePosition = filePosition;
        this.rankPosition = rankPosition;
    }

    public Coordinate(String stringCoordinate) {
        this.filePosition = stringCoordinate.charAt(0);
        this.rankPosition = stringCoordinate.charAt(1) - '0';
    }

    public char getFilePosition() {
        return filePosition;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return filePosition == that.filePosition && rankPosition == that.rankPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePosition, rankPosition);
    }

    @Override
    public String toString() {
        return filePosition + "" + rankPosition;
    }
}
