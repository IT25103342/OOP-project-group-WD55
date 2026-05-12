package util;

import entity.Bike;
import java.util.List;
import java.util.regex.Pattern;

public class InputValidator {
    public static boolean isValidBikeId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            Pattern pattern = Pattern.compile("^[A-Za-z0-9]{3,10}$");
            if (!pattern.matcher(id.trim()).matches()) {
                System.out.println(" Error: Bike ID must be 3-10 alphanumeric characters");
                return false;
            } else {
                return true;
            }
        } else {
            System.out.println(" Error: Bike ID cannot be empty");
            return false;
        }
    }

    public static boolean isValidBikeType(String type) {
        if (type != null && !type.trim().isEmpty()) {
            String[] validTypes = new String[]{"MOUNTAIN", "ROAD", "HYBRID", "ELECTRIC", "BMX", "CRUISER"};
            String upperType = type.trim().toUpperCase();

            for(String validType : validTypes) {
                if (validType.equals(upperType)) {
                    return true;
                }
            }

            System.out.println(" Error: Invalid bike type. Valid types: MOUNTAIN, ROAD, HYBRID, ELECTRIC, BMX, CRUISER");
            return false;
        } else {
            System.out.println(" Error: Bike type cannot be empty");
            return false;
        }
    }

    public static boolean isValidBikeStatus(String status) {
        if (status != null && !status.trim().isEmpty()) {
            String[] validStatuses = new String[]{"AVAILABLE", "RENTED", "MAINTENANCE", "RESERVED"};
            String upperStatus = status.trim().toUpperCase();

            for(String validStatus : validStatuses) {
                if (validStatus.equals(upperStatus)) {
                    return true;
                }
            }

            System.out.println(" Error: Invalid status. Valid statuses: AVAILABLE, RENTED, MAINTENANCE, RESERVED");
            return false;
        } else {
            System.out.println(" Error: Bike status cannot be empty");
            return false;
        }
    }

    public static boolean isUniqueId(String id, List<Bike> bikes) {
        for(Bike bike : bikes) {
            if (bike.getId().equalsIgnoreCase(id.trim())) {
                System.out.println(" Error: Bike ID '" + id + "' already exists!");
                return false;
            }
        }

        return true;
    }
}

