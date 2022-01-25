package cz.cvut.fel.pjv.model;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * this class caluclates
 * @author vitnademlejnsky
 */
public final class BoardEvaluator {
    private Tile[][] boardField;
    private boolean evaluatingForBlack;
    private boolean validMove = true;
    private List<Pieces> activeWhitePieces;
    private List<Pieces> activeBlackPieces;
    private List<Move> allPossibleMovesForWhite;
    private List<Move> allPossibleMovesForBlack;
    
    
    public BoardEvaluator(Tile[][] boardField, boolean evaluatingForBlack, Move evaluatingMove){
        this.boardField = boardField;
        this.evaluatingForBlack = evaluatingForBlack;
        updateBoardField(evaluatingMove);
        countValidity();
    }

    private void countValidity() {
        if (evaluatingForBlack) {
            for (Move enemyMove : allPossibleMovesForWhite) {
                if (enemyMove.isAttackingMove() && enemyMove.getAttackedPiece().getPieceType().equals("k")){
                    validMove = false;
                    break;
                }
            }
        } else {
            for (Move enemyMove : allPossibleMovesForBlack) {
                if (enemyMove.isAttackingMove() && enemyMove.getAttackedPiece().getPieceType().equals("k")){
                    validMove = false;
                    break;
                }
            }
        }
    }
    
    private void updateBoardField(Move evaluatingMove) {
        Pieces movedPiece = evaluatingMove.getMovedPiece();
        int startRow = movedPiece.getRow();
        int startColumn = movedPiece.getColumn();
        int targetRow = evaluatingMove.getRow();
        int targetColumn = evaluatingMove.getColumn();
        this.boardField[startRow][startColumn].changeTileStatement(true, null);
        if (evaluatingMove.isMoveCastleMove()) {
            Pieces rookForCastle = evaluatingMove.getAttackedPiece();
            this.boardField[startRow][rookForCastle.getColumn()].changeTileStatement(true, null);
            if (rookForCastle.getColumn() == 7) {
                this.boardField[startRow][startColumn + 1].changeTileStatement(false, rookForCastle);
                this.boardField[startRow][startColumn + 1].getPiece().changePosition(startRow, startColumn + 1);
            } else {
                this.boardField[startRow][startColumn - 1].changeTileStatement(false, rookForCastle);
                this.boardField[startRow][startColumn - 1].getPiece().changePosition(startRow, startColumn - 1);
            }
        }
        this.boardField[targetRow][targetColumn].changeTileStatement(false, movedPiece);
        this.boardField[targetRow][targetColumn].getPiece().changePosition(targetRow, targetColumn);
        if (evaluatingMove.isAttackingMove() &&
            evaluatingMove.getAttackedPiece().getRow() != targetRow) {
            Pieces attackedPawn = evaluatingMove.getAttackedPiece();
            this.boardField[attackedPawn.getRow()][attackedPawn.getColumn()].changeTileStatement(true, null);
        }
        this.activeWhitePieces = countActivePieces(false);
        this.activeBlackPieces = countActivePieces(true);
        if (movedPiece.getPieceType().equals("p") && abs(startRow - targetRow) == 2) {
            if (this.evaluatingForBlack) {
                for (Pieces piece : this.activeWhitePieces) {
                    if(piece.getPieceType().equals("p") &&
                       piece.getRow() == targetRow &&
                       abs(piece.getColumn() - targetColumn) == 1) {
                        Pawn pawn = (Pawn) piece;
                        pawn.setEnPassanteOn(targetColumn - pawn.getColumn());
                    }
                }
            } else {
                for (Pieces piece : this.activeBlackPieces) {
                    if(piece.getPieceType().equals("p") &&
                       piece.getRow() == targetRow &&
                       abs(piece.getColumn() - targetColumn) == 1) {
                        Pawn pawn = (Pawn) piece;
                        pawn.setEnPassanteOn(targetColumn - pawn.getColumn());
                    }
                }
            }
        }
        if (this.evaluatingForBlack) {
            this.allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
            this.allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
        } else {
            this.allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
            this.allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
        }
    }
    
    /*public void undoBoardField(Move evaluatingMove) {
        if (!evaluatingMove.isMoveCastleMove()) {
            boardField[startRow][startColumn].changeTileStatement(false, movedPiece);
            boardField[startRow][startColumn].getPiece().changePosition(startRow, startColumn);
            if (evaluatingMove.isAttackingMove()) {
                boardField[targetRow][targetColumn].changeTileStatement(false, attackedPiece);
            } else {
                boardField[targetRow][targetColumn].changeTileStatement(true, null);
            }
        } else {
            movedPiece.changePosition(startRow, startColumn);
            boardField[startRow][startColumn].changeTileStatement(false, movedPiece);
            boardField[targetRow][targetColumn].changeTileStatement(true, null);
            if(attackedPiece.getColumn() == 3) {
                boardField[attackedPiece.getRow()][attackedPiece.getColumn()].changeTileStatement(true, null);
                attackedPiece.changePosition(startRow, 0);
                boardField[startRow][0].changeTileStatement(false, attackedPiece);
            } else {
                boardField[attackedPiece.getRow()][attackedPiece.getColumn()].changeTileStatement(true, null);
                attackedPiece.changePosition(startRow, 7);
                boardField[startRow][7].changeTileStatement(false, attackedPiece);
            }
        }
    }*/
    
    private List<Pieces> countActivePieces(boolean isBlack) {
        List<Pieces> activePieces = new ArrayList<>();
        
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(this.boardField[row][column].isTileEmpty())){
                    Pieces pieceOnTile = this.boardField[row][column].getPiece();
                    if (pieceOnTile.isPieceBlack() == isBlack) {
                        activePieces.add(pieceOnTile);
                    } 
                }    
            }
        }
        
        return activePieces;
    }

    private List<Move> getAllPossibleMoves(List<Pieces> activePieces) {
        List<Move> allPossibleMoves = new ArrayList<>();
        for (Pieces piece : activePieces) {
            if(piece.getPieceType().equals("k")) {
                King king = (King) piece;
                if (king.isPieceBlack()) {
                    allPossibleMoves.addAll(king.getPossibleMoves(this.boardField, allPossibleMovesForWhite));
                } else {
                    allPossibleMoves.addAll(king.getPossibleMoves(this.boardField, allPossibleMovesForBlack));
                }
            } else {
                allPossibleMoves.addAll(piece.getPossibleMoves(this.boardField, null));
            }
        }
        return allPossibleMoves;
    }
    /**
     * this method returns true if the inserted move that we used on potential board
     * was valid (the player don't stay in check if is played) or false if the move
     * was not valid
     * @return true if the move was valid false if not
     */
    public boolean isValidMove() {
        return validMove;
    }
    
}
