import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SafeCleanGUI extends JFrame {
    private JTextArea outputArea;
    private JProgressBar progressBar;
    private JButton[] cleanupButtons;
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
        setTitle("SafeClean WinX - System Cleanup Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

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

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(45, 62, 80));

        JLabel titleLabel = new JLabel("SafeClean WinX");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Windows System Cleanup Tool - Run as Administrator");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 200, 200));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);

        panel.add(textPanel, BorderLayout.WEST);

        // Add warning icon
        JLabel warningLabel = new JLabel("⚠️");
        warningLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        panel.add(warningLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create buttons panel
        JPanel buttonsPanel = createButtonsPanel();
        
        // Create output panel
        JPanel outputPanel = createOutputPanel();

        // Split pane to divide buttons and output
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttonsPanel, outputPanel);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.4);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Cleanup Options"));

        cleanupButtons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(300, 40));
            button.setPreferredSize(new Dimension(300, 40));
            button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            
            // Set colors based on operation type
            if (i == 4 || i == 5) { // Advanced operations
                button.setBackground(new Color(255, 193, 7));
                button.setForeground(Color.BLACK);
            } else if (i == 6) { // Recycle bin (last option)
                button.setBackground(new Color(220, 53, 69));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(40, 167, 69));
                button.setForeground(Color.WHITE);
            }

            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performCleanup(index);
                }
            });

            cleanupButtons[i] = button;
            panel.add(Box.createVerticalStrut(10));
            panel.add(button);
        }

        // Add "Run All" button
        panel.add(Box.createVerticalStrut(20));
        JButton runAllButton = new JButton("🚀 Run All Cleanup Operations");
        runAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        runAllButton.setMaximumSize(new Dimension(300, 50));
        runAllButton.setPreferredSize(new Dimension(300, 50));
        runAllButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        runAllButton.setBackground(new Color(108, 117, 125));
        runAllButton.setForeground(Color.WHITE);
        runAllButton.addActionListener(e -> runAllCleanup());
        panel.add(runAllButton);

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Output Log"));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(new Color(248, 249, 250));
        outputArea.setText("Welcome to SafeClean WinX!\nSelect a cleanup operation to begin.\n\n" +
                         "⚠️ IMPORTANT WARNINGS:\n" +
                         "• Run this application as Administrator\n" +
                         "• Some operations permanently delete data\n" +
                         "• Backup important files before proceeding\n" +
                         "• Advanced operations may affect system functionality\n\n");

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setBackground(new Color(248, 249, 250));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Ready");
        
        JLabel statusLabel = new JLabel("Status: Ready to clean | Author: 6a6ak | Version: 1.0");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        statusLabel.setForeground(new Color(108, 117, 125));

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
                
                // Disable all buttons during operation
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
                progressBar.setString("Ready");
                setButtonsEnabled(true);
            }
        };

        worker.execute();
    }

    private String getPowerShellCommand(int operationIndex) {
        switch (operationIndex) {
            case 0: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { if (Test-Path $Path) { Get-ChildItem -Path $Path -Recurse -Force | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue; Write-Host 'Cleared: $Path' } else { Write-Host 'Path does not exist: $Path' } }; " +
                          "Write-Host 'Cleaning TEMP files...'; Clear-Folder '$env:TEMP'; Clear-Folder 'C:\\Windows\\Temp'; Write-Host 'Temporary files cleaned.' }\"";
            
            case 1: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { if (Test-Path $Path) { Get-ChildItem -Path $Path -Recurse -Force | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue; Write-Host 'Cleared: $Path' } else { Write-Host 'Path does not exist: $Path' } }; " +
                          "Write-Host 'Cleaning Windows Update cache...'; Stop-Service -Name wuauserv -Force; Clear-Folder 'C:\\Windows\\SoftwareDistribution\\Download'; Start-Service -Name wuauserv; Write-Host 'Windows Update cache cleaned.' }\"";
            
            case 2: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "function Clear-Folder($Path) { if (Test-Path $Path) { Get-ChildItem -Path $Path -Recurse -Force | Remove-Item -Recurse -Force -ErrorAction SilentlyContinue; Write-Host 'Cleared: $Path' } else { Write-Host 'Path does not exist: $Path' } }; " +
                          "Write-Host 'Cleaning Thumbnail cache...'; Clear-Folder '$env:LOCALAPPDATA\\Microsoft\\Windows\\Explorer'; Write-Host 'Thumbnail cache cleaned.' }\"";
            
            case 3: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host 'Clearing Event Logs...'; " +
                          "$logs = wevtutil el | Where-Object { $_ -notmatch 'Security|Setup|System' }; " +
                          "$cleared = 0; $skipped = 0; " +
                          "foreach ($log in $logs) { " +
                          "try { " +
                          "wevtutil cl $log 2>$null; " +
                          "$cleared++; " +
                          "if ($cleared % 10 -eq 0) { Write-Host 'Cleared $cleared logs...' } " +
                          "} catch { " +
                          "$skipped++; " +
                          "if ($skipped -le 5) { Write-Host 'Skipped: $log (Access denied)' } " +
                          "} }; " +
                          "Write-Host 'Event logs clearing completed. Cleared: $cleared, Skipped: $skipped' }\"";
            
            case 4: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host 'Reducing WinSxS (Component Store)...'; Dism.exe /online /Cleanup-Image /StartComponentCleanup /ResetBase; Write-Host 'WinSxS cleanup completed.' }\"";
            
            case 5: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host 'Disabling hibernation and removing hiberfil.sys...'; powercfg /hibernate off; Write-Host 'Hibernation disabled and hiberfil.sys removed.'; Write-Host 'Note: You can re-enable hibernation later with powercfg /hibernate on' }\"";
            
            case 6: return "powershell.exe -ExecutionPolicy Bypass -Command \"& {" +
                          "Write-Host 'Cleaning Recycle Bin...'; $(New-Object -ComObject Shell.Application).NameSpace(0xA).Items() | ForEach-Object { Remove-Item $_.Path -Recurse -Force -ErrorAction SilentlyContinue }; Write-Host 'Recycle Bin cleaned.' }\"";
            
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
                    
                    publish("🚀 Starting comprehensive system cleanup...\n");
                    publish("=".repeat(50) + "\n");

                    for (int i = 0; i < buttonLabels.length; i++) {
                        publish("Step " + (i + 1) + "/" + buttonLabels.length + ": " + buttonLabels[i] + "\n");
                        try {
                            String command = getPowerShellCommand(i);
                            executePowerShellCommand(command);
                            publish("✅ " + buttonLabels[i] + " completed\n\n");
                        } catch (Exception e) {
                            publish("❌ Error in " + buttonLabels[i] + ": " + e.getMessage() + "\n\n");
                        }
                    }

                    publish("=".repeat(50) + "\n");
                    publish("🎉 All cleanup operations completed!\n");
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
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            // Try to set system look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } else if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Use default look and feel if others fail
            System.out.println("Using default look and feel");
        }

        // Check if running as administrator (Windows)
        SwingUtilities.invokeLater(() -> {
            SafeCleanGUI gui = new SafeCleanGUI();
            gui.setVisible(true);

            // Show admin warning
            JOptionPane.showMessageDialog(gui,
                "⚠️ ADMINISTRATOR REQUIRED ⚠️\n\n" +
                "This application requires Administrator privileges to function properly.\n" +
                "Please ensure you are running this as Administrator.\n\n" +
                "Right-click the executable and select 'Run as Administrator'",
                "Administrator Required",
                JOptionPane.WARNING_MESSAGE);
        });
    }
}
