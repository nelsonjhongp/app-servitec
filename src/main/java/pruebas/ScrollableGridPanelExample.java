
package pruebas;

/* 🚀 Developed by NelsonJGP */
import javax.swing.*;
import java.awt.*;

public class ScrollableGridPanelExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Scrollable GridPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        int maxColumns = 3;

        mainPanel.setLayout(new GridLayout(0, maxColumns));

        int numberOfPanels = 10;

        for (int i = 0; i < numberOfPanels; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.setPreferredSize(new Dimension(200, 100));
            
            // Creamos un JLabel para enumerar el panel
            JLabel label = new JLabel("Panel " + (i + 1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            
            // Agregamos el JLabel al centro del panel
            panel.setLayout(new BorderLayout());
            panel.add(label, BorderLayout.CENTER);
            
            mainPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(scrollPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}