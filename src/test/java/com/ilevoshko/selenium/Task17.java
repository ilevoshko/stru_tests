package com.ilevoshko.selenium;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task17 {
    private WebDriver driver;

    @Before
    public void start(){
        driver = new ChromeDriver();
    }


    @Test
    public void browserLogsTest_1(){
        //Go to Admin login
        driver.get("http://localhost/litecart/admin/");

        //Login to admin panel
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go to Catalog page
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        //Count rows in products table minus footer
        //There is only one table so just //tr will work
        int rowCount = driver.findElements(By.xpath("//tr")).size()-1;

        //Click through products check logs
        //First product is on the 5th row, adjust if more (sub)categories present
        for (int i=5; i < rowCount; i++){
            //go to product page
            driver.findElement(By.xpath("//tr["+i+"]/td[3]/a")).click();
            //Print the product name (to know where it went wrong if it did so)
            System.out.println(driver.findElement(By.tagName("h1")).getText());
            //Check that there are no browser logs
            Assert.assertFalse(driver.manage().logs().get("browser").getAll().size()>0);
            //Go back to catalog page
            driver.navigate().back();
        }

    }


    @After
    public void stop(){
        driver.quit();
    }
}
