package com.reversi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game {
    public static final Map<Character, Integer> LETTERS_AS_NUMBERS = Map.ofEntries(
            Map.entry('A', 0),
            Map.entry('B', 1),
            Map.entry('C', 2),
            Map.entry('D', 3),
            Map.entry('E', 4),
            Map.entry('F', 5),
            Map.entry('G', 6),
            Map.entry('H', 7)
    );

    private boolean isGreenTurn = true;

    private final ReversiBoard reversiBoard;

    protected Game() {
        reversiBoard = new ReversiBoard();
    }

    public final boolean isGreenTurn() {
        return isGreenTurn;
    }

    public final ReversiBoard getReversiBoard() {
        return reversiBoard;
    }

    public final void finishMove() {
        this.isGreenTurn = !isGreenTurn;
    }

    public static Map<Coordinate, List<Square>> getAvailableMoves(ReversiBoard board, PieceColor friendlyColor, PieceColor opponentsColor) {
        Map<Coordinate, List<Square>> availableMoves = new HashMap<>();
        for (int i = 0; i < board.getBoard().size(); ++i) {
            List<Square> rank = board.getRank(i);
            for (int j = 0; j < rank.size(); ++j) {
                List<Square> opponentsPieces = new ArrayList<>();
                if (j < 7 && isPieceNull(rank, j) && !isPieceNull(rank, j + 1) &&
                        hasOpponentsColor(rank, j + 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsRightPieces(rank, j + 1, friendlyColor, opponentsColor));
                }
                if (j > 0 && isPieceNull(rank, j) && !isPieceNull(rank, j - 1) &&
                        hasOpponentsColor(rank, j - 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsLeftPieces(rank, j - 1, friendlyColor, opponentsColor));
                }
                if (i < 7 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i + 1), j) &&
                        hasOpponentsColor(board.getRank(i + 1), j, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsDownPieces(board, i + 1, j, friendlyColor, opponentsColor));
                }
                if (i > 0 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i - 1), j) &&
                        hasOpponentsColor(board.getRank(i - 1), j, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsUpPieces(board, i - 1, j, friendlyColor, opponentsColor));
                }
                if (i > 0 && j > 0 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i - 1), j - 1) &&
                        hasOpponentsColor(board.getRank(i - 1), j - 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsLeftUpPieces(board, i - 1, j - 1, friendlyColor, opponentsColor));
                }
                if (i > 0 && j < 7 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i - 1), j + 1) &&
                        hasOpponentsColor(board.getRank(i - 1), j + 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsRightUpPieces(board, i - 1, j + 1, friendlyColor, opponentsColor));
                }
                if (i < 7 && j > 0 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i + 1), j - 1) &&
                        hasOpponentsColor(board.getRank(i + 1), j - 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsLeftDownPieces(board, i + 1, j - 1, friendlyColor, opponentsColor));
                }
                if (i < 7 && j < 7 && isPieceNull(rank, j) && !isPieceNull(board.getRank(i + 1), j + 1) &&
                        hasOpponentsColor(board.getRank(i + 1), j + 1, opponentsColor)) {
                    opponentsPieces.addAll(getOpponentsRightDownPieces(board, i + 1, j + 1, friendlyColor, opponentsColor));
                }
                if (!opponentsPieces.isEmpty()) {
                    availableMoves.put(rank.get(j).getCoordinate(), opponentsPieces);
                }
            }
        }
        return availableMoves;
    }

    public static void visualizeAvailableMoves(ReversiBoard board, Map<Coordinate, List<Square>> availableMoves) {
        for (Coordinate coordinate : availableMoves.keySet()) {
            board.getRank(8 - coordinate.getRankPosition()).
                    get(LETTERS_AS_NUMBERS.get(coordinate.getFilePosition())).
                    setSquareColor(BackgroundColor.RED);
        }
    }

    public static void removeVisualization(ReversiBoard board, Map<Coordinate, List<Square>> availableMoves) {
        for (Coordinate coordinate : availableMoves.keySet()) {
            Square square = board.getRank(8 - coordinate.getRankPosition()).
                    get(LETTERS_AS_NUMBERS.get(coordinate.getFilePosition()));
            if ((LETTERS_AS_NUMBERS.get(square.getCoordinate().getFilePosition())
                    + square.getCoordinate().getRankPosition()) % 2 == 0) {
                square.setSquareColor(BackgroundColor.WHITE);
            } else {
                square.setSquareColor(BackgroundColor.BLACK);
            }
        }
    }

    public static void changeOpponentsPiecesColor(List<Square> opponentsPieces, PieceColor color) {
        for (Square square : opponentsPieces) {
            square.getPiece().setPieceColor(color);
        }
    }

    private static List<Square> getOpponentsRightPieces(List<Square> rank, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = rankIndex; i < rank.size(); ++i) {
            if (hasFriendlyColor(rank, i, friendlyColor)) {
                return opponentsPieces;
            } else if (hasOpponentsColor(rank, i, opponentsColor)) {
                opponentsPieces.add(rank.get(i));
            } else if (isPieceNull(rank, i)) {
                opponentsPieces.clear();
                return opponentsPieces;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsLeftPieces(List<Square> rank, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = rankIndex; i >= 0; --i) {
            if (hasFriendlyColor(rank, i, friendlyColor)) {
                return opponentsPieces;
            } else if (hasOpponentsColor(rank, i, opponentsColor)) {
                opponentsPieces.add(rank.get(i));
            } else if (isPieceNull(rank, i)) {
                opponentsPieces.clear();
                return opponentsPieces;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsDownPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i < board.getBoard().size(); ++i) {
            if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                return opponentsPieces;
            } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                opponentsPieces.add(board.getRank(i).get(rankIndex));
            } else if (isPieceNull(board.getRank(i), rankIndex)) {
                opponentsPieces.clear();
                return opponentsPieces;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsUpPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i >= 0; --i) {
            if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                return opponentsPieces;
            } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                opponentsPieces.add(board.getRank(i).get(rankIndex));
            } else if (isPieceNull(board.getRank(i), rankIndex)) {
                opponentsPieces.clear();
                return opponentsPieces;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsLeftUpPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i >= 0; --i) {
            if (rankIndex >= 0) {
                if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                    return opponentsPieces;
                } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                    opponentsPieces.add(board.getRank(i).get(rankIndex));
                } else if (isPieceNull(board.getRank(i), rankIndex)) {
                    opponentsPieces.clear();
                    return opponentsPieces;
                }
                --rankIndex;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsRightUpPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i >= 0; --i) {
            if (rankIndex <= 7) {
                if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                    return opponentsPieces;
                } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                    opponentsPieces.add(board.getRank(i).get(rankIndex));
                } else if (isPieceNull(board.getRank(i), rankIndex)) {
                    opponentsPieces.clear();
                    return opponentsPieces;
                }
                ++rankIndex;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsLeftDownPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i < board.getBoard().size(); ++i) {
            if (rankIndex >= 0) {
                if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                    return opponentsPieces;
                } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                    opponentsPieces.add(board.getRank(i).get(rankIndex));
                } else if (isPieceNull(board.getRank(i), rankIndex)) {
                    opponentsPieces.clear();
                    return opponentsPieces;
                }
                --rankIndex;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static List<Square> getOpponentsRightDownPieces(ReversiBoard board, int boardIndex, int rankIndex, PieceColor friendlyColor, PieceColor opponentsColor) {
        List<Square> opponentsPieces = new ArrayList<>();
        for (int i = boardIndex; i < board.getBoard().size(); ++i) {
            if (rankIndex <= 7) {
                if (hasFriendlyColor(board.getRank(i), rankIndex, friendlyColor)) {
                    return opponentsPieces;
                } else if (hasOpponentsColor(board.getRank(i), rankIndex, opponentsColor)) {
                    opponentsPieces.add(board.getRank(i).get(rankIndex));
                } else if (isPieceNull(board.getRank(i), rankIndex)) {
                    opponentsPieces.clear();
                    return opponentsPieces;
                }
                ++rankIndex;
            }
        }
        opponentsPieces.clear();
        return opponentsPieces;
    }

    private static boolean isPieceNull(List<Square> rank, int index) {
        return rank.get(index).getPiece() == null;
    }

    private static boolean hasOpponentsColor(List<Square> rank, int index, PieceColor opponentsColor) {
        return rank.get(index).getPiece() != null && rank.get(index).getPiece().getPieceColor() == opponentsColor;
    }

    private static boolean hasFriendlyColor(List<Square> rank, int index, PieceColor friendlyColor) {
        return rank.get(index).getPiece() != null && rank.get(index).getPiece().getPieceColor() == friendlyColor;
    }
}
