package com.hellofresh.pages;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;
/**
 * Sign Page - Locators and Methods
 * @author kb
 *
 */
public class AuthenticationPage {
	WebDriver driver;
	
	@FindBy(id="email_create")
	private WebElement emailIdTextBox;
	
	@FindBy(id="SubmitCreate")
	private WebElement createAccountBtn;
	
	@FindBy(id="email")
	private WebElement existingEmailIdTxtBox;
	
	@FindBy(id="passwd")
	private WebElement existingPasswordTxtBox;
	
	@FindBy(id="SubmitLogin")
	private WebElement signInBtn;

	
	public AuthenticationPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, emailIdTextBox))
		{
			throw new ElementNotVisibleException("Authentication Page is not loaded properly");
		}
		
		
	}
	
	
	/**
	 * Function to key in the email id in the email text box
	 * @param emailId
	 */
	public void fillEmailId(String emailId)
	{
		this.emailIdTextBox.sendKeys(emailId);
	}
	
	public AccountCreationPage clickCreateAnAccount()
	{
		this.createAccountBtn.click();
		return new AccountCreationPage(this.driver);
		
	}
	
	public MyAccountPage login(String userName, String password)
	{
		existingEmailIdTxtBox.sendKeys(userName);
		existingPasswordTxtBox.sendKeys(password);
		signInBtn.click();
		return new MyAccountPage(this.driver);
	}
	
	
}
