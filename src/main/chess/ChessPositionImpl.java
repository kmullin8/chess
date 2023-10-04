package chess;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition{

    private int row;
    private int col;

    public ChessPositionImpl(int rowValue, int colValue) {
        this.row = rowValue;
        this.col = colValue;
    }
    @Override
    public int hashCode() { //not sure of purpose
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object obj) { //not sure of purpose
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChessPositionImpl otherPosition = (ChessPositionImpl) obj;
        return row == otherPosition.row && col == otherPosition.col;
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
