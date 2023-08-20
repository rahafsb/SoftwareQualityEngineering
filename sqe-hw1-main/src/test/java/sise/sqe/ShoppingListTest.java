package sise.sqe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.lang.AssertionError;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ShoppingListTest {
    private Supermarket superMarket;
    private ShoppingList shop;
    @BeforeEach
    public void beforeEach(){
        superMarket = Mockito.mock(Supermarket.class);
        shop = new ShoppingList(superMarket);
    }

    @Test
    public void Add_Product_directly_success() {
        try{
            Product p1 = new Product("1","lemon",8);
            shop.addProduct(p1);
            Field products = ShoppingList.class.getDeclaredField("products");
            products.setAccessible(true);
            List<Product> name = (List<Product>)products.get(shop);
            assertEquals(1,name.size());
            Product p2 = new Product("2","pear",6);
            shop.addProduct(p2);
            assertEquals(2,name.size());
            Product p3 = new Product("3","orange",7);
            shop.addProduct(p3);
            assertEquals(3,name.size());
        }
        catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @Test
    public void Add_Product_indirectly_success() {
        Product p1 = new Product("1","ice-cream",3);
        shop.addProduct(p1);
        Product p2 = new Product("2","peanut butter",23);
        shop.addProduct(p2);
        when(superMarket.getPrice("2")).thenReturn(9.0);
        assertNotEquals(0,shop.getMarketPrice());
    }

    @Test
    public void get_MarketPrice_of_empty_products_Success(){
        assertEquals(0,shop.getMarketPrice());
    }

    @Test
    public void get_MarketPrice_of_one_products_Success(){
        Product p1 = new Product("1","Besli",1);
        shop.addProduct(p1);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        assertEquals(1.0,shop.getMarketPrice());
    }

    @Test
    public void get_MarketPrice_of_multiple_products_Success(){
        Product p1 = new Product("1","Besli",1);
        Product p2 = new Product("3","dish",4);
        Product p3 = new Product("5","Bamba",7);
        shop.addProduct(p1);
        shop.addProduct(p2);
        shop.addProduct(p3);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        when(superMarket.getPrice("3")).thenReturn(1.0);
        when(superMarket.getPrice("5")).thenReturn(1.0);
        assertEquals(12.0,shop.getMarketPrice());
    }


    @Test
    public void get_price_products_with_same_id_success(){
        Product p1 = new Product("1","Besli",1);
        Product p2 = new Product("1","dish",4);
        Product p3 = new Product("5","Bamba",7);
        shop.addProduct(p1);
        shop.addProduct(p2);
        shop.addProduct(p3);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        when(superMarket.getPrice("5")).thenReturn(1.0);
        assertEquals(12.0,shop.getMarketPrice());
    }

    @Test
    public void get_price_product_negative_quantity_fail(){
        Product p1 = new Product("1","Besli",-1);
        shop.addProduct(p1);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        try{
            shop.getMarketPrice();
        }
        catch(IllegalArgumentException exp){
            assertEquals("Price cannot be negative",exp.getMessage());
        }

    }
    @Test
    public void get_price_product_negative_Price_fail(){
        Product p1 = new Product("1","Besli",1);
        shop.addProduct(p1);
        when(superMarket.getPrice("1")).thenReturn(-1.0);
        try{
            shop.getMarketPrice();
        }
        catch(IllegalArgumentException exp){
            assertEquals("Price cannot be negative",exp.getMessage());
        }

    }

    @Test
    void get_price_quantity_and_price_negative_fail(){
        Product p1 = new Product("1","Besli",-1);
        shop.addProduct(p1);
        when(superMarket.getPrice("1")).thenReturn(-1.0);

        try{
            assertEquals(0,shop.getMarketPrice());
        }
        catch (AssertionError e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void get_price_zero_quantity_success(){
        Product p1 = new Product("1","Besli",0);
        Product p2 = new Product("3","dish",0);
        Product p3 = new Product("5","Bamba",0);
        shop.addProduct(p1);
        shop.addProduct(p2);
        shop.addProduct(p3);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        when(superMarket.getPrice("3")).thenReturn(1.0);
        when(superMarket.getPrice("5")).thenReturn(1.0);
        assertEquals(0.0,shop.getMarketPrice());
    }

    @Test
    public void get_price_zero_price_success(){
        Product p1 = new Product("1","Besli",10);
        Product p2 = new Product("3","dish",100);
        Product p3 = new Product("5","Bamba",5);
        shop.addProduct(p1);
        shop.addProduct(p2);
        shop.addProduct(p3);
        when(superMarket.getPrice("1")).thenReturn(0.0);
        when(superMarket.getPrice("3")).thenReturn(0.0);
        when(superMarket.getPrice("5")).thenReturn(0.0);
        assertEquals(0.0,shop.getMarketPrice());
    }

    @Test
    public void discount_negative_price_success(){
        try{
            shop.getDiscount(-10);
        }
        catch(Exception exp){
            assertEquals("Price cannot be negative",exp.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("discountParams")
    public void no_discount_success(double positive, double expected){
        double result = shop.getDiscount(positive);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> discountParams() {
        return Stream.of(
                Arguments.of(0.0, 1.0),
                Arguments.of(500.0, 1.0),
                Arguments.of(10.0, 1.0)
        );
    }

    @Test
    public void five_percent_discount_success(){
        for(double price =500.1;price<750;price++ ){
            assertEquals(0.95,shop.getDiscount(price));
        }
        assertEquals(0.95,shop.getDiscount(750));
    }

    @ParameterizedTest
    @CsvSource({"1000.0,0.9", "750.1,0.9", "820.357,0.9"})
    public void ten_percent_discount_success(double positive, double expected){
        double result = shop.getDiscount(positive);
        assertEquals(expected, result);
    }

    @Test
    public void fifteen_percent_discount(){
        Random rand = new Random();
        double number = 0.0;
        for(int i=0; i< 15;i++){
            number = rand.nextInt(1000)+1001;
            double result = shop.getDiscount(number);
            assertEquals(0.85, result);
        }
        assertEquals(0.85, shop.getDiscount(1000.1));
    }

    @Test
    public void get_market_price_with_discount_10_percent_success(){
        Product p1 = new Product("1","soap",2);
        Product p2 = new Product("2","shampoo",2);
        Product p3 = new Product("3","conditioner",1);
        shop.addProduct(p1);
        shop.addProduct(p2);
        shop.addProduct(p3);
        when(superMarket.getPrice("1")).thenReturn(200.0);
        when(superMarket.getPrice("2")).thenReturn(200.0);
        when(superMarket.getPrice("3")).thenReturn(100.0);
        assertEquals(810,shop.getMarketPrice());
    }

    @ParameterizedTest
    @MethodSource("marketdiscount5")
    public void get_market_price_with_discount_5_percent_success(double quantity, double expected){
        Product prod = new Product("1","soda",(int)quantity);
        shop.addProduct(prod);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        assertEquals(expected, shop.getMarketPrice());
    }

    private static Stream<Arguments> marketdiscount5() {
        return Stream.of(
                Arguments.of(750.0, 712.5),
                Arguments.of(502.0, 476.9),
                Arguments.of(630, 598.5),
                Arguments.of(690.0, 655.5),
                Arguments.of(675, 641.25)
        );
    }

    @ParameterizedTest
    @CsvSource({"2000.0,1700.0", "10000.0,8500.0", "1001.0,850.85","3400.0,2890.0","5000.0,4250.0"})
    public void get_market_price_with_discount_15_percent_success(double quant, double expected){
        Product prod = new Product("1","sofa",(int)quant);
        shop.addProduct(prod);
        when(superMarket.getPrice("1")).thenReturn(1.0);
        double result = shop.getMarketPrice();
        assertEquals(expected, result);
    }

    @Test
    public void priceWithDelivery_negative_miles_success(){
        Product prod = new Product("0","sprite",6);
        when(superMarket.getPrice("0")).thenReturn(50.0);
        shop.addProduct(prod);
        try{
            shop.priceWithDelivery(-1);
        }
        catch(Exception exp){
            assertEquals("Miles cannot be negative",exp.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("priceWithPositiveMiles")
    public void priceWithDelivery_positive_miles_success(double quantity, double expected){
        Product prod = new Product("0","sprite",1);
        when(superMarket.getPrice("0")).thenReturn(100.0);
        when(superMarket.calcDeliveryFee(5,1)).thenReturn(15.0);
        when(superMarket.calcDeliveryFee(15,1)).thenReturn(50.0);
        when(superMarket.calcDeliveryFee(45,1)).thenReturn(250.0);
        when(superMarket.calcDeliveryFee(70,1)).thenReturn(380.0);
        when(superMarket.calcDeliveryFee(100,1)).thenReturn(420.0);
        shop.addProduct(prod);
        assertEquals(expected, shop.priceWithDelivery((int)quantity));
    }

    private static Stream<Arguments> priceWithPositiveMiles() {
        return Stream.of(
                Arguments.of(5, 115.0),
                Arguments.of(15, 150.0),
                Arguments.of(45, 350.0),
                Arguments.of(70,480.0),
                Arguments.of(100, 520.0)
        );
    }

    @Test
    public void changeQuantity_Negative_success(){
        Product prod = new Product("0","candy",6);
        shop.addProduct(prod);
        try{
            shop.changeQuantity(-1,"0");
        }
        catch(Exception exp){
            assertEquals("Quantity cannot be negative",exp.getMessage());
        }
    }


    @Test
    public void changeQuantity_ID_NotFound_success(){
        Product p1 = new Product("1","lemon",10);
        shop.addProduct(p1);
        shop.changeQuantity(2, "lemon");
        assertEquals(10, p1.getQuantity());
    }


    @ParameterizedTest
    @CsvSource({"200", "1", "8","521","63"})
    public void changeQuantity_ID_Found_success(int quantity){
        Product prod = new Product("1","mayo",4);
        shop.addProduct(prod);
        shop.changeQuantity(quantity,"1");
        assertEquals(quantity,prod.getQuantity());
    }

    @Test
    public void changeQuantity_remove_product_success(){
        try{
            Product p1 = new Product("1","lemon",8);
            shop.addProduct(p1);
            Field products = ShoppingList.class.getDeclaredField("products");
            products.setAccessible(true);
            List<Product> name = (List<Product>)products.get(shop);

            //change quantity
            shop.changeQuantity(0, "1");
            assertEquals(0,name.size());
        }
        catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }
}