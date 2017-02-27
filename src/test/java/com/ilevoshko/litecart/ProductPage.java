package com.ilevoshko.litecart;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class ProductPage extends Page{

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    private boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @FindBy (css=".quantity")
    private WebElement quantityInCart;

    @FindBy (name="add_cart_product")
    private WebElement addToCart;

    @FindBy (name = "options[Size]")
    private WebElement size;

    public ProductPage addProductToCart(){
        int newCount = Integer.parseInt(quantityInCart.getText())+1;

        if(areElementsPresent(driver, By.cssSelector(".options"))){
            new Select(size).selectByValue("Small");
        }

        //Add product to the cart
        addToCart.click();

        //Wait until the products in cart count changes to +1 from original
        wait.until(textToBePresentInElement(quantityInCart, Integer.toString(newCount)));

        return this;
    }
}
