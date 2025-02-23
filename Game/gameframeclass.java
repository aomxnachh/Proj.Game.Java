package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class gameframeclass implements ActionListener, ComponentListener {
    int Xlocation = 458;
    int Ylocation = 130;
    
    // System
    int arjHP = 100;
    int witchayaHP = 100;
    int damageAmount = 20;

    String userinput;
    String Cominput;
    String winner;
    
    JFrame frame;
    JPanel mainPanel;
    JPanel controlPanel;
    JPanel battlePanel;
    JPanel resultPanel;
    JPanel imagePanel;
    
    JProgressBar arjHPBar;
    JProgressBar witchayaHPBar;
    
    JLabel arjLabel;
    JLabel witchayaLabel;
    JLabel resultLabel;
    JLabel battleLabel;
    JLabel arjMoveLabel;
    JLabel witchayaMoveLabel;
    
    JButton rockButton;
    JButton paperButton;
    JButton scissorsButton;
    
    public void gameframe() {
        frame = new JFrame("SuntaWit Showdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocation(Xlocation, Ylocation);
        frame.setResizable(false);
        
        // Main Panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // HP Panel
        JPanel hpPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        arjLabel = new JLabel("Arj.Suntana HP:");
        witchayaLabel = new JLabel("Dr.Witchaya HP:");
        
        arjHPBar = createHPBar(Color.GREEN);
        witchayaHPBar = createHPBar(Color.RED);
        
        hpPanel.add(arjLabel);
        hpPanel.add(arjHPBar);
        hpPanel.add(witchayaLabel);
        hpPanel.add(witchayaHPBar);
        
        // Image Panel
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(500, 200));
        
        // Image Sub Panel
        JPanel leftImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel rightImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        arjMoveLabel = new JLabel();
        witchayaMoveLabel = new JLabel();
        
        // Size
        arjMoveLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        witchayaMoveLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        
        leftImagePanel.add(arjMoveLabel);
        rightImagePanel.add(witchayaMoveLabel);
        
        imagePanel.add(leftImagePanel, BorderLayout.WEST);
        imagePanel.add(rightImagePanel, BorderLayout.EAST);
        
        // Battle Display
        battlePanel = new JPanel();
        battleLabel = new JLabel("Choose your move!");
        battleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        battlePanel.add(battleLabel);
        
        // Control Panel
        controlPanel = new JPanel();
        rockButton = createGameButton("Rock");
        paperButton = createGameButton("Paper");
        scissorsButton = createGameButton("Scissors");
        
        controlPanel.add(rockButton);
        controlPanel.add(paperButton);
        controlPanel.add(scissorsButton);
        resultPanel = new JPanel();
        resultLabel = new JLabel("Battle Start!");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultPanel.add(resultLabel);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imagePanel, BorderLayout.CENTER);
        centerPanel.add(battlePanel, BorderLayout.SOUTH);
        
        mainPanel.add(hpPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(resultPanel, BorderLayout.EAST);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.addComponentListener(this);
    }
    
    private final String IMAGE_PATH = "src/GuiRockPaperScissors/images/";
    private final int IMAGE_WIDTH = 150;
    private final int IMAGE_HEIGHT = 150;
    
    //image part
    private ImageIcon loadAndResizeImage(String filename) {
        try {
            File file = new File(IMAGE_PATH + filename);
            if (!file.exists()) {
                System.out.println("Image file not found: " + file.getAbsolutePath());
                return null;
            }
            
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (Exception e) {
            System.out.println("Error loading image " + filename + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    private void updateMoveImages(String playerMove, String computerMove) {
        // Arj.Suntana Image
        String arjImage = switch(playerMove) {
            case "Rock" -> "rock";
            case "Paper" -> "paper";
            case "Scissors" -> "scissors";
            default -> null;
        };
        
        // Dr.Witchaya image
        String witchayaImage = switch(computerMove) {
            case "Rock" -> "rock2";
            case "Paper" -> "paper2";
            case "Scissors" -> "scissors2";
            default -> null;
        };
        
        if (arjImage != null) {
            ImageIcon arjIcon = loadAndResizeImage(arjImage);
            if (arjIcon != null) {
                arjMoveLabel.setIcon(arjIcon);
            }
        }
        
        if (witchayaImage != null) {
            ImageIcon witchayaIcon = loadAndResizeImage(witchayaImage);
            if (witchayaIcon != null) {
                witchayaMoveLabel.setIcon(witchayaIcon);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            String choice = clickedButton.getText();
            
            if (choice.equals("Rock") || choice.equals("Paper") || choice.equals("Scissors")) {
                userinput = choice;
                String computerChoice = generateComputerChoice();
                updateMoveImages(userinput, computerChoice);
                String result = determineWinner(userinput, computerChoice);
                
                if (result.equals("Arj.Suntana")) {
                    witchayaHP -= damageAmount;
                    witchayaHPBar.setValue(witchayaHP);
                    resultLabel.setText("Arj.Suntana wins this round!");
                } else if (result.equals("Dr.Witchaya")) {
                    arjHP -= damageAmount;
                    arjHPBar.setValue(arjHP);
                    resultLabel.setText("Dr.Witchaya wins this round!");
                } else {
                    resultLabel.setText("It's a draw!");
                }
                
                battleLabel.setText(userinput + " vs " + computerChoice);
                
                if (arjHP <= 0 || witchayaHP <= 0) {
                    String winner = (arjHP <= 0) ? "Dr.Witchaya" : "Arj.Suntana";
                    showGameOver(winner + " WINS!");
                }
            }
        }
    }
    
    private JProgressBar createHPBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setStringPainted(true);
        bar.setForeground(color);
        return bar;
    }
    
    private JButton createGameButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 40));
        button.addActionListener(this);
        return button;
    }
    
    private String generateComputerChoice() {
        String[] choices = {"Rock", "Paper", "Scissors"};
        return choices[new Random().nextInt(choices.length)];
    }
    
    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) return "Draw";
        
        if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
            (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
            (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            return "Arj.Suntana";
        }
        
        return "Dr.Witchaya";
    }
    
    private void showGameOver(String winner) {
        JOptionPane.showMessageDialog(frame, winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        int choice = JOptionPane.showConfirmDialog(frame, "Would you like to play again?", "Play Again?", 
                                                 JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }
    
    private void resetGame() {
        arjHP = 100;
        witchayaHP = 100;
        arjHPBar.setValue(arjHP);
        witchayaHPBar.setValue(witchayaHP);
        battleLabel.setText("Choose your move!");
        resultLabel.setText("Battle Start!");
        arjMoveLabel.setIcon(null);
        witchayaMoveLabel.setIcon(null);
    }
    
    @Override
    public void componentResized(ComponentEvent e) {}
    
    @Override
    public void componentMoved(ComponentEvent e) {
        Xlocation = frame.getX();
        Ylocation = frame.getY();
        frame.setLocation(Xlocation, Ylocation);
    }
    
    @Override
    public void componentShown(ComponentEvent e) {}
    
    @Override
    public void componentHidden(ComponentEvent e) {}
}