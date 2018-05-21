package com.hellofresh.testutils;

import java.io.File;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.util.ResourceUtils;

/**
 * Browser Factory - Handling the browser and operating systems based on Configuration.properties
 * @author kb
 *
 */

public class BrowserFactory {

	WebDriver driver;
	String os;
	String browser;
	
	public BrowserFactory(String os,String browser)
	{
		this.os=os;
		this.browser=browser;
	}

	public String getOSFolderPath()
	{
		switch(this.os.toLowerCase())
		{
		case "win":
			return "BrowserDrivers/windows/";
		case "linux":
			return "BrowserDrivers/linux/";
		case "macos":
			return "BrowserDrivers/macos/";			
		}
		return "BrowserDrivers/windows/";
	}
	

	public WebDriver getWebDriver()
	{	
		String osFolderPath=getOSFolderPath();
		switch(this.browser.toLowerCase())
		{
		case "chrome":
			if(this.os.toLowerCase().equals("win"))
			{
				try {
					System.setProperty("webdriver.chrome.driver", ResourceUtils.getFile("classpath:"+osFolderPath+"chromedriver.exe").getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				
				try {
					File chromedriverFile=ResourceUtils.getFile("classpath:"+osFolderPath+"chromedriver");
					
					chromedriverFile.setExecutable(true);
					
					System.setProperty("webdriver.chrome.driver", ResourceUtils.getFile("classpath:"+osFolderPath+"chromedriver").getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.driver=new ChromeDriver();
			break;
		case "firefox":
			if(this.os.toLowerCase().equals("win"))
			{
				try {
					System.setProperty("webdriver.gecko.driver", ResourceUtils.getFile("classpath:"+osFolderPath+"geckodriver.exe").getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					File firefoxdriverFile=ResourceUtils.getFile("classpath:"+osFolderPath+"geckodriver");
					
					firefoxdriverFile.setExecutable(true);
					System.setProperty("webdriver.gecko.driver", ResourceUtils.getFile("classpath:"+osFolderPath+"geckodriver").getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.driver=new FirefoxDriver();
			break;
		case "ie":
			if(this.os.toLowerCase().equals("win"))
			{
				try {
					System.setProperty("webdriver.ie.driver", ResourceUtils.getFile("classpath:"+osFolderPath+"IEDriverServer.exe").getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("IE driver does not work in Operating Systems other than Windows");
			}
			this.driver=new InternetExplorerDriver();
			break;
		}
		driver.manage().window().maximize();
		return this.driver;		
	}
}
