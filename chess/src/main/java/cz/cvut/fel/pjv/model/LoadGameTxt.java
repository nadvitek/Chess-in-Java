
package cz.cvut.fel.pjv.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * this class loads the game
 * @author vitnademlejnsky
 */
public class LoadGameTxt {
    private Tile[][] boardField;
    private boolean isBlackOnTurn;
    private boolean isAIOn;
    private boolean isHumanBlackPlayer;
    
    public LoadGameTxt(int loadSlot) {
        this.boardField = new Tile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                this.boardField[row][column] = new Tile(row, column, true, null);
            }
        }
        File file = new File("src/main/saves/savefile" + (loadSlot + 1) + ".txt");
        try {
            Scanner sc = new Scanner(file);
            wasAIOn(sc.nextLine());
            wasHumanBlackPlayer(sc.nextLine());
            wasBlackOnTurn(sc.nextLine());
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    String info = sc.nextLine();
                    createPiece(info, row, column);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadGameTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void wasBlackOnTurn(String info) {
        if (info.charAt(0) == '1') {
            this.isBlackOnTurn = true;
        } else {
            this.isBlackOnTurn = false;
        }
    }
    
    private void wasAIOn(String info) {
        if (info.charAt(0) == '1') {
            this.isAIOn = true;
        } else {
            this.isAIOn = false;
        }
    }
    
    private void wasHumanBlackPlayer(String info) {
        if (info.charAt(0) == '1') {
            this.isHumanBlackPlayer = true;
        } else {
            this.isHumanBlackPlayer = false;
        }
    }
    
    private void createPiece(String info, int row, int column) {
        if (info.charAt(0) == 'p') {
            Pawn pawn;
            if (info.charAt(1) == '1'){
                pawn = new Pawn(true, row, column);
            } else {
                pawn = new Pawn(false, row, column);  
            }
            if (info.charAt(2) == '1') {
                if (info.charAt(3) == '1') {
                    pawn.setEnPassanteOn(1);
                } else {
                    pawn.setEnPassanteOn(-1);
                } 
            }
            this.boardField[row][column].changeTileStatement(false, pawn);
        } else if (info.charAt(0) == 'n') {
            Knight knight;
            if (info.charAt(1) == '1'){
                knight = new Knight(true, row, column);
            } else {
                knight = new Knight(false, row, column);  
            }
            this.boardField[row][column].changeTileStatement(false, knight);
        } else if (info.charAt(0) == 'b') {
            Bishop bishop;
            if (info.charAt(1) == '1'){
                bishop = new Bishop(true, row, column);
            } else {
                bishop = new Bishop(false, row, column);  
            }
            this.boardField[row][column].changeTileStatement(false, bishop);
        } else if (info.charAt(0) == 'r') {
            Rook rook;
            if (info.charAt(1) == '1'){
                rook = new Rook(true, row, column);
            } else {
                rook = new Rook(false, row, column);  
            }
            if (info.charAt(2) == '0') {
                rook.setStatusToHasMoved();
            }
            this.boardField[row][column].changeTileStatement(false, rook);
        } else if (info.charAt(0) == 'q') {
            Queen queen;
            if (info.charAt(1) == '1'){
                queen = new Queen(true, row, column);
            } else {
                queen = new Queen(false, row, column);  
            }
            this.boardField[row][column].changeTileStatement(false, queen);
        } else if (info.charAt(0) == 'k') {
            King king;
            if (info.charAt(1) == '1'){
                king = new King(true, row, column);
            } else {
                king = new King(false, row, column);  
            }
            if (info.charAt(2) == '0') {
                king.setStatusToHasMoved();
            }
            this.boardField[row][column].changeTileStatement(false, king);
        }
    }
    
    /**
     * this method returns board field of file that we loaded
     * @return board field of loaded board
     */
    public Tile[][] getBoardField() {
        return boardField;
    }
    
    /**
     * this method tells if the black player was on turn when user saved the game
     * @return true if the black player was on turn when user saved the game
     */
    public boolean isBlackOnTurn() {
        return isBlackOnTurn;
    }
    
    /**
     * this method tells if the game that user saved was vs AI
     * @return true if the game that user saved was vs AI
     */
    public boolean isAIOn() {
        return isAIOn;
    }
    
    /**
     * this method tells if the human that played vs AI was black player
     * @return true if the human that played vs AI was black player
     */
    public boolean isHumanBlackPlayer() {
        return isHumanBlackPlayer;
    }
}
