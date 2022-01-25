
package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Bishop extends Pieces  {
    private List<Move> possibleMoves; 
    private int column;
    private int row;
    private final boolean isBlack;
    private final String pieceType = "b";
    private boolean hasMoved;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public Bishop (boolean isBlack, int row, int column) {
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
        this.possibleMoves = new ArrayList<>();
        diagonalLeftLowerTilePossibleMoves(boardField, this.possibleMoves);
        diagonalLeftUpperTilePossibleMoves(boardField, this.possibleMoves);
        diagonalRightUpperTilePossibleMoves(boardField, this.possibleMoves);
        diagonalRightLowerTilePossibleMoves(boardField, this.possibleMoves);
        return this.possibleMoves;
    }
    
    private void diagonalRightUpperTilePossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int diagonalRightUpperTile = 1; diagonalRightUpperTile < 8; diagonalRightUpperTile++) {
            int newPossibleRow = row + diagonalRightUpperTile;
            int newPossibleColumn = column + diagonalRightUpperTile;
            if (!isOutOfBoard(newPossibleRow, newPossibleColumn)) {
                if(boardField[newPossibleRow][newPossibleColumn].isTileEmpty()) {
                    possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
                } else {
                    Pieces pieceOnTile = boardField[newPossibleRow][newPossibleColumn].getPiece();
                    boolean isPieceOnTileBlack = pieceOnTile.isPieceBlack();
                    if (isPieceOnTileBlack != this.isBlack) {
                        possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, true, this, pieceOnTile));
                    }
                    break;
                }
            } else {
                break;
            }    
        }
    }
    
    private void diagonalLeftUpperTilePossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int diagonalLeftUpperTile = 1; diagonalLeftUpperTile < 8; diagonalLeftUpperTile++) {
            int newPossibleRow = row + diagonalLeftUpperTile;
            int newPossibleColumn = column - diagonalLeftUpperTile;
            if (!isOutOfBoard(newPossibleRow, newPossibleColumn)) {
                if(boardField[newPossibleRow][newPossibleColumn].isTileEmpty()) {
                    possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
                } else {
                    Pieces pieceOnTile = boardField[newPossibleRow][newPossibleColumn].getPiece();
                    boolean isPieceOnTileBlack = pieceOnTile.isPieceBlack();
                    if (isPieceOnTileBlack != this.isBlack) {
                        possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, true, this, pieceOnTile));
                    }
                    break;
                }
            } else {
                break;
            }    
        }
    }
    
    private void diagonalLeftLowerTilePossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int diagonalLeftLowerTile = 1; diagonalLeftLowerTile < 8; diagonalLeftLowerTile++) {
            int newPossibleRow = row - diagonalLeftLowerTile;
            int newPossibleColumn = column - diagonalLeftLowerTile;
            if (!isOutOfBoard(newPossibleRow, newPossibleColumn)) {
                if(boardField[newPossibleRow][newPossibleColumn].isTileEmpty()) {
                    possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
                } else {
                    Pieces pieceOnTile = boardField[newPossibleRow][newPossibleColumn].getPiece();
                    boolean isPieceOnTileBlack = pieceOnTile.isPieceBlack();
                    if (isPieceOnTileBlack != this.isBlack) {
                        possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, true, this, pieceOnTile));
                    }
                    break;
                }
            } else {
                break;
            }    
        }
    }
    
    private void diagonalRightLowerTilePossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int diagonalRightLowerTile = 1; diagonalRightLowerTile < 8; diagonalRightLowerTile++) {
            int newPossibleRow = row - diagonalRightLowerTile;
            int newPossibleColumn = column + diagonalRightLowerTile;
            if (!isOutOfBoard(newPossibleRow, newPossibleColumn)) {
                if(boardField[newPossibleRow][newPossibleColumn].isTileEmpty()) {
                    possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
                } else {
                    Pieces pieceOnTile = boardField[newPossibleRow][newPossibleColumn].getPiece();
                    boolean isPieceOnTileBlack = pieceOnTile.isPieceBlack();
                    if (isPieceOnTileBlack != this.isBlack) {
                        possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, true, this, pieceOnTile));
                    }
                    break;
                }
            } else {
                break;
            }
        }
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
        LOGGER.warning("The method hasAlreadyMoved called for Bishop");
        return true;
    }

    /**
     *method for rook and king especially
     */
    @Override
    public void setStatusToHasMoved() {
        LOGGER.warning("The method setStatusToHasMoves called for Bishop");
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
