package com.hellofresh.pages;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;
/**
 * Home Page - Locators and Methods
 * http://automationpractice.com/index.php
 * @author kb
 *
 */
public class HomePage {

	WebDriver driver;
	
	@FindBy(className="login")
	private WebElement signInLink;
	
	public HomePage(WebDriver driver, String appUrl)
	{		
		this.driver=driver;
		this.driver.get(appUrl);
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, signInLink))
		{
			throw new ElementNotVisibleException("Home page is not loaded properly");
		}
		
	}
	
	public AuthenticationPage clickSignInLink()
	{
		signInLink.click();
		return new AuthenticationPage(this.driver);
	}
}
