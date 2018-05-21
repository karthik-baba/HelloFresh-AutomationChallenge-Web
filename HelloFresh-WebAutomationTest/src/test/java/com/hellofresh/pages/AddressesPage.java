package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Addresses Page - Locators and Methods
 * 
 * @author kb
 *
 */
public class AddressesPage {
	WebDriver driver;

	@FindBy(name="processAddress")
	WebElement proceedToCheckOutbtn;

	public AddressesPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, proceedToCheckOutbtn))
		{
			throw new NoSuchElementException("Addresses Page not loaded properly");
		}
	}

	public ShippingPage clickProceedToCheckoutBtn()
	{
		proceedToCheckOutbtn.click();
		return new ShippingPage(this.driver);
	}
}
