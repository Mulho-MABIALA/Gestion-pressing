package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import utils.DatabaseConnection;

public class DashboardDB extends JFrame {
    private final JPanel mainPanel;

    public DashboardDB() {
        setTitle("ETS IbrahimRocha_Store - Tableau de bord");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(21, 32, 43));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        String[] menuItems = { "Tableau de bord", "Point De Vente", "Ordres", "Clients", "Profil" };
        for (String item : menuItems) {
            JButton button = createSidebarButton(item);
            button.addActionListener(e -> switchContent(item));
            sidebar.add(button);
        }

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        showDashboard();

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    private JButton createSidebarButton(String title) {
        JButton button = new JButton(" " + title);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(21, 32, 43));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(36, 50, 63));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(21, 32, 43));
            }
        });

        return button;
    }

    private void switchContent(String section) {
        mainPanel.removeAll();
        if (section.equals("Tableau de bord")) {
            showDashboard();
        } else {
            JLabel label = new JLabel("Section : " + section, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 24));
            label.setForeground(new Color(44, 62, 80));
            mainPanel.add(label, BorderLayout.CENTER);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showDashboard() {
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summaryPanel.setBackground(Color.WHITE);

        String ventes = "$ 0";
        String commandes = "0";
        String services = "0";
        String clients = "0";
        Object[][] data = new Object[0][0];

        try {
            ventes = "$ " + DatabaseConnection.getDouble("SELECT SUM(total) FROM commandes");
            commandes = String.valueOf(DatabaseConnection.getCount("SELECT COUNT(*) FROM commandes"));
            services = String.valueOf(DatabaseConnection.getCount("SELECT COUNT(*) FROM services"));
            clients = String.valueOf(DatabaseConnection.getCount("SELECT COUNT(*) FROM clients"));
            data = DatabaseConnection.getRecentOrders();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement du tableau de bord : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }

        summaryPanel.add(createSummaryCard("Ventes totales", ventes, new Color(52, 152, 219)));
        summaryPanel.add(createSummaryCard("Commandes totales", commandes, new Color(46, 204, 113)));
        summaryPanel.add(createSummaryCard("Services totaux", services, new Color(155, 89, 182)));
        summaryPanel.add(createSummaryCard("Clients totaux", clients, new Color(241, 196, 15)));

        String[] columns = {
                "Numero Commande", "Client", "Total", "Statut", "Date_Réception", "Livraison prévue", "Action"
        };

        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        content.setBackground(Color.WHITE);

        JLabel recentLabel = new JLabel("Commandes récentes", SwingConstants.CENTER);
        recentLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        recentLabel.setForeground(new Color(52, 73, 94));
        recentLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        content.add(summaryPanel, BorderLayout.NORTH);
        content.add(recentLabel, BorderLayout.CENTER);
        content.add(scrollPane, BorderLayout.SOUTH);

        mainPanel.add(content, BorderLayout.CENTER);
    }

    private JPanel createSummaryCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setPreferredSize(new Dimension(150, 80));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(valueLabel);
        card.add(Box.createVerticalGlue());
        return card;
    }

    public static void main(String[] args) {
        UIManager.put("ScrollBar.thumb", new Color(160, 160, 160));
        UIManager.put("ScrollBar.track", new Color(240, 240, 240));
        SwingUtilities.invokeLater(DashboardDB::new);
    }
}
