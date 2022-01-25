/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Rook;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class RookTest {
    private Rook rook;
    public RookTest() {
    }
    
    @Test
    public void getPossibleMoves(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        rook = new Rook(false, 3, 2);
        boardField[3][2].changeTileStatement(false, rook);
        assertEquals(14, rook.getPossibleMoves(boardField, null).size());
        boardField[3][2].changeTileStatement(true, null);
        rook.changePosition(7, 7);
        boardField[7][7].changeTileStatement(false, rook);
        assertEquals(14, rook.getPossibleMoves(boardField, null).size());
        boardField[7][4].changeTileStatement(false, new Rook(true, 7, 4));
        assertEquals(10, rook.getPossibleMoves(boardField, null).size());
        boardField[2][7].changeTileStatement(false, new Rook(false, 2, 7));
        assertEquals(7, rook.getPossibleMoves(boardField, null).size());
    }
    
    @Test
    public void hasAlreadyMoved(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        rook = new Rook(false, 0, 0);
        boardField[0][0].changeTileStatement(false, rook);
        Board board = new Board();
        board.setCustomBoard(boardField, false);
        assertFalse(rook.hasAlreadyMoved());
        Move tryMove = new Move(1, 0, false, rook, null);
        board.updateBoard(tryMove);
        assertTrue(rook.hasAlreadyMoved());
    }
    
}
