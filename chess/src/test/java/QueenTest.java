/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Queen;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class QueenTest {
    private Queen queen;
    public QueenTest() {
    }
    
    @Test
    public void getPossibleMoves(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        queen = new Queen(false, 3, 2);
        boardField[3][2].changeTileStatement(false, queen);
        assertEquals(25, queen.getPossibleMoves(boardField, null).size());
        boardField[3][2].changeTileStatement(true, null);
        queen.changePosition(7, 7);
        boardField[7][7].changeTileStatement(false, queen);
        assertEquals(21, queen.getPossibleMoves(boardField, null).size());
        boardField[7][4].changeTileStatement(false, new Queen(true, 7, 4));
        assertEquals(17, queen.getPossibleMoves(boardField, null).size());
        boardField[2][7].changeTileStatement(false, new Queen(false, 2, 7));
        assertEquals(14, queen.getPossibleMoves(boardField, null).size());
    }
    
}
