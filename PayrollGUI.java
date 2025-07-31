import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;

public class PayrollGUI extends JFrame implements ActionListener {
    JTextField nameField, idField, salaryField, extraField;
    JComboBox<String> typeBox;
    JTextArea resultArea;
    private ArrayList<Employee> employees = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> employeeJList;

    private final Color primaryColor = new Color(33, 150, 243);      // Blue
    private final Color secondaryColor = new Color(236, 239, 241);   // Light gray
    private final Color accentColor = new Color(255, 193, 7);        // Amber
    private final Color buttonHoverColor = new Color(30, 136, 229);  // Darker blue
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 15);
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
    private final Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

    public PayrollGUI() {
        setTitle("Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 430);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Employee Details", TitledBorder.LEFT, TitledBorder.TOP, titleFont, primaryColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Employee Type
        JLabel typeLabel = new JLabel("Employee Type:");
        typeLabel.setFont(mainFont);
        typeLabel.setForeground(primaryColor);
        formPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        typeBox = new JComboBox<>(new String[]{"Employee", "Manager", "HR"});
        typeBox.setFont(labelFont);
        typeBox.setToolTipText("Select employee type");
        formPanel.add(typeBox, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy++;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(mainFont);
        nameLabel.setForeground(primaryColor);
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setFont(mainFont);
        nameField.setToolTipText("Enter employee name");
        formPanel.add(nameField, gbc);

        // ID
        gbc.gridx = 0; gbc.gridy++;
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(mainFont);
        idLabel.setForeground(primaryColor);
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        idField = new JTextField();
        idField.setFont(mainFont);
        idField.setToolTipText("Enter employee ID (numbers only)");
        formPanel.add(idField, gbc);

        // Base Salary
        gbc.gridx = 0; gbc.gridy++;
        JLabel salaryLabel = new JLabel("Base Salary:");
        salaryLabel.setFont(mainFont);
        salaryLabel.setForeground(primaryColor);
        formPanel.add(salaryLabel, gbc);
        gbc.gridx = 1;
        salaryField = new JTextField();
        salaryField.setFont(mainFont);
        salaryField.setToolTipText("Enter base salary");
        formPanel.add(salaryField, gbc);

        // Bonus / Commission
        gbc.gridx = 0; gbc.gridy++;
        JLabel extraLabel = new JLabel("Bonus / Commission:");
        extraLabel.setFont(mainFont);
        extraLabel.setForeground(primaryColor);
        formPanel.add(extraLabel, gbc);
        gbc.gridx = 1;
        extraField = new JTextField();
        extraField.setFont(mainFont);
        extraField.setToolTipText("Enter bonus or commission (if any)");
        formPanel.add(extraField, gbc);

        // Calculate Button
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton calculateBtn = new JButton("Calculate Salary");
        calculateBtn.setFont(labelFont.deriveFont(Font.BOLD));
        calculateBtn.addActionListener(this);
        formPanel.add(calculateBtn, gbc);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(primaryColor);
        resultArea.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Payslip", TitledBorder.LEFT, TitledBorder.TOP, titleFont, accentColor));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Employee List Panel
        JPanel listPanel = new JPanel(new BorderLayout(5, 5));
        listPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Employee List", TitledBorder.LEFT, TitledBorder.TOP, titleFont, primaryColor));
        employeeJList = new JList<>(listModel);
        employeeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(employeeJList);
        listPanel.add(listScroll, BorderLayout.CENTER);

        // Add/Remove Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnPanel.setBackground(secondaryColor);

        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");
        JButton showPayslipBtn = new JButton("Show Payslip");
        JButton exportBtn = new JButton("Export Payslip");
        JButton exportPdfBtn = new JButton("Export as PDF");

        // Style buttons
        addBtn.setBackground(primaryColor);
        addBtn.setForeground(Color.BLACK);
        addBtn.setFont(buttonFont);
        addBtn.setBorder(new EmptyBorder(8, 18, 8, 18));

        removeBtn.setBackground(primaryColor);
        removeBtn.setForeground(Color.BLACK);
        removeBtn.setFont(buttonFont);
        removeBtn.setBorder(new EmptyBorder(8, 18, 8, 18));

        showPayslipBtn.setBackground(accentColor);
        showPayslipBtn.setForeground(Color.BLACK);
        showPayslipBtn.setFont(buttonFont);
        showPayslipBtn.setBorder(new EmptyBorder(8, 18, 8, 18));

        exportBtn.setBackground(primaryColor);
        exportBtn.setForeground(Color.BLACK);
        exportBtn.setFont(buttonFont);
        exportBtn.setBorder(new EmptyBorder(8, 18, 8, 18));

        exportPdfBtn.setBackground(primaryColor);
        exportPdfBtn.setForeground(Color.BLACK);
        exportPdfBtn.setFont(buttonFont);
        exportPdfBtn.setBorder(new EmptyBorder(8, 18, 8, 18));

        // Add hover effects
        addHoverEffect(addBtn, accentColor.darker(), primaryColor);
        addHoverEffect(removeBtn, accentColor.darker(), primaryColor);
        addHoverEffect(exportBtn, accentColor.darker(), primaryColor);
        addHoverEffect(exportPdfBtn, accentColor.darker(), primaryColor);
        addHoverEffect(showPayslipBtn, accentColor.darker(), accentColor);

        // Add buttons to panel
        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(showPayslipBtn);
        btnPanel.add(exportBtn);
        btnPanel.add(exportPdfBtn);

        listPanel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(listPanel, BorderLayout.EAST);

        // Button actions
        addBtn.addActionListener(e -> addEmployee());
        removeBtn.addActionListener(e -> removeEmployee());
        showPayslipBtn.addActionListener(e -> showSelectedPayslip());
        exportBtn.addActionListener(e -> exportSelectedPayslip());
        //exportPdfBtn.addActionListener(e -> exportSelectedPayslipAsPDF());

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText().trim();
        String idText = idField.getText().trim();
        String salaryText = salaryField.getText().trim();
        String extraText = extraField.getText().trim();

        if (name.isEmpty() || idText.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        double baseSalary, extra = 0;
        try {
            id = Integer.parseInt(idText);
            baseSalary = Double.parseDouble(salaryText);
            if (!extraText.isEmpty()) {
                extra = Double.parseDouble(extraText);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, Salary, and Extra.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee emp;
        switch (type) {
            case "Manager":
                emp = new Manager(name, id, baseSalary, extra);
                break;
            case "HR":
                emp = new HR(name, id, baseSalary, extra);
                break;
            default:
                emp = new Employee(name, id, baseSalary);
        }

        String payslip = Payslip.generate(emp);
        resultArea.setText(payslip);
    }

    private void addEmployee() {
        String type = (String) typeBox.getSelectedItem();
        String name = nameField.getText().trim();
        String idText = idField.getText().trim();
        String salaryText = salaryField.getText().trim();
        String extraText = extraField.getText().trim();

        if (name.isEmpty() || idText.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        double baseSalary, extra = 0;
        try {
            id = Integer.parseInt(idText);
            baseSalary = Double.parseDouble(salaryText);
            if (!extraText.isEmpty()) {
                extra = Double.parseDouble(extraText);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, Salary, and Extra.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee emp;
        switch (type) {
            case "Manager":
                emp = new Manager(name, id, baseSalary, extra);
                break;
            case "HR":
                emp = new HR(name, id, baseSalary, extra);
                break;
            default:
                emp = new Employee(name, id, baseSalary);
        }

        employees.add(emp);
        listModel.addElement(emp.getRole() + ": " + emp.getName() + " (ID: " + emp.getId() + ")");
        clearForm();
    }

    private void removeEmployee() {
        int idx = employeeJList.getSelectedIndex();
        if (idx >= 0) {
            employees.remove(idx);
            listModel.remove(idx);
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to remove.", "Remove Employee", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showSelectedPayslip() {
        int idx = employeeJList.getSelectedIndex();
        if (idx >= 0) {
            Employee emp = employees.get(idx);
            String payslip = Payslip.generate(emp);
            resultArea.setText(payslip);
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to view payslip.", "Show Payslip", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportSelectedPayslip() {
        int idx = employeeJList.getSelectedIndex();
        if (idx >= 0) {
            Employee emp = employees.get(idx);
            String payslip = Payslip.generate(emp);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Payslip");
            fileChooser.setSelectedFile(new java.io.File(emp.getName() + "_Payslip.txt")); // You can change to .doc if you want
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave)) {
                    fw.write(payslip);
                    JOptionPane.showMessageDialog(this, "Payslip exported successfully!", "Export", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to export payslip.", "Export Payslip", JOptionPane.WARNING_MESSAGE);
        }
    }

    /*private void exportSelectedPayslipAsPDF() {
        int idx = employeeJList.getSelectedIndex();
        if (idx >= 0) {
            Employee emp = employees.get(idx);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Payslip as PDF");
            fileChooser.setSelectedFile(new java.io.File(emp.getName() + "_Payslip.pdf"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new java.io.FileOutputStream(fileToSave));
                    document.open();

                    // Add photo (optional, keep if you want)
                    String photoPath = "photo.jpg";
                    try {
                        Image img = Image.getInstance(photoPath);
                        img.scaleToFit(80, 80);
                        img.setAlignment(Element.ALIGN_RIGHT);
                        document.add(img);
                    } catch (Exception ex) {
                        // If photo not found, skip image
                    }

                    document.add(new Paragraph("Payslip", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
                    document.add(new Paragraph(" ")); // Empty line

                    // Add payslip as plain text instead of table
                    String payslip = Payslip.generate(emp);
                    document.add(new Paragraph(payslip));

                    document.close();
                    JOptionPane.showMessageDialog(this, "Payslip exported as PDF successfully!", "Export PDF", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error saving PDF: " + ex.getMessage(), "Export PDF Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to export payslip.", "Export PDF", JOptionPane.WARNING_MESSAGE);
        }
    }*/

    private void clearForm() {
        nameField.setText("");
        idField.setText("");
        salaryField.setText("");
        extraField.setText("");
    }

    private void addHoverEffect(final JButton button, final Color hoverColor, final Color normalColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new PayrollGUI();
    }
}