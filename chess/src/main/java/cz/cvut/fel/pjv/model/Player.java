
package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Player {
    private List<Pieces> myPieces;
    private List<Pieces> enemyPieces;
    private List<Move> myPossibleMoves;
    private List<Move> opponentPossibleMoves;
    private final boolean isPlayerBlack;
    private Tile[][] boardField;
    private King myKing;
    private Board board;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Player(List<Pieces> myPieces,
                  List<Pieces> enemyPieces,
                  List<Move> myMoves,
                  List<Move> opponentMoves,
                  boolean blackPlayer,
                  Tile[][] boardField,
                  Board board) {
        this.myPieces = myPieces;
        this.enemyPieces = enemyPieces;
        this.myPossibleMoves = myMoves;
        this.opponentPossibleMoves  = opponentMoves;
        this.isPlayerBlack = blackPlayer;
        this.boardField = boardField;
        this.board = board;
    }
    
    /**
     * this method returns true if this player is a black player
     * @return true if this player is a black player
     */
    public boolean isPlayerBlackPlayer() {
        return this.isPlayerBlack;
    }
    
    /**
     * this method returns List of pieces of this player
     * @return List of pieces of this player
     */
    public List<Pieces> getMyPieces() {
        return myPieces;
    }
    /**
     * this method tells if the players king is in check
     * @return true if the players king is attacked (in check)
     */
    public boolean isPlayerInCheck() {
        King myKing = null;
        for (Pieces myPiece : myPieces) {
            if (myPiece.getPieceType().equals("k")) {
                myKing = (King) myPiece;
            }
        }
        for (Move enemyPieceMove : opponentPossibleMoves) {
            if (enemyPieceMove.getColumn() == myKing.getColumn() && enemyPieceMove.getRow() == myKing.getRow()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * this method tells if the player is in check mate
     * @return true if the player is in check mate
     */
    public boolean isPlayerInMate() {
        return isPlayerInCheck() && myPossibleMoves.isEmpty();
    }
    
    /**
     * this method returns true if the player is in stale mate 
     * @return true if the player is in stale mate 
     */
    public boolean isPlayerInStalemate() {
        return !isPlayerInCheck() && myPossibleMoves.isEmpty();
    }
    
    /**
     * this method updates Player class with new board field and updates lists
     * of their and enemy pieces
     * it also cleares moves from invalid moves (move when the player stay in check after its playing)
     * @param newBoard updated board after played move
     */
    public void updatePlayer(Board newBoard) {
        this.boardField = newBoard.getBoardField();
        if (this.isPlayerBlack){
            this.myPieces = newBoard.getActiveBlackPieces();
            this.enemyPieces = newBoard.getActiveWhitePieces();
            this.myPossibleMoves = newBoard.getAllPossibleMovesForBlack();
            if (board.isIsBlackOnTurn()){
                this.myPossibleMoves = getOnlyValidMoves(myPossibleMoves);
                LOGGER.info("Invalid Moves erased");
            }
            this.opponentPossibleMoves = newBoard.getAllPossibleMovesForWhite();
        } else {
            this.myPieces = newBoard.getActiveWhitePieces();
            this.enemyPieces = newBoard.getActiveBlackPieces();
            this.myPossibleMoves = newBoard.getAllPossibleMovesForWhite();
            if (!board.isIsBlackOnTurn()){
                this.myPossibleMoves = getOnlyValidMoves(myPossibleMoves);
                LOGGER.info("Invalid Moves erased");
            }
            this.opponentPossibleMoves = newBoard.getAllPossibleMovesForBlack();
        }
        
    }
    
    /**
     * this method returns List of players possible moves
     * @return List of players possible moves
     */
    public List<Move> getMyPossibleMoves() {
        return myPossibleMoves;
    }
    
    private List<Move> getOnlyValidMoves(List<Move> oldPossibleMoves){
        List<Move> newValidPossibleMoves = new ArrayList<>();
        for (Move myMove : oldPossibleMoves) {
            Tile[][] potentialBoardField = deepCopyOfBoard(boardField);
            Move inspectedMove = deepCopyOfMove(myMove);
            BoardEvaluator potentialBoard = new BoardEvaluator(potentialBoardField, isPlayerBlack, inspectedMove);
            if (potentialBoard.isValidMove()) {
                newValidPossibleMoves.add(myMove);
                //System.out.println(myMove + " is a safe move");
            }
        }
        return newValidPossibleMoves;
    }

    private Tile[][] deepCopyOfBoard(Tile[][] realBoardField) {
        Tile[][] potentialBoardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                potentialBoardField[row][column] = new Tile(row, column, true, null);
                if (!realBoardField[row][column].isTileEmpty()) {
                    switch (realBoardField[row][column].getPiece().getPieceType()) {
                        case "p":
                            
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                Pawn pawn = new Pawn(true, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, pawn);
                            } else {
                                Pawn pawn = new Pawn(false, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, pawn);
                            }   break;
                        case "n":
                            
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                Knight knight = new Knight(true, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, knight);
                            } else {
                                Knight knight = new Knight(false, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, knight);
                            }   break;
                        case "b":
                            
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                Bishop bishop = new Bishop(true, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, bishop);
                            } else {
                                Bishop bishop = new Bishop(false, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, bishop);
                            }   break;
                        case "r":
                            
                            Rook rook;
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                rook = new Rook(true, row, column);
                            } else {
                                rook = new Rook(false, row, column);
                            }   if (realBoardField[row][column].getPiece().hasAlreadyMoved()) {
                                rook.setStatusToHasMoved();
                            }   potentialBoardField[row][column].changeTileStatement(false, rook);
                            break;
                        case "q":
                            
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                Queen queen = new Queen(true, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, queen);
                            } else {
                                Queen queen = new Queen(false, row, column);
                                potentialBoardField[row][column].changeTileStatement(false, queen);
                            }   break;
                        case "k":
                           
                            King king;
                            if (realBoardField[row][column].getPiece().isPieceBlack()) {
                                king = new King(true, row, column);
                            } else {
                                king = new King(false, row, column);
                            }   if (realBoardField[row][column].getPiece().hasAlreadyMoved()) {
                                king.setStatusToHasMoved();
                            }   potentialBoardField[row][column].changeTileStatement(false, king);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return potentialBoardField;
    }
    
    private Move deepCopyOfMove(Move oldMove) {
        int row = oldMove.getRow();
        int column = oldMove.getColumn();
        boolean isAttackingMove = oldMove.isAttackingMove();
        Pieces movedPiece = oldMove.getMovedPiece();
        int startRow = movedPiece.getRow();
        int startColumn = movedPiece.getColumn();
        switch (movedPiece.getPieceType()) { 
                        case "p":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new Pawn(true, startRow, startColumn);
                            } else {
                                movedPiece = new Pawn(false, startRow, startColumn);
                            }
                            break;
                        case "n":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new Knight(true, startRow, startColumn);
                            } else {
                                movedPiece = new Knight(false, startRow, startColumn);
                            }   break;
                        case "b":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new Bishop(true, startRow, startColumn);
                            } else {
                                movedPiece = new Bishop(false, startRow, startColumn);
                            }   break;
                        case "r":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new Rook(true, startRow, startColumn);
                            } else {
                                movedPiece = new Rook(false, startRow, startColumn);
                            }   if (movedPiece.hasAlreadyMoved()) {
                                movedPiece.setStatusToHasMoved();
                            }
                            break;
                        case "q":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new Queen(true, startRow, startColumn);
                            } else {
                                movedPiece = new Queen(false, startRow, startColumn);
                            }   break;
                        case "k":
                            if (movedPiece.isPieceBlack()) {
                                movedPiece = new King(true, startRow, startColumn);
                            } else {
                                movedPiece = new King(false, startRow, startColumn);
                            }   if (movedPiece.hasAlreadyMoved()) {
                                movedPiece.setStatusToHasMoved();
                            }
                            break;
                        default:
                            break;
                    }
        Pieces attackedPiece = oldMove.getAttackedPiece();
        if (attackedPiece != null) {
            startRow = attackedPiece.getRow();
            startColumn = attackedPiece.getColumn();
            switch (attackedPiece.getPieceType()) {
                        case "p":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new Pawn(true, startRow, startColumn);
                            } else {
                                attackedPiece = new Pawn(false, startRow, startColumn);
                            }
                            break;
                        case "n":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new Knight(true, startRow, startColumn);
                            } else {
                                attackedPiece = new Knight(false, startRow, startColumn);
                            }   break;
                        case "b":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new Bishop(true, startRow, startColumn);
                            } else {
                                attackedPiece = new Bishop(false, startRow, startColumn);
                            }   break;
                        case "r":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new Rook(true, startRow, startColumn);
                            } else {
                                attackedPiece = new Rook(false, startRow, startColumn);
                            }   if (attackedPiece.hasAlreadyMoved()) {
                                attackedPiece.setStatusToHasMoved();
                            }
                            break;
                        case "q":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new Queen(true, startRow, startColumn);
                            } else {
                                attackedPiece = new Queen(false, startRow, startColumn);
                            }   break;
                        case "k":
                            if (attackedPiece.isPieceBlack()) {
                                attackedPiece = new King(true, startRow, startColumn);
                            } else {
                                attackedPiece = new King(false, startRow, startColumn);
                            }   if (attackedPiece.hasAlreadyMoved()) {
                                attackedPiece.setStatusToHasMoved();
                            }
                            break;
                        default:
                            break;
                    }
        }
        Move copiedMove = new Move(row, column, isAttackingMove, movedPiece, attackedPiece);
        return copiedMove;
    }
    
}
