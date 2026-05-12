

package BikeManagement.controller;

import entity.Bike;
import java.util.List;
import java.util.Scanner;
import service.InventoryService;

public class Main {
    private static InventoryService inventoryService = new InventoryService();
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("      BIKE INVENTORY MANAGEMENT SYSTEM ");
        System.out.println("=".repeat(60));

        while (true) {
            displayMenu();
            switch (getInput("Enter your choice: ")) {
                case "1":
                    addBike();
                    break;
                case "2":
                    viewAllBikes();
                    break;
                case "3":
                    removeBike();
                    break;
                case "4":
                    searchBike();
                    break;
                case "5":
                    System.out.println("\n Thank you for using Bike Inventory System!");
                    System.out.println("=".repeat(60));
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(" Invalid choice! Please choose 1-5");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(60));
        System.out.println("1.  Add New Bike (CREATE)");
        System.out.println("2.  View All Bikes (READ)");
        System.out.println("3.  Remove/Archive Bike (DELETE)");
        System.out.println("4.  Search Bike by ID");
        System.out.println("5.  Exit");
        System.out.println("-".repeat(60));
    }

    private static void addBike() {
        System.out.println("\n--- ADD NEW BIKE ---");
        String id = getInput("Enter Bike ID: ");
        String type = getInput("Enter Bike Type: ");
        String status = getInput("Enter Bike Status: ");
        if (inventoryService.addBike(id, type, status)) {
            System.out.println(" Bike added successfully to Database!");
        } else {
            System.out.println(" Failed to add bike. Check validation or ID uniqueness.");
        }

        waitForEnter();
    }

    private static void viewAllBikes() {
        System.out.println("\n--- CURRENT INVENTORY ---");
        List<Bike> bikes = inventoryService.getBikes();
        if (bikes.isEmpty()) {
            System.out.println(" Database is empty (Empty set).");
        } else {
            for (Bike b : bikes) {
                System.out.println(b);
            }
        }

        waitForEnter();
    }

    private static void removeBike() {
        System.out.println("\n--- REMOVE/ARCHIVE BIKE ---");
        String id = getInput("Enter Bike ID to remove: ");
        if (inventoryService.removeBike(id)) {
            System.out.println(" Bike archived in database.");
        } else {
            System.out.println(" Bike not found.");
        }

        waitForEnter();
    }

    private static void searchBike() {
        System.out.println("\n--- SEARCH BIKE ---");
        String id = getInput("Enter Bike ID to search: ");
        inventoryService.searchBike(id);
        waitForEnter();
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    static {
        scanner = new Scanner(System.in);
    }
}