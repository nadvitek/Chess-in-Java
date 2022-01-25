/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Rook;
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
public class TileTest {
    private Tile tile;
    
    public TileTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void initTile(){
        tile = new Tile(0, 0, true, null);
        assertTrue(tile.isTileEmpty());
        assertEquals(0, tile.getColumn());
        assertEquals(0, tile.getRow());
        assertEquals(null, tile.getPiece());
    }
    
    @Test
    public void changeTileStatement() {
        tile = new Tile(0, 0, true, null);
        tile.changeTileStatement(false, new Rook(false, 0, 0));
        assertFalse(tile.isTileEmpty());
        assertEquals(0, tile.getColumn());
        assertEquals(0, tile.getRow());
        assertEquals(new Rook(false, 0, 0).getPieceType(), tile.getPiece().getPieceType());
    }
    
}
