/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Knight;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class KnightTest {
    private Knight knight;
    public KnightTest() {
    }
    
    @Test
    public void getPossibleMoves() {
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        knight = new Knight(false, 3, 2);
        boardField[3][2].changeTileStatement(false, knight);
        assertEquals(8, knight.getPossibleMoves(boardField, null).size());
        boardField[3][2].changeTileStatement(true, null);
        knight.changePosition(7, 7);
        boardField[7][7].changeTileStatement(false, knight);
        assertEquals(2, knight.getPossibleMoves(boardField, null).size());
        boardField[6][5].changeTileStatement(false, new Knight(true, 6, 5));
        assertEquals(2, knight.getPossibleMoves(boardField, null).size());
        boardField[5][6].changeTileStatement(false, new Knight(false, 5, 6));
        assertEquals(1, knight.getPossibleMoves(boardField, null).size());
        boardField[6][6].changeTileStatement(false, new Knight(true, 6, 6));
        assertEquals(1, knight.getPossibleMoves(boardField, null).size());
    }
}
