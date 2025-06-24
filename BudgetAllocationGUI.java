// importing the necessary java libraries
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// creating the frame
public class BudgetAllocationGUI extends JFrame {
    private ArrayList<Project> projects;
    private DefaultTableModel projectTableModel;
    private DefaultTableModel solutionTableModel;
    private JTable projectTable;
    private JTable solutionTable;
    private JTextField budgetField;
    private JTextArea resultArea;
    private JLabel statusLabel;
    

    public BudgetAllocationGUI() {
        projects = new ArrayList<Project>();
        initializeGUI();
    }
    
    // initializing the gui
    private void initializeGUI() {
        setTitle("Barangay Budget Allocation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel backgroundPanel = new JPanel(){
            @Override 
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), 
                                                0, getHeight(), new Color(135, 206, 235));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // create the main panels
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(backgroundPanel);
        
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    // top panel
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        LineBorder blueLine = new LineBorder(new Color(135, 206, 250), 0);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blueLine, "Budget Configuration");
        titledBorder.setTitleColor(Color.WHITE); 
        panel.setBorder(titledBorder);
        panel.setOpaque(false); 

        JLabel totalBudgetLabel = new JLabel("Total Budget (₱):");
        totalBudgetLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        totalBudgetLabel.setForeground(Color.WHITE);
        panel.add(totalBudgetLabel);

        budgetField = new JTextField("", 10);
        panel.add(budgetField);
        
        JButton optimizeButton = new JButton("Optimize Allocation");
        optimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optimizeBudget();
            }
        });
        panel.add(optimizeButton);
        
        JButton addProjectButton = new JButton("Add Project");
        addProjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddProjectDialog();
            }
        });
        panel.add(addProjectButton);
        
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllProjects();
            }
        });
        panel.add(clearButton);
        
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelectedProject();
            }
        });
        panel.add(removeButton);
        
        return panel;
    }
    
    // center panel 
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);

        // projects table
        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.setOpaque(false);
        LineBorder blueLine = new LineBorder(new Color(135, 206, 250), 0); 
        TitledBorder titledBorder = BorderFactory.createTitledBorder(blueLine, "Available Projects");
        titledBorder.setTitleColor(Color.WHITE); 
        projectPanel.setBorder(titledBorder);


        String[] projectColumns = {"Name", "Cost (₱)", "Benefit", "Ratio", "Category"};
        projectTableModel = new DefaultTableModel(projectColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        projectTable = new JTable(projectTableModel);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane projectScrollPane = new JScrollPane(projectTable);
        projectPanel.add(projectScrollPane, BorderLayout.CENTER);
        projectScrollPane.setOpaque(false);

        // solutions table
        JPanel solutionPanel = new JPanel(new BorderLayout());
        solutionPanel.setOpaque(false);

        LineBorder Lineblue = new LineBorder(new Color(135, 206, 250), 0); 

        TitledBorder BorderedTitle = BorderFactory.createTitledBorder(Lineblue, "Optimal Selection");
        BorderedTitle.setTitleColor(Color.WHITE); 

        solutionPanel.setBorder(BorderedTitle);

        String[] solutionColumns = {"Selected Project", "Cost (₱)", "Benefit", "Category"};
        solutionTableModel = new DefaultTableModel(solutionColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        solutionTable = new JTable(solutionTableModel);
        
        JScrollPane solutionScrollPane = new JScrollPane(solutionTable);
        solutionPanel.add(solutionScrollPane, BorderLayout.CENTER);
        
        panel.add(projectPanel);
        panel.add(solutionPanel);
        
        return panel;
    }
    
    // bottom panel
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // result area
        resultArea = new JTextArea(8, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        LineBorder blueLine = new LineBorder (new Color(70, 130, 180), 0);
        TitledBorder borderedTitle = BorderFactory.createTitledBorder(blueLine, "Optimization Results");
        borderedTitle.setTitleColor(Color.WHITE);
        resultScrollPane.setOpaque(false);
        resultScrollPane.setBorder(borderedTitle);
        resultScrollPane.getViewport().setOpaque(false);

        // status label
        statusLabel = new JLabel("Ready to optimize budget allocation");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        panel.add(resultScrollPane, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // project dialog
    private void showAddProjectDialog() {
        JDialog dialog = new JDialog(this, "Add New Project", true);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(200, 220, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextField costField = new JTextField(15);
        JTextField benefitField = new JTextField(15);
        String[] categories = {"Infrastructure", "Health", "Education", "Environment", "Social Services", "Economic Development"};
        JComboBox<String> categoryCombo = new JComboBox<String>(categories);
        JTextArea descArea = new JTextArea(3, 20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Project Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Cost (₱):"), gbc);
        gbc.gridx = 1;
        dialog.add(costField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Benefit Score:"), gbc);
        gbc.gridx = 1;
        dialog.add(benefitField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        dialog.add(categoryCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        dialog.add(new JScrollPane(descArea), gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Project");
        JButton cancelButton = new JButton("Cancel");
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    double cost = Double.parseDouble(costField.getText());
                    double benefit = Double.parseDouble(benefitField.getText());
                    String category = (String) categoryCombo.getSelectedItem();
                    String description = descArea.getText().trim();
                    
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please enter a project name.");
                        return;
                    }
                    
                    if (cost <= 0 || benefit <= 0) {
                        JOptionPane.showMessageDialog(dialog, "Cost and benefit must be positive numbers.");
                        return;
                    }
                    
                    Project project = new Project(name, cost, benefit, category, description);
                    projects.add(project);
                    updateProjectTable();
                    dialog.dispose();
                    statusLabel.setText("Project added successfully. Total projects: " + projects.size());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for cost and benefit.");
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // method to optimize the budget allocation
    private void optimizeBudget() {
        try {
            double budget = Double.parseDouble(budgetField.getText());
            
            // if there's no projects 
            if (projects.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add some projects first.");
                return;
            }
            
            // if the budget is not valid
            if (budget <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive budget amount.");
                return;
            }
            
            statusLabel.setText("Optimizing budget allocation...");
            
            // run the optimization algorithm
            BranchAndBound.Solution solution = BranchAndBound.solveKnapsack(projects, budget);
            displaySolution(solution, budget);
            statusLabel.setText("Optimization completed successfully.");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid budget amount.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during optimization: " + e.getMessage());
            statusLabel.setText("Optimization failed.");
        }
    }
    
    // display the solution 
    private void displaySolution(BranchAndBound.Solution solution, double budget) {
        // update the solution panel
        solutionTableModel.setRowCount(0);
        for (int i = 0; i < solution.selectedProjects.size(); i++) {
            Project project = solution.selectedProjects.get(i);
            solutionTableModel.addRow(new Object[]{
                project.getName(),
                String.format("₱%.2f", project.getCost()),
                String.format("%.2f", project.getBenefit()),
                project.getCategory()
            });
        }
        
        // update the result area
        StringBuilder results = new StringBuilder();
        results.append("=== BUDGET ALLOCATION OPTIMIZATION RESULTS ===\n\n");
        results.append(String.format("Total Budget: ₱%.2f\n", budget));
        results.append(String.format("Allocated Amount: ₱%.2f (%.1f%%)\n", 
                                    solution.totalCost, (solution.totalCost / budget) * 100));
        results.append(String.format("Remaining Budget: ₱%.2f\n", budget - solution.totalCost));
        results.append(String.format("Total Benefit Score: %.2f\n", solution.totalBenefit));
        results.append(String.format("Efficiency Ratio: %.3f\n\n", solution.efficiency));
        
        results.append("Selected Projects:\n");
        for (int i = 0; i < 60; i++) results.append("─");
        results.append("\n");
        
        for (int i = 0; i < solution.selectedProjects.size(); i++) {
            Project project = solution.selectedProjects.get(i);
            results.append(String.format("• %s\n", project.getName()));
            results.append(String.format("  Cost: ₱%.2f | Benefit: %.2f | Category: %s\n", 
                                        project.getCost(), project.getBenefit(), project.getCategory()));
        }
        
        results.append("\n");
        for (int i = 0; i < 60; i++) results.append("─");
        results.append("\n");
        results.append(String.format("Projects Selected: %d out of %d available\n", 
                                    solution.selectedProjects.size(), projects.size()));
        
        // breakdown by category
        results.append("\nCategory Breakdown:\n");
        HashMap<String, ArrayList<Project>> categoryMap = new HashMap<String, ArrayList<Project>>();
        for (int i = 0; i < solution.selectedProjects.size(); i++) {
            Project project = solution.selectedProjects.get(i);
            String category = project.getCategory();
            if (!categoryMap.containsKey(category)) {
                categoryMap.put(category, new ArrayList<Project>());
            }
            categoryMap.get(category).add(project);
        }
        
        for (Map.Entry<String, ArrayList<Project>> entry : categoryMap.entrySet()) {
            double categoryTotal = 0;
            for (Project project : entry.getValue()) {
                categoryTotal += project.getCost();
            }
            results.append(String.format("• %s: %d projects, ₱%.2f\n", 
                                        entry.getKey(), entry.getValue().size(), categoryTotal));
        }
        
        resultArea.setText(results.toString());
        resultArea.setCaretPosition(0);
    }
    
    // update the project table
    private void updateProjectTable() {
        projectTableModel.setRowCount(0);
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            projectTableModel.addRow(new Object[]{
                project.getName(),
                String.format("₱%.2f", project.getCost()),
                String.format("%.2f", project.getBenefit()),
                String.format("%.3f", project.getBenefitCostRatio()),
                project.getCategory()
            });
        }
    }
    
    // remove the selected project 
    private void removeSelectedProject() {
        int selectedRow = projectTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < projects.size()) {
            String projectName = projects.get(selectedRow).getName();
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to remove project: " + projectName + "?", 
                "Confirm Removal", 
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                projects.remove(selectedRow);
                updateProjectTable();
                solutionTableModel.setRowCount(0);
                resultArea.setText("");
                statusLabel.setText("Project removed. Total projects: " + projects.size());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a project to remove.");
        }
    }
    
    // clear all the input projects 
    private void clearAllProjects() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all projects?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            projects.clear();
            updateProjectTable();
            solutionTableModel.setRowCount(0);
            resultArea.setText("");
            statusLabel.setText("All projects cleared.");
        }
    }

    // main method
    public static void main(String[] args) {
    // show title card first
    if (args.length == 0) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TitleCard().setVisible(true);
            }
        });
        return;
    }
    
    // the main method of the program called from the title card java file
    startBudgetAllocationGUI();
}

public static void startBudgetAllocationGUI() {
    // set the application environment
    try {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        System.err.println("Look and feel class not found: " + ex.getMessage());
    } catch (InstantiationException ex) {
        System.err.println("Could not instantiate look and feel: " + ex.getMessage());
    } catch (IllegalAccessException ex) {
        System.err.println("Could not access look and feel: " + ex.getMessage());
    } catch (UnsupportedLookAndFeelException ex) {
        System.err.println("Look and feel not supported: " + ex.getMessage());
    }
    
    // show the gui
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new BudgetAllocationGUI().setVisible(true);
        }
    });
}
}