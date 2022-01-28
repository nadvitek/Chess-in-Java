
package cz.cvut.fel.pjv.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * this class creates the main menu with buttons
 * @author vitnademlejnsky
 */
public class MainMenu {
    private final JFrame mainMenu;
    private final JLabel background;
    private JButton playButton;
    private JButton rulesButton;
    private JButton exitButton;
    
    
    public MainMenu() {
        this.mainMenu = new JFrame("MainMenu");
        this.background = new JLabel();
        background.setIcon(new ImageIcon("src/main/resources/bg.jpg"));
        background.setSize(new Dimension(1125, 800));
        this.mainMenu.getContentPane().add(background);
        this.mainMenu.setLayout(new BorderLayout());
        setPlayButton(background);
        setRulesButton(background);
        setExitButton(background);
        this.mainMenu.setPreferredSize(new Dimension(800, 800));
        this.mainMenu.pack();
        this.mainMenu.setVisible(true);
        this.mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    private void setPlayButton(JLabel buttonPanel) {
        playButton = new JButton();
        playButton.setText("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Play();
                mainMenu.dispose();
            }
        });
        playButton.setLocation(320, 160);
        playButton.setSize(160, 80);
        playButton.setVisible(true);
        buttonPanel.add(playButton);
    }
    
    private void setRulesButton(JLabel buttonPanel) {
        rulesButton = new JButton();
        rulesButton.setText("Documentation");
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebpage("https://github.com/nadvitek/Chess-in-Java/wiki/Chess");
            }
        });
        rulesButton.setLocation(320, 320);
        rulesButton.setSize(160, 80);
        rulesButton.setVisible(true);
        buttonPanel.add(rulesButton);
    }

    private void setExitButton(JLabel buttonPanel) {
        exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.dispose();
                System.exit(0);
            }
        });
        exitButton.setLocation(320, 480);
        exitButton.setSize(160, 80);
        exitButton.setVisible(true);
        buttonPanel.add(exitButton);
    }
    
    private static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
