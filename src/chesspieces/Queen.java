package chesspieces;

import chess.ChessPiece;
import chess.Color;
import entities.Board;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }
}
