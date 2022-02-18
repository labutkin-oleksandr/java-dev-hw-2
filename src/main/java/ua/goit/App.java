package ua.goit;

public class App {
    public static void main( String[] args ) {
        CashRegister shoppingCart = new CashRegister();
        double totalCost = shoppingCart.calculateTotalCost("ABCDABA");
        System.out.println(totalCost);
    }
}
