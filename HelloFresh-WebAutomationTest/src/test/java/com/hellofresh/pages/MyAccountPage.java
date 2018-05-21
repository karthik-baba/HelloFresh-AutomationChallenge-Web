package com.hellofresh.pages;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * My Account Page[displayed after sign in] - Locators and Methods
 * @author kb
 *
 */
public class MyAccountPage {
	
	WebDriver driver;
	
	@FindBy(css="h1")
	WebElement accountPageHeader;
	
	@FindBy(className="account")
	WebElement loggedInName;
	
	@FindBy(className="info-account")
	WebElement welcomeTxt;
	
	@FindBy(className="logout")
	WebElement logoutLink;
	
	@FindBy(linkText="Women")
	WebElement womenSectionLink;
	
	public MyAccountPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, accountPageHeader) & !TestUtilities.checkIfElementDisplayed(driver, womenSectionLink))
		{
			throw new ElementNotVisibleException("My Account page is not loaded properly");
		}
		
	}
	
	public boolean isDisplayed()
	{
		return accountPageHeader.isDisplayed();
	}
	
	public boolean verifyHeaderText(String text)
	{
		return getHeaderText().equals(text);
	}
	
	public String getHeaderText()
	{
		return accountPageHeader.getText();
	}
	
	public String getLoggedInName()
	{
		return loggedInName.getText();
	}
	
	public boolean verifyLoggedInName(String name)
	{
		return getLoggedInName().equals(name);
	}
	
	public String getWelcomeText()
	{
		return welcomeTxt.getText();
	}
	
	public boolean verifyWelcomeText(String seq)
	{
		return getWelcomeText().contains(seq);
	}
	
	public boolean isLogoutLinkDisplayed()
	{
		return logoutLink.isDisplayed();
	}

	public String getCurrentUrl()
	{
		return driver.getCurrentUrl();
	}
	
	public boolean verifyCurrentUrl(String urlSeq)
	{
		return getCurrentUrl().contains(urlSeq);
	}
	
	public WomenSectionPage clickWomenSectionLink()
	{
		womenSectionLink.click();
		return new WomenSectionPage(this.driver);
	}
}
