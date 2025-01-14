package com.jamesswafford.chess4j.hash;


import com.jamesswafford.chess4j.Color;
import com.jamesswafford.chess4j.board.Board;
import com.jamesswafford.chess4j.board.CastlingRights;
import com.jamesswafford.chess4j.board.squares.Square;
import com.jamesswafford.chess4j.pieces.*;
import com.jamesswafford.chess4j.utils.PieceFactory;

import java.util.*;


public final class Zobrist {

    private static Map<Piece, Long[]> pieceMap = new HashMap<Piece, Long[]>();
    private static Map<Color, Long> playerMap = new HashMap<Color, Long>();
    private static Map<CastlingRights, Long> castlingMap = new HashMap<CastlingRights, Long>();
    private static Map<Square, Long> epMap = new HashMap<Square, Long>();

    static {
        Random r = new Random();
        createZobristKeys(Pawn.BLACK_PAWN);
        createZobristKeys(Pawn.WHITE_PAWN);
        createZobristKeys(Rook.BLACK_ROOK);
        createZobristKeys(Rook.WHITE_ROOK);
        createZobristKeys(Knight.BLACK_KNIGHT);
        createZobristKeys(Knight.WHITE_KNIGHT);
        createZobristKeys(Bishop.BLACK_BISHOP);
        createZobristKeys(Bishop.WHITE_BISHOP);
        createZobristKeys(Queen.BLACK_QUEEN);
        createZobristKeys(Queen.WHITE_QUEEN);
        createZobristKeys(King.BLACK_KING);
        createZobristKeys(King.WHITE_KING);

        playerMap.put(Color.BLACK, r.nextLong());
        playerMap.put(Color.WHITE, r.nextLong());

        Set<CastlingRights> crs = EnumSet.allOf(CastlingRights.class);
        for (CastlingRights cr : crs) {
            castlingMap.put(cr, r.nextLong());
        }

        List<Square> sqs = Square.allSquares();
        for (Square sq : sqs) {
            epMap.put(sq, r.nextLong());
        }
    }

    private Zobrist() {
    }

    private static void createZobristKeys(Piece p) {
        Random r = new Random();
        Long[] keys = new Long[Square.NUM_SQUARES];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = r.nextLong();
        }
        pieceMap.put(p, keys);
    }

    public static long getPieceKey(Square sq, Piece p) {
        return pieceMap.get(p)[sq.value()];
    }

    public static long getPlayerKey(Color c) {
        return playerMap.get(c);
    }

    public static long getCastlingKey(CastlingRights cr) {
        return castlingMap.get(cr);
    }

    public static long getEnPassantKey(Square sq) {
        return epMap.get(sq);
    }

    public static long getPawnKey(Board b) {
        long key = 0;

        List<Square> sqs = Square.allSquares();
        for (Square sq : sqs) {
            Piece p = b.getPiece(sq);
            if (p instanceof Pawn) {
                key ^= getPieceKey(sq, p);
            }
        }

        return key;
    }

    public static long getBoardKey(Board b) {
        long key = 0;

        List<Square> sqs = Square.allSquares();
        for (Square sq : sqs) {
            Piece p = b.getPiece(sq);
            if (p != null) {
                key ^= getPieceKey(sq, p);
            }
        }

        Set<CastlingRights> crs = EnumSet.allOf(CastlingRights.class);
        for (CastlingRights cr : crs) {
            if (b.hasCastlingRight(cr)) {
                key ^= castlingMap.get(cr);
            }
        }

        Square epSquare = b.getEPSquare();
        if (epSquare != null) {
            key ^= epMap.get(epSquare);
        }

        key ^= playerMap.get(b.getPlayerToMove());

        return key;
    }

    public static List<Long> getAllKeys() {
        List<Long> keys = new ArrayList<Long>();

        String[] strPieces = {"P", "R", "N", "B", "Q", "K", "p", "r", "n", "b", "q", "k"};

        for (String strPiece : strPieces) {
            Piece piece = PieceFactory.getPiece(strPiece);
            Long[] pieceKeys = pieceMap.get(piece);
            for (int i = 0; i < Square.NUM_SQUARES; i++) {
                keys.add(pieceKeys[i]);
            }
        }

        keys.add(playerMap.get(Color.WHITE));
        keys.add(playerMap.get(Color.BLACK));

        keys.add(castlingMap.get(CastlingRights.BLACK_QUEENSIDE));
        keys.add(castlingMap.get(CastlingRights.BLACK_KINGSIDE));
        keys.add(castlingMap.get(CastlingRights.WHITE_QUEENSIDE));
        keys.add(castlingMap.get(CastlingRights.WHITE_KINGSIDE));

        for (int i = 0; i < Square.NUM_SQUARES; i++) {
            keys.add(epMap.get(Square.valueOf(i)));
        }

        return keys;
    }

    public static void setKeys(List<Long> keys) {
        String[] strPieces = {"P", "R", "N", "B", "Q", "K", "p", "r", "n", "b", "q", "k"};

        int ind = 0;

        for (String strPiece : strPieces) {
            Piece piece = PieceFactory.getPiece(strPiece);
            Long[] pieceKeys = new Long[Square.NUM_SQUARES];
            for (int i = 0; i < Square.NUM_SQUARES; i++) {
                pieceKeys[i] = keys.get(ind++);
            }
            pieceMap.put(piece, pieceKeys);
        }

        playerMap.put(Color.WHITE, keys.get(ind++));
        playerMap.put(Color.BLACK, keys.get(ind++));

        castlingMap.put(CastlingRights.BLACK_QUEENSIDE, keys.get(ind++));
        castlingMap.put(CastlingRights.BLACK_KINGSIDE, keys.get(ind++));
        castlingMap.put(CastlingRights.WHITE_QUEENSIDE, keys.get(ind++));
        castlingMap.put(CastlingRights.WHITE_KINGSIDE, keys.get(ind++));

        for (int i = 0; i < Square.NUM_SQUARES; i++) {
            epMap.put(Square.valueOf(i), keys.get(ind++));
        }
    }
}
