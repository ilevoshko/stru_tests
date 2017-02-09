package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Task11 {
    private WebDriver driver;

    //Helper method to provide a timestamp for email generation
    //Change Format if you want to run the test more than once in 1 second
    //Could've used just currentTimeMillis, but this way it is easier to see when the user was registered
    private String timestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @Test
    public void registrationTest(){
        //Preparing reusable data for the test
        //Could've used a test gmail account, but using 10minutemail, no emails sent out anyway
        String testEmail = "l1899521+"+timestamp()+"@mvrht.com";
        String testPassword = "test";

        driver.get("http://localhost/litecart/");

        //Go to registration page
        //Not a good locator, but the easiest one
        driver.findElement(By.linkText("New customers click here")).click();

        //Filling required fields
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("John");
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("Doe");
        driver.findElement(By.cssSelector("input[name='address1']")).sendKeys("Beverly Hills");
        driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("90210");
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Los Angeles");

        Select countrySelect = new Select(driver.findElement(By.cssSelector("select[name='country_code']")));
        countrySelect.selectByValue("US");

        Select zoneSelect = new Select(driver.findElement(By.cssSelector("select[name='zone_code']")));
        zoneSelect.selectByValue("CA");

        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(testEmail);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+123456789012");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(testPassword);
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys(testPassword);

        //Clicking registration button
        driver.findElement(By.cssSelector("button[name='create_account']")).click();

        //Logging out
        driver.findElement(By.linkText("Logout")).click();

        //Logging in and out with the registered account
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(testEmail);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(testPassword);
        driver.findElement(By.cssSelector("button[name='login']")).click();
        driver.findElement(By.linkText("Logout")).click();
    }


    @After
    public void stop(){
        driver.quit();
    }

}
