package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;

public class Gameframeclass implements ActionListener, ComponentListener {
    int Xlocation = 458;
    int Ylocation = 130;

    // System
    int arjHP = 100;
    int pitchayahp = 100;
    int damageAmount = 20;

    String userinput;
    String Cominput;
    String winner;

    JFrame frame;
    JPanel mainPanel;
    JPanel controlPanel;
    JPanel battlePanel;
    JPanel imagePanel;

    JProgressBar arjHPBar;
    JProgressBar pitchayaHPbar;

    JLabel arjLabel;
    JLabel pitchayalabel;
    JLabel battleLabel;
    JLabel arjMoveLabel;
    JLabel witchayaMoveLabel;
    JLabel vsLabel;
    JLabel winnerLabel;

    JButton rockButton;
    JButton paperButton;
    JButton scissorsButton;

    // Color
    private static final Color BACKGROUND_COLOR = new Color(10, 15, 30);
    private static final Color PANEL_COLOR = new Color(20, 25, 40, 200);
    private static final Color ACCENT_COLOR = new Color(0, 255, 170);
    private static final Color SECONDARY_ACCENT = new Color(255, 0, 128);
    private static final Color TEXT_COLOR = new Color(220, 220, 255);
    private static final Color BUTTON_COLOR = new Color(30, 40, 80);
    private static final Color BUTTON_HOVER = new Color(50, 60, 120);
    private static final Color BUTTON_TEXT_COLOR = new Color(0, 255, 170);

    //Audio
    private Clip backgroundMusic;
    private FloatControl volumeControl;
    private boolean isMusicPlaying = false;

    private void playBackgroundMusic() {
    try {
        //path
        File musicFile = new File("Proj.Game.Java/sounds/background_music.wav");
        
        if (!musicFile.exists()) {
            System.out.println("Music file not found: " + musicFile.getAbsolutePath());
            return;
        }
        
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
        backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(audioStream);
        
        //loop
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        
        if (backgroundMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f);
        }
        
        backgroundMusic.start();
        isMusicPlaying = true;
        
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        System.out.println("Error playing background music: " + e.getMessage());
        e.printStackTrace();
    }
}

// restart music method
private void restartBackgroundMusic() {
    if (backgroundMusic != null) {
        backgroundMusic.stop();
        backgroundMusic.setFramePosition(0);
        backgroundMusic.start();
        isMusicPlaying = true;
    }
}

// dispose music method
private void disposeBackgroundMusic() {
    if (backgroundMusic != null) {
        backgroundMusic.stop();
        backgroundMusic.close();
        isMusicPlaying = false;
    }
}

    // Image dimension
    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 350;
    private static final int SPACING = 20;

    public void gameframe() {
        frame = new JFrame("MuntaPit Showdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800); // Window size
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setResizable(false);

        // Main panel
        mainPanel = new JPanel(new BorderLayout(SPACING, SPACING)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Background
                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR,
                        0, getHeight(), new Color(30, 20, 60));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Grid overlay
                g2d.setColor(new Color(0, 255, 170, 30));
                g2d.setStroke(new BasicStroke(0.5f));

                // Draw horizontal grid lines
                for (int i = 0; i < getHeight(); i += 20) {
                    g2d.drawLine(0, i, getWidth(), i);
                }

                // Draw vertical grid lines
                for (int i = 0; i < getWidth(); i += 20) {
                    g2d.drawLine(i, 0, i, getHeight());
                }

                // Random
                g2d.setColor(new Color(0, 255, 170, 15));
                Random rand = new Random();
                for (int i = 0; i < 10; i++) {
                    int x = rand.nextInt(getWidth());
                    g2d.drawLine(x, 0, x, getHeight());
                }
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));

        // HP panel
        JPanel hpPanel = new JPanel(new GridLayout(2, 2, SPACING, SPACING)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Add border
                g2d.setColor(ACCENT_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        hpPanel.setOpaque(false);

        arjLabel = createLabel("PLAYER: ARJ.MUNTANA", new Font("Monospaced", Font.BOLD, 16));
        pitchayalabel = createLabel("COM: DR.PITCHAYA", new Font("Monospaced", Font.BOLD, 16));

        arjHPBar = createHPbar(ACCENT_COLOR);
        pitchayaHPbar = createHPbar(SECONDARY_ACCENT);

        hpPanel.add(arjLabel);
        hpPanel.add(arjHPBar);
        hpPanel.add(pitchayalabel);
        hpPanel.add(pitchayaHPbar);

        // Image panel
        imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setOpaque(false);

        // Image sub Panels
        JPanel leftImagePanel = createSubPanel();
        JPanel rightImagePanel = createSubPanel();

        arjMoveLabel = new JLabel();
        witchayaMoveLabel = new JLabel();

        // Set fixed size for images
        arjMoveLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        witchayaMoveLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

        leftImagePanel.add(arjMoveLabel, new GridBagConstraints());
        rightImagePanel.add(witchayaMoveLabel, new GridBagConstraints());

        // Create VS
        vsLabel = createLabel("VS", new Font("Monospaced", Font.BOLD, 36));
        vsLabel.setForeground(SECONDARY_ACCENT);

        // Create winner
        winnerLabel = createLabel("BATTLE INITIATED", new Font("Monospaced", Font.BOLD, 20));
        winnerLabel.setForeground(ACCENT_COLOR);

        // Layout for image panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, SPACING);
        imagePanel.add(leftImagePanel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        imagePanel.add(vsLabel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, SPACING, 0, 0);
        imagePanel.add(rightImagePanel, gbc);

        // Battle display
        battlePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Add border
                g2d.setColor(SECONDARY_ACCENT);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        battlePanel.setOpaque(false);

        battleLabel = createLabel("SELECT YOUR MOVES", new Font("Monospaced", Font.BOLD, 24));
        battleLabel.setForeground(ACCENT_COLOR);

        // Add battle label and winner label to battle panel
        GridBagConstraints battleGbc = new GridBagConstraints();
        battleGbc.gridx = 0;
        battleGbc.gridy = 0;
        battleGbc.insets = new Insets(SPACING, 0, SPACING / 2, 0);
        battlePanel.add(battleLabel, battleGbc);

        battleGbc.gridy = 1;
        battleGbc.insets = new Insets(0, 0, SPACING, 0);
        battlePanel.add(winnerLabel, battleGbc);

        // Control Panel
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, SPACING * 2, SPACING)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Add border
                g2d.setColor(ACCENT_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        controlPanel.setOpaque(false);

        rockButton = createButton("ROCK");
        paperButton = createButton("PAPER");
        scissorsButton = createButton("SCISSORS");

        controlPanel.add(rockButton);
        controlPanel.add(paperButton);
        controlPanel.add(scissorsButton);

        // Layout assembly
        JPanel centerPanel = new JPanel(new BorderLayout(SPACING, SPACING));
        centerPanel.setOpaque(false);
        centerPanel.add(imagePanel, BorderLayout.CENTER);
        centerPanel.add(battlePanel, BorderLayout.SOUTH);

        mainPanel.add(hpPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        playBackgroundMusic();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disposeBackgroundMusic();
            }
        });
    }

    private JPanel createSubPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Add border
                g2d.setColor(ACCENT_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                // Add corner accents
                g2d.setColor(SECONDARY_ACCENT);
                g2d.fillRect(0, 0, 20, 5);
                g2d.fillRect(0, 0, 5, 20);
                g2d.fillRect(getWidth() - 20, 0, 20, 5);
                g2d.fillRect(getWidth() - 5, 0, 5, 20);
                g2d.fillRect(0, getHeight() - 5, 20, 5);
                g2d.fillRect(0, getHeight() - 20, 5, 20);
                g2d.fillRect(getWidth() - 20, getHeight() - 5, 20, 5);
                g2d.fillRect(getWidth() - 5, getHeight() - 20, 5, 20);
            }

            // Set size
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(IMAGE_WIDTH + 20, IMAGE_HEIGHT + 20);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private final String IMAGE_PATH = "Proj.Game.Java/images/";

    // Load image and resize
    private ImageIcon loadAndResizeImage(String filename) {
        try {
            File file = new File(IMAGE_PATH + filename + ".png");
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
        // Arj.Muntana(Player) image
        String arjImage = switch (playerMove) {
            case "ROCK" -> "rock";
            case "PAPER" -> "paper";
            case "SCISSORS" -> "scissors";
            default -> null;
        };

        // Dr.Pitchaya(Computer) image
        String pitchayaImage = switch (computerMove) {
            case "ROCK" -> "rock2";
            case "PAPER" -> "paper2";
            case "SCISSORS" -> "scissors2";
            default -> null;
        };

        if (arjImage != null) {
            ImageIcon arjIcon = loadAndResizeImage(arjImage);
            if (arjIcon != null) {
                arjMoveLabel.setIcon(arjIcon);
            }
        }

        if (pitchayaImage != null) {
            ImageIcon witchayaIcon = loadAndResizeImage(pitchayaImage);
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

            if (choice.equals("ROCK") || choice.equals("PAPER") || choice.equals("SCISSORS")) {
                userinput = choice;
                String computerChoice = generateComputerChoice();
                updateMoveImages(userinput, computerChoice);
                String result = determineWinner(userinput, computerChoice);

                if (result.equals("Arj.Muntana")) {
                    pitchayahp -= damageAmount;
                    pitchayaHPbar.setValue(pitchayahp);
                    updateWinnerLabel("ARJ.MUNTANA WINS ROUND");
                } else if (result.equals("Dr.Pitchaya")) {
                    arjHP -= damageAmount;
                    arjHPBar.setValue(arjHP);
                    updateWinnerLabel("DR.PITCHAYA WINS ROUND");
                } else {
                    updateWinnerLabel("COMBAT DRAW");
                }

                battleLabel.setText(userinput + " vs " + computerChoice);

                if (arjHP <= 0 || pitchayahp <= 0) {
                    String winner = (arjHP <= 0) ? "DR.PITCHAYA" : "ARJ.MUNTANA";
                    showGameOver(winner + " WINS!");
                }
            }
        }
    }

    private void updateWinnerLabel(String text) {
        winnerLabel.setText(text);
    }

    private JProgressBar createHPbar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2d.setColor(new Color(10, 10, 20));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Progress
                g2d.setColor(color);
                int width = (int) (getWidth() * ((double) getValue() / getMaximum()));
                g2d.fillRect(0, 0, width, getHeight());

                // Grid lines on progress bar
                g2d.setColor(new Color(255, 255, 255, 50));
                for (int i = 0; i < getWidth(); i += 5) {
                    g2d.drawLine(i, 0, i, getHeight());
                }

                // Draw text
                String text = getValue() + "%";
                g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
                FontMetrics metrics = g2d.getFontMetrics();
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getHeight();
                g2d.setColor(TEXT_COLOR);
                g2d.drawString(text, (getWidth() - textWidth) / 2,
                        (getHeight() + textHeight / 2) / 2);
            }
        };
        bar.setValue(100);
        bar.setStringPainted(false);
        bar.setBorderPainted(false);
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(200, 5));
        return bar;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(BUTTON_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_HOVER);
                } else {
                    g2.setColor(BUTTON_COLOR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Add border
                g2.setColor(ACCENT_COLOR);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);

                // Add accents
                g2.setColor(SECONDARY_ACCENT);
                g2.fillRect(0, 0, 10, 3);
                g2.fillRect(0, 0, 3, 10);
                g2.fillRect(getWidth() - 10, 0, 10, 3);
                g2.fillRect(getWidth() - 3, 0, 3, 10);
                g2.fillRect(0, getHeight() - 3, 10, 3);
                g2.fillRect(0, getHeight() - 10, 3, 10);
                g2.fillRect(getWidth() - 10, getHeight() - 3, 10, 3);
                g2.fillRect(getWidth() - 3, getHeight() - 10, 3, 10);

                g2.dispose();

                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Monospaced", Font.BOLD, 18));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setPreferredSize(new Dimension(150, 50));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(this);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private String generateComputerChoice() {
        String[] choices = { "ROCK", "PAPER", "SCISSORS" };
        return choices[new Random().nextInt(choices.length)];
    }

    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice))
            return "Draw";

        if ((playerChoice.equals("ROCK") && computerChoice.equals("SCISSORS")) ||
                (playerChoice.equals("PAPER") && computerChoice.equals("ROCK")) ||
                (playerChoice.equals("SCISSORS") && computerChoice.equals("PAPER"))) {
            return "Arj.Muntana";
        }

        return "Dr.Pitchaya";
    }

    private void showGameOver(String winner) {
        JDialog dialog = new JDialog(frame, "BATTLE CONCLUSION", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(frame);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Background
                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR,
                        0, getHeight(), new Color(30, 20, 60));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Grid overlay
                g2d.setColor(new Color(0, 255, 170, 30));
                g2d.setStroke(new BasicStroke(0.5f));

                for (int i = 0; i < getHeight(); i += 10) {
                    g2d.drawLine(0, i, getWidth(), i);
                }

                for (int i = 0; i < getWidth(); i += 10) {
                    g2d.drawLine(i, 0, i, getHeight());
                }

                // Add border
                g2d.setColor(ACCENT_COLOR);
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
            }
        };

        JLabel winnerLabel = new JLabel("<html><div style='text-align: center;'>" + winner + "</div></html>");//HTML for help
        winnerLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        winnerLabel.setForeground(ACCENT_COLOR);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton yesButton = createButton("RESTART");
        JButton noButton = createButton("EXIT");

        yesButton.addActionListener(e -> {
            dialog.dispose();
            resetGame();
        });

        noButton.addActionListener(e -> {
            dialog.dispose();
            disposeBackgroundMusic();
            System.exit(0);
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        panel.add(winnerLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    private void resetGame() {
        arjHP = 100;
        pitchayahp = 100;
        arjHPBar.setValue(arjHP);
        pitchayaHPbar.setValue(pitchayahp);
        battleLabel.setText("SELECT YOUR MOVES");
        updateWinnerLabel("BATTLE INITIATED");
        arjMoveLabel.setIcon(null);
        witchayaMoveLabel.setIcon(null);
        restartBackgroundMusic();
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        Xlocation = frame.getX();
        Ylocation = frame.getY();
        frame.setLocation(Xlocation, Ylocation);
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
