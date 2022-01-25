
package cz.cvut.fel.pjv.model;

import java.util.List;

public abstract class Pieces {
    private int row;
    private int column;
    private boolean hasMoved;

    /**
     *
     * method especially for king and rook
     * tells if the king or rook has moved since the start of the game
     * @return true if the king or rook has moved since the start of the game
     */
    public abstract boolean hasAlreadyMoved();
    
    /**
     *this method returns current column of this piece
     * @return row of this piece
     */
    public abstract int getRow();
    
    /**
     *this method returns current column of this piece
     * @return column of this piece
     */
    public abstract int getColumn();
    
    /**
     *this method tells if this piece is a piece of black player
     * @return true if this piece is piece of black player else return false
     */
    public abstract boolean isPieceBlack();
    
    /**
     *this method calculates all possible moves for current type of piece
     * @param boardField - board field of chess board
     * @param enemyMoves - moves of enemy player
     * @return returns all moves of current type of piece
     */
    public abstract List<Move> getPossibleMoves(Tile[][] boardField, List<Move> enemyMoves);
    
    /**
     *this method tells if the tile with row and column is out of board
     * @param row - row of target tile
     * @param column - column of target tile
     * @return returns true if the tile is out of board, else returns false
     */
    public abstract boolean isOutOfBoard(int row, int column);
    
    /**
     *this method changes position of this piece
     * @param newRow new row of piece
     * @param newColumn new column of piece
     */
    public abstract void changePosition(int newRow, int newColumn);
    
    /**
     *this method tells which piece is this piece
     * @return type of piece
     */
    public abstract String getPieceType();
    
    /**
     *changes the status of king or rook for future knowing whether the king or rook is 
     * possible to make castle
     */
    public abstract void setStatusToHasMoved();
}
