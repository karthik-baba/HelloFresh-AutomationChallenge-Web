package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Shipping Information Page - Locators and Methods
 * @author kb
 *
 */
public class ShippingPage {
	WebDriver driver;

	@FindBy(id="uniform-cgv")
	WebElement termsConditionCheckBox;
	
	@FindBy(name="processCarrier")
	WebElement proceedToCheckoutBtn;


	public ShippingPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, termsConditionCheckBox))
		{
			throw new NoSuchElementException("Shipping Page not loaded properly");
		}
	}
	
	public void agreeTermsConditionsCheckBox() 
	{
		termsConditionCheckBox.click();		
	}
	

	public PaymentPage clickProceedToCheckoutBtn()
	{
		proceedToCheckoutBtn.click();
		return new PaymentPage(this.driver);
	}
}
