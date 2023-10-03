package chess;

public class ChessPositionImpl implements ChessPosition{

    private int row;
    private int col;

    public ChessPositionImpl(int rowValue, int colValue) {
        row = rowValue;
        col = colValue;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return col;
    }
}
