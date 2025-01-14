
package com.jamesswafford.chess4j.board.squares;


public final class North extends Direction {

    private static final North INSTANCE = new North();

    private North() {
    }

    public static North getInstance() {
        return INSTANCE;
    }

    @Override
    public Square next(Square sq) {
        return Square.valueOf(sq.file(), sq.rank().north());
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public int value() {
        return 0;
    }
}
