package com.hellofresh.testutils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TestUtilities {

	/**
	 * To Generate Random Email Id
	 * @return Randomly Generated Email 
	 */
	public static String genrateRandomEmail()
	{
		String timestamp = String.valueOf(new Date().getTime());
		return "hf_challenge_" + timestamp + "@hf" + timestamp.substring(7) + ".com";
	}

	/**
	 * To Take Full Page Screenshot
	 * Not working properly with this website
	 * @param driver
	 * @return
	 */
	public static String takeFullPageScreenshot(WebDriver driver)
	{
		Date now=new Date();
		DateFormat df=new SimpleDateFormat("MMddyyyHHmmssS");
		String fileName="Screenshot"+"-"+df.format(now)+".png";
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		try {
			ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir")+"/custom-reports/screenshots/"+fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return System.getProperty("user.dir")+"/custom-reports/screenshots/"+fileName;
	}

	/**
	 * To Take full page screenshot using a different API
	 * @param driver
	 * @return
	 */
	public static String takeScreenShotUsingShutterBug(WebDriver driver)
	{
		Date now=new Date();
		DateFormat df=new SimpleDateFormat("MMddyyyHHmmssS");
		String fileName="Scr"+df.format(now)+".png";
		String outpath=System.getProperty("user.dir")+"/custom-reports/screenshots/";
		Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).withName(fileName).save(outpath);
		return outpath+fileName;

	}
	
	/**
	 * To Take screenshot - Native version
	 * @param driver
	 * @return
	 */
	public static String takeScreenshot(WebDriver driver)
	{
		Date now=new Date();
		DateFormat df=new SimpleDateFormat("MMddyyyHHmmssS");
		String filePath=System.getProperty("user.dir")+"/custom-reports/screenshots/";
		String fileName="Screenshot"+"-"+df.format(now)+".png";
		// Take screenshot and store as a file format
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);		
		// now copy the  screenshot to desired location using copyFile //method
		try {
			FileUtils.copyFile(src, new File(filePath+fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath+fileName;		
	}
	
	/**
	 * To check if an element is displayed on the page 
	 * @param driver
	 * @param webelement
	 * @return
	 */
	public static boolean checkIfElementDisplayed(WebDriver driver, WebElement webelement)
	{
		WebDriverWait wait=new WebDriverWait(driver, 30);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(webelement));
			return true;
		}
		catch (NoSuchElementException e ) {
			return false;
		}
		catch (StaleElementReferenceException f) {
			return false;
		}
	}
	
	/**
	 * To check if an element is Clickable
	 * Waits for 60 Secs
	 * @param driver
	 * @param webelement
	 * @return
	 */
	public static boolean checkIfElementClickable(WebDriver driver, WebElement webelement)
	{
		WebDriverWait wait=new WebDriverWait(driver, 60);
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(webelement));
			return true;
		}
		catch (NoSuchElementException e ) {
			return false;
		}
		catch (StaleElementReferenceException f) {
			return false;
		}
	}
}
