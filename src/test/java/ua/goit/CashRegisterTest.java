package ua.goit;

import java.util.HashMap;
import java.util.Optional;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class CashRegisterTest {
    static Warehouse shoppingCart;
    static CashRegister cashRegister;

    public CashRegisterTest() {
    }

    @BeforeClass
    public static void initClass() {
        HashMap<String, Object> warehouse = new HashMap();
        warehouse.put("A", new Product(1.5D, 3, 3.0D));
        warehouse.put("B", new Product(4.25D, 2, 8.0D));
        warehouse.put("C", new Product(1.0D, 6, 5.0D));
        warehouse.put("D", new Product(0.75D, 0, 0.0D));
        warehouse.put("E", new Product(1.5D, 2, 0.0D));
        shoppingCart = (Warehouse)Mockito.mock(Warehouse.class);
        cashRegister = new CashRegister(shoppingCart);
        Mockito.when(shoppingCart.getWarehouse()).thenReturn(warehouse);
    }

    @Test
    public void calculateProductsAmountTest() {
        HashMap<String, Integer> firstCheck = cashRegister.calculateProductsAmount("ABCDABA");
        Assert.assertEquals("calculateProductsAmount should return the correct size", 4L, (long)firstCheck.size());
        Assert.assertEquals("calculateProductsAmount should return the correct value by key", Optional.of(3), Optional.of((Integer)firstCheck.get("A")));
    }

    @Test
    public void calculateProductsAmountInDifferentRegisterTest() {
        HashMap<String, Integer> check = cashRegister.calculateProductsAmount("AaDBdb");
        Assert.assertEquals("calculateProductsAmount should return the correct size", 3L, (long)check.size());
        Assert.assertEquals("calculateProductsAmount should return the correct value by key", Optional.of(2), Optional.of((Integer)check.get("B")));
    }

    @Test
    public void calculateTotalCostByCheckWithPromotionalProductsTest() {
        double check = cashRegister.calculateTotalCost("AAABBBCd");
        Assert.assertEquals("calculateTotalCost should return the correct cost", 17.0D, check, 0.0D);
    }

    @Test
    public void calculateTotalCostByCheckWithoutPromotionalProductsTest() {
        double check = cashRegister.calculateTotalCost("AABCDE");
        Assert.assertEquals("calculateTotalCost should return the correct cost", 10.5D, check, 0.0D);
    }

    @Test
    public void calculateTotalCostByEmptyCheckTest() {
        double check = cashRegister.calculateTotalCost(" ");
        Assert.assertEquals("calculateTotalCost should return default cost", 0.0D, check, 0.0D);
    }

    @Test
    public void calculateTotalCostByCheckWithUnknownProductsTest() {
        double firstCheck = cashRegister.calculateTotalCost("MJK");
        double secondCheck = cashRegister.calculateTotalCost("ZASBCY");
        Assert.assertEquals("calculateTotalCost should return default cost", 0.0D, firstCheck, 0.0D);
        Assert.assertEquals("calculateTotalCost should return correct cost", 6.75D, secondCheck, 0.0D);
    }

    @Test
    public void calculateCostAtSpecialPriceTest() {
        double check = cashRegister.calculateCostAtSpecialPrice(8.0D, 3.0D, 2);
        Assert.assertEquals("calculateCostAtSpecialPrice should return correct cost", 8.0D, check, 0.0D);
    }

    @Test
    public void calculateCostAtSpecialPriceWhenQuantityLessSpecialTest() {
        double check = cashRegister.calculateCostAtSpecialPrice(8.0D, 1.0D, 2);
        Assert.assertEquals("calculateCostAtSpecialPrice should return default cost", 0.0D, check, 0.0D);
    }

    @Test
    public void calculateCostAtRegularPriceTest() {
        Product product = new Product(1.5D, 3, 3.0D);
        double check = cashRegister.calculateCostAtRegularPrice(product, 5.0D, 3.0D);
        Assert.assertEquals("calculateCostAtRegularPrice should return correct cost", 3.0D, check, 0.0D);
    }
}