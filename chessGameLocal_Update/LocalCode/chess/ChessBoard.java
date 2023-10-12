package chess;

public class ChessBoardImpl implements ChessBoard {

    private final int BOARD_SIZE = 8;
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position != null && position.isValid()) {
            board[position.getRow()][position.getColumn()] = piece;
        } else {
            throw new IllegalArgumentException("Invalid chess position");
        }
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        if (position != null && position.isValid()) {
            return board[position.getRow()][position.getColumn()];
        } else {
            throw new IllegalArgumentException("Invalid chess position");
        }
    }

    @Override
    public void resetBoard() {
 
        for (int i = 0; i < BOARD_SIZE; i++) {
            addPiece(new ChessPosition(1, i), new ChessPiece(ChessPieceType.PAWN, ChessColor.WHITE));
            addPiece(new ChessPosition(6, i), new ChessPiece(ChessPieceType.PAWN, ChessColor.BLACK));
        }

        addPiece(new ChessPosition(0, 0), new ChessPiece(ChessPieceType.ROOK, ChessColor.WHITE));
        addPiece(new ChessPosition(0, 7), new ChessPiece(ChessPieceType.ROOK, ChessColor.WHITE));
        addPiece(new ChessPosition(7, 0), new ChessPiece(ChessPieceType.ROOK, ChessColor.BLACK));
        addPiece(new ChessPosition(7, 7), new ChessPiece(ChessPieceType.ROOK, ChessColor.BLACK));

        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }
    }
}
