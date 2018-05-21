package com.hellofresh.RegressionSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hellofresh.pages.AccountCreationPage;
import com.hellofresh.pages.AuthenticationPage;
import com.hellofresh.pages.HomePage;
import com.hellofresh.pages.MyAccountPage;
import com.hellofresh.testutils.BrowserFactory;
import com.hellofresh.testutils.HtmlRecord;
import com.hellofresh.testutils.HtmlReporter;
import com.hellofresh.testutils.TestCase;
import com.hellofresh.testutils.TestConfig;
import com.hellofresh.testutils.TestUtilities;

/**
 * 
 * @author kb
 * ----------------
 * SIGN IN TESTCASE
 * ----------------
 * Open [Home page](http://automationpractice.com/index.php)
 * Click *Sign in* button
 * Fill *Email address* to create an account
 * Click *Create an account* 
 * Fill all fields with correct data 
 * Click *Register* button
 *
 */
public class SignInTest extends TestConfig
{
	WebDriver driver;
	List<HtmlRecord> htmlRecords;
	TestCase tc;
	HtmlReporter hmReporterObj;
	BrowserFactory browserFactoryObj;

	@BeforeTest
	public void setupTest() throws FileNotFoundException, IOException
	{
		browserFactoryObj=new BrowserFactory(TestConfig.os,TestConfig.browser);
		if(browserFactoryObj != null) {
			this.driver=browserFactoryObj.getWebDriver();		
		}
		htmlRecords=new ArrayList();
		tc=new TestCase(this.getClass().getSimpleName().toString(),TestConfig.os);
		hmReporterObj=new HtmlReporter();

		try
		{
			tc.setOutputFolderPath(hmReporterObj.fn_CreateFolder(tc.getReportPath()));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			tc.setOutputPath(hmReporterObj.fn_CopyTemplateFiles(tc.getOutputFolderPath(),tc.getStartTime().replaceAll(":", "").replaceAll("-", "")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	@Test
	public void signInVerificationTest()
	{
		HomePage homePageObj = null;
		try
		{
			homePageObj=new HomePage(this.driver,appUrl);
			htmlRecords.add(new HtmlRecord("Verify launching url","PASS","Launched URL:"+TestConfig.appUrl,TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify launching url","FAIL","Issue in launching appurl",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		AuthenticationPage authenticationPageObj=null;
		try
		{
			authenticationPageObj=homePageObj.clickSignInLink();
			htmlRecords.add(new HtmlRecord("Verify on clicking Sign In","PASS","Expected Value ~ Authentication page should be displayed <BR>Actual Value ~ Authentication page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify on clicking Sign In","FAIL","Expected Value ~ Authentication page should be displayed <BR>Actual Value ~ Authentication page is not displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}
		try
		{
			authenticationPageObj.fillEmailId(TestUtilities.genrateRandomEmail());
			htmlRecords.add(new HtmlRecord("Fill Email Id","PASS","Random Email Id is generated and filled",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();

		}

		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Fill Email Id","FAIL","Issue in filling email id",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		AccountCreationPage accountCreationPage=null;
		try
		{
			accountCreationPage=authenticationPageObj.clickCreateAnAccount();	
			htmlRecords.add(new HtmlRecord("Verify on clicking Create Account","PASS","Expected Value ~ Account Creation page should be displayed <BR>Actual Value ~ Account Creation page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify on clicking Create Account","FAIL","Expected Value ~ Account Creation page should be displayed <BR>Actual Value ~ Account Creation page is not displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		String firstName = null,lastName = null;
		try
		{
			firstName=RandomStringUtils.randomAlphabetic(10);
			lastName=RandomStringUtils.randomAlphabetic(10);
			accountCreationPage.fillInDetails(firstName,lastName,"1","1","2000","Colorado","hf");
			htmlRecords.add(new HtmlRecord("Fill in the details","PASS","Details were filled successfully",TestUtilities.takeFullPageScreenshot(driver)));
			tc.incrementPassCount();

		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Fill in the details","FAIL","Issue in filling the details",TestUtilities.takeFullPageScreenshot(driver)));
			tc.incrementFailCount();
		}

		//Check if my account page is displayed
		MyAccountPage myAccountPageObj = null;
		try
		{
			myAccountPageObj=accountCreationPage.clickRegisterBtn();
			htmlRecords.add(new HtmlRecord("Verify on clicking Register Button","PASS","Expected Value ~ My Account page should be displayed <BR>Actual Value ~ My Account page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();

		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify on clicking Register Button","FAIL","Expected Value ~ My Account page should be displayed <BR>Actual Value ~ My Account page is not displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		//Check Header	

		if(myAccountPageObj.verifyHeaderText("MY ACCOUNT"))
		{
			htmlRecords.add(new HtmlRecord("Verify text in the Header of My Account Page","PASS","Expected Value ~ MY ACCOUNT <BR>Actual Value ~ "+myAccountPageObj.getHeaderText(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify text in the Header of My Account Page","FAIL","Expected Value ~ MY ACCOUNT <BR>Actual Value ~ "+myAccountPageObj.getHeaderText(),"NA"));
			tc.incrementFailCount();
		}

		//Check Logged In Name
		if(myAccountPageObj.verifyLoggedInName(firstName+" "+lastName))
		{
			htmlRecords.add(new HtmlRecord("Verify Full Name in My Account Page","PASS","Expected Value ~ "+firstName+" "+lastName+" <BR>Actual Value ~ "+myAccountPageObj.getLoggedInName(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify Full Name in My Account Page","FAIL","Expected Value ~ "+firstName+" "+lastName+" <BR>Actual Value ~ "+myAccountPageObj.getLoggedInName(),"NA"));
			tc.incrementFailCount();
		}

		//Check Welcome text
		if(myAccountPageObj.verifyWelcomeText("Welcome to your account."))
		{
			htmlRecords.add(new HtmlRecord("Verify Welcome text in My Account Page contains expected value","PASS","Expected Value ~ Welcome to your account. <BR>Actual Value ~ "+myAccountPageObj.getWelcomeText(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify Welcome text in My Account Page contains expected value","FAIL","Expected Value ~ Welcome to your account. <BR>Actual Value ~ "+myAccountPageObj.getWelcomeText(),"NA"));
			tc.incrementFailCount();
		}
		
		//Logout Link
		
		if(myAccountPageObj.isLogoutLinkDisplayed())
		{
			htmlRecords.add(new HtmlRecord("Verify if Logout link is displayed","PASS","Expected Value ~ true <BR>Actual Value ~ "+myAccountPageObj.isLogoutLinkDisplayed(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify if Logout link is displayed","FAIL","Expected Value ~ true <BR>Actual Value ~ "+myAccountPageObj.isLogoutLinkDisplayed(),"NA"));
			tc.incrementFailCount();
		}
		
		
		//URL
		if(myAccountPageObj.verifyCurrentUrl("controller=my-account"))
		{
			htmlRecords.add(new HtmlRecord("Verify current url of My Account Page contains expected value","PASS","Expected Value ~ controller=my-account <BR>Actual Value ~ "+myAccountPageObj.getCurrentUrl(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify current url of My Account Page contains expected value","FAIL","Expected Value ~ controller=my-account <BR>Actual Value ~ "+myAccountPageObj.getCurrentUrl(),"NA"));
			tc.incrementFailCount();
		}
		
		
		Assert.assertEquals(tc.getFailCount(), 0);

	}

	@AfterTest
	public void tearDownTest()
	{
		tc.setEndNano();
		
		tc.setEndTime(new Timestamp(System.currentTimeMillis()).toString());
		try {
			hmReporterObj.fn_WriteToHTMLReport(htmlRecords,tc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.driver.quit();
	}

}
