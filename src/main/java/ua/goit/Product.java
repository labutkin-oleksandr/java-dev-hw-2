package ua.goit;

public class Product {
    private double price;
    private int promotionalAmount;
    private double promotionalPrice;

    public Product(double price, int promotionalAmount, double promotionalPrice) {
        this.price = price;
        this.promotionalAmount = promotionalAmount;
        this.promotionalPrice = promotionalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPromotionalAmount() {
        return promotionalAmount;
    }

    public void setPromotionalAmount(int promotionalAmount) {
        this.promotionalAmount = promotionalAmount;
    }

    public double getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(double promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }
}
