package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hellofresh.testutils.TestUtilities;

/**
 * Account Creation Page Locators and Methods
 * URL-http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation
 * @author kb
 *
 */
public class AccountCreationPage {

	WebDriver driver;

	@FindBy(id="id_gender2")
	private WebElement mrsRadioBtn;

	@FindBy(id="customer_firstname")
	private WebElement firstNameTxtBox;

	@FindBy(id="customer_lastname")
	private WebElement lastNameTxtBox;

	@FindBy(id="passwd")
	private WebElement passwordTxtBox;

	@FindBy(id="days")
	private WebElement dobDaysDrpDwnBox;

	@FindBy(id="months")
	private WebElement dobMonthsDrpDwnBox;

	@FindBy(id="years")
	private WebElement dobYearsDrpDwnBox;

	@FindBy(id="company")
	private WebElement companyTxtBox;

	@FindBy(id="address1")
	private WebElement address1TxtBox;

	@FindBy(id="address2")
	private WebElement address2TxtBox;

	@FindBy(id="city")
	private WebElement cityTxtBox;

	@FindBy(id="id_state")
	private WebElement stateDrpDwnBox;

	@FindBy(id="postcode")
	private WebElement postcodeTxtBox;

	@FindBy(id="other")
	private WebElement otherTxtBox;

	@FindBy(id="phone")
	private WebElement homePhoneTxtBox;

	@FindBy(id="phone_mobile")
	private WebElement mobilePhoneTxtBox;

	@FindBy(id="alias")
	private WebElement aliasTxtBox;

	@FindBy(id="submitAccount")
	private WebElement registerBtn;

	public AccountCreationPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, mrsRadioBtn))
		{
			throw new NoSuchElementException("Account Creation page is not loaded properly");
		}
					
	}
	
	public void fillInDetails(String firstName, String lastName, String day, String month, String year, String state, String alias)
	{
		fillFirstName(firstName);
		fillLastName(lastName);
		fillPassword();
		selectDayInDob(day);
		selectMonthInDob(month);
		selectYearInDob(year);
		fillInCompanyName();
		fillInAddress1();
		fillInAddress2();
		fillInCity();
		selectState(state);
		fillInPostCode();
		fillAdditionalDetails();
		fillInHomePhone();
		fillInMobilePhone();
		fillInAlias(alias);
		
	}
	
	public void fillFirstName(String firstName)
	{
		firstNameTxtBox.sendKeys(firstName);
	}

	public void fillLastName(String lastName)
	{
		lastNameTxtBox.sendKeys(lastName);
	}
	
	public void fillPassword() 
	{
		passwordTxtBox.sendKeys(RandomStringUtils.randomAlphanumeric(6));
	}
	
	public void selectDayInDob(String day)
	{
		Select selectDay=new Select(dobDaysDrpDwnBox);
		selectDay.selectByValue(day);
	}
	
	public void selectMonthInDob(String month)
	{
		Select selectMonth=new Select(dobMonthsDrpDwnBox);
		selectMonth.selectByValue(month);
	}
	
	public void selectYearInDob(String year)
	{
		Select selectYear=new Select(dobYearsDrpDwnBox);
		selectYear.selectByValue(year);
	}
	
	public void fillInCompanyName()
	{
		companyTxtBox.sendKeys(RandomStringUtils.randomAlphabetic(10));
	}
	
	public void fillInAddress1()
	{
		address1TxtBox.sendKeys(RandomStringUtils.randomAlphanumeric(25));
	}
	
	public void fillInAddress2()
	{
		address2TxtBox.sendKeys(RandomStringUtils.randomAlphabetic(20));
	}
	
	public void fillInCity()
	{
		cityTxtBox.sendKeys(RandomStringUtils.randomAlphabetic(10));
	}
	
	public void selectState(String state)
	{
		Select stateSelect=new Select(stateDrpDwnBox);
		stateSelect.selectByVisibleText(state);
	}
	
	public void fillInPostCode()
	{
		postcodeTxtBox.sendKeys(RandomStringUtils.randomNumeric(5));
	}

	public void fillAdditionalDetails()
	{
		otherTxtBox.sendKeys(RandomStringUtils.randomAlphabetic(30));
	}
	
	public void fillInHomePhone()
	{
		homePhoneTxtBox.sendKeys(RandomStringUtils.randomNumeric(10));
	}
	
	public void fillInMobilePhone()
	{
		mobilePhoneTxtBox.sendKeys(RandomStringUtils.randomNumeric(10));
	}
	
	public void fillInAlias(String alias)
	{
		aliasTxtBox.sendKeys(alias);
	}
	
	public MyAccountPage clickRegisterBtn()
	{
		registerBtn.click();
		return new MyAccountPage(this.driver);
	}
	

}
