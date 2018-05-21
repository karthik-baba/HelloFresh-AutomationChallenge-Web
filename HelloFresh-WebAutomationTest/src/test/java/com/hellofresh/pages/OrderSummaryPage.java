package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Order Summary Page - Locators and Methods
 * @author kb
 *
 */
public class OrderSummaryPage {
	WebDriver driver;

	@FindBy(xpath="//*[@id='cart_navigation']/button")
	WebElement confirmOrderBtn;	



	public OrderSummaryPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, confirmOrderBtn))
		{
			throw new NoSuchElementException("Order Summary Page not loaded properly");
		}
	}
	
	public OrderConfirmationPage clickConfirmOrderBtn()
	{
		confirmOrderBtn.click();
		return new OrderConfirmationPage(this.driver);
	}
}
