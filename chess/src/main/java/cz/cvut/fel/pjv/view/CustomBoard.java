
package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.Bishop;
import cz.cvut.fel.pjv.model.King;
import cz.cvut.fel.pjv.model.Knight;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Pawn;
import cz.cvut.fel.pjv.model.Pieces;
import cz.cvut.fel.pjv.model.Queen;
import cz.cvut.fel.pjv.model.Rook;
import cz.cvut.fel.pjv.model.Tile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * this class creates menu, where the user can set up his own game and then play it
 * @author vitnademlejnsky
 */
public class CustomBoard {
    private final JFrame customBoardFrame;
    private final CustomBoard.BoardPanel boardPanel;
    private final JPanel leftButtonPanel;
    private final JPanel piecesButtonPanel;
    private final Color lightBrown = Color.decode("#FFFACD");
    private final Color darkBrown = Color.decode("#593E1A");
    private final Tile[][] boardField;
    private String chosenPieceType = null;
    private boolean isChosenPieceBlack = false;
    private boolean isBlackOnTurn;

    public CustomBoard() {
        this.customBoardFrame = new JFrame("Chessgame");
        this.customBoardFrame.setLayout(new BorderLayout());
        final JMenuBar customBoardMenuBar = new JMenuBar();
        populateMenuBar(customBoardMenuBar, this.customBoardFrame);
        this.customBoardFrame.setJMenuBar(customBoardMenuBar);
        this.customBoardFrame.setSize(new Dimension(1100, 800));
        this.boardField = new Tile[8][8];
        fullfilBoardWithEmptyTiles();
        this.isBlackOnTurn = false;
        this.boardPanel = new BoardPanel();
        this.piecesButtonPanel = new JPanel();
        this.leftButtonPanel = new JPanel();
        this.piecesButtonPanel.setLayout(new GridLayout(6, 2));
        this.leftButtonPanel.setLayout(new BorderLayout());
        setPieceButtonPanel(this.piecesButtonPanel);
        setLeftPanel(leftButtonPanel);
        this.customBoardFrame.add(this.leftButtonPanel, BorderLayout.WEST);
        this.customBoardFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.customBoardFrame.add(this.piecesButtonPanel, BorderLayout.EAST);
        this.customBoardFrame.setVisible(true);
        this.customBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void populateMenuBar(final JMenuBar tableMenuBar, JFrame customBoard){
        tableMenuBar.add(createFileMenu(customBoard));
    }
    
    private void fullfilBoardWithEmptyTiles() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                this.boardField[row][column] = new Tile(row, column, true, null);
            }
        }
    }
    
    private void missingKings() {
        String title = "Missing King";
        String message = "In the game must be kings of both colors.";
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null);
    }
    
    private void kingInCheck() {
        String title = "Game can't start";
        String message = "";
        if (this.isBlackOnTurn) {
            message = "Black can't be on turn, because white king is in check";
        } else {
            message = "White can't be on turn, because black king is in check";
        }
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null);
    }
    
    private boolean isKingAlreadyInTheGame(boolean isKingBlack) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!boardField[row][column].isTileEmpty() &&
                    boardField[row][column].getPiece().isPieceBlack() == isKingBlack &&
                    boardField[row][column].getPiece().getPieceType().equals("k")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public JMenu createFileMenu(JFrame customBoard) {
        final JMenu fileMenu = new JMenu("File");
        
        final JMenuItem toMainMenu = new JMenuItem("Return to MainMenu");
        toMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                customBoard.dispose();
            }
        });
        
        fileMenu.add(toMainMenu);
        
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    
    private void setLeftPanel(JPanel leftPanel) {
        leftPanel.add(createPlayButton(), BorderLayout.CENTER);
        leftPanel.add(createBlackIsOnMoveButton(), BorderLayout.NORTH);
        leftPanel.add(createWhiteIsOnMoveButton(), BorderLayout.SOUTH);
    }
    
    private JButton createPlayButton() {
        JButton playButton = new JButton("Play Game");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isKingAlreadyInTheGame(true) && isKingAlreadyInTheGame(false)) {
                    if (!isKingInCheck()) {
                        new Table(false, false, boardField, isBlackOnTurn);
                        customBoardFrame.dispose();
                    } else {
                        kingInCheck();
                    }
                } else {
                    missingKings();
                }
            }
        });
        playButton.setVisible(true);
        return playButton;
    }
    
    private JButton createBlackIsOnMoveButton() {
        JButton blackIsOnMoveButton = new JButton("Black is on turn");
        blackIsOnMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBlackOnTurn = true;
            }
        });
        blackIsOnMoveButton.setVisible(true);
        return blackIsOnMoveButton;
    }
    
    private JButton createWhiteIsOnMoveButton() {
        JButton blackIsOnMoveButton = new JButton("White is on turn");
        blackIsOnMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBlackOnTurn = false;
            }
        });
        blackIsOnMoveButton.setVisible(true);
        return blackIsOnMoveButton;
    }
    
    private void setPieceButtonPanel(JPanel piecesButtonPanel) {
        JButton whiteKingButton = createPieceButton("k", false);
        JButton blackKingButton = createPieceButton("k", true);
        JButton whiteQueenButton = createPieceButton("q", false);
        JButton blackQueenButton = createPieceButton("q", true);
        JButton whiteRookButton = createPieceButton("r", false);
        JButton blackRookButton = createPieceButton("r", true);
        JButton whiteBishopButton = createPieceButton("b", false);
        JButton blackBishopButton = createPieceButton("b", true);
        JButton whiteKnightButton = createPieceButton("n", false);
        JButton blackKnightButton = createPieceButton("n", true);
        JButton whitePawnButton = createPieceButton("p", false);
        JButton blackPawnButton = createPieceButton("p", true);
        piecesButtonPanel.add(whiteKingButton);
        piecesButtonPanel.add(blackKingButton);
        piecesButtonPanel.add(whiteQueenButton);
        piecesButtonPanel.add(blackQueenButton);
        piecesButtonPanel.add(whiteRookButton);
        piecesButtonPanel.add(blackRookButton);
        piecesButtonPanel.add(whiteBishopButton);
        piecesButtonPanel.add(blackBishopButton);
        piecesButtonPanel.add(whiteKnightButton);
        piecesButtonPanel.add(blackKnightButton);
        piecesButtonPanel.add(whitePawnButton);
        piecesButtonPanel.add(blackPawnButton);
    }
    
    private JButton createPieceButton(String pieceType, boolean isPieceBlack) {
        JButton pieceButton;
        if (isPieceBlack) {
            pieceButton = new JButton(new ImageIcon("src/main/resources/" + pieceType + ".png"));
        } else {
            pieceButton = new JButton(new ImageIcon("src/main/resources/w" + pieceType + ".png"));
        }
        pieceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPieceBlack) {
                    isChosenPieceBlack = true;
                } else {
                    isChosenPieceBlack = false;
                }
                chosenPieceType = pieceType;
            }
        });
        pieceButton.setVisible(true);
        return pieceButton;
    }
    
    private List<Pieces> countActivePieces(boolean isBlack) {
        List<Pieces> activePieces = new ArrayList<>();
        
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(this.boardField[row][column].isTileEmpty())){
                    Pieces pieceOnTile = this.boardField[row][column].getPiece();
                    if (pieceOnTile.isPieceBlack() == isBlack) {
                        activePieces.add(pieceOnTile);
                    } 
                }    
            }
        }
        
        return activePieces;
    }

    private List<Move> getAllPossibleMoves(Collection<Pieces> activePieces) {
        List<Move> allPossibleMoves = new ArrayList<>();
        for (Pieces piece : activePieces) {
            if(piece.getPieceType().equals("k")) {
                King king = (King) piece;
                if (king.isPieceBlack()) {
                    allPossibleMoves.addAll(king.getPossibleMoves(this.boardField, null));
                } else {
                    allPossibleMoves.addAll(king.getPossibleMoves(this.boardField, null));
                }
            } else {
                allPossibleMoves.addAll(piece.getPossibleMoves(this.boardField, null));
            }
        }
        return allPossibleMoves;
    }
    
    private boolean isKingInCheck() {
        for (Move move : getAllPossibleMoves(countActivePieces(isBlackOnTurn))) {
            if (move.isAttackingMove() &&
                move.getAttackedPiece().getPieceType().equals("k")) {
                return true;
            }
        }
        return false;
    }
    
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
        
        public BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int row = 7; row >= 0; row--) {
                for (int column = 0; column < 8; ++column) {
                    TilePanel tilePanel = new TilePanel(this, row, column);
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(new Dimension(400, 400));
        }
        
        private void drawBoard(Tile[][] boardField) {
            removeAll();
            for (TilePanel boardTile : boardTiles) {
                boardTile.drawTile(boardField);
                add(boardTile);
            }
            validate();
            repaint();
        }
    }
    
    private class TilePanel extends JPanel {
        private final int row;
        private final int column;
         
        public TilePanel(BoardPanel boardPanel, int row, int column) {
            super(new BorderLayout());
            this.row = row;
            this.column = column;
            setPreferredSize(new Dimension(15, 15));
            
            assignTileColor();
            assignTilePieceIcon(boardField);
             
            addMouseListener(new MouseListener() {
                /**
                 * this method changes statement of the tile, with right click
                 * it erases the piece on tile, with left click it  creates selected
                 * piece
                 */
                @Override
                public void mouseClicked(MouseEvent event) {
                    if(isRightMouseButton(event)) {
                        boardField[row][column].changeTileStatement(true, null);
                    } else if(isLeftMouseButton(event)){
                        if (chosenPieceType == "p") {
                            Pawn pawn;
                            if (isChosenPieceBlack) {
                                pawn = new Pawn(true, row, column);
                            } else {
                                pawn = new Pawn(false, row, column);
                            }
                            if (row == 0 || row == 7) {
                                wrongPiecePosition();
                            } else {
                                boardField[row][column].changeTileStatement(false, pawn);
                            }
                        } else if (chosenPieceType == "n") {
                            Knight knight;
                            if (isChosenPieceBlack) {
                                knight = new Knight(true, row, column);
                            } else {
                                knight = new Knight(false, row, column);
                            }
                            boardField[row][column].changeTileStatement(false, knight);
                        } else if (chosenPieceType == "b") {
                            Bishop bishop;
                            if (isChosenPieceBlack) {
                                bishop = new Bishop(true, row, column);
                            } else {
                                bishop = new Bishop(false, row, column);
                            }
                            boardField[row][column].changeTileStatement(false, bishop);
                        } else if (chosenPieceType == "r") {
                            Rook rook;
                            if (isChosenPieceBlack) {
                                rook = new Rook(true, row, column);
                                if (row != 7 || (column != 0 && column != 7)) {
                                    rook.setStatusToHasMoved();
                                }
                            } else {
                                rook = new Rook(false, row, column);
                                if (row != 0 || (column != 0 && column != 7)) {
                                    rook.setStatusToHasMoved();
                                }
                            }
                            boardField[row][column].changeTileStatement(false, rook);
                        } else if (chosenPieceType == "q") {
                            Queen queen;
                            if (isChosenPieceBlack) {
                                queen = new Queen(true, row, column);
                            } else {
                                queen = new Queen(false, row, column);
                            }
                            boardField[row][column].changeTileStatement(false, queen);
                        } else if (chosenPieceType == "k") {
                            King king;
                            if (isChosenPieceBlack) {
                                king = new King(true, row, column);
                                if (row != 7 || column != 4) {
                                    king.setStatusToHasMoved();
                                }
                            } else {
                                king = new King(false, row, column);
                                if (row != 0 || column != 4) {
                                    king.setStatusToHasMoved();
                                }
                            }
                            if (isKingAlreadyInTheGame(isChosenPieceBlack)) {
                                tooManyKings();
                            } else {
                                boardField[row][column].changeTileStatement(false, king);
                            }
                        }
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(boardField);
                        }
                    });
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }

                private boolean isRightMouseButton(MouseEvent event) {
                    return ((event.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0 || event.getButton() == MouseEvent.BUTTON3);
                }

                private boolean isLeftMouseButton(MouseEvent event) {
                    return ((event.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0 || event.getButton() == MouseEvent.BUTTON1);
                }
            });
             
            validate();
        }
        
        private void drawTile(Tile[][] boardField){
            assignTileColor();
            assignTilePieceIcon(boardField);
            validate();
            repaint();
        }
         
        private void assignTilePieceIcon(final Tile[][] boardField) {
            this.removeAll();
            if (!boardField[row][column].isTileEmpty()) {
                Pieces piece = boardField[row][column].getPiece();
                String pieceImageName = piece.getPieceType();
                if (!piece.isPieceBlack()) {
                    pieceImageName = "w" + pieceImageName;
                }
                add(new JLabel(new ImageIcon("src/main/resources/" + pieceImageName + ".png")), BorderLayout.CENTER);
            }
        }
         
        private void assignTileColor(){
            if ((this.row + this.column) % 2 == 1){
                setBackground(lightBrown);
            } else {
                setBackground(darkBrown);
            }
        }
        
        private void wrongPiecePosition() {
            String title = "Invalid Position";
            String message = "You can't place this type of piece on this tile.";
            JOptionPane.showConfirmDialog(null, message, title, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null);
        }
        
        private void tooManyKings() {
            String title = "Too many Kings";
            String message = "You can't place more than one king of each color in game.";
            JOptionPane.showConfirmDialog(null, message, title, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
