package ua.goit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CashRegister {
    private Warehouse warehouse;

    public CashRegister() {
        warehouse = new Warehouse();
    }

    public CashRegister(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public HashMap<String, Integer> calculateProductsAmount(String order) {
        HashMap<String, Integer> productsAmount = new HashMap<>();
        // better do the validation as first line before creating objects
        if(order.length() == 0) {
            return productsAmount;
        }

        for (String product : order.toUpperCase().split("")) {
            if(productsAmount.containsKey(product)) {
                int newValue = productsAmount.get(product) + 1;
                productsAmount.put(product, newValue);
            } else {
                productsAmount.put(product, 1);
            }
        }

        return productsAmount;
    }

    public double calculateTotalCost(String order) {
        Map<String, Integer> productsAmount = calculateProductsAmount(order.toUpperCase());
        final AtomicReference<Double> totalCost = new AtomicReference<>(0.00);

        for (Map.Entry<String, Integer> product : productsAmount.entrySet()) {
            String key = product.getKey();
            double value = product.getValue();
            Product productPrice = (Product) warehouse.getWarehouse().get(key);
            int promotionalAmount = productPrice.getPromotionalAmount();
            double promotionalPrice = productPrice.getPromotionalPrice();

            if(promotionalAmount > 0 && value >= promotionalAmount) {
                // I suggest decomposing such expression in extra methods
                double sum = totalCost.get() + (promotionalPrice * Math.floor(value / promotionalAmount)) + (productPrice.getPrice() * (value %promotionalAmount));
                totalCost.set(sum);
            } else {
                totalCost.set(totalCost.get() + (value * productPrice.getPrice()));
            }
        }

        return totalCost.get();
    }
}
