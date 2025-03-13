package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Help implements ActionListener, KeyListener {
    // Color
    private static final Color BACKGROUND_COLOR = new Color(10, 15, 30);
    private static final Color PANEL_COLOR = new Color(20, 25, 40, 200);
    private static final Color ACCENT_COLOR = new Color(0, 255, 170);
    private static final Color SECONDARY_ACCENT = new Color(255, 0, 128);
    private static final Color TEXT_COLOR = new Color(220, 220, 255);
    private static final Color BUTTON_COLOR = new Color(30, 40, 80);
    private static final Color BUTTON_HOVER = new Color(50, 60, 120);

    JFrame helpFrame;
    JPanel mainPanel;
    JButton backButton;

    public void helpFrame() {
        helpFrame = new JFrame("SYS://HELP.MODULE");
        helpFrame.setSize(500, 400);
        helpFrame.setLocationRelativeTo(null);
        helpFrame.setResizable(false);

        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10)) {
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
                for (int i = 0; i < getHeight(); i += 15) {
                    g2d.drawLine(0, i, getWidth(), i);
                }

                // Draw vertical grid lines
                for (int i = 0; i < getWidth(); i += 15) {
                    g2d.drawLine(i, 0, i, getHeight());
                }

                // Random
                g2d.setColor(new Color(0, 255, 170, 15));
                Random rand = new Random();
                for (int i = 0; i < 8; i++) {
                    int x = rand.nextInt(getWidth());
                    g2d.drawLine(x, 0, x, getHeight());
                }
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("COMBAT PROTOCOL MANUAL");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        titleLabel.setForeground(ACCENT_COLOR);
        titlePanel.add(titleLabel);

        // Help text
        JPanel textPanel = new JPanel(new BorderLayout()) {
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

                // Add accents
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
        };
        textPanel.setOpaque(false);

        JTextArea helpText = new JTextArea();
        helpText.setText("""
                >> GAME INFO

                This IS A SIMPLE ROCK-PAPER-SCISSORS GAME 
                YOU WILL PLAY THE ROLE OF ARJ.MUNTANA WHO WILL HAVE TO FIGHT DR.PITCHAYA

                >> RULES:

                01. BATTLE: ARJ.MUNTANA vs DR.PITCHAYA
                02. INITIAL HP: 100 UNITS PER COMBATANT
                03. SELECT ATTACK: ROCK | PAPER | SCISSORS
                04. DAMAGE: 20 HP PER SUCCESSFUL STRIKE
                05. VICTORY CONDITION: REDUCE OPPONENT HP TO ZERO

                >> WE HOPE YOU ALL ENJOY THE GAME!
                """);

        helpText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setForeground(TEXT_COLOR);
        helpText.setBackground(new Color(20, 25, 40));
        helpText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add JScrollPane
        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ACCENT_COLOR;
                this.trackColor = new Color(30, 35, 50);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        textPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        backButton = new JButton("RETURN") {
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
        backButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        backButton.setForeground(ACCENT_COLOR);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

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
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
