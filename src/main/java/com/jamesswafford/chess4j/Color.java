package com.jamesswafford.chess4j;


public enum Color {
    BLACK(0), WHITE(1);

    private int color;

    Color(int color) {
        this.color = color;
    }

    public static Color swap(Color c) {
        return c.getColor() == 0 ? WHITE : BLACK;
    }

    public int getColor() {
        return color;
    }

    public boolean isBlack() {
        return color == 0;
    }

    public boolean isWhite() {
        return color == 1;
    }

    public String toString() {
        if (color == BLACK.getColor()) {
            return "black";
        } else {
            return "white";
        }
    }
}
