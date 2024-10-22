import javax.swing.*;
import java.awt.*;

public class InventoryManager extends JFrame {
    public InventoryManager() {
        setTitle("Aeternum 3D Inventory Control");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Color scheme and fonts
        Color primaryColor = new Color(0, 122, 204); // Blue
        Color secondaryColor = new Color(240, 240, 240); // Light gray
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(secondaryColor);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        buttonsPanel.setLayout(new GridLayout(2, 1, 20, 20));

        JButton productInventoryButton = new JButton("Product Inventory");
        JButton salesInventoryButton = new JButton("Sales Inventory");

        // Style buttons
        productInventoryButton.setFont(buttonFont);
        productInventoryButton.setBackground(primaryColor);
        productInventoryButton.setForeground(Color.WHITE);
        productInventoryButton.setFocusPainted(false);

        salesInventoryButton.setFont(buttonFont);
        salesInventoryButton.setBackground(primaryColor);
        salesInventoryButton.setForeground(Color.WHITE);
        salesInventoryButton.setFocusPainted(false);

        // Add buttons to panel
        buttonsPanel.add(productInventoryButton);
        buttonsPanel.add(salesInventoryButton);

        add(buttonsPanel, BorderLayout.CENTER);

        // Button actions
        productInventoryButton.addActionListener(e -> {
            new ProductInventory(this);
            dispose();
        });

        salesInventoryButton.addActionListener(e -> {
            new SalesInventory(this);
            dispose();
        });
    }
}
