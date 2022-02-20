package ua.goit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

public class CashRegisterTest {
    static Warehouse shoppingCart;
    static CashRegister cashRegister;

    @BeforeClass
    public static void initClass() {
        HashMap<String, Object> warehouse = new HashMap<>();
        warehouse.put("A", new Product(1.50, 3, 3.00 ));
        warehouse.put("B", new Product(4.25, 2, 8.00 ));
        warehouse.put("C", new Product(1.00, 6, 5.00 ));
        warehouse.put("D", new Product(0.75, 0, 0.00 ));
        warehouse.put("E", new Product(1.50, 2, 0.00 ));

        shoppingCart = Mockito.mock(Warehouse.class);
        cashRegister = new CashRegister(shoppingCart);

        Mockito.when(shoppingCart.getWarehouse()).thenReturn(warehouse);
    }

    @Test
    public void calculateProductsAmountTest() {
        HashMap<String, Integer> firstCheck = cashRegister.calculateProductsAmount("ABCDABA");

        Assert.assertEquals("calculateProductsAmount should return the correct size", 4, firstCheck.size());
        Assert.assertEquals("calculateProductsAmount should return the correct value by key", java.util.Optional.of(3), java.util.Optional.of(firstCheck.get("A")));
    }

    @Test
    public void calculateProductsAmountInDifferentRegisterTest() {
        HashMap<String, Integer> check = cashRegister.calculateProductsAmount("AaDBdb");

        Assert.assertEquals("calculateProductsAmount should return the correct size", 3, check.size());
        Assert.assertEquals("calculateProductsAmount should return the correct value by key", java.util.Optional.of(2), java.util.Optional.of(check.get("B")));
    }

    @Test
    public void calculateTotalCostByCheckWithPromotionalProductsTest() {
        double check = cashRegister.calculateTotalCost("AAABBBCd");
        Assert.assertEquals("calculateTotalCost should return the correct cost", 17.0, check, 0);
    }

    @Test
    public void calculateTotalCostByCheckWithoutPromotionalProductsTest() {
        double check = cashRegister.calculateTotalCost("AABCDE");
        Assert.assertEquals("calculateTotalCost should return the correct cost", 10.5, check, 0);
    }

    @Test
    public void calculateTotalCostByEmptyCheckTest() {
        double check = cashRegister.calculateTotalCost(" ");
        Assert.assertEquals("calculateTotalCost should return default cost", 0.00, check, 0);
    }

    @Test
    public void calculateTotalCostByCheckWithUnknownProductsTest() {
        double firstCheck = cashRegister.calculateTotalCost("MJK");
        double secondCheck = cashRegister.calculateTotalCost("ZASBCY");
        Assert.assertEquals("calculateTotalCost should return default cost", 0.00, firstCheck, 0);
        Assert.assertEquals("calculateTotalCost should return correct cost", 6.75, secondCheck, 0);
    }

    @Test
    public void calculateCostAtSpecialPriceTest() {
        double check = cashRegister.calculateCostAtSpecialPrice(8.00,3, 2);
        Assert.assertEquals("calculateCostAtSpecialPrice should return correct cost", 8.0, check, 0);
    }

    @Test
    public void calculateCostAtSpecialPriceWhenQuantityLessSpecialTest() {
        double check = cashRegister.calculateCostAtSpecialPrice(8.00,1, 2);
        Assert.assertEquals("calculateCostAtSpecialPrice should return default cost", 0.00, check, 0);
    }

    @Test
    public void calculateCostAtRegularPriceTest() {
        Product product = new Product(1.50, 3, 3.00 );
        double check = cashRegister.calculateCostAtRegularPrice(product,5, 3);
        Assert.assertEquals("calculateCostAtRegularPrice should return correct cost", 3.0, check, 0);
    }
}