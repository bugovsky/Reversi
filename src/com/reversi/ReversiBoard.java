package com.reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ReversiBoard {
    private static final Map<Integer, Character> NUMBERS_AS_LETTERS = Map.ofEntries(
            Map.entry(0, 'A'),
            Map.entry(1, 'B'),
            Map.entry(2, 'C'),
            Map.entry(3, 'D'),
            Map.entry(4, 'E'),
            Map.entry(5, 'F'),
            Map.entry(6, 'G'),
            Map.entry(7, 'H')
    );

    private static final String LETTERS_COORDINATES = "   A  B  C  D  E  F  G  H";
    private final List<List<Square>> board;

    public List<List<Square>> getBoard() {
        return board;
    }

    public List<Square> getRank(int index) {
        return board.get(index);
    }

    public ReversiBoard() {
        board = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 8; ++j) {
                BackgroundColor color = (i + j) % 2 == 1 ? BackgroundColor.BLACK : BackgroundColor.WHITE;
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    board.get(i).add(new Square(color, PieceColor.WHITE, NUMBERS_AS_LETTERS.get(j), 8 - i));
                } else if (i == 3 && j == 4 || i == 4 && j == 3) {
                    board.get(i).add(new Square(color, PieceColor.GREEN, NUMBERS_AS_LETTERS.get(j), 8 - i));
                } else {
                    board.get(i).add(new Square(color, NUMBERS_AS_LETTERS.get(j), 8 - i));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder boardAsString = new StringBuilder();
        for (int i = 0; i < board.size(); ++i) {
            List<Square> rank = board.get(i);
            boardAsString.append(8 - i).append(" ");
            for (Square square : rank) {
                boardAsString.append(square);
            }
            boardAsString.append("\n");
        }
        boardAsString.append(LETTERS_COORDINATES);
        return boardAsString.toString();
    }
}
