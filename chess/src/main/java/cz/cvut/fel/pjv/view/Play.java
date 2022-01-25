 
package cz.cvut.fel.pjv.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * this class creates menu, where you can choose if you want to set up your own board,
 * play vs human or AI or get back to the main menu
 * @author vitnademlejnsky
 */
public class Play {
    private final JFrame playMenu;
    private final JLabel background = new JLabel();
    private JButton pvpButton;
    private JButton customBoardButton;
    private JButton pveButton;
    private JButton backButton;
    
    
    public Play() {
        this.playMenu = new JFrame("PlayMenu");
        background.setIcon(new ImageIcon("src/main/resources/bg.jpg"));
        background.setSize(new Dimension(1125, 800));
        this.playMenu.getContentPane().add(background);
        this.playMenu.setLayout(new BorderLayout());
        setPvPButton(background);
        setPvEButton(background);
        setCustomBoardButton(background);
        setBackButton(background);
        this.playMenu.setPreferredSize(new Dimension(800, 800));
        this.playMenu.pack();
        this.playMenu.setVisible(true);
        this.playMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    private void setPvPButton(JLabel buttonPanel) {
        pvpButton = new JButton();
        pvpButton.setText("Player vs. Player");
        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Table(false, false, null, false);
                playMenu.dispose();
            }
        });
        pvpButton.setLocation(320, 120);
        pvpButton.setSize(160, 80);
        pvpButton.setVisible(true);
        buttonPanel.add(pvpButton);
    }
    
    private void setPvEButton(JLabel buttonPanel) {
        pveButton = new JButton();
        pveButton.setText("Player vs. BOT");
        pveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseColorMenu();
                playMenu.dispose();
            }
        });
        pveButton.setLocation(320, 280);
        pveButton.setSize(160, 80);
        pveButton.setVisible(true);
        buttonPanel.add(pveButton);
    }
    
    private void setCustomBoardButton(JLabel buttonPanel) {
        customBoardButton = new JButton();
        customBoardButton.setText("Create Custom Board");
        customBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomBoard();
                playMenu.dispose();
            }
        });
        customBoardButton.setLocation(320, 440);
        customBoardButton.setSize(160, 80);
        customBoardButton.setVisible(true);
        buttonPanel.add(customBoardButton);
    }

    private void setBackButton(JLabel buttonPanel) {
        backButton = new JButton();
        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                playMenu.dispose();
            }
        });
        backButton.setLocation(320, 600);
        backButton.setSize(160, 80);
        backButton.setVisible(true);
        buttonPanel.add(backButton);
    }
}
