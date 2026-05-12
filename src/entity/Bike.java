package entity;

import java.io.Serializable;

public class Bike implements Serializable {
    private String id;
    private String type;
    private String status;
    private boolean isArchived;

    public Bike(String id, String type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.isArchived = false;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isArchived() {
        return this.isArchived;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setArchived(boolean archived) {
        this.isArchived = archived;
    }

    public String toString() {
        return String.format("ID: %-8s | Type: %-12s | Status: %-10s", this.id, this.type, this.status);
    }
}
