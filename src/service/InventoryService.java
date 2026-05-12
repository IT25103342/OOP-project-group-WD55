package service;

import entity.Bike;
import util.InputValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<Bike> bikes = new ArrayList();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bike_rental";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Aslan2005@";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public InventoryService() {
        this.loadBikesFromDatabase();
    }

    private void loadBikesFromDatabase() {
        String query = "SELECT bike_id, type, status FROM bikes WHERE archived = false";

        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_rental", "root", "Aslan2005@");
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
        if (InputValidator.isValidBikeId(id) && InputValidator.isUniqueId(id, this.bikes)) {
            String query = "INSERT INTO bikes (bike_id, type, status, archived) VALUES (?, ?, ?, false)";

            try {
                boolean var7;
                try (
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_rental", "root", "Aslan2005@");
                        PreparedStatement pstmt = conn.prepareStatement(query);
                ) {
                    pstmt.setString(1, id.trim().toUpperCase());
                    pstmt.setString(2, type.trim().toUpperCase());
                    pstmt.setString(3, status.trim().toUpperCase());
                    pstmt.executeUpdate();
                    this.bikes.add(new Bike(id.trim().toUpperCase(), type.trim().toUpperCase(), status.trim().toUpperCase()));
                    var7 = true;
                }

                return var7;
            } catch (SQLException e) {
                System.out.println(" Database error: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean removeBike(String id) {
        String query = "UPDATE bikes SET archived = true WHERE bike_id = ?";

        try {
            boolean var6;
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_rental", "root", "Aslan2005@")) {
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, id.trim().toUpperCase());
                    int rows = pstmt.executeUpdate();
                    if (rows <= 0) {
                        return false;
                    }

                    this.bikes.removeIf((bike) -> bike.getId().equalsIgnoreCase(id.trim()));
                    System.out.println(" Bike archived successfully");
                    var6 = true;
                }
            }

            return var6;
        } catch (SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
            return false;
        }
    }

    public void searchBike(String id) {
        for(Bike bike : this.bikes) {
            if (bike.getId().equalsIgnoreCase(id.trim())) {
                System.out.println("\n Found: " + String.valueOf(bike));
                return;
            }
        }

        System.out.println(" Bike not found with ID: " + id);
    }

    public List<Bike> getBikes() {
        return this.bikes;
    }
}

