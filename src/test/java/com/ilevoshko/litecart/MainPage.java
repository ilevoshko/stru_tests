package com.ilevoshko.litecart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage extends Page{

    public MainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy (css="#box-most-popular ul li a:first-child")
    private WebElement firstPopularProduct;

    @FindBy (css="#cart a.link")
    private WebElement cartLink;

    public MainPage open(){
        driver.get("http://localhost/litecart/");
        return this;
    }

    public ProductPage clickOnFirstPopularProduct(){
        firstPopularProduct.click();
        return new ProductPage(driver);
    }

    public CartPage openCart(){
        cartLink.click();
        return new CartPage(driver);
    }

}
