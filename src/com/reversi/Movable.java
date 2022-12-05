package com.reversi;

import java.util.List;
import java.util.Map;

public interface Movable {
    void move(ReversiBoard board, Coordinate coordinate,
              Map<Coordinate, List<Square>> availableMoves);
}
