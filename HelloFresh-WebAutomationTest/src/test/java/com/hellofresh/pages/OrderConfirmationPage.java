package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Order Confirmation Page [Last Step] - Locators and Methods 
 * @author kb
 *
 */
public class OrderConfirmationPage {
	WebDriver driver;

	@FindBy(css="h1")
	WebElement headerTxt;	

	@FindBy(xpath="//li[@class='step_done step_done_last four']")
	WebElement paymentListItem;
	
	@FindBy(xpath="//li[@id='step_end' and @class='step_current last']")
	WebElement lastStepMarker;
	
	@FindBy(xpath="//*[@class='cheque-indent']/strong")
	WebElement confirmationTxt;
	
	


	public OrderConfirmationPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, headerTxt))
		{
			throw new NoSuchElementException("Order confirmation page not loaded properly");
		}
	}
	
	public String getHeaderTxt()
	{
		return headerTxt.getText();
	}
	
	public boolean checkHeaderTxt(String expValue)
	{
		return getHeaderTxt().equals(expValue);
	}
	
	public boolean isPaymentStepDisplayed()
	{
		if (TestUtilities.checkIfElementDisplayed(driver, paymentListItem))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isLastStepMarkerDisplayed()
	{
		if (TestUtilities.checkIfElementDisplayed(driver, lastStepMarker))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getConfirmationTxt()
	{
		return confirmationTxt.getText();
	}
	
	public boolean verifyConfirmationTxt(String confMsg)
	{
		return getConfirmationTxt().equals(confMsg);
	}
	
	public String getCurrentUrl()
	{
		return driver.getCurrentUrl();
	}
	
	public boolean verifyCurrentUrl(String urlSeq)
	{
		return getCurrentUrl().contains(urlSeq);
	}
}
