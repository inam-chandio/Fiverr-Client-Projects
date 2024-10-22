import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
           //System.out.println("");
        }

        SwingUtilities.invokeLater(() -> {
            InventoryManager manager = new InventoryManager();
            manager.setVisible(true);
        });
    }
}
