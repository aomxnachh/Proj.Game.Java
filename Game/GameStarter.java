package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameStarter implements ActionListener, KeyListener {
    //Color
    private static final Color BACKGROUND_COLOR = new Color(10, 15, 30);
    private static final Color ACCENT_COLOR = new Color(0, 255, 170);
    private static final Color SECONDARY_ACCENT = new Color(255, 0, 128);
    private static final Color BUTTON_COLOR = new Color(30, 40, 80);
    private static final Color BUTTON_HOVER = new Color(50, 60, 120);
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 60;

    JFrame mainFrame;
    JPanel mainPanel;
    JButton startButton;
    JButton helpButton;
    JButton exitButton;
    JLabel titleLabel;
    JLabel versionLabel;

    Gameframeclass mainGame = new Gameframeclass();
    Help helpScreen = new Help();

    public void GameRunner() {
        mainFrame = new JFrame("MUNTAPIT SHOWDOWN");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        // Main panel
        mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        // Title
        titleLabel = new JLabel("MUNTAPIT");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 60));
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titleGlowPanel = createGlowEffect("MUNTAPIT", 60);

        JLabel subtitleLabel = new JLabel("SHOWDOWN");
        subtitleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        subtitleLabel.setForeground(SECONDARY_ACCENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel subtitleGlowPanel = createGlowEffect("SHOWDOWN", 40);

        titlePanel.add(Box.createVerticalStrut(30));
        titlePanel.add(titleGlowPanel);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(subtitleGlowPanel);
        titlePanel.add(Box.createVerticalStrut(50));

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);

        startButton = createButton("START GAME");
        helpButton = createButton("HELP");
        exitButton = createButton("EXIT");

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(startButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonsPanel.add(helpButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonsPanel.add(exitButton);
        buttonsPanel.add(Box.createVerticalGlue());

        // Credits Panel
        versionLabel = new JLabel("Created by Aom,Andy");
        versionLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        versionLabel.setForeground(ACCENT_COLOR);
        versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(versionLabel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.addKeyListener(this);
        mainFrame.setFocusable(true);
    }

    private JPanel createGlowEffect(String text, int fontSize) {
        JPanel glowPanel = new JPanel();
        glowPanel.setLayout(new OverlayLayout(glowPanel));
        glowPanel.setOpaque(false);
        glowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel glow3 = new JLabel(text);
        glow3.setFont(new Font("Monospaced", Font.BOLD, fontSize + 6));
        glow3.setForeground(new Color(0, 255, 170, 30));
        glow3.setAlignmentX(Component.CENTER_ALIGNMENT);
        glow3.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Medium glow
        JLabel glow2 = new JLabel(text);
        glow2.setFont(new Font("Monospaced", Font.BOLD, fontSize + 3));
        glow2.setForeground(new Color(0, 255, 170, 60));
        glow2.setAlignmentX(Component.CENTER_ALIGNMENT);
        glow2.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Small glow
        JLabel glow1 = new JLabel(text);
        glow1.setFont(new Font("Monospaced", Font.BOLD, fontSize + 1));
        glow1.setForeground(new Color(0, 255, 170, 90));
        glow1.setAlignmentX(Component.CENTER_ALIGNMENT);
        glow1.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Main text
        JLabel mainText = new JLabel(text);
        mainText.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        mainText.setForeground(text.equals("SUNTAWIT") ? ACCENT_COLOR : SECONDARY_ACCENT);
        mainText.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainText.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Stack
        glowPanel.add(mainText);
        glowPanel.add(glow1);
        glowPanel.add(glow2);
        glowPanel.add(glow3);

        return glowPanel;
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
        button.setForeground(ACCENT_COLOR);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(this);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        switch (e.getKeyChar()) {
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
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
