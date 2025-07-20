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
        setIconImage(createCustomIcon());

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

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JMenuItem aboutItem = new JMenuItem("About SafeClean");
        aboutItem.addActionListener(e -> showAboutDialog());
        
        JMenuItem githubItem = new JMenuItem("View on GitHub");
        githubItem.addActionListener(e -> openGitHubRepo());
        
        JMenuItem userGuideItem = new JMenuItem("User Guide");
        userGuideItem.addActionListener(e -> showUserGuide());
        
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        helpMenu.add(githubItem);
        helpMenu.add(userGuideItem);

        menuBar.add(logMenu);
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
        iconLabel.setIcon(new ImageIcon(createCustomIcon()));
        
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
            
        JOptionPane.showMessageDialog(this, 
            aboutMessage,
            "About SafeClean", 
            JOptionPane.INFORMATION_MESSAGE);
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
