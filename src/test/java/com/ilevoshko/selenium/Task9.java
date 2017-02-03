package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task9 {
    private WebDriver driver;

    //Helper method to check sorting in lists
    //Could have used a boolean here, but results would not be informative
    private String isSorted(List<String> list){
        String result;
        String previous = "";

        for (final String current: list) {
            if (current.compareTo(previous) < 0) {
                result = current;
                return result;
            }
            previous = current;
        }
        result = "Passed";
        return result;
    }

    @Before
    public void start(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @Test
    public void countrySortingTest() throws InterruptedException {
        //Login as admin
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go to country list by URL
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //Creating lists to store necessary info
        List<Integer> rowsWithZones = new ArrayList<>();
        List<String> countryList = new ArrayList<>();

        //Counting table rows
        int rowsCount = driver.findElements(By.cssSelector(".dataTable tr")).size();

        //Getting the country name and zones count from each row with information
        for (int i = 2; i<rowsCount; i++){
            String currentCountry = driver.findElement(By.cssSelector(".dataTable tr:nth-child("+i+") td:nth-child(5)")).getText();
            countryList.add(currentCountry);

            String zones = driver.findElement(By.cssSelector(".dataTable tr:nth-child("+i+") td:nth-child(6)")).getText();
            if(Integer.parseInt(zones)>0){
                rowsWithZones.add(i);
            }
        }

        //Checking that the country list is sorted and showing if anything's wrong
        Assert.assertTrue("Country out of order: "+isSorted(countryList), isSorted(countryList).equals("Passed"));

        //Going through the countries with zones and checking their sorting
        for (int row : rowsWithZones){
            //Go to a country with zones
            driver.findElement(By.cssSelector(".dataTable tr:nth-child("+row+") td:nth-child(5) a")).click();

            //Creating a list to store necessary info
            List<String> zonesList = new ArrayList<>();

            //Counting table rows
            int zoneRowsCount = driver.findElements(By.cssSelector("#table-zones tr")).size();

            //Getting zones names
            for (int j = 2; j<zoneRowsCount; j++){
                String currentZone = driver.findElement(By.cssSelector("#table-zones tr:nth-child("+j+") td:nth-child(3)")).getText();
                zonesList.add(currentZone);
            }

            //Getting the name of the country for debug purposes
            String countryName = driver.findElement(By.cssSelector("[name=name]")).getAttribute("value");

            //Checking that the zones list is sorted and showing if anything's wrong
            Assert.assertTrue("Zone out of order: "+isSorted(zonesList)+"; in country: "+countryName, isSorted(zonesList).equals("Passed"));

            //Going back to the countries list
            driver.navigate().back();
        }
    }

    @After
    public void stop(){
        driver.quit();
    }
}
