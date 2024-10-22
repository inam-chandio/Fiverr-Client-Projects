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

public class SalesInventory extends JFrame {
    private final ArrayList<ClientOrder> orders = new ArrayList<>();
    private final DefaultTableModel tableModel;

    public SalesInventory(JFrame parent) {
        setTitle("Sales Inventory");
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
        tableModel = new DefaultTableModel(new String[]{"Description", "Filament Type", "Color(s)", "Grams Used", "Price", "Payment Status"}, 0) {
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
        JTextField descriptionField = new JTextField(10);
        JTextField filamentTypeField = new JTextField(10);
        JTextField colorField = new JTextField(10);
        JTextField gramsUsedField = new JTextField(5);
        JTextField priceField = new JTextField(5);
        JComboBox<String> paymentStatusCombo = new JComboBox<>(new String[]{"Paid", "Pending Payment"});

        // Labels
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        JLabel filamentTypeLabel = new JLabel("Filament Type:");
        filamentTypeLabel.setFont(labelFont);
        JLabel colorLabel = new JLabel("Color(s):");
        colorLabel.setFont(labelFont);
        JLabel gramsUsedLabel = new JLabel("Grams Used:");
        gramsUsedLabel.setFont(labelFont);
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont);
        JLabel paymentStatusLabel = new JLabel("Payment Status:");
        paymentStatusLabel.setFont(labelFont);

        // Add Button
        JButton addButton = new JButton("Add Order");
        addButton.setFont(buttonFont);
        addButton.setBackground(primaryColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        // Arrange components in two columns
        // First row
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(descriptionField, gbc);

        gbc.gridx = 2;
        inputPanel.add(filamentTypeLabel, gbc);

        gbc.gridx = 3;
        inputPanel.add(filamentTypeField, gbc);

        // Second row
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(colorLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(colorField, gbc);

        gbc.gridx = 2;
        inputPanel.add(gramsUsedLabel, gbc);

        gbc.gridx = 3;
        inputPanel.add(gramsUsedField, gbc);

        // Third row
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(priceField, gbc);

        gbc.gridx = 2;
        inputPanel.add(paymentStatusLabel, gbc);

        gbc.gridx = 3;
        inputPanel.add(paymentStatusCombo, gbc);

        // Add button in the fourth row
        gbc.gridx = 0;
        gbc.gridy = 3;
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
                String description = descriptionField.getText().trim();
                String filamentType = filamentTypeField.getText().trim();
                String color = colorField.getText().trim();
                double gramsUsed = Double.parseDouble(gramsUsedField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                String paymentStatus = (String) paymentStatusCombo.getSelectedItem();

                if (description.isEmpty() || filamentType.isEmpty() || color.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ClientOrder order = new ClientOrder(description, filamentType, color, gramsUsed, price, paymentStatus);
                orders.add(order);
                tableModel.addRow(new Object[]{description, filamentType, color, gramsUsed, price, paymentStatus});
                descriptionField.setText("");
                filamentTypeField.setText("");
                colorField.setText("");
                gramsUsedField.setText("");
                priceField.setText("");

                // Update product inventory
                updateProductInventory(filamentType, color, gramsUsed);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric values for 'Grams Used' and 'Price'.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void updateProductInventory(String filamentType, String color, double gramsUsed) {
        // [Implement the logic to update product inventory]
        // ...
    }


    private void exportToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Inventory");

        // Create header
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Description", "Filament Type", "Color(s)", "Grams Used", "Price", "Payment Status"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Fill data
        int rowNum = 1;
        for (ClientOrder order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getDescription());
            row.createCell(1).setCellValue(order.getFilamentType());
            row.createCell(2).setCellValue(order.getColor());
            row.createCell(3).setCellValue(order.getGramsUsed());
            row.createCell(4).setCellValue(order.getPrice());
            row.createCell(5).setCellValue(order.getPaymentStatus());
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream("SalesInventory.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(this, "Data exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data.");
        }
    }
}
