package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class Task14 {
    private WebDriver driver;

    //Helper method from the lecture
    private ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows){
        return driver -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size()>0 ? handles.iterator().next() : null;
        };
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
    }


    @Test
    public void newWindowsTest_1(){
        //Creating the wait object
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //Preparing variables for window handles
        Set<String> existingWindows;
        String originalWindow;
        String newWindow;
        //Storing css locators for all the external links
        //Not the best locators, but it works
        String[] cssLocators = new String [7];
        cssLocators[0]= "tbody tr:nth-child(2) a";
        cssLocators[1]= "tbody tr:nth-child(3) a";
        cssLocators[2]= "tbody tr:nth-child(6) a";
        //Not sure why it is (3) as I can see only 2 links in the DOM there, but the locators aren't really good
        cssLocators[3]= "tbody tr:nth-child(7) a:nth-child(3)";
        cssLocators[4]= "tbody tr:nth-child(8) a";
        cssLocators[5]= "tbody tr:nth-child(9) a";
        cssLocators[6]= "tbody tr:nth-child(10) a";

        //Go to Admin login
        driver.get("http://localhost/litecart/admin/");

        //Login to admin panel
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go directly to countries page
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //Click on Add New Country
        driver.findElement(By.cssSelector("#content div a")).click();

        //Storing the original window's handle
        originalWindow = driver.getWindowHandle();

        //Going through the external links opening and closing new windows
        for (String locator : cssLocators) {
            //Getting current existing handles
            existingWindows = driver.getWindowHandles();

            //Clicking on an external link
            driver.findElement(By.cssSelector(locator)).click();

            //Getting new window's handle
            newWindow = wait.until(anyWindowOtherThan(existingWindows));

            //Switching to the new window
            driver.switchTo().window(newWindow);

            //Used this to see where links go, but it slows down the test significantly
            //System.out.println(driver.getTitle());

            //Closing current (new) window
            driver.close();

            //Switching back to original window
            driver.switchTo().window(originalWindow);
        }

    }


    @After
    public void stop(){
        driver.quit();
    }
}
