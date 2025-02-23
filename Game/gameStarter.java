package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class gameStarter implements ActionListener, KeyListener {
    JFrame mainFrame;
    JPanel mainPanel;
    JButton startButton;
    JButton helpButton;
    JButton exitButton;
    JLabel titleLabel;
    JLabel versionLabel;
    
    gameframeclass mainGame = new gameframeclass();
    help helpScreen = new help();
    
    public void GameRunner() {
        mainFrame = new JFrame("SuntaWit Showdown");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("SuntaWit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Showdown");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(subtitleLabel);
        
        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        
        startButton = createMenuButton("Start Game");
        helpButton = createMenuButton("Help");
        exitButton = createMenuButton("Exit");
        
        buttonsPanel.add(startButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(helpButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(exitButton);
        
        // Version Label
        versionLabel = new JLabel("V2.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(versionLabel, BorderLayout.SOUTH);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.addKeyListener(this);
        mainFrame.setFocusable(true);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        button.addActionListener(this);
        button.setFocusable(false);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            mainFrame.dispose();
            mainGame.gameframe();
        } else if (e.getSource() == helpButton) {
            helpScreen.helpFrame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 's': 
                mainFrame.dispose();
                mainGame.gameframe();
                break;
            case 'h': 
                helpScreen.helpFrame();
                break;
            case 'e': 
                System.exit(0);
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}