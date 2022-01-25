
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
 * this class shows menu and let you choose if you want to play as a White or Black
 * against AI
 * @author vitnademlejnsky
 */
public class ChooseColorMenu {
    private final JFrame chooseColorMenu;
    private final JLabel background = new JLabel();
    private JButton whiteButton;
    private JButton blackButton;
    private JButton backButton;
    
    
    public ChooseColorMenu() {
        this.chooseColorMenu = new JFrame("ChooseColorMenu");
        background.setIcon(new ImageIcon("src/main/resources/bg.jpg"));
        background.setSize(new Dimension(1125, 800));
        this.chooseColorMenu.getContentPane().add(background);
        this.chooseColorMenu.setLayout(new BorderLayout());
        setWhiteButton(background);
        setBlackButton(background);
        setBackButton(background);
        this.chooseColorMenu.setPreferredSize(new Dimension(800, 800));
        this.chooseColorMenu.pack();
        this.chooseColorMenu.setVisible(true);
        this.chooseColorMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    private void setWhiteButton(JLabel buttonPanel) {
        whiteButton = new JButton();
        whiteButton.setText("White");
        whiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Table(true, false, null, false);
                chooseColorMenu.dispose();
            }
        });
        whiteButton.setLocation(320, 160);
        whiteButton.setSize(160, 80);
        whiteButton.setVisible(true);
        buttonPanel.add(whiteButton);
    }
    
    private void setBlackButton(JLabel buttonPanel) {
        blackButton = new JButton();
        blackButton.setText("Black");
        blackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Table(true, true, null, false);
                chooseColorMenu.dispose();
            }
        });
        blackButton.setLocation(320, 320);
        blackButton.setSize(160, 80);
        blackButton.setVisible(true);
        buttonPanel.add(blackButton);
    }

    private void setBackButton(JLabel buttonPanel) {
        backButton = new JButton();
        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Play();
                chooseColorMenu.dispose();
            }
        });
        backButton.setLocation(320, 480);
        backButton.setSize(160, 80);
        backButton.setVisible(true);
        buttonPanel.add(backButton);
    }
}
