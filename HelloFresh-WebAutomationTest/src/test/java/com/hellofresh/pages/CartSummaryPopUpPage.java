package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;
/**
 * Cart Summary Pop Up[Displayed on clicking on Adding item to cart] - Locators and Methods
 * @author kb
 *
 */
public class CartSummaryPopUpPage {
	WebDriver driver;
	
	@FindBy(xpath="//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")
	WebElement confirmationTxt;
	
	@FindBy(xpath="//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']")
	WebElement proceedToCheckOutBtn;
	
	public CartSummaryPopUpPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, confirmationTxt))
		{
			throw new NoSuchElementException("Cart Summary Pop up not loaded properly");
		}
	}
	
	public ShoppingCartSummaryPage clickProceedToCheckOutInPopUpPage()
	{
		proceedToCheckOutBtn.click();
		return new ShoppingCartSummaryPage(this.driver);
	}
	
	
}
