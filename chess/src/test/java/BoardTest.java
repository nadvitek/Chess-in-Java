/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.King;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Pawn;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class BoardTest {
    private Move tryMove;
    private Board board;
    
    public BoardTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    } 
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        board = new Board();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void initBoard() {
        this.board = new Board();
        assertEquals(20, board.getCurrentPlayer().getMyPossibleMoves().size());
        assertEquals(20, board.getWaitingPlayer().getMyPossibleMoves().size());
        assertEquals(16, board.getActiveWhitePieces().size());
        assertEquals(16, board.getActiveBlackPieces().size());
        assertFalse(board.isIsBlackOnTurn());
        assertFalse(board.getCurrentPlayer().isPlayerInMate());
        assertFalse(board.getCurrentPlayer().isPlayerInStalemate());
        assertFalse(board.getWaitingPlayer().isPlayerInMate());
        assertFalse(board.getWaitingPlayer().isPlayerInStalemate());
    }
    
    @Test
    public void updateBoard() {
        this.board = new Board();
        tryMove = new Move(3, 4, false, new Pawn(false, 1, 4), null);
        assertEquals(16, board.updateBoard(tryMove).getActiveWhitePieces().size());
        assertEquals(16, board.getActiveBlackPieces().size());
        assertEquals(30, board.getAllPossibleMovesForWhite().size());
        assertEquals(20, board.getAllPossibleMovesForBlack().size());
        assertTrue(board.isIsBlackOnTurn());
        tryMove = new Move(4, 3, false, new Pawn(true, 6, 3), null);
        assertEquals(16, board.updateBoard(tryMove).getActiveBlackPieces().size());
        assertEquals(16, board.getActiveWhitePieces().size());
        assertEquals(31, board.getAllPossibleMovesForWhite().size());
        assertEquals(29, board.getAllPossibleMovesForBlack().size());
        assertFalse(board.isIsBlackOnTurn());
        tryMove = new Move(4, 3, true, new Pawn(false, 3, 4), new Pawn(true, 4, 3));
        assertEquals(16, board.updateBoard(tryMove).getActiveWhitePieces().size());
        assertEquals(15, board.getActiveBlackPieces().size());
        assertEquals(30, board.getAllPossibleMovesForWhite().size());
        assertEquals(28, board.getAllPossibleMovesForBlack().size());
        assertTrue(board.isIsBlackOnTurn());
    }
    
    @Test
    public void setCustomBoard() {
        this.board = new Board();
        tryMove = new Move(3, 4, false, new Pawn(false, 1, 4), null);
        Move tryMoveTwo = new Move(4, 3, false, new Pawn(true, 6, 3), null);
        assertTrue(board.setCustomBoard(new Board().updateBoard(tryMove).getBoardField(), true).isIsBlackOnTurn());
        board = new Board();
        assertFalse(board.setCustomBoard(new Board().updateBoard(tryMove).updateBoard(tryMoveTwo).getBoardField(), false).isIsBlackOnTurn());
        Board customBoard = new Board().updateBoard(tryMove).updateBoard(tryMoveTwo);
        board = new Board();
        board.setCustomBoard(customBoard.getBoardField(), false);
        assertEquals(board.getActiveBlackPieces(), customBoard.getActiveBlackPieces());
        assertEquals(board.getActiveWhitePieces(), customBoard.getActiveWhitePieces());
        assertEquals(board.getAllPossibleMovesForBlack().size(), customBoard.getAllPossibleMovesForBlack().size());
        assertEquals(board.getAllPossibleMovesForWhite().size(), customBoard.getAllPossibleMovesForWhite().size());
    }
    
    @Test
    public void executePromotionMove() {
        this.board = new Board();
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        Pawn promotedPawn = new Pawn(false, 6, 2);
        boardField[1][3].changeTileStatement(false, new King(false, 1, 3));
        boardField[3][6].changeTileStatement(false, new King(true, 3, 6));
        boardField[6][2].changeTileStatement(false, promotedPawn);
        this.board.setCustomBoard(boardField, false);
        assertEquals(2, this.board.getActiveWhitePieces().size());
        tryMove = new Move(7, 2, false, promotedPawn, null);
        this.board = board.updateBoard(tryMove);
        this.board = board.executePromotionMove(3, tryMove);
        assertEquals(2, this.board.getActiveWhitePieces().size());
        assertEquals(1, this.board.getActiveBlackPieces().size());
        assertEquals("q", this.board.getBoardField()[7][2].getPiece().getPieceType());
        assertTrue(this.board.getBlackPlayer().isPlayerInCheck());
        assertTrue(this.board.isIsBlackOnTurn());
    }
}
