/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cz.cvut.fel.pjv.model.Bishop;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class BishopTest {
    private Bishop bishop;
    public BishopTest() {
    }
    
    @Test
    public void getPossibleMoves(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        bishop = new Bishop(false, 7, 7);
        boardField[7][7].changeTileStatement(false, bishop);
        assertEquals(7, bishop.getPossibleMoves(boardField, null).size());
        boardField[7][7].changeTileStatement(true, null);
        bishop.changePosition(3, 2);
        boardField[3][2].changeTileStatement(false, bishop);
        assertEquals(11, bishop.getPossibleMoves(boardField, null).size());
        boardField[1][0].changeTileStatement(false, new Bishop(true, 1, 0));
        assertEquals(11, bishop.getPossibleMoves(boardField, null).size());
        boardField[5][4].changeTileStatement(false, new Bishop(false, 5, 4));
        assertEquals(8, bishop.getPossibleMoves(boardField, null).size());
    }
    
    
}
