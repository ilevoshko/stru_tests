package com.ilevoshko.selenium;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task7 {
    private WebDriver driver;

    private boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
    }


    @Test
    public void adminMenuTest_1(){
        //Go to Admin login
        driver.get("http://localhost/litecart/admin/");

        //Login to admin panel
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Count top menu elements
        int menuCount = driver.findElements(By.cssSelector("#box-apps-menu li")).size();

        //Click through top menu items and check for h1
        for (int i=1; i <= menuCount; i++){
            driver.findElement(By.cssSelector("#box-apps-menu li:nth-child("+i+") a")).click();
            Assert.assertTrue(areElementsPresent(driver, By.cssSelector("h1")));

            //Check if submenus exist
            boolean submenusExist = areElementsPresent(driver, By.cssSelector("#box-apps-menu li:nth-child("+i+") li"));

            //Count and click through submenus and check for h1
            if (submenusExist){
                int subMenuCount = driver.findElements(By.cssSelector("#box-apps-menu li:nth-child("+i+") li")).size();
                for (int j=1; j<=subMenuCount; j++){
                    driver.findElement(By.cssSelector("#box-apps-menu li:nth-child("+i+") li:nth-child("+j+") a")).click();
                    Assert.assertTrue(areElementsPresent(driver, By.cssSelector("h1")));
                }
            }
        }
    }


    @After
    public void stop(){
        driver.quit();
    }
}
