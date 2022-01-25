
package cz.cvut.fel.pjv.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * this class saves the game
 * @author vitnademlejnsky
 */
public class SaveGameTxt {
    private final Tile[][] boardField;
    private final boolean blackIsOnTurn;
    
    public SaveGameTxt(Tile[][] boardField, boolean blackIsOnTurn, int numberOfFile, boolean isAIOn, boolean isHumanBlackPlayer) throws IOException {
        this.boardField = boardField;
        this.blackIsOnTurn = blackIsOnTurn;
        try {
            FileWriter savedFile = new FileWriter("src/main/saves/savefile" + (numberOfFile + 1) + ".txt");
            savedFile.write(writeIsAIOn(isAIOn) + "\n");
            savedFile.write(writeIsHumanBlackPlayer(isHumanBlackPlayer) + "\n");
            savedFile.write(writeWhoIsOnTurn(this.blackIsOnTurn) + "\n");
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    savedFile.write(writeFigure(this.boardField[row][column]));
                }
            }
            savedFile.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveGameTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String writeWhoIsOnTurn(boolean isBlackOnTurn) {
        if (isBlackOnTurn) {
            return "1";
        } else {
            return "0";
        }
    }
    
    private String writeIsAIOn(boolean isAIOn) {
        if (isAIOn) {
            return "1";
        } else {
            return "0";
        }
    }
    
    private String writeIsHumanBlackPlayer (boolean isHumanBlackPlayer) {
        if (isHumanBlackPlayer) {
            return "1";
        } else {
            return "0";
        }
    }

    private String writeFigure(Tile tile) {
        if (tile.isTileEmpty()) {
            return "0\n";
        } else {
            switch (tile.getPiece().getPieceType()) {
                case "p":
                {
                    Pawn pawn = (Pawn) tile.getPiece();
                    String returnValue = "p";
                    if (pawn.isPieceBlack()){
                        returnValue += "1";    
                    } else {
                        returnValue += "0";
                    }
                    if(pawn.isIsEnPassantePossible()) {
                        int cIterator = pawn.getEnPassanteColumnIterator();
                        returnValue += "1";
                        if (cIterator == 1) {
                            returnValue += "1";
                        } else {
                            returnValue += "0";
                        }
                    } else {
                        returnValue += "00";
                    }
                    return returnValue += "\n";
                }
                case "n":
                {
                    String returnValue = "n";
                    if (tile.getPiece().isPieceBlack()){
                        returnValue += "1";
                    } else {
                        returnValue += "0";
                    }
                    return returnValue += "\n";
                }
                case "b":
                {
                    String returnValue = "b";
                    if (tile.getPiece().isPieceBlack()){
                        returnValue += "1";
                    } else {
                        returnValue += "0";    
                    }
                    return returnValue += "\n";
                }
                case "r":
                {
                    Rook rook = (Rook) tile.getPiece();
                    String returnValue = "r";
                    if (rook.isPieceBlack()){
                        returnValue += "1";
                    } else {    
                        returnValue += "0";
                    }
                    if (rook.hasAlreadyMoved()){
                        returnValue += "0";
                    } else {
                        returnValue += "1";
                    }
                    return returnValue += "\n";
                }
                case "q":
                {
                    String returnValue = "q";
                    if (tile.getPiece().isPieceBlack()){
                        returnValue += "1";
                    } else {
                        returnValue += "0";
                    }    
                    return returnValue += "\n";
                }
                case "k":
                {
                    King king = (King) tile.getPiece();
                    String returnValue = "k";    
                    if (king.isPieceBlack()){
                        returnValue += "1";
                    } else {
                        returnValue += "0";
                    }
                    if (king.hasAlreadyMoved()){
                        returnValue += "0";
                    } else {
                        returnValue += "1";
                    }
                    return returnValue += "\n";
                }
                default:
                    return "0\n";
            }
        }
    }
}
