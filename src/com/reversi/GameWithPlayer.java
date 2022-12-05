package com.reversi;

public final class GameWithPlayer extends Game {
    private static int bestGreenScore = 0;
    private static int bestWhiteScore = 0;

    public GameWithPlayer() {
        super();
    }

    public static void setBestGreenScore(int score) {
        if (score > bestGreenScore) {
            GameWithPlayer.bestGreenScore = score;
        }
    }

    public static void setBestWhiteScore(int score) {
        if (score > bestGreenScore) {
            GameWithPlayer.bestWhiteScore = score;
        }
    }

    public static int getBestGreenScore() {
        return bestGreenScore;
    }

    public static int getBestWhiteScore() {
        return bestWhiteScore;
    }

}
