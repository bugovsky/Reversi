package com.reversi;

public enum PieceColor {
    WHITE("\033[1;97m"),
    GREEN("\033[1;92m");
    private final String colorCode;

    public String getColorCode() {
        return colorCode;
    }

    PieceColor(String colorCode) {
        this.colorCode = colorCode;
    }
}
