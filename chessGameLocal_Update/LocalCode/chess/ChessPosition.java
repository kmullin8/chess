package chess;

public class ChessPositionImpl implements ChessPosition {
    private final int row;
    private final int column;

    public ChessPositionImpl(int row, int column) {
        if (row < 1 || row > 8 || column < 1 || column > 8) {
            throw new IllegalArgumentException("Invalid chess position");
        }
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChessPositionImpl that = (ChessPositionImpl) obj;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return 31 * row + column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
