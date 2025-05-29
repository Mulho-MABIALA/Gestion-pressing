package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pressing_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Modifiez si nécessaire

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour afficher les commandes (console)
    public static void afficherCommandes() {
        String query = """
                    SELECT c.id, cl.nom, c.date_reception, c.date_livraison_prevue, c.statut, c.total
                    FROM commandes c
                    JOIN clients cl ON c.client_id = cl.id
                    ORDER BY c.id DESC
                """;
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String client = rs.getString("nom");
                Date reception = rs.getDate("date_reception");
                Date livraison = rs.getDate("date_livraison_prevue");
                String status = rs.getString("statut");
                double total = rs.getDouble("total");

                System.out.printf(
                        "ID: %d | Client: %s | Réception: %s | Livraison prévue: %s | Statut: %s | Total: %.2f%n",
                        id, client, reception, livraison, status, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour total ventes (SUM)
    public static String getDouble(String query) {
        // Assurez-vous que la requête est correcte et retourne un seul résultat
        if (query == null || query.trim().isEmpty()) {
            return "0";
        }
        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return String.format("%.2f", rs.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    // Méthode pour compter (COUNT)
    public static int getCount(String sql) {
        int count = 0;
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Méthode pour récupérer les commandes récentes (tableau pour Swing)
    // public static Object[][] getRecentOrders() {
    // List<Object[]> results = new ArrayList<>();
    // String query = "SELECT id, client_id, total, statut, date_reception,
    // date_livraison_prevue FROM commandes ORDER BY date_reception DESC LIMIT 10";

    // try (Connection conn = connect(); // <- CORRIGÉ ICI
    // PreparedStatement stmt = conn.prepareStatement(query);
    // ResultSet rs = stmt.executeQuery()) {

    // while (rs.next()) {
    // int id = rs.getInt("id");
    // int clientId = rs.getInt("client_id");
    // double total = rs.getDouble("total");
    // String statut = rs.getString("statut");
    // Date dateReception = rs.getDate("date_reception");
    // Date dateLivraison = rs.getDate("date_livraison_prevue");

    // results.add(new Object[] {
    // id,
    // "Client #" + clientId,
    // total,
    // statut,
    // dateReception,
    // dateLivraison,
    // "Actions"
    // });
    // }

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }

    // return results.toArray(new Object[0][]);
    // }

    // public static void supprimerCommande(int id) {
    // String sql = "DELETE FROM commandes WHERE id = ?";
    // try (Connection conn = connect();
    // PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setInt(1, id);
    // stmt.executeUpdate();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    public static Object[][] getRecentOrders() {
        List<Object[]> orders = new ArrayList<>();
        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT c.id, cl.nom, c.total, c.statut, c.date_reception, c.date_livraison_prevue " +
                                "FROM commandes c " +
                                "JOIN clients cl ON c.client_id = cl.id " +
                                "ORDER BY c.date_reception ASC " +
                                "LIMIT 10");
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(new Object[] {
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("total"),
                        rs.getString("statut"),
                        rs.getDate("date_reception"),
                        rs.getDate("date_livraison_prevue"),
                        "Supprimer/Modifier"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders.toArray(new Object[0][0]);
    }

    public static void main(String[] args) {
        afficherCommandes(); // Test console
    }
}
