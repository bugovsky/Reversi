package com.reversi;

public enum BackgroundColor {
    BLACK("\033[0;100m"),
    WHITE("\033[47m"),
    RED("\033[0;101m"),
    RESET("\033[0m");
    private final String colorCode;

    BackgroundColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

}
