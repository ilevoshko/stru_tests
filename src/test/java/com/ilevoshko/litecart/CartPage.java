package com.ilevoshko.litecart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy (css="[name='cart_form'] div p a")
    private WebElement productTitle;

    @FindBy (name="remove_cart_item")
    private WebElement removeFromCart;

    @FindBy (name = "quantity")
    private WebElement quantityInCart;

    @FindBy (css = ".dataTable tr:nth-child(2) td:nth-child(2)")
    private WebElement firstProductInTable;

    @FindBy (xpath = "//*[@id='checkout-cart-wrapper']/p/em")
    private WebElement noItemsInCart;

   public CartPage removeFirstProduct(){
       //Get first product name to check its removal from table later
       String productName = productTitle.getText();

       //Remove first product from cart
       removeFromCart.click();

       //Wait until the first product is removed from products table or no items message appears
       wait.until(ExpectedConditions.or(
               ExpectedConditions.not(textToBePresentInElement(firstProductInTable, productName)),
               ExpectedConditions.visibilityOf(noItemsInCart)
               )
       );
       return this;
   }

   public Integer getQuantity(){
       int quantity = Integer.parseInt(quantityInCart.getAttribute("value"));
       return quantity;
   }

   public boolean verifyNoItemsInCart(){
       return noItemsInCart.isDisplayed();
   }

}
