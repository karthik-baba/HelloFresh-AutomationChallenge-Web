package com.hellofresh.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.hellofresh.testutils.TestUtilities;
/**
 * Product Details Page - [displayed on click the item] - Locators and Methods
 * @author kb
 *
 */
public class ProductDetailPage {
	WebDriver driver;
	
	public String genericXpathColor="//ul[@id='color_to_pick_list']/li/a[contains(@name,'DRESS_COLOR')]";
	
	@FindBy(id="quantity_wanted")
	WebElement quantityTxtBox;
	
	@FindBy(id="group_1")
	WebElement sizeDrpDown;
	
	@FindBy(name="Submit")
	WebElement addToCartBtn;
	
	public ProductDetailPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		if(!TestUtilities.checkIfElementDisplayed(driver, quantityTxtBox))
		{
			throw new NoSuchElementException("Product Detail Page not loaded properly");
		}
		
	}
	
	public WebElement findColorByName(String dressColor)
	{
		WebElement webElement=this.driver.findElement(By.xpath(genericXpathColor.replace("DRESS_COLOR", dressColor)));
		return webElement;		
	}
	
	public void selectColor(String dressColor)
	{
		findColorByName(dressColor).click();
	}
	
	public void fillQuantity(String quantity)
	{
		quantityTxtBox.clear();
		quantityTxtBox.sendKeys(quantity);
	}
	
	public void selectSize(String size)
	{
		Select sizeSelect=new Select(sizeDrpDown);
		sizeSelect.selectByVisibleText(size);
	}
	
	public CartSummaryPopUpPage clickAddToCart()
	{
		addToCartBtn.click();
		return new CartSummaryPopUpPage(this.driver);
	}
}
