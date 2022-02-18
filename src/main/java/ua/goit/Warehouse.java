package ua.goit;

import java.util.HashMap;

public class Warehouse {
    private HashMap<String, Object> warehouse = new HashMap<>();

    public Warehouse() {
        warehouse.put("A", new Product(1.25, 3, 3.00 ));
        warehouse.put("B", new Product(4.25, 0, 0.00 ));
        warehouse.put("C", new Product(1.00, 6, 5.00 ));
        warehouse.put("D", new Product(0.75, 0, 0.00 ));
    }

    public Warehouse(HashMap<String, Object> warehouse) {
        this.warehouse = warehouse;
    }

    public HashMap<String, Object> getWarehouse() {
        return warehouse;
    }
}
