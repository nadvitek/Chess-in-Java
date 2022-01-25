
package cz.cvut.fel.pjv.view;
import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.LoadGameTxt;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.Pieces;
import cz.cvut.fel.pjv.model.SaveGameTxt;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
* this class creates frame where you can see chess and control your pieces,
* you can also surrender and offer a tie and choose all different options
* as saving or loading game or returning to the main menu or exiting
*/
public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final JPanel buttonPanel;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Pieces humanMovedPiece;
    private List<Move> movesForPiece = null;
    private final boolean isAIOn; 
    private final boolean HumanIsBlack;
    private boolean isStart = true;
    private boolean whiteWinner = false;
    private boolean blackWinner = false;
    private boolean isTie = false;
    private final Color lightBrown = Color.decode("#FFFACD");
    private final Color darkBrown = Color.decode("#593E1A");
    private final Color red = Color.decode("#FF3333");
    
    public Table(boolean isAIOn, boolean PlayerIsBlack, Tile[][] customBoardField, boolean isBlackOnTurn) {
        this.isAIOn = isAIOn;
        this.HumanIsBlack = PlayerIsBlack;
        this.gameFrame = new JFrame("Chessgame");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar, this.isAIOn, this.HumanIsBlack, this.gameFrame);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(new Dimension(900, 800));
        this.chessBoard = new Board();
        if (customBoardField != null) {
            chessBoard = chessBoard.setCustomBoard(customBoardField, isBlackOnTurn);
        }
        if (chessBoard.getCurrentPlayer().isPlayerInMate()){
            if (chessBoard.getCurrentPlayer().isPlayerBlackPlayer()){
                whiteWinner = true;
            } else {
                blackWinner = true;
            }
        } else if (chessBoard.getCurrentPlayer().isPlayerInStalemate()) {
            isTie = true;
        }
        this.boardPanel = new BoardPanel();
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BorderLayout());
        createSurrenderButton(buttonPanel, chessBoard);
        createOfferTieButton(buttonPanel, chessBoard);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.buttonPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void populateMenuBar(final JMenuBar tableMenuBar, boolean isAIOn, boolean isHumanBlack, JFrame table){
        tableMenuBar.add(createFileMenu(isAIOn, isHumanBlack, table));
    }
    
    private JMenu createFileMenu(boolean isAIOn, boolean isHumanBlack, JFrame table) {
        final JMenu fileMenu = new JMenu("File");
        
        final JMenuItem toMainMenu = new JMenuItem("Return to MainMenu");
        toMainMenu.addActionListener(new ActionListener() {
            /**
             * this button returns you to the main menu
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                table.dispose();
            }
        });
        
        fileMenu.add(toMainMenu);
        
        final JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener(){
            /**
             * this button saves your game on selected slot
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int slotOption = chooseSaveSlot();
                try {
                    new SaveGameTxt(chessBoard.getBoardField(), chessBoard.isIsBlackOnTurn(), slotOption, isAIOn, isHumanBlack);
                } catch (IOException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileMenu.add(saveGame);
        
        final JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(new ActionListener(){
            /**
             * this button loads a game from selected slot
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int slotOption = chooseLoadSlot();
                LoadGameTxt loadGame = new LoadGameTxt(slotOption);
                new Table(loadGame.isAIOn(), loadGame.isHumanBlackPlayer(), loadGame.getBoardField(), loadGame.isBlackOnTurn());
                table.dispose();
            }
        });
        fileMenu.add(loadGame);
        
        final JMenuItem restartGame = new JMenuItem("Restart Game");
        restartGame.addActionListener(new ActionListener() {
            /**
             * this button restarts the game
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new Table(isAIOn, isHumanBlack, null, false);
                table.dispose();
            }
        });
        
        fileMenu.add(restartGame);
        
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            /**
             * this button exits the game
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    
    private int chooseSaveSlot() {
            String title = "Choose Save Slot";
            String message = "Which Save Slot do you choose?";
            ImageIcon icon = new ImageIcon("src/main/resources/save.png");
            Object[] options = {"Save Slot 3", "Save Slot 2", "Save Slot 1"};
            int optionButtons = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
            return optionButtons;
        }
    
    private int chooseLoadSlot() {
            String title = "Choose Load Slot";
            String message = "Which Load Slot do you choose?";
            ImageIcon icon = new ImageIcon("src/main/resources/load.png");
            Object[] options = {"Load Slot 3", "Load Slot 2", "Load Slot 1"};
            int optionButtons = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
            return optionButtons;
        }
    
    private int showResultPanel(boolean whiteWinner, boolean blackWinner, boolean isTie) {
        String message = null;
        ImageIcon icon = null;
        String title = null;
        if (whiteWinner) {
            message = "The white player has won!\nDo you want to watch the board or exit?";
            title = "White wins!";
            icon = new ImageIcon("src/main/resources/wwinner.png");
        } else if (blackWinner) {
            message = "The black player has won!\nDo you want watch the board or exit?";
            title = "Black wins!";
            icon = new ImageIcon("src/main/resources/bwinner.png");
        } else if (isTie) {
            message = "It's a Tie!\nDo you want to watch the board or exit?";
            title = "Tie!";
            icon = new ImageIcon("src/main/resources/tie.jpg");
        }
        Object[] options = {"Exit", "Watch the board"};
        int optionButtons = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        return optionButtons;
        }
    
    private int showOfferATiePanel() {
        String message = null;
        ImageIcon icon = new ImageIcon("src/main/resources/tie.jpg");
        String title = null;
        if (this.chessBoard.getCurrentPlayer().isPlayerBlackPlayer()) {
            message = "The black player has offered a tie!\nDo you accept?";
            title = "Black player offers a tie";
        } else {
            message = "The white player has offered a tie!\nDo you accept?";
            title = "White player offers a tie";
        }
        Object[] options = {"No", "Yes"};
        int optionButtons = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        return optionButtons;
    }
    
    private void createSurrenderButton(JPanel buttonPanel, Board chessBoard){
        JButton surrenderButton = new JButton();
        surrenderButton.setText("Surrender");
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chessBoard.getCurrentPlayer().isPlayerBlackPlayer()) {
                    whiteWinner = true;
                } else {
                    blackWinner = true;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(chessBoard, movesForPiece);
                        if (whiteWinner || blackWinner || isTie) {
                            int nextStep = showResultPanel(whiteWinner, blackWinner, isTie);
                            if (nextStep == JOptionPane.YES_OPTION){
                                System.exit(0);
                            }
                        }
                    }
                });
            }
        });
        surrenderButton.setVisible(true);
        buttonPanel.add(surrenderButton, BorderLayout.NORTH);
    }
        
    private void createOfferTieButton(JPanel buttonPanel, Board chessBoard){
        JButton offerTieButton = new JButton();
        offerTieButton.setText("Offer a tie");
        offerTieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isAIOn && chessBoard.getWaitingPlayer().getMyPieces().size() <= chessBoard.getCurrentPlayer().getMyPieces().size()) {
                    isTie = true;
                } else if (!isAIOn){
                    int value = showOfferATiePanel();
                    if (value == 1) {
                        isTie = true;
                    }
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        boardPanel.drawBoard(chessBoard, movesForPiece);
                        if (whiteWinner || blackWinner || isTie) {
                            int nextStep = showResultPanel(whiteWinner, blackWinner, isTie);
                            if (nextStep == JOptionPane.YES_OPTION){
                                System.exit(0);
                            }
                        }
                    }
                });
            }
        });
        offerTieButton.setVisible(true);
        buttonPanel.add(offerTieButton, BorderLayout.SOUTH);
    }
    
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
        
        public BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            if (isAIOn && HumanIsBlack) {
                if (isStart) {
                    List<Move> botMoves = chessBoard.getCurrentPlayer().getMyPossibleMoves();
                    int moveListSize = botMoves.size();
                    Random rand = new Random();
                    int moveIndex = rand.nextInt(moveListSize);
                    chessBoard = chessBoard.updateBoard(botMoves.get(moveIndex));
                }
                isStart = false;
                for (int row = 0; row < 8; row++) {
                    for (int column = 7; column >= 0; column--) {
                        TilePanel tilePanel = new TilePanel(this, row, column);
                        this.boardTiles.add(tilePanel);
                        add(tilePanel);
                    }
                }
            } else {
                for (int row = 7; row >= 0; row--) {
                    for (int column = 0; column < 8; ++column) {
                        TilePanel tilePanel = new TilePanel(this, row, column);
                        this.boardTiles.add(tilePanel);
                        add(tilePanel);
                    }
                }
            }
            setPreferredSize(new Dimension(400, 400));
        }
        
        private void drawBoard(Board board, List<Move> piecePossibleMoves) {
            removeAll();
            for (TilePanel boardTile : boardTiles) {
                boardTile.drawTile(board, piecePossibleMoves);
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
            assignTilePieceIcon(chessBoard);
             
            addMouseListener(new MouseListener() {
                /**
                 * if you click on tile with left button and you didnt have 
                 * selected any piece yet, it will show you possible moves of selected piece
                 * and then you can click on one of the moves location and execute the move
                 * if you click with the right button, you cancel all actions
                 * after every your click the board is being repainted
                 */
                @Override
                public void mouseClicked(MouseEvent event) {
                    if (!whiteWinner && !blackWinner && !isTie) {
                        if(isRightMouseButton(event)) {
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                            movesForPiece = null;
                        } else if(isLeftMouseButton(event)){
                            if (sourceTile == null) {
                                sourceTile = chessBoard.getBoardField()[row][column];
                                humanMovedPiece = sourceTile.getPiece();
                                if (humanMovedPiece == null) {
                                    sourceTile = null;
                                } else {
                                    movesForPiece = getPossibleMovesForPiece(row, column, chessBoard);
                                }
                            } else {
                                destinationTile = chessBoard.getBoardField()[row][column];
                                Move move = getPossibleMove(sourceTile, destinationTile, chessBoard);
                                if (move != null) {
                                    chessBoard = chessBoard.updateBoard(move);
                                    if (move.isPawnPromotionMove()) {
                                        int choice = choosePromotedPiece();
                                        chessBoard = chessBoard.executePromotionMove(choice, move);
                                    }
                                    if (chessBoard.getCurrentPlayer().isPlayerInMate()){
                                        if (chessBoard.getCurrentPlayer().isPlayerBlackPlayer()){
                                            whiteWinner = true;
                                        } else {
                                            blackWinner = true;
                                        }
                                    } else if (chessBoard.getCurrentPlayer().isPlayerInStalemate()) {
                                        isTie = true;
                                    }
                                }
                                sourceTile = null;
                                destinationTile = null;
                                humanMovedPiece = null;
                                movesForPiece = null;
                                if (isAIOn && chessBoard.isIsBlackOnTurn() != HumanIsBlack) {
                                    if (!whiteWinner && !blackWinner && !isTie) {
                                       List<Move> botMoves = chessBoard.getCurrentPlayer().getMyPossibleMoves();
                                        int moveListSize = botMoves.size();
                                        Random rand = new Random();
                                        int moveIndex = rand.nextInt(moveListSize);
                                        Move botMove = botMoves.get(moveIndex);
                                        chessBoard = chessBoard.updateBoard(botMove);
                                        if (botMove.isPawnPromotionMove()) {
                                            chessBoard = chessBoard.executePromotionMove(3, botMove);
                                        }
                                        if (chessBoard.getCurrentPlayer().isPlayerInMate()){
                                            if (chessBoard.getCurrentPlayer().isPlayerBlackPlayer()) {
                                                whiteWinner = true;
                                            } else {
                                                blackWinner = true;
                                            }     
                                        }
                                    }
                                }
                            }
                        }
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(chessBoard, movesForPiece);
                            if (whiteWinner || blackWinner || isTie) {
                                int nextStep = showResultPanel(whiteWinner, blackWinner, isTie);
                                if (nextStep == JOptionPane.YES_OPTION){
                                    System.exit(0);
                                }
                            }
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

                private List<Move> getPossibleMovesForPiece(int row, int column, Board chessBoard) {
                    List<Move> possibleMoves = new ArrayList<>();
                    List<Move> allMoves = chessBoard.getCurrentPlayer().getMyPossibleMoves();
                    for (Move move : allMoves) {
                        if (move.getMovedPiece().getRow() == row && move.getMovedPiece().getColumn() == column) {
                            possibleMoves.add(move);
                        }
                    }
                    return possibleMoves;
                }

                private Move getPossibleMove(Tile sourceTile, Tile destinationTile, Board chessBoard) {
                    Move myMove = null;
                    List<Move> allPossibleMoves = chessBoard.getCurrentPlayer().getMyPossibleMoves();
                    for (Move move : allPossibleMoves) {
                        if (move.getMovedPiece().getRow() == sourceTile.getRow() && 
                            move.getMovedPiece().getColumn() == sourceTile.getColumn() &&
                            move.getRow() == destinationTile.getRow() &&
                            move.getColumn() == destinationTile.getColumn()) {
                            myMove = move;
                        }
                    }
                    return myMove;
                }
            });
             
            validate();
        }
        
        private void drawTile(Board board, List<Move> piecePossibleMoves){
            assignTileColor();
            assignTilePieceIcon(board);
            if (piecePossibleMoves != null) {
                assignTilePossibleMove(piecePossibleMoves);
            }
            validate();
            repaint();
        }
         
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (!chessBoard.getBoardField()[row][column].isTileEmpty()) {
                Pieces piece = chessBoard.getBoardField()[row][column].getPiece();
                String pieceImageName = piece.getPieceType();
                if (!piece.isPieceBlack()) {
                    pieceImageName = "w" + pieceImageName;
                }
                add(new JLabel(new ImageIcon("src/main/resources/" + pieceImageName + ".png")), BorderLayout.CENTER);
            }
        }
        
        private void assignTilePossibleMove(List<Move> piecePossibleMoves){
            for (Move piecePossibleMove : piecePossibleMoves) {
                if (piecePossibleMove.getColumn() == this.column &&
                    piecePossibleMove.getRow() == this.row) {
                    if (piecePossibleMove.isAttackingMove() && !piecePossibleMove.isMoveCastleMove()) {
                        setBackground(red);
                    } else {
                        add(new JLabel(new ImageIcon("src/main/resources/move.png")), BorderLayout.CENTER);
                    }
                }
            }
        }
         
        private void assignTileColor(){
            if ((this.row + this.column) % 2 == 1){
                setBackground(lightBrown);
            } else {
                setBackground(darkBrown);
            }
        }
        
        private int choosePromotedPiece() {
            String title = "Promote Pawn";
            String message = "Choose Piece you want to promote to.";
            ImageIcon icon = new ImageIcon("src/main/resources/promotion.png");
            Object[] options = {"Knight", "Bishop", "Rook", "Queen"};
            int optionButtons = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
            return optionButtons;
        }
    }
}
