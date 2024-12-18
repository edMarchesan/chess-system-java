package chess;

import chesspieces.King;
import chesspieces.Rook;
import entities.Board;
import entities.Piece;
import entities.Position;

public class ChessMatch {

    private Color currentPlayer;
    private int turn;
    private Board board;

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

        private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
        }
        private void initialSetup(){
         placeNewPiece('a',8, new Rook(board, Color.WHITE));
         placeNewPiece('a',1, new Rook(board, Color.BLACK));
        }

        public boolean[][] possibleMoves(ChessPosition sourcePosition) {
            Position position = sourcePosition.toPosition();
            validateSourcePosition(position);
            return board.piece(position).possibleMoves();
        }
        public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
            Position source = sourcePosition.toPosition();
            Position target = targetPosition.toPosition();
            validateSourcePosition(source);
            validateTargetPosition(source, target);
            Piece capturedPiece = makeMove(source, target);
            nextTurn();
            return (ChessPiece) capturedPiece;
        }

        private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There's no piece in source position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor())
        {
            throw new ChessException("Trying to move opponent piece");
        }
        if (board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no move for the chosen piece");
        }
        }

        private void validateTargetPosition(Position source, Position target) {
            if (board.piece(source).possibleMove(target)) {
                throw new ChessException("The chosen piece cannot move to the target location");
            }
        }

        private Piece makeMove(Position source, Position target) {
            Piece p = board.removePiece(source);
            Piece capturedPiece = board.removePiece(target);
            board.placePiece(p, target);
            return capturedPiece;
        }

        private void nextTurn () {
            turn ++;
            if (currentPlayer == Color.BLACK){
                currentPlayer = Color.WHITE;
            } else if (currentPlayer == Color.WHITE) {
                currentPlayer = Color.BLACK;
            }
        }
    }


