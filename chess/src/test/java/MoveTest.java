/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.King;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Pawn;
import cz.cvut.fel.pjv.model.Rook;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vitnademlejnsky
 */
public class MoveTest {
    private Move move;
    public MoveTest() {
    }
    
    @Test
    public void isMoveCastleMove() {
        Rook castleRook = new Rook(false, 0, 0);
        King king = new King(false, 4, 0);
        move = new Move(0, 2, true, king, castleRook);
        assertTrue(move.isMoveCastleMove());
        Rook enemyRook = new Rook(true, 1, 4);
        move = new Move(1, 4, true, king, enemyRook);
        assertFalse(move.isMoveCastleMove());
    }
    
    @Test
    public void isPawnPromotionMove(){
        Pawn pawn = new Pawn(false, 6, 1);
        move = new Move(7, 1, false, pawn, null);
        assertTrue(move.isPawnPromotionMove());
        Pawn attackedPawn = new Pawn(true, 7, 0);
        move = new Move(7, 0, true, pawn, attackedPawn);
        assertTrue(move.isPawnPromotionMove());
        pawn = new Pawn(false, 3, 4);
        move = new Move(4, 4, false, pawn, null);
        assertFalse(move.isPawnPromotionMove());
    }
}
