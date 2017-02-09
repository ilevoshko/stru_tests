package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Task12 {
    private WebDriver driver;

    //Helper method to provide a timestamp for product name generation
    //Change Format if you want to run the test more than once in 1 second
    //Could've used just currentTimeMillis, but this way it is easier to see when the product was created
    private String timestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    //Helper method to define if elements are on the page
    private boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }


    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }


    @Test
    public void addProductTest(){
        //Preparing product information for the test. Product name should be unique per test.
        String name = "Green Stuff "+timestamp();
        String code = "gs001";
        String quantity = "10";
        String image = System.getProperty("user.dir") + "/src/res/test_image.jpg";
        String shortDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. Cras scelerisque dui non consequat sollicitudin. Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna. Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a. Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.";
        String sku = code.toUpperCase();
        String weight = "1";
        String width = "6.00";
        String height = "10.00";
        String length = "10.00";
        String attributes = "Colors\nBody: Green\nEyes: Black\nMouth: Black\nBackground: Blue\n\nOther\nMaterial: Pure idea";
        String price = "10";

        //Login to admin panel
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go to 'Add new product' page
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2) a")).click();
        driver.findElement(By.cssSelector("#content div a:nth-child(2)")).click();

        //Click Status Active
        driver.findElement(By.cssSelector("input[name='status'][value='1']")).click();
        //Fill in name and code
        driver.findElement(By.name("name[en]")).sendKeys(name);
        driver.findElement(By.name("code")).sendKeys(code);
        //Clear quantity field and fill in the quantity
        driver.findElement(By.name("quantity")).clear();
        driver.findElement(By.name("quantity")).sendKeys(quantity);
        //upload product image
        driver.findElement(By.name("new_images[]")).sendKeys(image);

        //Switch to Information tab
        driver.findElement(By.cssSelector(".index li:nth-child(2) a")).click();
        //Select Manufacturer
        Select manufacturerSelect = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturerSelect.selectByValue("1");
        //Fill in descriptions. No sure if full description element is correct, but it works
        driver.findElement(By.name("short_description[en]")).sendKeys(shortDescription);
        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys(description);

        //Switch to Data tab
        driver.findElement(By.cssSelector(".index li:nth-child(3) a")).click();
        //Fill in SKU
        driver.findElement(By.name("sku")).sendKeys(sku);
        //Clear and fill in Weight
        driver.findElement(By.name("weight")).clear();
        driver.findElement(By.name("weight")).sendKeys(weight);
        //Clear and fill in Width
        driver.findElement(By.name("dim_x")).clear();
        driver.findElement(By.name("dim_x")).sendKeys(width);
        //Clear and fill in Height
        driver.findElement(By.name("dim_y")).clear();
        driver.findElement(By.name("dim_y")).sendKeys(height);
        //Clear and fill in Length
        driver.findElement(By.name("dim_z")).clear();
        driver.findElement(By.name("dim_z")).sendKeys(length);
        //Fill in attributes
        driver.findElement(By.name("attributes[en]")).sendKeys(attributes);

        //Switch to Price tab
        driver.findElement(By.cssSelector(".index li:nth-child(4) a")).click();
        //Set price
        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys(price);
        //Set currency to USD
        Select currencySelect = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        currencySelect.selectByValue("USD");
        //Set visible price
        driver.findElement(By.name("prices[USD]")).clear();
        driver.findElement(By.name("prices[USD]")).sendKeys(price);

        //Save the product
        driver.findElement(By.name("save")).click();

        //Go to catalog
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2) a")).click();

        //Check that the product is in catalog now
        //Could've used By.linkText(name) but it was slower in Chrome (7,663s vs 8,296s)
        Assert.assertTrue(areElementsPresent(driver, By.xpath("//*[contains(text(), '"+name+"')]")));
    }


    @After
    public void stop(){
        driver.quit();
    }
}
