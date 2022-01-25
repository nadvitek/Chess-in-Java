
package cz.cvut.fel.pjv.model;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Board {
    protected Tile[][] boardField;
    private List<Pieces> activeWhitePieces;
    private List<Pieces> activeBlackPieces;
    private List<Move> allPossibleMovesForWhite;
    private List<Move> allPossibleMovesForBlack;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private boolean isBlackOnTurn;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public Board() {
        boardField = new Tile[8][8];
        setUpBoard();
        activeWhitePieces = countActivePieces(false);
        activeBlackPieces = countActivePieces(true);
        allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
        allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
        whitePlayer = new Player(activeWhitePieces, activeBlackPieces, allPossibleMovesForWhite, allPossibleMovesForBlack, false, this.boardField, this);
        blackPlayer = new Player(activeBlackPieces, activeWhitePieces, allPossibleMovesForBlack, allPossibleMovesForWhite, true, this.boardField, this);
        isBlackOnTurn = false;
    }
    /**
     * method that changes board if user loads board or creates his own
     * @param boardField new board field
     * @param isBlackOnTurn tells if black is on turn in this new board
     * @return the updated class board
     */
    public Board setCustomBoard(Tile[][] boardField, boolean isBlackOnTurn) {
        this.boardField = boardField;
        this.isBlackOnTurn = isBlackOnTurn;
        activeWhitePieces = countActivePieces(false);
        activeBlackPieces = countActivePieces(true);
        if (isBlackOnTurn) {
            allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
            allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
        } else {
            allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
            allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
        }
        updatePlayers();
        LOGGER.info("Custom or loaded board set up.");
        return this;
    }
    
    private void setUpBoard() {
        fullfilBoardWithEmptyTiles();
        createAndSetKings();
        createAndSetQueens();
        createAndSetRooks();
        createAndSetBishops();
        createAndSetKnights();
        createAndSetPawns();
        LOGGER.fine("Board set up.");
    }
    
    private void createAndSetKings() {
        this.boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        this.boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
    }
    
    private void createAndSetQueens() {
        this.boardField[0][3].changeTileStatement(false, new Queen(false, 0, 3));
        this.boardField[7][3].changeTileStatement(false, new Queen(true, 7, 3));
    }
    
    private void createAndSetBishops() {
        this.boardField[0][2].changeTileStatement(false, new Bishop(false, 0, 2));
        this.boardField[0][5].changeTileStatement(false, new Bishop(false, 0, 5));
        this.boardField[7][2].changeTileStatement(false, new Bishop(true, 7, 2));
        this.boardField[7][5].changeTileStatement(false, new Bishop(true, 7, 5));
    }
    
    private void createAndSetKnights() {
        this.boardField[0][1].changeTileStatement(false, new Knight(false, 0, 1));
        this.boardField[0][6].changeTileStatement(false, new Knight(false, 0, 6));
        this.boardField[7][1].changeTileStatement(false, new Knight(true, 7, 1));
        this.boardField[7][6].changeTileStatement(false, new Knight(true, 7, 6));
    }
    
    private void createAndSetRooks() {
        this.boardField[0][0].changeTileStatement(false, new Rook(false, 0, 0));
        this.boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        this.boardField[7][0].changeTileStatement(false, new Rook(true, 7, 0));
        this.boardField[7][7].changeTileStatement(false, new Rook(true, 7, 7));
    }
    
    private void createAndSetPawns() {
        for (int i = 0; i < 8; i++) {
            this.boardField[1][i].changeTileStatement(false, new Pawn(false, 1, i));
            this.boardField[6][i].changeTileStatement(false, new Pawn(true, 6, i));
        }
    }

    private void fullfilBoardWithEmptyTiles() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                this.boardField[row][column] = new Tile(row, column, true, null);
            }
        }
    }

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
        if (isBlack) {
            LOGGER.info("Active pieces counted for black player");
        } else {
            LOGGER.info("Active pieces counted for white player");
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
        LOGGER.info("All possible moves got.");
        return allPossibleMoves;
    }
    
    /**
     * method that returns active pieces of white player
     * @return list of active pieces of white player 
     */
    public List<Pieces> getActiveWhitePieces() {
        return activeWhitePieces;
    }
    /**
     * method that returns active pieces of black player
     * @return list of active pieces of black player 
     */
    public List<Pieces> getActiveBlackPieces() {
        return activeBlackPieces;
    }
    /**
     * method that returns white player of this board
     * @return white player of this board
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }
    /**
     * method that returns black player of this board
     * @return black player of this board
     */
    public Player getBlackPlayer() {
        return blackPlayer;
    }
    /**
     * method that returns board field of this board
     * @return board field of this board
     */
    public Tile[][] getBoardField() {
        return boardField;
    }
    /**
     * method that updates board with a executed move
     * it rewrites board field and catches if the move is special move
     * @param executingMove move that was executed
     * @return updated board after executed move
     */
    public Board updateBoard(Move executingMove) {
        Pieces movedPiece = executingMove.getMovedPiece();
        int startRow = movedPiece.getRow();
        int startColumn = movedPiece.getColumn();
        int targetRow = executingMove.getRow();
        int targetColumn = executingMove.getColumn();
        this.boardField[startRow][startColumn].changeTileStatement(true, null);
        if (executingMove.isMoveCastleMove()) {
            Pieces rookForCastle = executingMove.getAttackedPiece();
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
        if (executingMove.isAttackingMove() &&
            executingMove.getAttackedPiece().getRow() != targetRow) {
            Pieces attackedPawn = executingMove.getAttackedPiece();
            this.boardField[attackedPawn.getRow()][attackedPawn.getColumn()].changeTileStatement(true, null);
        }
        this.activeWhitePieces = countActivePieces(false);
        this.activeBlackPieces = countActivePieces(true);
        if (movedPiece.getPieceType().equals("p") && abs(startRow - targetRow) == 2) {
            if (isBlackOnTurn) {
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
        this.allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
        this.allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
        giveTurnToNextPlayer();
        LOGGER.info("Chess is updated");
        updatePlayers();
        return this;
    }
    /**
     * this method updates both players
     * it updates their pieces and gives them their moves
     */
    private void updatePlayers() {
        if (this.isBlackOnTurn) {
            this.blackPlayer.updatePlayer(this);
            this.whitePlayer.updatePlayer(this);
        } else {
            this.whitePlayer.updatePlayer(this);
            this.blackPlayer.updatePlayer(this);
        }
        LOGGER.info("Players are updated.");
    }
    
    private void giveTurnToNextPlayer() {
        this.isBlackOnTurn = !this.isBlackOnTurn;
    }
    /**
     * this method returns possible moves for white player
     * @return possible moves for white player
     */
    public List<Move> getAllPossibleMovesForWhite() {
        return allPossibleMovesForWhite;
    }
    /**
     * this method returns possible moves for black player
     * @return possible moves for black player
     */
    public List<Move> getAllPossibleMovesForBlack() {
        return allPossibleMovesForBlack;
    }
    /**
     * this method returns player that is on turn
     * @return the player that is on turn 
     */
    public Player getCurrentPlayer() {
        if (this.isBlackOnTurn){
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }
    /**
     * this method returns player that is not on turn
     * @return the player that is not on turn
     */
    public Player getWaitingPlayer() {
        if (!this.isBlackOnTurn){
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }
    /**
     * this method tells who is on turn with asking
     * if the black player is on turn
     * @return true if black player is on turn
     */
    public boolean isIsBlackOnTurn() {
        return isBlackOnTurn;
    }
    /**
     * this method executes a promotion move
     * it changes pawn to other piece
     * @param choice number that says which piece the player has chosen
     * @param move move of pawn that promoted to stronger piece
     * @return updated board
     */
    public Board executePromotionMove(int choice, Move move) {
        Pieces promotedPiece = null;
        switch (choice) {
            case 0:
                promotedPiece = new Knight(move.getMovedPiece().isPieceBlack(), move.getRow(), move.getColumn());
                break;
            case 1:
                promotedPiece = new Bishop(move.getMovedPiece().isPieceBlack(), move.getRow(), move.getColumn());
                break;
            case 2:
                promotedPiece = new Rook(move.getMovedPiece().isPieceBlack(), move.getRow(), move.getColumn());
                promotedPiece.setStatusToHasMoved();
                break;
            case 3:
                promotedPiece = new Queen(move.getMovedPiece().isPieceBlack(), move.getRow(), move.getColumn());
                break;
            default:
                break;
        }
        try {
            this.boardField[promotedPiece.getRow()][promotedPiece.getColumn()].changeTileStatement(false, promotedPiece);
        } catch (NullPointerException e){
            LOGGER.warning("Fail in piece promotion");
        }
        this.activeWhitePieces = countActivePieces(false);
        this.activeBlackPieces = countActivePieces(true);
        this.allPossibleMovesForBlack = getAllPossibleMoves(activeBlackPieces);
        this.allPossibleMovesForWhite = getAllPossibleMoves(activeWhitePieces);
        updatePlayers();
        return this;
    }
    
    
}
