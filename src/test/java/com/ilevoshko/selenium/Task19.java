package com.ilevoshko.selenium;

import com.ilevoshko.litecart.CartPage;
import com.ilevoshko.litecart.MainPage;
import com.ilevoshko.litecart.ProductPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task19 {
    private WebDriver driver;



    @Before
    public void start(){
        driver = new ChromeDriver();
    }

    // Basic test using page objects. No App layer.
    @Test
    public void checkoutTest_2(){
        //Go to homepage
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        //Add 3 products to the cart
        for(int i=1;i<4;i++){
            //Go to Product page
            ProductPage productPage = mainPage.clickOnFirstPopularProduct();
            //Add product to cart
            productPage.addProductToCart();
            //Go back to previous page
            productPage.goBack();
        }

        //Go to Checkout
        CartPage checkout = mainPage.openCart();

        //Remove the products from Cart
        for(int i=1;i<4;i++){
            //Checking the quantity in case several identical items were added
            //Identical products are removed in batch
            if(checkout.getQuantity()>1){
                i=i+checkout.getQuantity()-1;
            }
            //Removing product from Cart
            checkout.removeFirstProduct();
        }

        //Check that all items were removed from the Cart
        Assert.assertTrue(checkout.verifyNoItemsInCart());
    }


    @After
    public void stop(){
        driver.quit();
    }
}
