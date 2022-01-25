/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.Move;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class PawnTest {
    
    public PawnTest() {
    }
    
    @Test
    public void getPossibleMoves(){
        Board board = new Board();
        assertEquals(2, board.getBoardField()[1][0].getPiece().getPossibleMoves(board.getBoardField(), null).size());
        board = board.updateBoard(new Move(3, 4, false, board.getBoardField()[1][4].getPiece(), null));
        board = board.updateBoard(new Move(4, 5, false, board.getBoardField()[6][5].getPiece(), null));
        assertEquals(2, board.getBoardField()[3][4].getPiece().getPossibleMoves(board.getBoardField(), null).size());
        board = board.updateBoard(new Move(4, 4, false, board.getBoardField()[3][4].getPiece(), null));
        board = board.updateBoard(new Move(4, 3, false, board.getBoardField()[6][3].getPiece(), null));
        assertEquals(31, board.getCurrentPlayer().getMyPossibleMoves().size());
    }
}
