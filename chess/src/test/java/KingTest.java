/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.King;
import cz.cvut.fel.pjv.model.Knight;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Queen;
import cz.cvut.fel.pjv.model.Rook;
import cz.cvut.fel.pjv.model.Tile;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vitnademlejnsky
 */
public class KingTest {
    private King king;
    public KingTest() {
    }
    
    @Test
    public void getPossibleMoves(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        king = new King(false, 3, 2);
        boardField[3][2].changeTileStatement(false, king);
        assertEquals(8, king.getPossibleMoves(boardField, null).size());
        boardField[3][2].changeTileStatement(true, null);
        king.changePosition(7, 7);
        boardField[7][7].changeTileStatement(false, king);
        assertEquals(3, king.getPossibleMoves(boardField, null).size());
        boardField[6][6].changeTileStatement(false, new Knight(true, 6, 6));
        assertEquals(3, king.getPossibleMoves(boardField, null).size());
        boardField[6][7].changeTileStatement(false, new Knight(false, 6, 7));
        assertEquals(2, king.getPossibleMoves(boardField, null).size());
    }
    
    @Test
    public void hasAlreadyMoved(){
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        king = new King(false, 0, 4);
        boardField[0][0].changeTileStatement(false, king);
        Board board = new Board();
        board.setCustomBoard(boardField, false);
        assertFalse(king.hasAlreadyMoved());
        Move tryMove = new Move(1, 4, false, king, null);
        board.updateBoard(tryMove);
        assertTrue(king.hasAlreadyMoved());
    }
    
    @Test
    public void kingSideCastle() {
        Tile[][] boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
        boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        boardField[7][0].changeTileStatement(false, new Rook(true, 7, 0));
        boardField[7][1].changeTileStatement(false, new Knight(true, 7, 1));
        Board board = new Board();
        board = board.setCustomBoard(boardField, false);
        assertEquals(6, board.getBoardField()[0][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForBlack()).size());
        Move move = new Move(6, 0, true, board.getBoardField()[0][4].getPiece(), board.getBoardField()[0][7].getPiece());
        board = board.updateBoard(move);
        assertEquals(5, board.getBoardField()[7][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForWhite()).size());
        
        boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
        boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        boardField[3][7].changeTileStatement(false, new Queen(true, 3, 7));
        board = new Board();
        board = board.setCustomBoard(boardField, false);
        assertEquals(5, board.getBoardField()[0][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForBlack()).size());
        
        boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
        boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        boardField[3][6].changeTileStatement(false, new Queen(true, 3, 6));
        board = new Board();
        board = board.setCustomBoard(boardField, false);
        assertEquals(5, board.getBoardField()[0][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForBlack()).size());
        
        boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
        boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        boardField[3][5].changeTileStatement(false, new Queen(true, 3, 5));
        board = new Board();
        board = board.setCustomBoard(boardField, false);
        assertEquals(5, board.getBoardField()[0][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForBlack()).size());
        
        boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        boardField[0][4].changeTileStatement(false, new King(false, 0, 4));
        boardField[7][4].changeTileStatement(false, new King(true, 7, 4));
        boardField[0][7].changeTileStatement(false, new Rook(false, 0, 7));
        boardField[3][5].changeTileStatement(false, new Queen(true, 3, 5));
        boardField[2][5].changeTileStatement(false, new Knight(false, 2, 5));
        board = new Board();
        board = board.setCustomBoard(boardField, false);
        assertEquals(6, board.getBoardField()[0][4].getPiece().getPossibleMoves(board.getBoardField(), board.getAllPossibleMovesForBlack()).size());
    }
}
