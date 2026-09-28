package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * GUI Application for running test automation with visual parameter selection
 * 
 * This popup window allows you to:
 * - Select environment (dev, staging, prod)
 * - Select browser (chromium, firefox, webkit)
 * - Toggle headless mode
 * - Select test tags (all, regression, sanity, smoke, qap-1)
 * - Select runner type (Test Runner, Automation Approval, Jira Status Update)
 * - Click Run to execute
 */
public class TestRunnerGUI extends JFrame {

    private JComboBox<String> environmentCombo;
    private JComboBox<String> browserCombo;
    private JCheckBox headlessCheckBox;
    private JComboBox<String> testTagsCombo;
    private JComboBox<String> runnerCombo;
    private JTextArea commandPreview;
    private JButton runButton;
    private JButton cancelButton;

    public TestRunnerGUI() {
        setTitle("Test Automation Runner");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        // Environment dropdown
        environmentCombo = new JComboBox<>(new String[]{"dev", "staging", "prod"});
        environmentCombo.setSelectedItem("dev");

        // Browser dropdown
        browserCombo = new JComboBox<>(new String[]{"chromium", "firefox", "webkit"});
        browserCombo.setSelectedItem("chromium");

        // Headless checkbox
        headlessCheckBox = new JCheckBox("Headless Mode");
        headlessCheckBox.setSelected(true);

        // Test tags dropdown
        testTagsCombo = new JComboBox<>(new String[]{"All Tests", "@regression", "@sanity", "@smoke", "@qap-1"});
        testTagsCombo.setSelectedItem("All Tests");

        // Runner dropdown
        runnerCombo = new JComboBox<>(new String[]{"Test Runner", "Automation Approval Runner", "Jira Status Update Runner"});
        runnerCombo.setSelectedItem("Test Runner");

        // Command preview
        commandPreview = new JTextArea(5, 40);
        commandPreview.setEditable(false);
        commandPreview.setFont(new Font("Monospaced", Font.PLAIN, 11));
        commandPreview.setBackground(new Color(240, 240, 240));
        updateCommandPreview();

        // Run button
        runButton = new JButton("▶ Run");
        runButton.setFont(new Font("Arial", Font.BOLD, 14));
        runButton.setPreferredSize(new Dimension(100, 35));

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 35));

        // Add action listeners
        environmentCombo.addActionListener(e -> updateCommandPreview());
        browserCombo.addActionListener(e -> updateCommandPreview());
        headlessCheckBox.addActionListener(e -> updateCommandPreview());
        testTagsCombo.addActionListener(e -> updateCommandPreview());
        runnerCombo.addActionListener(e -> updateCommandPreview());

        runButton.addActionListener(this::runCommand);
        cancelButton.addActionListener(e -> dispose());
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Environment
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Environment:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(environmentCombo, gbc);

        // Browser
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Browser:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(browserCombo, gbc);

        // Headless
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Mode:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(headlessCheckBox, gbc);

        // Test Tags
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Test Tags:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(testTagsCombo, gbc);

        // Runner
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("Runner:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(runnerCombo, gbc);

        // Command Preview
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JLabel("Command Preview:"), gbc);

        gbc.gridy = 6;
        gbc.weighty = 1.0;
        JScrollPane scrollPane = new JScrollPane(commandPreview);
        formPanel.add(scrollPane, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.add(cancelButton);
        buttonPanel.add(runButton);

        // Add to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateCommandPreview() {
        String env = (String) environmentCombo.getSelectedItem();
        String browser = (String) browserCombo.getSelectedItem();
        boolean headless = headlessCheckBox.isSelected();
        String tags = (String) testTagsCombo.getSelectedItem();
        String runner = (String) runnerCombo.getSelectedItem();

        StringBuilder command = new StringBuilder();
        command.append("./gradlew.bat ");

        if (runner.equals("Test Runner")) {
            command.append("clean test ");
            command.append(String.format("\"-Denv=%s\" ", env));
            command.append(String.format("\"-Dbrowser=%s\" ", browser));
            command.append(String.format("\"-Dheadless=%s\" ", headless));

            if (!tags.equals("All Tests")) {
                command.append(String.format("\"-Dcucumber.filter.tags=%s\" ", tags));
            }
        } else if (runner.equals("Automation Approval Runner")) {
            command.append("runAutomationApprovalRunner");
        } else if (runner.equals("Jira Status Update Runner")) {
            command.append("runJiraStatusUpdateRunner");
        }

        commandPreview.setText(command.toString());
    }

    private void runCommand(ActionEvent e) {
        String command = commandPreview.getText();
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Run the following command?\n\n" + command,
            "Confirm Run",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Execute the command
                ProcessBuilder processBuilder = new ProcessBuilder();
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    processBuilder.command("cmd", "/c", command);
                } else {
                    processBuilder.command("bash", "-c", command);
                }
                
                processBuilder.directory(new File(System.getProperty("user.dir")));
                
                // Show progress dialog
                JDialog progressDialog = new JDialog(this, "Running...", true);
                progressDialog.setSize(300, 100);
                progressDialog.setLocationRelativeTo(this);
                
                JLabel statusLabel = new JLabel("Running tests...", SwingConstants.CENTER);
                progressDialog.add(statusLabel);
                
                // Run in separate thread
                new Thread(() -> {
                    try {
                        Process process = processBuilder.start();
                        int exitCode = process.waitFor();
                        
                        SwingUtilities.invokeLater(() -> {
                            progressDialog.dispose();
                            
                            if (exitCode == 0) {
                                JOptionPane.showMessageDialog(
                                    this,
                                    "Tests completed successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                            } else {
                                JOptionPane.showMessageDialog(
                                    this,
                                    "Tests failed with exit code: " + exitCode,
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                                );
                            }
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            progressDialog.dispose();
                            JOptionPane.showMessageDialog(
                                this,
                                "Error running command: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                        });
                    }
                }).start();
                
                progressDialog.setVisible(true);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel
            }

            TestRunnerGUI gui = new TestRunnerGUI();
            gui.setVisible(true);
        });
    }
}
