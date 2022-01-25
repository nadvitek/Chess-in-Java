
package cz.cvut.fel.pjv.model;

public class Tile {
    private boolean isEmpty;
    private Pieces piece; 
    private final int row;
    private final int column;
    
    public Tile (final int row, final int column, boolean isEmpty, Pieces piece) {
        this.piece = piece;
        this.isEmpty = isEmpty;
        this.row = row;
        this.column = column;
    }
    
    /**
     * this method changes statement of this tile (empty, not empty) and 
     * changes the piece of the tile (piece, null)
     * @param newStatement true if the tile is empty now
     * @param newPiece new piece that stands on the tile
     */
    public void changeTileStatement (boolean newStatement, Pieces newPiece) {
        isEmpty = newStatement;
        piece = newPiece;
    }
    
    /**
     * this method tells if the tile is empty
     * @return true if this tile is empty (no piece stands on the tile)
     */
    public boolean isTileEmpty () {
        return isEmpty;
    }
    
    /**
     * this method returns the piece that stands on this tile
     * @return the piece that stands on this tile
     */
    public Pieces getPiece() {
        return piece;
    }
    
    /**
     * this method returns the number of row of this tile 
     * @return the number of row of this tile 
     */
    public int getRow() {
        return row;
    }
    
    /**
     * this method returns the number of column of this tile 
     * @return the number of column of this tile 
     */
    public int getColumn() {
        return column;
    }
    
    
    
}
