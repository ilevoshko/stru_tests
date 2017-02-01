package com.ilevoshko.selenium;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Task8 {
    private WebDriver driver;


    @Before
    public void start(){
        driver = new ChromeDriver();
    }


    @Test
    public void productLabelTest_1(){
        driver.get("http://localhost/litecart/");

        //Get the list of products
        List<WebElement> products = driver.findElements(By.cssSelector(".product"));
        for (WebElement product:products) {
            //Count stickers for a product
            int stickersCount = product.findElements(By.cssSelector(".sticker")).size();
            //Check that there is 1 sticker
            Assert.assertTrue("Number of stickers found: "+stickersCount,stickersCount==1);
        }

    }


    @After
    public void stop(){
        driver.quit();
    }
}
