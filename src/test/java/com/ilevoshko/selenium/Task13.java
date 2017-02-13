package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

public class Task13 {
    private WebDriver driver;

    private boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
    }


    @Test
    public void checkoutTest_1(){
        //Creating wait to use in tests
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Go to homepage
        driver.get("http://localhost/litecart/");

        //Add 3 products to the cart
        for(int i=1;i<4;i++){
            //CLick on first product on page (in 'most popular' section)
            driver.findElement(By.cssSelector("#box-most-popular ul li a:first-child")).click();

            //Check if the product requires size selection (Yellow Duck) and select 'Small' size
            if(areElementsPresent(driver, By.cssSelector(".options"))){
                new Select(driver.findElement(By.name("options[Size]"))).selectByValue("Small");
            }

            //Add product to the cart
            driver.findElement(By.name("add_cart_product")).click();

            //Wait until the products in cart count changes
            wait.until(textToBe(By.cssSelector(".quantity"), Integer.toString(i)));

            //Go back to previous page
            driver.navigate().back();
        }

        //Go to Checkout
        driver.findElement(By.cssSelector("#cart a.link")).click();

        //Remove the products from cart
        for(int i=1;i<4;i++){
            //Get first product name to check its removal from table later
            String productName = driver.findElement(By.cssSelector("[name='cart_form'] div p a")).getText();

            //Checking the quantity in case several identical items were added
            //Identical products are removed in batch
            int quantity = Integer.parseInt(driver.findElement(By.name("quantity")).getAttribute("value"));
            if(quantity>1){
                i=i+quantity-1;
            }

            //Remove first product from cart
            driver.findElement(By.name("remove_cart_item")).click();

            //Wait until the first product is removed from products table
            wait.until(ExpectedConditions.not(textToBe(By.cssSelector(".dataTable tr:nth-child(2) td:nth-child(2)"), productName)));
        }

    }


    @After
    public void stop(){
        driver.quit();
    }
}
