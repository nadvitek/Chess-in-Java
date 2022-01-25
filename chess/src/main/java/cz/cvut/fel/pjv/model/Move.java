
package cz.cvut.fel.pjv.model;

public class Move {
    private int row;
    private int column;
    private boolean attackingMove;
    private Pieces attackedPiece;
    private Pieces movedPiece;
    private boolean isCastleMove;
    private boolean isPromotionMove = false;
    private boolean isMateMove = false;
    private boolean isCheckMove = false;

    public Move(int row, int column, boolean attackingMove, Pieces movedPiece, Pieces attackedPiece) {
        this.row = row;
        this.column = column;
        this.attackingMove = attackingMove;
        this.attackedPiece = attackedPiece;
        this.movedPiece = movedPiece;
        if (attackingMove) {
            isCastleMove = movedPiece.isPieceBlack() == attackedPiece.isPieceBlack();
        }
        if (movedPiece.isPieceBlack()) {
            if (row == 0 && movedPiece.getPieceType().equals("p")) {
                isPromotionMove = true;
            }
        } else {
            if (row == 7 && movedPiece.getPieceType().equals("p")) {
                isPromotionMove = true;
            }
        }
    }
    /**
     * this method tells if the move is a castle move
     * @return true if the move is a castle move
     */
    public boolean isMoveCastleMove() {
        return isCastleMove;
    }
    
    /**
     * this method tells if the move is a pawn promotion move
     * @return true if the move is a pawn promotion move
     */
    public boolean isPawnPromotionMove() {
        return isPromotionMove;
    }
    
    /**
     * this method returns target row of this move
     * @return target row of this move
     */
    public int getRow() {
        return row;
    }
    
    /**
     * this method returns target column of this move
     * @return target column of this move
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * this method tells if the move is a attacking move
     * @return true if the move is a attacking move
     */
    public boolean isAttackingMove() { 
        return attackingMove;
    }
    
    /**
     * this method returns attacked piece if the move is attackin move
     * @return attacked piece if the move is attacking move
     */
    public Pieces getAttackedPiece() {
        return attackedPiece;
    }
    /**
     * this method returns piece that initializes the move
     * @return piece that initializes the move
     */
    public Pieces getMovedPiece() {
        return movedPiece;
    }
    
    /**
     * this method sets this move on mate move
     */
    public void setMoveOnMateMove() {
        this.isMateMove = true;
    }
    
    /**
     * this method sets this move on mate move
     */
    public void setMoveOnCheckMove() {
        this.isCheckMove = true;
    }
    
    @Override
    public String toString() {
        String moveOut = "";
        if (!this.movedPiece.getPieceType().equals("p")) {
            moveOut += this.movedPiece.getPieceType().toUpperCase();
        } else {
            if (this.attackingMove) {
                moveOut += getLetterForColumn(this.movedPiece.getColumn());
            }
        }
        if (this.attackingMove) {
            moveOut += "x";
        }
        moveOut += (getLetterForColumn(this.column) + (this.row + 1));
        if (isCheckMove) {
            moveOut += "+";
        } else if (isMateMove) {
            moveOut += "#";
        }
        if (this.isCastleMove) {
            if (this.attackedPiece.getColumn() == 0) {
                moveOut = "0-0-0";
            } else {
                moveOut = "0-0";
            }
        }
        return moveOut;
    }
    
    private String getLetterForColumn(int column) {
        if (column == 0) {
            return "a";
        } else if (column == 1) {
            return "b";
        } else if (column == 2) {
            return "c";
        } else if (column == 3) {
            return "d";
        } else if (column == 4) {
            return "e";
        } else if (column == 5) {
            return "f";
        } else if (column == 6) {
            return "g";
        } else if (column == 7) {
            return "h";
        }
        return "wrong";
    }
}
