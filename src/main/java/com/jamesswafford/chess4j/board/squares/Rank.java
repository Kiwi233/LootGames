
package com.jamesswafford.chess4j.board.squares;


public enum Rank {
    RANK_8(0, "8"), RANK_7(1, "7"), RANK_6(2, "6"), RANK_5(3, "5"), RANK_4(4, "4"), RANK_3(5, "3"), RANK_2(6, "2"), RANK_1(7, "1");

    private static Rank[] ranks_arr = new Rank[8];

    static {
        for (int i = 0; i < 8; i++) {
            for (Rank r : Rank.values()) {
                if (r.getValue() == i) {
                    ranks_arr[i] = r;
                }
            }
        }
    }

    private int value;
    private String label;

    Rank(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Rank rank(int value) {
        assert (value >= 0 && value <= 7);

        return ranks_arr[value];
    }

    public static Rank rank(String value) {
        for (Rank r : Rank.values()) {
            if (r.getLabel().equalsIgnoreCase(value)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Rank value not found: " + value);
    }

    public int distance(Rank r) {
        return Math.abs(r.getValue() - getValue());
    }

    public Rank flip() {
        return Rank.rank(7 - value);
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public boolean isPromotionRank() {
        return value == RANK_1.getValue() || value == RANK_8.getValue();
    }

    public Rank north() {
        if (value == 0) {
            return null;
        }
        return rank(value - 1);
    }

    public boolean northOf(Rank r) {
        return this.getValue() < r.getValue();
    }

    public Rank south() {
        if (value == 7) {
            return null;
        }
        return rank(value + 1);
    }

    public boolean southOf(Rank r) {
        return this.getValue() > r.getValue();
    }

}
