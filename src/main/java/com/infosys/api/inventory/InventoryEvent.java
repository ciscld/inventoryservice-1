package com.infosys.api.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class InventoryEvent {

    private String inventoryId;
    private String name;
    private int reservedQuantity;

    public InventoryEvent(String inventoryId, String name, int reservedQuantity) {
        this.inventoryId = inventoryId;
        this.name = name;
        this.reservedQuantity = reservedQuantity;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
}
