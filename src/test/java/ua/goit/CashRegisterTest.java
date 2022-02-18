package ua.goit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

public class CashRegisterTest {
    static CashRegister shoppingCart;

    @BeforeClass
    public static void start() {
        HashMap<String, Object> warehouse = new HashMap<>();
        warehouse.put("A", new Product(1.50, 3, 3.00 ));
        warehouse.put("B", new Product(4.25, 2, 8.00 ));
        warehouse.put("C", new Product(1.00, 6, 5.00 ));
        warehouse.put("D", new Product(0.75, 0, 0.00 ));
        warehouse.put("E", new Product(1.50, 2, 0.00 ));

        shoppingCart = new CashRegister(new Warehouse(warehouse));
    }

    @Test
    public void calculateProductsAmountTest() {
        HashMap<String, Integer> firstCheck = shoppingCart.calculateProductsAmount("ABCDABA");
        HashMap<String, Integer> secondCheck = shoppingCart.calculateProductsAmount("AaDBdb");
        HashMap<String, Integer> thirdCheck = shoppingCart.calculateProductsAmount("");

        Assert.assertTrue("calculateProductsAmountTest by firstCheck size", firstCheck.size() == 4);
        Assert.assertTrue("calculateProductsAmountTest by firstCheck size", firstCheck.get("A") == 3);

        Assert.assertTrue("calculateProductsAmountTest by tripleCheck size", secondCheck.size() == 3);
        Assert.assertTrue("calculateProductsAmountTest by tripleCheck size", secondCheck.get("B") == 2);

        Assert.assertTrue("calculateProductsAmountTest by thirdCheck size", thirdCheck.size() == 0);
        Assert.assertNull("calculateProductsAmountTest by thirdCheck size", thirdCheck.get("B"));

        Assert.assertNotNull(thirdCheck);
    }

    @Test
    public void calculateTotalCost() {
        double firstCheck = shoppingCart.calculateTotalCost("AAAAaaA");
        double secondCheck = shoppingCart.calculateTotalCost("AAabbbCd");
        double thirdCheck = shoppingCart.calculateTotalCost("");
        double fourthCheck = shoppingCart.calculateTotalCost("EE");

        Assert.assertTrue("calculateTotalCost by firstCheck", firstCheck == 7.5 );
        Assert.assertTrue("calculateTotalCost by secondCheck", secondCheck == 17.0 );
        Assert.assertTrue("calculateTotalCost by thirdCheck", thirdCheck == 0.0);
        Assert.assertTrue("calculateTotalCost by fourthCheck", fourthCheck == 0.0);
    }
}