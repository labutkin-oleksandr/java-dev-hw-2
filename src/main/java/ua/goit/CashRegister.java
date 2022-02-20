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

    public double calculateCostAtSpecialPrice(double promotionalPrice, double amount, int promotionalAmount) {
        double result = promotionalPrice * Math.floor(amount / promotionalAmount);
        return result > 0 ? result : 0.00;
    }

    public double calculateCostAtRegularPrice(Product productPrice, double amount, double promotionalAmount) {
        return productPrice.getPrice() * (amount %promotionalAmount);
    }

    public double calculateTotalCost(String order) {
        if(order.trim().length() == 0) {
            return 0.00;
        }

        Map<String, Integer> productsAmount = calculateProductsAmount(order.toUpperCase());
        final AtomicReference<Double> totalCost = new AtomicReference<>(0.00);

        for (Map.Entry<String, Integer> product : productsAmount.entrySet()) {
            String key = product.getKey();
            Product productPrice = (Product) warehouse.getWarehouse().get(key);

            if(productPrice == null) {
                continue;
            }

            double amount = product.getValue();
            int promotionalAmount = productPrice.getPromotionalAmount();
            double promotionalPrice = productPrice.getPromotionalPrice();

            if(promotionalAmount > 0 && amount >= promotionalAmount) {
                double sum = totalCost.get() + calculateCostAtSpecialPrice(promotionalPrice, amount, promotionalAmount) + calculateCostAtRegularPrice(productPrice, amount, promotionalAmount);
                totalCost.set(sum);
            } else {
                totalCost.set(totalCost.get() + (amount * productPrice.getPrice()));
            }
        }

        return totalCost.get();
    }
}
