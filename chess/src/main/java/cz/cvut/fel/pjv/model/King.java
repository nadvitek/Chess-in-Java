
package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class King extends Pieces{
    private List<Move> possibleMoves; 
    private int column;
    private int row;
    private final boolean isBlack;
    private boolean hasMoved = false;
    private final String pieceType = "k";
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public King (boolean isBlack, int row, int column) {
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
        leftPossibleMove(boardField, possibleMoves);
        upperPossibleMove(boardField, possibleMoves);
        rightPossibleMove(boardField, possibleMoves);
        lowerPossibleMove(boardField, possibleMoves);
        diagonalLeftLowerTilePossibleMove(boardField, possibleMoves);
        diagonalLeftUpperTilePossibleMove(boardField, possibleMoves);
        diagonalRightLowerTilePossibleMove(boardField, possibleMoves);
        diagonalRightUpperTilePossibleMove(boardField, possibleMoves);
        queenSideCastle(boardField, possibleMoves, enemyMoves);
        kingSideCastle(boardField, possibleMoves, enemyMoves);
        return possibleMoves;
    }
    
    private void leftPossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row;
        int newPossibleColumn = column - 1;
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
    
    private void upperPossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row + 1;
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
            }
        }
    }
    
    private void rightPossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row;
        int newPossibleColumn = column + 1;
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
    
    private void lowerPossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row - 1;
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
            }
        }
    }
    
    private void diagonalRightUpperTilePossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row + 1;
        int newPossibleColumn = column + 1;
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
    
    private void diagonalLeftUpperTilePossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row + 1;
        int newPossibleColumn = column - 1;
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
    
    private void diagonalLeftLowerTilePossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row - 1;
        int newPossibleColumn = column - 1;
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
    
    private void diagonalRightLowerTilePossibleMove(Tile[][] boardField, List<Move> possibleMoves) {
        int newPossibleRow = row - 1;
        int newPossibleColumn = column + 1;
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
    
    private void queenSideCastle(Tile[][] boardField, List<Move> possibleMoves, List<Move> enemyMoves) {
        boolean castleIsSafe = true;
        if (!this.hasMoved){
            if(isRookOnQueenSideReadyForCastle(boardField)){
                for (int i = -2; i < 1; i++) {
                    if (enemyMoves != null) {
                        if (isTileAttackedByPlayer(this.row, this.column + i, enemyMoves)) {
                           castleIsSafe = false;
                        }
                    } else {
                        castleIsSafe = false;
                    }
                }
                if (castleIsSafe) {
                    System.out.println("QUEEN CASTLE READY");
                    Pieces rookForCastle = boardField[this.row][0].getPiece();
                    possibleMoves.add(new Move(this.row, this.column - 2, true, this, rookForCastle));
                    LOGGER.fine("Castle is Possible");
                }
            }
        }

    }
    
    private void kingSideCastle(Tile[][] boardField, List<Move> possibleMoves, List<Move> enemyMoves) {
        boolean castleIsSafe = true;
        if (!this.hasMoved){
            if(isRookOnKingSideReadyForCastle(boardField)){
                for (int i = 0; i < 3; i++) {
                    if (enemyMoves != null) {
                        if (isTileAttackedByPlayer(this.row, this.column + i, enemyMoves)) {
                           castleIsSafe = false;
                        }
                    } else {
                        castleIsSafe = false;
                    }
                }
                if (castleIsSafe) {
                    Pieces rookForCastle = boardField[this.row][7].getPiece();
                    possibleMoves.add(new Move(this.row, this.column + 2, true, this, rookForCastle));
                    LOGGER.fine("Castle is Possible");
                }
            }
        }
    }
    
    private boolean isRookOnQueenSideReadyForCastle(Tile[][] boardField) {
        boolean isReady = false;
        if (!boardField[this.row][0].isTileEmpty() && 
            boardField[this.row][0].getPiece().getPieceType().equals("r") &&
            !boardField[this.row][0].getPiece().hasAlreadyMoved()){
            for (int i = 1; i < 4; i++) {
                if (!boardField[this.row][i].isTileEmpty()){
                    isReady = false;
                    break;
                } else {
                    isReady = true;
                }
            }
        }
        return isReady;
    }
    
    private boolean isRookOnKingSideReadyForCastle(Tile[][] boardField) {
        boolean isReady = false;
        if (!boardField[this.row][7].isTileEmpty() &&
            boardField[this.row][7].getPiece().getPieceType().equals("r") &&
            !boardField[this.row][7].getPiece().hasAlreadyMoved()){
            for (int i = 5; i < 7; i++) {
                if (!boardField[this.row][i].isTileEmpty()){
                    isReady = false;
                    break;
                } else {
                    isReady = true;
                }
            }
        }
        return isReady;
    }
    
    private boolean isTileAttackedByPlayer(int row, int column, List<Move> enemyMoves) {
        for (Move enemyMove : enemyMoves) {
                if (enemyMove.getColumn() == column && enemyMove.getRow() == row){
                    return true;
                }
            }    
        return false;
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
     * method tells if the king has moved since the start of the game
     * @return true if the king has moved since the start of the game
     */
    @Override
    public boolean hasAlreadyMoved() {
        return hasMoved;
    }

    /**
     *changes the status of king for future knowing whether the king is 
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
