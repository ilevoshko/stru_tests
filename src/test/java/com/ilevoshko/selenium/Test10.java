package com.ilevoshko.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class Test10 {
    private WebDriver driver;

    //Helper method to 'define' the colors, a bad one
    private String redOrGrey(String rgba){

        String[] colors;

        //Parse rgba string to red, green and blue integers
        //First condition for Chrome, second for FF and Safari
        if (rgba.startsWith("rgba"))
            colors = rgba.substring(5, rgba.length() - 1 ).split(",");
        else
            colors = rgba.substring(4, rgba.length() - 1 ).split(",");

        int r = Integer.parseInt(colors[0].trim());
        int g = Integer.parseInt(colors[1].trim());
        int b = Integer.parseInt(colors[2].trim());

        //Return red if red is dominant color
        if (r>g && r>b)
            return "red";

        //Return grey if colors values match
        if (r==g && g==b)
            return "grey";

        //If neither matched
        return "error";
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @Test
    public void productParametersTest(){
        driver.get("http://localhost/litecart/");

        //Getting product name on homepage
        String hpProductName = driver.findElement(By.cssSelector("#box-campaigns .product div.name")).getText();

        //Getting homepage regular and campaign price elements
        WebElement hpRegPrice = driver.findElement(By.cssSelector("#box-campaigns .product div.price-wrapper .regular-price"));
        WebElement hpCampPrice = driver.findElement(By.cssSelector("#box-campaigns .product div.price-wrapper .campaign-price"));

        //Getting homepage regular price parameters
        String hpRegPriceValue = hpRegPrice.getText();
        String hpRegPriceColor = hpRegPrice.getCssValue("color");
        String hpRegPriceStyle = hpRegPrice.getTagName();
        int hpRegPriceSize = hpRegPrice.getSize().getHeight();

        //Getting homepage campaign price parameters
        String hpCampPriceValue = hpCampPrice.getText();
        String hpCampPriceColor = hpCampPrice.getCssValue("color");
        String hpCampPriceStyle = hpCampPrice.getTagName();
        int hpCampPriceSize = hpCampPrice.getSize().getHeight();

        //Homepage checks
        Assert.assertTrue("No strikethrough on regular price on homepage", hpRegPriceStyle.equals("s"));
        Assert.assertTrue("Campaign price on homepage not in bold", hpCampPriceStyle.equals("strong"));
        Assert.assertTrue("Campaign price is not larger than regular price on homepage",hpCampPriceSize>hpRegPriceSize);
        Assert.assertTrue("Regular price is not grey on homepage" , redOrGrey(hpRegPriceColor).equals("grey"));
        Assert.assertTrue("Campaign price is not red(ish) on homepage", redOrGrey(hpCampPriceColor).equals("red"));

        //Go to product page
        driver.findElement(By.cssSelector("#box-campaigns .product a.link")).click();

        //Getting product name on product page
        String ppProductName = driver.findElement(By.cssSelector("h1.title")).getText();

        //Getting product page regular and campaign price elements
        WebElement ppRegPrice = driver.findElement(By.cssSelector(".regular-price"));
        WebElement ppCampPrice = driver.findElement(By.cssSelector(".campaign-price"));

        //Getting product page regular price parameters
        String ppRegPriceValue = ppRegPrice.getText();
        String ppRegPriceColor = ppRegPrice.getCssValue("color");
        String ppRegPriceStyle = ppRegPrice.getTagName();
        int ppRegPriceSize = ppRegPrice.getSize().getHeight();

        //Getting product page campaign price parameters
        String ppCampPriceValue = ppCampPrice.getText();
        String ppCampPriceColor = ppCampPrice.getCssValue("color");
        String ppCampPriceStyle = ppCampPrice.getTagName();
        int ppCampPriceSize = ppCampPrice.getSize().getHeight();

        //Checks between pages
        Assert.assertTrue("Product names on homepage and product page do not match",hpProductName.equals(ppProductName));
        Assert.assertTrue("Product regular price on homepage and product page do not match",hpRegPriceValue.equals(ppRegPriceValue));
        Assert.assertTrue("Product campaign price on homepage and product page do not match",hpCampPriceValue.equals(ppCampPriceValue));

        //Product page checks
        Assert.assertTrue("No strikethrough on regular price on product page", ppRegPriceStyle.equals("s"));
        Assert.assertTrue("Campaign price on product page not in bold", ppCampPriceStyle.equals("strong"));
        Assert.assertTrue("Campaign price is not larger than regular price on product page",ppCampPriceSize>ppRegPriceSize);
        Assert.assertTrue("Regular price is not grey on product page" , redOrGrey(ppRegPriceColor).equals("grey"));
        Assert.assertTrue("Campaign price is not red(ish) on product page", redOrGrey(ppCampPriceColor).equals("red"));
    }


    @After
    public void stop(){
        driver.quit();
    }
}

