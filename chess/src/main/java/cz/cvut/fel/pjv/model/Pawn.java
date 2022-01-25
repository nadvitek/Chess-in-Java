
package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Pawn extends Pieces{
    private List<Move> possibleMoves; 
    private int column;
    private int row;
    private final boolean isBlack;
    private final String pieceType = "p";
    private boolean hasMoved;
    private boolean isEnPassantePossible = false;
    private int EnPassanteColumnIterator  = 0;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public Pawn (boolean isBlack, int row, int column) {
        this.isBlack = isBlack;
        this.row = row;
        this.column = column;
    }
    
    @Override
    public List<Move> getPossibleMoves(Tile[][] boardField, List<Move> enemyMoves) {
        this.possibleMoves = new ArrayList<>();
        int rowIterator = isBlack ? -1 : 1;
        twoTilesMove(boardField, rowIterator, possibleMoves);
        oneTileMove(boardField, rowIterator, possibleMoves);
        attackingMove(boardField, rowIterator, possibleMoves);
        if (isEnPassantePossible) {
            enPassanteMove(boardField, rowIterator, possibleMoves);
        }
        return possibleMoves;
    }
    
    private boolean isOnStartPosition() {
        return ((this.isBlack && row == 6) || (!this.isBlack && row == 1));
    }
    
    private void attackingMove(Tile[][] boardField, int rowIterator, List<Move> possibleMoves) {
        if(!isOutOfBoard(row + rowIterator, column + rowIterator)) {
            Pieces pieceOnTile = boardField[row + rowIterator][column + rowIterator].getPiece();
            if(!(boardField[row + rowIterator][column + rowIterator].isTileEmpty()) && pieceOnTile.isPieceBlack() != isBlack) {
                possibleMoves.add(new Move(row + rowIterator, column + rowIterator, true, this, pieceOnTile));
            }
        }
        if(!isOutOfBoard(row + rowIterator, column - rowIterator)) {
            Pieces pieceOnTile = boardField[row + rowIterator][column - rowIterator].getPiece();
            if(!(boardField[row + rowIterator][column - rowIterator].isTileEmpty()) && pieceOnTile.isPieceBlack() != isBlack) {
                possibleMoves.add(new Move(row + rowIterator, column - rowIterator, true, this, pieceOnTile));
            }  
        } 
    }
    
    private void twoTilesMove(Tile[][] boardField, int rowIterator, List<Move> possibleMoves) {
        if (isOnStartPosition()) {
            int newPossibleColumn = column;
            int newPossibleRow = row + 2 * rowIterator;
            Tile firstTile = boardField[newPossibleRow - rowIterator][newPossibleColumn];
            Tile secondTile = boardField[newPossibleRow][newPossibleColumn];
            if(firstTile.isTileEmpty() && secondTile.isTileEmpty()) {
                possibleMoves.add(new Move(newPossibleRow, newPossibleColumn, false, this, null));
            }
        }
    }
    
    private void oneTileMove(Tile[][] boardField, int rowIterator, List<Move> possibleMoves) {
        if (!isOutOfBoard(row + rowIterator, column)) {
            if(boardField[row + rowIterator][column].isTileEmpty()) {
                possibleMoves.add(new Move(row + rowIterator, column, false, this, null));
            }
        }
    }
    
    private void enPassanteMove(Tile[][] boardField, int rowIterator, List<Move> possibleMoves) {
        int attackedPawnRow = this.row;
        int attackedPawnColumn = this.column + EnPassanteColumnIterator;
        Pieces attackedPawn = boardField[attackedPawnRow][attackedPawnColumn].getPiece();
        isEnPassantePossible = false;
        possibleMoves.add(new Move(row + rowIterator, attackedPawnColumn, true, this, attackedPawn));
    }
    
    public void setEnPassanteOn(int columnIterator) {
        isEnPassantePossible = true;
        EnPassanteColumnIterator = columnIterator;
        LOGGER.info("Enabled EnPassante for pawn");
    }
    /**
     * this method tells if this pawn can do enpassante move
     * @return true if this pawn can do enpassante move
     */
    public boolean isIsEnPassantePossible() {
        return isEnPassantePossible;
    }
    /**
     * this method tells if the pawn can do the enpassante move on the left or right side
     * @return 1 if this pawn can enpassante enemy pawn on the right side
     * -1 if this pawn can enpassante enemy pawn on the right side
     */
    public int getEnPassanteColumnIterator() {
        return EnPassanteColumnIterator;
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
        LOGGER.warning("The method hasAlreadyMoved called for Pawn");
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
