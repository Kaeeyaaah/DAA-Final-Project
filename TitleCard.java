import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleCard extends JFrame {
    
    public TitleCard() {
        initializeGUI();
    }
    
    // initialize the gui
    private void initializeGUI() {
        setTitle("Design and Analysis of Algorithms - Final Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // set the background color
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), 
                                                         0, getHeight(), new Color(135, 206, 235));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // title
        JLabel titleLabel = new JLabel("Pondong Planado");

        titleLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        mainPanel.add(titleLabel, gbc);

        // subtitle
        JLabel subtitleLabel = new JLabel("A Smart Budgeting System for Barangay Projects");
        subtitleLabel.setFont(new Font("Roboto", Font.ITALIC, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 40, 20);
        mainPanel.add(subtitleLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        // start button
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(120, 100));
        startButton.setBackground(new Color(34, 139, 34));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // start button hover effect
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(0, 128, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(34, 139, 34));
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startApplication();
            }
        });
        
        // exit button
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setPreferredSize(new Dimension(120, 100));
        exitButton.setBackground(new Color(220, 20, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // exit button hover effect
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(178, 34, 34));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(220, 20, 60));
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(buttonPanel, gbc);
        
        // footer
        JLabel footerLabel = new JLabel("Final Requirement for Design and Analysis of Algorithms");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        gbc.insets = new Insets(40, 20, 20, 20);
        mainPanel.add(footerLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void startApplication() {
        // hide the title card
        this.setVisible(false);
        
        // start the main application (BudgetAllocationGUI.java file)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BudgetAllocationGUI mainApp = new BudgetAllocationGUI();
                mainApp.setVisible(true);
                
                // Add window listener to show title card when main app is closed
                mainApp.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        TitleCard.this.setVisible(true);
                    }
                });
            }
        });
    }
    
    // exit app method
    private void exitApplication() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the application?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    // main method 
    public static void main(String[] args) {
        // set the environment 
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // use default look instead if nimbus is not available
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TitleCard().setVisible(true);
            }
        });
    }
}