
package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Pieces { 
    private int column;
    private int row;
    private final boolean isBlack;
    private String pieceType = "r";
    private boolean hasMoved = false;
    private List<Move> possibleMoves;
    
    public Rook (boolean isBlack, int row, int column) {
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
        leftPossibleMoves(boardField, possibleMoves);
        upperPossibleMoves(boardField, possibleMoves);
        rightPossibleMoves(boardField, possibleMoves);
        lowerPossibleMoves(boardField, possibleMoves);
        return possibleMoves;
    }
    
    private void leftPossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int columnIterator = 1; columnIterator < 8; columnIterator++) {
            int newPossibleRow = row;
            int newPossibleColumn = column - columnIterator;
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
    
    private void upperPossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int rowIterator = 1; rowIterator < 8; rowIterator++) {
            int newPossibleRow = row + rowIterator;
            int newPossibleColumn = column;
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
    
    private void rightPossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int columnIterator = 1; columnIterator < 8; columnIterator++) {
            int newPossibleRow = row;
            int newPossibleColumn = column + columnIterator;
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
    
    private void lowerPossibleMoves(Tile[][] boardField, List<Move> possibleMoves) {
        for (int rowIterator = 1; rowIterator < 8; rowIterator++) {
            int newPossibleRow = row - rowIterator;
            int newPossibleColumn = column;
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
     * method tells if the rook has moved since the start of the game
     * @return true if the rook has moved since the start of the game
     */
    @Override
    public boolean hasAlreadyMoved() {
        return hasMoved;
    }

    /**
     *changes the status of rook for future knowing whether the rook is 
     * possible to make castle
     */
    @Override
    public void setStatusToHasMoved() {
        hasMoved = true;
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
        this.hasMoved = true;
    }
}
