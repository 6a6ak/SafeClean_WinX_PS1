package com.safeclean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SafeCleanGUI extends JFrame {
    private JTextArea outputArea;
    private JProgressBar progressBar;
    private JButton[] cleanupButtons;
    private JButton runAllButton;
    private String[] buttonLabels = {
        "Clean Temporary Files",
        "Clean Windows Update Cache",
        "Clean Thumbnail Cache", 
        "Clear Event Logs",
        "Reduce WinSxS Folder (Advanced)",
        "Disable Hibernation & Remove hiberfil.sys",
        "Clean Recycle Bin"
    };

    public SafeCleanGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("SafeClean - System Cleanup Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Set application icons (multiple sizes)
        setApplicationIcons();

        // Create menu bar
        setJMenuBar(createMenuBar());

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create main content panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Create status panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
    }

    private BufferedImage createCustomIcon() {
        // Try to load external icon first - prefer ICO, then PNG
        try {
            // Try ICO first (better for Windows)
            java.net.URL iconURL = getClass().getResource("/icons/safeclean-icon.ico");
            if (iconURL != null) {
                BufferedImage img = javax.imageio.ImageIO.read(iconURL);
                System.out.println("✓ Loaded main icon from ICO file");
                return img;
            }
            
            // Fallback to PNG
            iconURL = getClass().getResource("/icons/safeclean-icon.png");
            if (iconURL != null) {
                BufferedImage img = javax.imageio.ImageIO.read(iconURL);
                System.out.println("✓ Loaded main icon from PNG file");
                return img;
            }
        } catch (Exception e) {
            System.out.println("Could not load external icon, using generated icon: " + e.getMessage());
        }
        
        // Fallback to programmatically created icon
        System.out.println("⚠ Using fallback generated icon");
        int size = 64;
        BufferedImage icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(45, 62, 80), size, size, new Color(70, 130, 180));
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, size, size, 12, 12);
        
        // Add border
        g2d.setColor(new Color(30, 40, 60));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, size-2, size-2, 12, 12);
        
        // Draw cleaning brush icon
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Brush handle
        g2d.drawLine(15, 45, 35, 25);
        
        // Brush head
        g2d.fillOval(32, 22, 8, 8);
        
        // Cleaning sparkles
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(42, 15, 45, 12);
        g2d.drawLine(48, 18, 51, 15);
        g2d.drawLine(45, 22, 48, 19);
        
        // Add "SC" text
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "SC";
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, (size - textWidth) / 2, size - 8);
        
        g2d.dispose();
        return icon;
    }

    // Utility method to load icons from resources
    private ImageIcon loadIcon(String iconPath, int width, int height) {
        try {
            java.net.URL iconURL = getClass().getResource(iconPath);
            if (iconURL != null) {
                BufferedImage img = javax.imageio.ImageIO.read(iconURL);
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (Exception e) {
            System.out.println("Could not load icon from: " + iconPath + " - " + e.getMessage());
        }
        
        // Try alternative formats if original fails
        if (iconPath.endsWith(".png")) {
            String icoPath = iconPath.replace(".png", ".ico");
            try {
                java.net.URL iconURL = getClass().getResource(icoPath);
                if (iconURL != null) {
                    BufferedImage img = javax.imageio.ImageIO.read(iconURL);
                    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
            } catch (Exception e) {
                System.out.println("Could not load alternative icon from: " + icoPath + " - " + e.getMessage());
            }
        }
        
        return null;
    }

    // Method to set multiple icon sizes for the application
    private void setApplicationIcons() {
        java.util.List<Image> iconImages = new java.util.ArrayList<>();
        
        // Try to load different sizes - support both PNG and ICO formats
        String[] iconSizes = {"16x16", "32x32", "64x64"};
        String[] formats = {".ico", ".png"};
        
        for (String size : iconSizes) {
            boolean loaded = false;
            for (String format : formats) {
                try {
                    java.net.URL iconURL = getClass().getResource("/icons/logo-" + size + format);
                    if (iconURL != null) {
                        BufferedImage img = javax.imageio.ImageIO.read(iconURL);
                        iconImages.add(img);
                        System.out.println("✓ Loaded icon: logo-" + size + format);
                        loaded = true;
                        break; // Found this size, move to next
                    }
                } catch (Exception e) {
                    System.out.println("Could not load icon logo-" + size + format + ": " + e.getMessage());
                }
            }
            
            // If no icon found for this size, try to scale main icon
            if (!loaded) {
                try {
                    java.net.URL mainIconURL = getClass().getResource("/icons/safeclean-icon.ico");
                    if (mainIconURL == null) {
                        mainIconURL = getClass().getResource("/icons/safeclean-icon.png");
                    }
                    if (mainIconURL != null) {
                        BufferedImage originalImg = javax.imageio.ImageIO.read(mainIconURL);
                        int targetSize = Integer.parseInt(size.split("x")[0]);
                        Image scaledImg = originalImg.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                        BufferedImage bufferedScaled = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedScaled.createGraphics();
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.drawImage(scaledImg, 0, 0, null);
                        g2d.dispose();
                        iconImages.add(bufferedScaled);
                        System.out.println("✓ Scaled main icon to " + size);
                    }
                } catch (Exception e) {
                    System.out.println("Could not scale main icon to " + size + ": " + e.getMessage());
                }
            }
        }
        
        // If we loaded any icons, set them
        if (!iconImages.isEmpty()) {
            setIconImages(iconImages);
            System.out.println("✓ Application icons set successfully (" + iconImages.size() + " sizes loaded)");
        } else {
            // Fallback to our custom icon
            setIconImage(createCustomIcon());
            System.out.println("⚠ Using fallback generated icon");
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(248, 249, 250));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 219, 221)));

        // Log Menu
        JMenu logMenu = new JMenu("Log");
        logMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JMenuItem clearLogItem = new JMenuItem("Clear Output Log");
        clearLogItem.addActionListener(e -> {
            outputArea.setText("Log cleared at " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "\n" +
                            "SafeClean ready for new operations...\n\n");
        });
        
        JMenuItem saveLogItem = new JMenuItem("Save Log to File");
        saveLogItem.addActionListener(e -> saveLogToFile());
        
        logMenu.add(clearLogItem);
        logMenu.add(saveLogItem);

        // Tools Menu
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JMenuItem systemInfoItem = new JMenuItem("System Information");
        systemInfoItem.addActionListener(e -> showSystemInfo());
        
        JMenuItem diskSpaceItem = new JMenuItem("Disk Space Analyzer");
        diskSpaceItem.addActionListener(e -> showDiskSpaceAnalyzer());
        
        JMenuItem registryCleanerItem = new JMenuItem("Registry Cleaner (Basic)");
        registryCleanerItem.addActionListener(e -> showRegistryCleaner());
        
        toolsMenu.add(systemInfoItem);
        toolsMenu.add(diskSpaceItem);
        toolsMenu.addSeparator();
        toolsMenu.add(registryCleanerItem);

        // View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JCheckBoxMenuItem alwaysOnTopItem = new JCheckBoxMenuItem("Always On Top");
        alwaysOnTopItem.addActionListener(e -> {
            setAlwaysOnTop(alwaysOnTopItem.isSelected());
        });
        
        JMenuItem fontSizeItem = new JMenuItem("Change Font Size");
        fontSizeItem.addActionListener(e -> changeFontSize());
        
        viewMenu.add(alwaysOnTopItem);
        viewMenu.addSeparator();
        viewMenu.add(fontSizeItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JMenuItem aboutItem = new JMenuItem("About SafeClean");
        aboutItem.addActionListener(e -> showAboutDialog());
        
        JMenuItem githubItem = new JMenuItem("View on GitHub");
        githubItem.addActionListener(e -> openGitHubRepo());
        
        JMenuItem userGuideItem = new JMenuItem("User Guide");
        userGuideItem.addActionListener(e -> showUserGuide());
        
        JMenuItem faqItem = new JMenuItem("FAQ");
        faqItem.addActionListener(e -> showFAQ());
        
        JMenuItem reportIssueItem = new JMenuItem("Report Issue");
        reportIssueItem.addActionListener(e -> reportIssue());
        
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        helpMenu.add(githubItem);
        helpMenu.add(userGuideItem);
        helpMenu.addSeparator();
        helpMenu.add(faqItem);
        helpMenu.add(reportIssueItem);

        menuBar.add(logMenu);
        menuBar.add(toolsMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // Create gradient background
        panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 62, 80), 0, getHeight(), new Color(70, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Create icon label
        JLabel iconLabel = new JLabel();
        BufferedImage headerIcon = createCustomIcon();
        if (headerIcon != null) {
            iconLabel.setIcon(new ImageIcon(headerIcon));
        } else {
            // Create a simple fallback icon if all else fails
            BufferedImage fallbackIcon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = fallbackIcon.createGraphics();
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillRoundRect(0, 0, 64, 64, 12, 12);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("SC", 20, 40);
            g2d.dispose();
            iconLabel.setIcon(new ImageIcon(fallbackIcon));
        }
        
        JLabel titleLabel = new JLabel("SafeClean");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Professional Windows System Cleanup Tool");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 220, 220));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(iconLabel, BorderLayout.WEST);
        leftPanel.add(Box.createHorizontalStrut(15), BorderLayout.CENTER);
        leftPanel.add(textPanel, BorderLayout.EAST);

        panel.add(leftPanel, BorderLayout.WEST);

        // Add warning section
        JPanel warningPanel = new JPanel();
        warningPanel.setLayout(new BoxLayout(warningPanel, BoxLayout.Y_AXIS));
        warningPanel.setOpaque(false);
        
        // Create custom warning icon
        JLabel warningIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = 32;
                // Draw warning triangle
                g2d.setColor(new Color(255, 193, 7));
                int[] xPoints = {size/2, size-4, 4};
                int[] yPoints = {4, size-4, size-4};
                g2d.fillPolygon(xPoints, yPoints, 3);
                
                // Draw exclamation mark
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(size/2, 10, size/2, 20);
                g2d.fillOval(size/2-2, 24, 4, 4);
            }
        };
        warningIcon.setPreferredSize(new Dimension(32, 32));
        warningIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel warningText1 = new JLabel("ADMINISTRATOR");
        warningText1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        warningText1.setForeground(new Color(255, 193, 7));
        warningText1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel warningText2 = new JLabel("REQUIRED");
        warningText2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        warningText2.setForeground(new Color(255, 193, 7));
        warningText2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        warningPanel.add(warningIcon);
        warningPanel.add(Box.createVerticalStrut(5));
        warningPanel.add(warningText1);
        warningPanel.add(warningText2);

        panel.add(warningPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(248, 249, 250));

        // Create buttons panel
        JPanel buttonsPanel = createButtonsPanel();
        
        // Create output panel
        JPanel outputPanel = createOutputPanel();

        // Split pane to divide buttons and output
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttonsPanel, outputPanel);
        splitPane.setDividerLocation(380);
        splitPane.setResizeWeight(0.4);
        splitPane.setBorder(null);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(45, 62, 80), 2), 
            "Cleanup Operations",
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(45, 62, 80)
        ));
        panel.setBackground(Color.WHITE);

        cleanupButtons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createStyledButton(buttonLabels[i], i);
            
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performCleanup(index);
                }
            });

            cleanupButtons[i] = button;
            panel.add(Box.createVerticalStrut(12));
            panel.add(button);
        }

        // Add "Run All" button
        panel.add(Box.createVerticalStrut(25));
        runAllButton = createRunAllButton();
        runAllButton.addActionListener(e -> runAllCleanup());
        panel.add(runAllButton);

        return panel;
    }

    private JButton createStyledButton(String text, int index) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button background
                Color bgColor;
                if (index == 4 || index == 5) { // Advanced operations
                    bgColor = getModel().isPressed() ? new Color(230, 173, 0) : new Color(255, 193, 7);
                } else if (index == 6) { // Recycle bin
                    bgColor = getModel().isPressed() ? new Color(200, 43, 59) : new Color(220, 53, 69);
                } else {
                    bgColor = getModel().isPressed() ? new Color(30, 147, 59) : new Color(40, 167, 69);
                }
                
                if (getModel().isRollover() && !getModel().isPressed()) {
                    bgColor = bgColor.brighter();
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                g2d.setColor(bgColor.darker());
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                // Set text color based on button state
                if ((index == 4 || index == 5) && getModel().isRollover() && !getModel().isPressed()) {
                    // Red text for advanced operations on hover
                    setForeground(new Color(220, 53, 69));
                } else {
                    // Default white text
                    setForeground(Color.WHITE);
                }
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(320, 45));
        button.setPreferredSize(new Dimension(320, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        return button;
    }

    private JButton createRunAllButton() {
        JButton button = new JButton("Run All Cleanup Operations") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor = getModel().isPressed() ? new Color(88, 97, 105) : new Color(108, 117, 125);
                if (getModel().isRollover() && !getModel().isPressed()) {
                    bgColor = bgColor.brighter();
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(bgColor.darker());
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(320, 55));
        button.setPreferredSize(new Dimension(320, 55));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        return button;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(45, 62, 80), 2), 
            "Output Log",
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(45, 62, 80)
        ));
        panel.setBackground(Color.WHITE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(new Color(253, 253, 253));
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        outputArea.setText("Welcome to SafeClean v2.0!\n" +
                         "Select a cleanup operation to begin.\n\n" +
                         "NEW FEATURES:\n" +
                         "• Detailed file path logging - see exactly what gets deleted\n" +
                         "• Log Menu - Clear output or save logs to files\n" +
                         "• Help Menu - Access GitHub repo and user guide\n\n" +
                         "IMPORTANT WARNINGS:\n" +
                         "• Run this application as Administrator\n" +
                         "• Some operations permanently delete data\n" +
                         "• Backup important files before proceeding\n" +
                         "• Advanced operations may affect system functionality\n\n" +
                         "Ready to start cleaning...\n");

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 25, 15, 25));
        panel.setBackground(new Color(45, 62, 80));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Ready to clean");
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        progressBar.setForeground(new Color(40, 167, 69));
        
        JLabel statusLabel = new JLabel("SafeClean v2.0 | Created by Babak Ahari | Professional System Cleanup Tool");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(200, 200, 200));

        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void performCleanup(int operationIndex) {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Starting " + buttonLabels[operationIndex] + "...\n");
                progressBar.setIndeterminate(true);
                progressBar.setString("Processing...");
                
                setButtonsEnabled(false);

                try {
                    String command = getPowerShellCommand(operationIndex);
                    executePowerShellCommand(command);
                    publish(buttonLabels[operationIndex] + " completed successfully!\n\n");
                } catch (Exception e) {
                    publish("Error during " + buttonLabels[operationIndex] + ": " + e.getMessage() + "\n\n");
                }

                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String chunk : chunks) {
                    outputArea.append(chunk);
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setString("Ready to clean");
                setButtonsEnabled(true);
            }
        };

        worker.execute();
    }

    private String getPowerShellCommand(int operationIndex) {
        switch (operationIndex) {
            case 0: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { " +
                          "Write-Host \"Scanning: $Path\"; " +
                          "if (Test-Path $Path) { " +
                          "$items = Get-ChildItem -Path $Path -Recurse -Force -ErrorAction SilentlyContinue; " +
                          "if ($items) { " +
                          "$count = ($items | Measure-Object).Count; " +
                          "Write-Host \"Found $count items to clean in $Path\"; " +
                          "$deleted = 0; " +
                          "foreach ($item in $items) { " +
                          "try { " +
                          "Remove-Item -Path $item.FullName -Recurse -Force -ErrorAction Stop; " +
                          "Write-Host \"  ✓ Deleted: $($item.FullName)\"; " +
                          "$deleted++; " +
                          "} catch { " +
                          "Write-Host \"  ✗ Skipped: $($item.FullName) (in use)\"; " +
                          "} }; " +
                          "Write-Host \"Completed: $Path ($deleted/$count items deleted)\"; " +
                          "} else { " +
                          "Write-Host \"No items found in $Path\"; " +
                          "} " +
                          "} else { " +
                          "Write-Host \"Path does not exist: $Path\"; " +
                          "} " +
                          "}; " +
                          "Write-Host '=== TEMPORARY FILES CLEANUP ==='; " +
                          "Clear-Folder '$env:TEMP'; " +
                          "Clear-Folder 'C:\\Windows\\Temp'; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            case 1: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { " +
                          "Write-Host \"Scanning: $Path\"; " +
                          "if (Test-Path $Path) { " +
                          "$items = Get-ChildItem -Path $Path -Recurse -Force -ErrorAction SilentlyContinue; " +
                          "if ($items) { " +
                          "$count = ($items | Measure-Object).Count; " +
                          "Write-Host \"Found $count items to clean in $Path\"; " +
                          "$deleted = 0; " +
                          "foreach ($item in $items) { " +
                          "try { " +
                          "Remove-Item -Path $item.FullName -Recurse -Force -ErrorAction Stop; " +
                          "Write-Host \"  ✓ Deleted: $($item.FullName)\"; " +
                          "$deleted++; " +
                          "} catch { " +
                          "Write-Host \"  ✗ Skipped: $($item.FullName) (in use)\"; " +
                          "} }; " +
                          "Write-Host \"Completed: $Path ($deleted/$count items deleted)\"; " +
                          "} else { " +
                          "Write-Host \"No items found in $Path\"; " +
                          "} " +
                          "} else { " +
                          "Write-Host \"Path does not exist: $Path\"; " +
                          "} " +
                          "}; " +
                          "Write-Host '=== WINDOWS UPDATE CACHE CLEANUP ==='; " +
                          "Write-Host 'Stopping Windows Update service...'; " +
                          "Stop-Service -Name wuauserv -Force -ErrorAction SilentlyContinue; " +
                          "Clear-Folder 'C:\\Windows\\SoftwareDistribution\\Download'; " +
                          "Write-Host 'Restarting Windows Update service...'; " +
                          "Start-Service -Name wuauserv -ErrorAction SilentlyContinue; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            case 2: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { " +
                          "Write-Host \"Scanning: $Path\"; " +
                          "if (Test-Path $Path) { " +
                          "$items = Get-ChildItem -Path $Path -Recurse -Force -ErrorAction SilentlyContinue; " +
                          "if ($items) { " +
                          "$count = ($items | Measure-Object).Count; " +
                          "Write-Host \"Found $count items to clean in $Path\"; " +
                          "$deleted = 0; " +
                          "foreach ($item in $items) { " +
                          "try { " +
                          "Remove-Item -Path $item.FullName -Recurse -Force -ErrorAction Stop; " +
                          "Write-Host \"  ✓ Deleted: $($item.FullName)\"; " +
                          "$deleted++; " +
                          "} catch { " +
                          "Write-Host \"  ✗ Skipped: $($item.FullName) (in use)\"; " +
                          "} }; " +
                          "Write-Host \"Completed: $Path ($deleted/$count items deleted)\"; " +
                          "} else { " +
                          "Write-Host \"No items found in $Path\"; " +
                          "} " +
                          "} else { " +
                          "Write-Host \"Path does not exist: $Path\"; " +
                          "} " +
                          "}; " +
                          "Write-Host '=== THUMBNAIL CACHE CLEANUP ==='; " +
                          "Clear-Folder '$env:LOCALAPPDATA\\Microsoft\\Windows\\Explorer'; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            case 3: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host '=== EVENT LOGS CLEANUP ==='; " +
                          "$logs = wevtutil el | Where-Object { $_ -notmatch 'Security|Setup|System' }; " +
                          "$totalLogs = ($logs | Measure-Object).Count; " +
                          "Write-Host \"Found $totalLogs event logs (excluding Security, Setup, System)\"; " +
                          "$cleared = 0; $skipped = 0; " +
                          "foreach ($log in $logs) { " +
                          "try { " +
                          "wevtutil cl $log 2>$null; " +
                          "$cleared++; " +
                          "Write-Host \"  ✓ Cleared log: $log\"; " +
                          "} catch { " +
                          "$skipped++; " +
                          "Write-Host \"  ✗ Skipped log: $log (Access denied)\"; " +
                          "} }; " +
                          "Write-Host \"=== CLEANUP COMPLETED ===\" " +
                          "Write-Host \"Event logs processed. Cleared: $cleared, Skipped: $skipped\" }\"";
            
            case 4: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host '=== WINSXS COMPONENT STORE CLEANUP ==='; " +
                          "Write-Host 'This operation may take several minutes...'; " +
                          "Write-Host 'Running DISM cleanup command...'; " +
                          "Dism.exe /online /Cleanup-Image /StartComponentCleanup /ResetBase; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            case 5: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host '=== HIBERNATION FILE CLEANUP ==='; " +
                          "Write-Host 'Checking hibernation status...'; " +
                          "$hibernationEnabled = (powercfg /query SCHEME_CURRENT SUB_SLEEP HIBERNATEIDLE | Select-String '0x00000000').Length -eq 0; " +
                          "if ($hibernationEnabled) { " +
                          "Write-Host 'Hibernation is currently enabled'; " +
                          "Write-Host 'Disabling hibernation and removing hiberfil.sys...'; " +
                          "powercfg /hibernate off; " +
                          "Write-Host '  ✓ Hibernation disabled and hiberfil.sys removed successfully.'; " +
                          "} else { " +
                          "Write-Host 'Hibernation is already disabled - no action needed'; " +
                          "}; " +
                          "Write-Host 'Note: You can re-enable hibernation later with: powercfg /hibernate on'; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            case 6: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host '=== RECYCLE BIN CLEANUP ==='; " +
                          "try { " +
                          "Add-Type -AssemblyName Microsoft.VisualBasic; " +
                          "$recycleBin = [Microsoft.VisualBasic.FileIO.FileSystem]::DirectoryExists('C:\\$Recycle.Bin'); " +
                          "if ($recycleBin) { " +
                          "Write-Host 'Emptying Recycle Bin using PowerShell cmdlet...'; " +
                          "Clear-RecycleBin -Force -ErrorAction Stop; " +
                          "Write-Host '  ✓ Recycle Bin emptied successfully'; " +
                          "} else { " +
                          "Write-Host 'Recycle Bin folder not accessible'; " +
                          "} " +
                          "} catch { " +
                          "Write-Host 'Trying alternative method...'; " +
                          "try { " +
                          "$shell = New-Object -ComObject Shell.Application; " +
                          "$recycleBinFolder = $shell.NameSpace(0xA); " +
                          "if ($recycleBinFolder) { " +
                          "$items = $recycleBinFolder.Items(); " +
                          "if ($items.Count -gt 0) { " +
                          "Write-Host \"Found $($items.Count) items in Recycle Bin\"; " +
                          "foreach ($item in $items) { " +
                          "$recycleBinFolder.MoveHere($item, 4); " +
                          "Write-Host \"  ✓ Deleted: $($item.Name)\"; " +
                          "}; " +
                          "Write-Host 'Recycle Bin emptied using COM method'; " +
                          "} else { " +
                          "Write-Host 'Recycle Bin is already empty'; " +
                          "} " +
                          "} " +
                          "} catch { " +
                          "Write-Host \"Error: $($_.Exception.Message)\"; " +
                          "Write-Host 'Recycle Bin cleanup failed - may require manual intervention'; " +
                          "} " +
                          "}; " +
                          "Write-Host '=== CLEANUP COMPLETED ===' }\"";
            
            default: return "";
        }
    }

    private void executePowerShellCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String output = line + "\n";
                SwingUtilities.invokeLater(() -> {
                    outputArea.append(output);
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                });
            }
        }

        process.waitFor();
    }

    private void runAllCleanup() {
        int result = JOptionPane.showConfirmDialog(this,
            "This will run ALL cleanup operations sequentially.\n" +
            "This may take several minutes and will:\n" +
            "• Delete temporary files\n" +
            "• Clear caches and logs\n" +
            "• Perform advanced system cleanup\n" +
            "• Empty recycle bin\n\n" +
            "Are you sure you want to continue?",
            "Confirm Run All Operations",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    setButtonsEnabled(false);
                    progressBar.setIndeterminate(true);
                    progressBar.setString("Running all operations...");
                    
                    publish("Starting comprehensive system cleanup...\n");
                    publish("==================================================\n");

                    for (int i = 0; i < buttonLabels.length; i++) {
                        publish("Step " + (i + 1) + "/" + buttonLabels.length + ": " + buttonLabels[i] + "\n");
                        try {
                            String command = getPowerShellCommand(i);
                            executePowerShellCommand(command);
                            publish(buttonLabels[i] + " completed\n\n");
                        } catch (Exception e) {
                            publish("Error in " + buttonLabels[i] + ": " + e.getMessage() + "\n\n");
                        }
                    }

                    publish("==================================================\n");
                    publish("All cleanup operations completed!\n");
                    return null;
                }

                @Override
                protected void process(java.util.List<String> chunks) {
                    for (String chunk : chunks) {
                        outputArea.append(chunk);
                        outputArea.setCaretPosition(outputArea.getDocument().getLength());
                    }
                }

                @Override
                protected void done() {
                    progressBar.setIndeterminate(false);
                    progressBar.setString("All operations completed");
                    setButtonsEnabled(true);
                    
                    JOptionPane.showMessageDialog(SafeCleanGUI.this,
                        "All cleanup operations have been completed!\n" +
                        "Check the output log for details.",
                        "Cleanup Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            };

            worker.execute();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        for (JButton button : cleanupButtons) {
            button.setEnabled(enabled);
        }
        runAllButton.setEnabled(enabled);
    }

    private void saveLogToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Log File");
        fileChooser.setSelectedFile(new java.io.File("SafeClean_Log_" + 
                                    new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile())) {
                writer.write(outputArea.getText());
                JOptionPane.showMessageDialog(this, 
                    "Log saved successfully to:\n" + fileChooser.getSelectedFile().getAbsolutePath(),
                    "Log Saved", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error saving log file:\n" + e.getMessage(),
                    "Save Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAboutDialog() {
        String aboutMessage = 
            "SafeClean v2.0.0\n" +
            "Professional Windows System Cleanup Tool\n\n" +
            "Created by: Babak Ahari\n" +
            "Website: Clean.tricks.se\n" +
            "GitHub: github.com/6a6ak/SafeClean\n" +
            "License: MIT License\n\n" +
            "Java GUI Version Features:\n" +
            "• Professional UI with custom graphics\n" +
            "• Real-time PowerShell execution\n" +
            "• Comprehensive system cleanup operations\n" +
            "• Windows 10/11 compatible\n" +
            "• Administrator privilege handling\n\n" +
            "Copyright (c) 2025 Babak Ahari\n" +
            "This software is provided \"as is\" without warranty.";
        
        // Try to load logo for about dialog - prefer ICO, then PNG
        ImageIcon logoIcon = null;
        
        // Try loading specific about logo
        logoIcon = loadIcon("/images/about-logo.ico", 64, 64);
        if (logoIcon == null) {
            logoIcon = loadIcon("/images/about-logo.png", 64, 64);
        }
        
        // Fallback to main application icon
        if (logoIcon == null) {
            logoIcon = loadIcon("/icons/safeclean-icon.ico", 64, 64);
        }
        if (logoIcon == null) {
            logoIcon = loadIcon("/icons/safeclean-icon.png", 64, 64);
        }
        
        // Ultimate fallback to custom icon
        if (logoIcon == null) {
            logoIcon = new ImageIcon(createCustomIcon());
        }
            
        JOptionPane.showMessageDialog(this, 
            aboutMessage,
            "About SafeClean", 
            JOptionPane.INFORMATION_MESSAGE,
            logoIcon);
    }

    private void openGitHubRepo() {
        try {
            String url = "https://github.com/6a6ak/SafeClean";
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            } else {
                // Fallback for systems without Desktop support
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Could not open browser. Please visit:\n" +
                "Website: Clean.tricks.se\n" +
                "GitHub: https://github.com/6a6ak/SafeClean\n\n" +
                "Error: " + e.getMessage(),
                "Browser Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUserGuide() {
        String userGuideMessage = 
            "SafeClean User Guide\n\n" +
            "CLEANUP OPERATIONS:\n\n" +
            "1. Clean Temporary Files\n" +
            "   • Removes user and system temp files\n" +
            "   • Safe operation, run anytime\n\n" +
            "2. Clean Windows Update Cache\n" +
            "   • Clears Windows Update downloads\n" +
            "   • Frees space from old update files\n\n" +
            "3. Clean Thumbnail Cache\n" +
            "   • Removes Explorer thumbnail cache\n" +
            "   • Improves folder browsing performance\n\n" +
            "4. Clear Event Logs\n" +
            "   • Clears Windows Event Logs (filtered for safety)\n" +
            "   • Protects Security, Setup, and System logs\n\n" +
            "5. Reduce WinSxS Folder (Advanced)\n" +
            "   • ADVANCED: Cleans Windows Component Store\n" +
            "   • Use only if you understand implications\n\n" +
            "6. Disable Hibernation & Remove hiberfil.sys\n" +
            "   • Disables hibernation feature\n" +
            "   • Removes hiberfil.sys file (saves GB of space)\n" +
            "   • Can be re-enabled later if needed\n\n" +
            "7. Clean Recycle Bin\n" +
            "   • Permanently empties Recycle Bin\n" +
            "   • WARNING: Data cannot be recovered\n\n" +
            "IMPORTANT NOTES:\n" +
            "• Always run as Administrator\n" +
            "• Backup important data before cleanup\n" +
            "• Advanced operations may affect system functionality\n" +
            "• Some operations permanently delete data";
            
        JTextArea textArea = new JTextArea(userGuideMessage);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane,
            "SafeClean User Guide", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSystemInfo() {
        try {
            String systemInfo = "System Information\n\n";
            
            // Basic system info
            systemInfo += "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n";
            systemInfo += "Architecture: " + System.getProperty("os.arch") + "\n";
            systemInfo += "Java Version: " + System.getProperty("java.version") + "\n";
            systemInfo += "User: " + System.getProperty("user.name") + "\n\n";
            
            // Memory info
            Runtime runtime = Runtime.getRuntime();
            long maxMemory = runtime.maxMemory();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            
            systemInfo += "Memory Information:\n";
            systemInfo += "Max Memory: " + (maxMemory / 1024 / 1024) + " MB\n";
            systemInfo += "Total Memory: " + (totalMemory / 1024 / 1024) + " MB\n";
            systemInfo += "Used Memory: " + (usedMemory / 1024 / 1024) + " MB\n";
            systemInfo += "Free Memory: " + (freeMemory / 1024 / 1024) + " MB\n\n";
            
            // Disk space info
            java.io.File[] roots = java.io.File.listRoots();
            systemInfo += "Disk Space Information:\n";
            for (java.io.File root : roots) {
                long totalSpace = root.getTotalSpace();
                long freeSpace = root.getFreeSpace();
                long usedSpace = totalSpace - freeSpace;
                
                if (totalSpace > 0) {
                    systemInfo += "Drive " + root.getAbsolutePath() + "\n";
                    systemInfo += "  Total: " + (totalSpace / 1024 / 1024 / 1024) + " GB\n";
                    systemInfo += "  Used: " + (usedSpace / 1024 / 1024 / 1024) + " GB\n";
                    systemInfo += "  Free: " + (freeSpace / 1024 / 1024 / 1024) + " GB\n\n";
                }
            }
            
            JTextArea textArea = new JTextArea(systemInfo);
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            textArea.setBackground(new Color(248, 249, 250));
            textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));
            
            JOptionPane.showMessageDialog(this, 
                scrollPane,
                "System Information", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error retrieving system information:\n" + e.getMessage(),
                "System Info Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDiskSpaceAnalyzer() {
        try {
            String diskAnalysis = "Disk Space Analysis\n\n";
            
            java.io.File[] roots = java.io.File.listRoots();
            for (java.io.File root : roots) {
                long totalSpace = root.getTotalSpace();
                long freeSpace = root.getFreeSpace();
                long usedSpace = totalSpace - freeSpace;
                
                if (totalSpace > 0) {
                    double usedPercentage = ((double) usedSpace / totalSpace) * 100;
                    diskAnalysis += "Drive: " + root.getAbsolutePath() + "\n";
                    diskAnalysis += "Total Space: " + String.format("%.2f GB", totalSpace / 1024.0 / 1024.0 / 1024.0) + "\n";
                    diskAnalysis += "Used Space: " + String.format("%.2f GB (%.1f%%)", usedSpace / 1024.0 / 1024.0 / 1024.0, usedPercentage) + "\n";
                    diskAnalysis += "Free Space: " + String.format("%.2f GB", freeSpace / 1024.0 / 1024.0 / 1024.0) + "\n";
                    
                    // Visual bar
                    int barLength = 30;
                    int usedBars = (int) (usedPercentage / 100 * barLength);
                    StringBuilder barBuilder = new StringBuilder("[");
                    for (int i = 0; i < usedBars; i++) barBuilder.append("█");
                    for (int i = usedBars; i < barLength; i++) barBuilder.append("░");
                    barBuilder.append("]");
                    String bar = barBuilder.toString();
                    diskAnalysis += bar + "\n\n";
                }
            }
            
            diskAnalysis += "Common Large Files Locations:\n";
            diskAnalysis += "• C:\\Windows\\WinSxS\\ (Windows Component Store)\n";
            diskAnalysis += "• C:\\Windows\\SoftwareDistribution\\ (Windows Updates)\n";
            diskAnalysis += "• %TEMP% (Temporary Files)\n";
            diskAnalysis += "• %LOCALAPPDATA%\\Microsoft\\Windows\\Explorer\\ (Thumbnails)\n";
            diskAnalysis += "• C:\\hiberfil.sys (Hibernation File)\n";
            diskAnalysis += "• C:\\pagefile.sys (Virtual Memory)\n\n";
            diskAnalysis += "Use SafeClean's cleanup operations to free space safely!";
            
            JTextArea textArea = new JTextArea(diskAnalysis);
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            textArea.setBackground(new Color(248, 249, 250));
            textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(550, 450));
            
            JOptionPane.showMessageDialog(this, 
                scrollPane,
                "Disk Space Analyzer", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error analyzing disk space:\n" + e.getMessage(),
                "Disk Analysis Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegistryCleaner() {
        String registryInfo = 
            "Registry Cleaner (Basic)\n\n" +
            "WARNING: Registry cleaning can be dangerous!\n\n" +
            "SafeClean includes basic registry maintenance through Windows built-in tools:\n\n" +
            "SAFE REGISTRY OPERATIONS:\n" +
            "• System File Checker (SFC Scan)\n" +
            "• DISM Health Restore\n" +
            "• Windows Registry Checker\n\n" +
            "MANUAL REGISTRY CLEANING:\n" +
            "1. Press Win+R, type 'regedit'\n" +
            "2. File → Export (Create backup first!)\n" +
            "3. Clean these safe locations:\n" +
            "   • HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\n" +
            "   • HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\n\n" +
            "RECOMMENDED TOOLS:\n" +
            "• CCleaner (Registry section)\n" +
            "• Windows built-in Disk Cleanup\n" +
            "• System Configuration (msconfig)\n\n" +
            "IMPORTANT: Always create a system restore point before registry changes!";
            
        int result = JOptionPane.showConfirmDialog(this,
            registryInfo + "\n\nWould you like to run a safe SFC scan now?",
            "Registry Cleaner Information",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            runSFCScan();
        }
    }

    private void runSFCScan() {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Starting System File Checker (SFC) scan...\n");
                publish("This may take 10-15 minutes. Please wait...\n\n");
                progressBar.setIndeterminate(true);
                progressBar.setString("Running SFC scan...");
                setButtonsEnabled(false);

                try {
                    String command = "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                                   "Write-Host '=== SYSTEM FILE CHECKER SCAN ==='; " +
                                   "Write-Host 'Starting SFC scan (this may take 10-15 minutes)...'; " +
                                   "sfc /scannow; " +
                                   "Write-Host '=== SFC SCAN COMPLETED ==='; " +
                                   "Write-Host 'Check the output above for results.' }\"";
                    executePowerShellCommand(command);
                    publish("SFC scan completed!\n\n");
                } catch (Exception e) {
                    publish("Error during SFC scan: " + e.getMessage() + "\n\n");
                }

                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String chunk : chunks) {
                    outputArea.append(chunk);
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setString("SFC scan completed");
                setButtonsEnabled(true);
            }
        };

        worker.execute();
    }

    private void changeFontSize() {
        String[] sizes = {"10", "12", "14", "16", "18", "20"};
        String currentSize = String.valueOf(outputArea.getFont().getSize());
        
        String selectedSize = (String) JOptionPane.showInputDialog(this,
            "Select font size for output log:",
            "Change Font Size",
            JOptionPane.QUESTION_MESSAGE,
            null,
            sizes,
            currentSize);
            
        if (selectedSize != null) {
            try {
                int newSize = Integer.parseInt(selectedSize);
                Font currentFont = outputArea.getFont();
                Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), newSize);
                outputArea.setFont(newFont);
                
                // Save preference for next time
                outputArea.append("Font size changed to " + newSize + "pt\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid font size selected",
                    "Font Size Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFAQ() {
        String faqContent = 
            "SafeClean - Frequently Asked Questions\n\n" +
            "Q: Do I need administrator privileges?\n" +
            "A: Yes, most cleanup operations require admin rights to access system folders.\n\n" +
            "Q: Is it safe to run all cleanup operations?\n" +
            "A: Yes, but advanced operations (WinSxS, Hibernation) should be used carefully.\n\n" +
            "Q: What if the cleanup gets stuck?\n" +
            "A: Some operations take time. Wait 10-15 minutes before force-closing.\n\n" +
            "Q: Can I recover deleted files?\n" +
            "A: No, most cleanup operations permanently delete files. Backup important data first.\n\n" +
            "Q: Why does Recycle Bin cleanup sometimes fail?\n" +
            "A: Files may be in use. Try closing all programs and running again.\n\n" +
            "Q: How much space will I free up?\n" +
            "A: Varies by system. Typically 1-10GB, sometimes more on heavily used systems.\n\n" +
            "Q: Can I schedule automatic cleanups?\n" +
            "A: Not built-in yet, but you can run SafeClean manually as needed.\n\n" +
            "Q: What's the difference between normal and advanced operations?\n" +
            "A: Normal operations are safe daily cleaning. Advanced operations affect system components.\n\n" +
            "Q: Why do I need Java to run this?\n" +
            "A: SafeClean is built in Java for cross-platform compatibility and modern UI features.\n\n" +
            "Q: How do I report bugs or request features?\n" +
            "A: Use the 'Report Issue' menu item or visit our GitHub repository.";
            
        JTextArea textArea = new JTextArea(faqContent);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textArea.setBackground(new Color(248, 249, 250));
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane,
            "SafeClean FAQ", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void reportIssue() {
        try {
            String url = "https://github.com/6a6ak/SafeClean/issues/new";
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            } else {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Could not open browser. Please visit:\n" +
                "https://github.com/6a6ak/SafeClean/issues\n\n" +
                "Or email: babak@clean.tricks.se\n\n" +
                "Error: " + e.getMessage(),
                "Report Issue", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } else if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Using default look and feel");
        }

        SwingUtilities.invokeLater(() -> {
            SafeCleanGUI gui = new SafeCleanGUI();
            gui.setVisible(true);
        });
    }
}
