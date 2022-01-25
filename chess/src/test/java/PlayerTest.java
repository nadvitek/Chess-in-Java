/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.King;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Queen;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vitnademlejnsky
 */
public class PlayerTest {
    
    public PlayerTest() {
    }
    
    @Test
    public void isPlayerInCheck() {
        Board board = new Board();
        Move move = new Move(3, 4, false, board.getBoardField()[1][4].getPiece(), null);
        board = board.updateBoard(move);
        move = new Move(5, 5, false, board.getBoardField()[6][5].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInCheck());
        assertFalse(board.getCurrentPlayer().isPlayerInCheck());
        move = new Move(4, 7, false, board.getBoardField()[0][3].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInCheck());
        assertTrue(board.getCurrentPlayer().isPlayerInCheck());
        move = new Move(5, 6, false, board.getBoardField()[6][6].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInCheck());
        assertFalse(board.getCurrentPlayer().isPlayerInCheck());
    }
    
    @Test
    public void isPlayerInMate(){
        Board board = new Board();
        Move move = new Move(3, 4, false, board.getBoardField()[1][4].getPiece(), null);
        board = board.updateBoard(move);
        move = new Move(5, 5, false, board.getBoardField()[6][5].getPiece(), null);
        board = board.updateBoard(move);
        move = new Move(2, 2, false, board.getBoardField()[0][1].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInMate());
        assertFalse(board.getCurrentPlayer().isPlayerInMate());
        move = new Move(4, 6, false, board.getBoardField()[6][6].getPiece(), null);
        board = board.updateBoard(move);
        move = new Move(4, 7, false, board.getBoardField()[0][3].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInCheck());
        assertTrue(board.getCurrentPlayer().isPlayerInCheck());
    }
    
    @Test
    public void isPlayerInStalemate(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[2][4].changeTileStatement(false, new King(false, 2, 4));
        boardField[1][7].changeTileStatement(false, new King(true, 1, 7));
        boardField[7][0].changeTileStatement(false, new Queen(false, 7, 0));
        Board board = new Board();
        board = board.setCustomBoard(boardField, false);
        Move move = new Move(7, 6, false, board.getBoardField()[7][0].getPiece(), null);
        board = board.updateBoard(move);
        move = new Move(0, 7, false, board.getBoardField()[1][7].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInStalemate());
        assertFalse(board.getCurrentPlayer().isPlayerInStalemate());
        move = new Move(2, 6, false, board.getBoardField()[7][6].getPiece(), null);
        board = board.updateBoard(move);
        assertFalse(board.getWaitingPlayer().isPlayerInStalemate());
        assertTrue(board.getCurrentPlayer().isPlayerInStalemate());
    }
    
}
