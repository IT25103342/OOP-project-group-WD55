package BikeManagement.service;

import BikeManagement.entity.Bike;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private List<Bike> bikes = new ArrayList<>();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bike_rental";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Aslan2005@";

    public InventoryService() {
        this.loadBikesFromDatabase();
    }

    private void loadBikesFromDatabase() {
        String query = "SELECT bike_id, type, status FROM bikes WHERE archived = false";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
        ) {
            this.bikes.clear();
            while(rs.next()) {
                this.bikes.add(new Bike(rs.getString("bike_id"), rs.getString("type"), rs.getString("status")));
            }
            System.out.println(" Loaded bikes from database");
        } catch (SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
    }

    public boolean addBike(String id, String type, String status) {
        String cleanId = id.trim().toUpperCase();
        String cleanType = type.trim().toUpperCase();
        String cleanStatus = status.trim().toUpperCase();

        String query = "INSERT INTO bikes (bike_id, type, status, archived) VALUES (?, ?, ?, false)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, cleanId);
            pstmt.setString(2, cleanType);
            pstmt.setString(3, cleanStatus);
            pstmt.executeUpdate();

            this.loadBikesFromDatabase();
            return true;
        } catch (SQLException e) {
            System.out.println(" Database error during insert: " + e.getMessage());
            return false;
        }
    }

    public boolean removeBike(String id) {
        String query = "UPDATE bikes SET archived = true WHERE bike_id = ?";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, id.trim().toUpperCase());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                this.bikes.removeIf((bike) -> bike.getId().equalsIgnoreCase(id.trim()));
                System.out.println(" Bike archived successfully");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
        return false;
    }

    public void searchBike(String id) {
        for(Bike bike : this.bikes) {
            if (bike.getId().equalsIgnoreCase(id.trim())) {
                System.out.println("\n Found: " + bike);
                return;
            }
        }
        System.out.println(" Bike not found with ID: " + id);
    }

    public List<Bike> getBikes() {
        this.loadBikesFromDatabase();
        return this.bikes;
    }
}