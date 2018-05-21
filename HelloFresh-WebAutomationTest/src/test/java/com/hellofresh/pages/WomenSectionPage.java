package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.testutils.TestUtilities;

/**
 * Women Section Page - Locators and Methods
 * @author kb
 *
 */
public class WomenSectionPage {
	WebDriver driver;

	public static String genricXpath="//a[@title='DRESS_TITLE']/ancestor::li";

	public static String genricXpath2="//img[@title='DRESS_TITLE']";

	@FindBy(xpath="//span[text()='Women']")
	WebElement womensectionSpan;


	public WomenSectionPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, womensectionSpan))
		{
			throw new NoSuchElementException("Women Section page not loaded properly");
		}

	}

	public WebElement findDressByTitle(String dressTitle)
	{
		WebElement webElement=this.driver.findElement(By.xpath(genricXpath.replace("DRESS_TITLE", dressTitle)));
		return webElement;		
	}
	
	public WebElement findDressByTitle2(String dressTitle)
	{
		WebElement webElement=this.driver.findElement(By.xpath(genricXpath2.replace("DRESS_TITLE", dressTitle)));
		return webElement;		
	}

	public ProductDetailPage clickOnDressByTitle(String dresstitle, String os)
	{
		if(TestUtilities.checkIfElementClickable(driver, findDressByTitle(dresstitle)))
		{
			
			if(os.equals("macos"))
			{
				findDressByTitle2(dresstitle).click();
			}
			else
			{
				findDressByTitle(dresstitle).click();

				if(driver instanceof ChromeDriver)
					findDressByTitle(dresstitle).click();
			}

		}
		return new ProductDetailPage(this.driver);
	}

}
