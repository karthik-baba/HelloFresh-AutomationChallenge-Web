package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Payment Page -  Locators and Methods
 * @author kb
 *
 */
public class PaymentPage {

	WebDriver driver;

	@FindBy(className="bankwire")
	WebElement bankWireLink;	



	public PaymentPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, bankWireLink))
		{
			throw new NoSuchElementException("Payment Page not loaded properly");
		}
	}

	public OrderSummaryPage clickBankWire()
	{
		bankWireLink.click();
		return new OrderSummaryPage(this.driver);
	}

}


