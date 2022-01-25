
package cz.cvut.fel.pjv.model;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Knight extends Pieces{
    private final int[] specialNumbersForMovesInRows = {1, -1, 2, -2};
    private final int[] specialNumbersForMovesInColumns = {1, -1};
    private int column;
    private int row;
    private final boolean isBlack;
    private String pieceType = "n";
    private boolean hasMoved;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private List<Move> possibleMoves;
    
    public Knight (boolean isBlack, int row, int column) {
        this.isBlack = isBlack;
        this.row = row;
        this.column = column;
    }
    
    /**
     *this method calculates all possible moves for current type of piece
     * @param boardField - board field of chess board
     * @param enemyMoves - moves of enemy player
     * @return returns all moves of current type of piece
     */
    @Override
    public List<Move> getPossibleMoves(Tile[][] boardField, List<Move> enemyMoves) {
        possibleMoves = new ArrayList<>(); 
        for (int rowIterator : specialNumbersForMovesInRows) {
            int newPossibleRow = row + rowIterator;
            int newPossibleColumn;
            for (int columnIterator : specialNumbersForMovesInColumns) {
                if (abs(rowIterator) == 1) {
                    newPossibleColumn = column + 2*columnIterator;
                } else {
                    newPossibleColumn = column + columnIterator;
                }
                if (!isOutOfBoard(newPossibleRow, newPossibleColumn)) {
                    if(boardField[newPossibleRow][newPossibleColumn].isTileEmpty()) {
                        possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
                    } else {
                        Pieces pieceOnTile = boardField[newPossibleRow][newPossibleColumn].getPiece();
                        boolean isPieceOnTileBlack = pieceOnTile.isPieceBlack();
                        if (isPieceOnTileBlack != this.isBlack) {
                            possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, true, this, pieceOnTile));
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }
    
    
    /**
     *this method tells if the tile with row and column is out of board
     * @param row - row of target tile
     * @param column - column of target tile
     * @return returns true if the tile is out of board, else returns false
     */
    @Override
    public boolean isOutOfBoard(int row, int column) {
        return !(row <= 7 && row >= 0 && column <= 7 && column >= 0);
    }
    
    /**
     *this method tells if this piece is a piece of black player
     * @return true if this piece is piece of black player else return false
     */
    @Override
    public boolean isPieceBlack() {
        return this.isBlack;
    }
    
    /**
     *this method tells which piece is this piece
     * @return type of piece
     */
    @Override
    public String getPieceType() {
        return pieceType;
    }
    
    /**
     *this method returns current column of this piece
     * @return column of this piece
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     *this method returns current column of this piece
     * @return row of this piece
     */
    @Override
    public int getRow() {
        return row;
    }
    
    /**
     *
     * method for rook and king especially
     * @return true
     */
    @Override
    public boolean hasAlreadyMoved() {
        LOGGER.warning("The method hasAlreadyMoved called for Knight");
        return true;
    }

    /**
     *method for rook and king especially
     */
    @Override
    public void setStatusToHasMoved() {
        LOGGER.warning("The method setStatusToHasMoves called for Knight");
    }
    
    /**
     *this method changes position of this piece
     * @param newRow new row of piece
     * @param newColumn new column of piece
     */
    @Override
    public void changePosition(int newRow, int newColumn) {
        this.row = newRow;
        this.column = newColumn;
    }
    
}
