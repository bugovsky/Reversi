package com.reversi;

public final class GameWithBot extends Game {
    private static int bestScore = 0;

    public GameWithBot() {
        super();
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void setBestScore(int score) {
        if (score > bestScore) {
            GameWithBot.bestScore = score;
        }
    }
}
