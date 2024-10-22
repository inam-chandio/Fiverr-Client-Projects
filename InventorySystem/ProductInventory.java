import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProductInventory extends JFrame {
    private final ArrayList<Filament> filaments = new ArrayList<>();
    private final DefaultTableModel tableModel;

    public ProductInventory(JFrame parent) {
        setTitle("Product Inventory");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        // Color scheme and fonts
        Color primaryColor = new Color(0, 122, 204);
        Color secondaryColor = new Color(240, 240, 240);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        // Table model
        tableModel = new DefaultTableModel(new String[]{"Type", "Subvariant", "Color", "Total Rolls", "Remaining Grams"}, 0) {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);

        // Input panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(secondaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        JTextField typeField = new JTextField(10);
        JTextField subvariantField = new JTextField(10);
        JTextField colorField = new JTextField(10);
        JTextField totalRollsField = new JTextField(5);

        // Labels
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        JLabel subvariantLabel = new JLabel("Subvariant:");
        subvariantLabel.setFont(labelFont);
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setFont(labelFont);
        JLabel totalRollsLabel = new JLabel("Total Rolls:");
        totalRollsLabel.setFont(labelFont);

        // Add Button
        JButton addButton = new JButton("Add Filament");
        addButton.setFont(buttonFont);
        addButton.setBackground(primaryColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        // Arrange components in two columns
        // First row
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);

        gbc.gridx = 2;
        inputPanel.add(subvariantLabel, gbc);

        gbc.gridx = 3;
        inputPanel.add(subvariantField, gbc);

        // Second row
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(colorLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(colorField, gbc);

        gbc.gridx = 2;
        inputPanel.add(totalRollsLabel, gbc);

        gbc.gridx = 3;
        inputPanel.add(totalRollsField, gbc);

        // Add button in the third row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        // Bottom buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(secondaryColor);

        JButton exportButton = new JButton("Export to Excel");
        exportButton.setFont(buttonFont);
        exportButton.setBackground(primaryColor);
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);

        JButton returnButton = new JButton("Return");
        returnButton.setFont(buttonFont);
        returnButton.setBackground(primaryColor);
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusPainted(false);

        buttonsPanel.add(exportButton);
        buttonsPanel.add(returnButton);

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add Button action
        addButton.addActionListener(e -> {
            try {
                String type = typeField.getText().trim();
                String subvariant = subvariantField.getText().trim();
                String color = colorField.getText().trim();
                int totalRolls = Integer.parseInt(totalRollsField.getText().trim());

                if (type.isEmpty() || subvariant.isEmpty() || color.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Filament filament = new Filament(type, subvariant, color, totalRolls);
                filaments.add(filament);
                tableModel.addRow(new Object[]{type, subvariant, color, totalRolls, filament.getRemainingGrams()});
                typeField.setText("");
                subvariantField.setText("");
                colorField.setText("");
                totalRollsField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number for 'Total Rolls'.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Export Button action
        exportButton.addActionListener(e -> exportToExcel());

        // Return Button action
        returnButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        setVisible(true);
    }


    private void exportToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Product Inventory");

        // Create header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Type", "Subvariant", "Color", "Total Rolls", "Remaining Grams"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Fill data
        int rowNum = 1;
        for (Filament filament : filaments) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(filament.getType());
            row.createCell(1).setCellValue(filament.getSubvariant());
            row.createCell(2).setCellValue(filament.getColor());
            row.createCell(3).setCellValue(filament.getTotalRolls());
            row.createCell(4).setCellValue(filament.getRemainingGrams());
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream("ProductInventory.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(this, "Data exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data.");
        }
    }
}
