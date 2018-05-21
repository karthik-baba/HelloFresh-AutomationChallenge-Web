package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Shopping Cart Summary Page - Locators and Methods
 * @author kb
 *
 */

public class ShoppingCartSummaryPage {
	WebDriver driver;
	
	@FindBy(xpath="//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']")
	WebElement proceedToCheckOutbtn;
	
	public ShoppingCartSummaryPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, proceedToCheckOutbtn))
		{
			throw new NoSuchElementException("Shipping Cart Summary Page not loaded properly");
		}
	}
	
	public AddressesPage clickProceedToCheckoutBtn()
	{
		proceedToCheckOutbtn.click();
		return new AddressesPage(this.driver);
	}

}
