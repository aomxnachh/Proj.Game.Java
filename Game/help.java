package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class help implements ActionListener, KeyListener {
    JFrame helpFrame;
    JPanel mainPanel;
    JButton backButton;
    
    public void helpFrame() {
        helpFrame = new JFrame("Game Help");
        helpFrame.setSize(500, 400);
        helpFrame.setLocationRelativeTo(null);
        helpFrame.setResizable(false);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea helpText = new JTextArea();
        helpText.setText("""
            How to Play:
            
            1. This is a battle between Arj.Suntana and Dr.Witchaya
            2. Each player starts with 100 HP
            3. Choose Rock, Paper, or Scissors when it's your turn
            4. Winning a round deals 20 damage to your opponent
            5. First player to reduce their opponent's HP to 0 wins!
            
            Controls:
            - Click buttons or use keyboard shortcuts:
              's' - Start Game
              'h' - Help
              'e' - Exit
            
            Good luck in battle!
            """);
        
        helpText.setFont(new Font("Arial", Font.PLAIN, 16));
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(helpText);
        
        backButton = new JButton("Back to Game");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(this);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        helpFrame.add(mainPanel);
        helpFrame.setVisible(true);
        helpFrame.addKeyListener(this);
        helpFrame.setFocusable(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            helpFrame.dispose();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'b' || e.getKeyChar() == 'e') {
            helpFrame.dispose();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}