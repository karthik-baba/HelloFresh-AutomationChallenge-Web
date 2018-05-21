package com.hellofresh.RegressionSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hellofresh.excelfactory.ExcelUtility;
import com.hellofresh.pages.AddressesPage;
import com.hellofresh.pages.AuthenticationPage;
import com.hellofresh.pages.CartSummaryPopUpPage;
import com.hellofresh.pages.HomePage;
import com.hellofresh.pages.MyAccountPage;
import com.hellofresh.pages.OrderConfirmationPage;
import com.hellofresh.pages.OrderSummaryPage;
import com.hellofresh.pages.PaymentPage;
import com.hellofresh.pages.ProductDetailPage;
import com.hellofresh.pages.ShippingPage;
import com.hellofresh.pages.ShoppingCartSummaryPage;
import com.hellofresh.pages.WomenSectionPage;
import com.hellofresh.testdatapojo.CheckoutTestData;
import com.hellofresh.testutils.BrowserFactory;
import com.hellofresh.testutils.HtmlRecord;
import com.hellofresh.testutils.HtmlReporter;
import com.hellofresh.testutils.TestCase;
import com.hellofresh.testutils.TestConfig;
import com.hellofresh.testutils.TestUtilities;
/**
 * 
 * @author kb
 * --------------------
 * CHECKOUT TESTCASE
 * --------------------
 * Log in as existing customer
 * Click *Women* button in the header
 * Click the product with name "Faded Short Sleeve T-shirts"
 * Click *Add to card*
 * Click *Proceed to checkout*
 * Click *Proceed to checkout*
 * Click *Proceed to checkout*
 * Click by *Terms of service* to agree
 * Click *Proceed to checkout*
 * Click by payment method *Pay by bank wire*
 * Click *I confirm my order*
 */
public class CheckoutTest extends TestConfig {
	WebDriver driver;
	List<HtmlRecord> htmlRecords;
	TestCase tc;
	HtmlReporter hmReporterObj;
	BrowserFactory browserFactoryObj;

	@BeforeTest
	public void setupTest() throws FileNotFoundException, IOException
	{
		browserFactoryObj=new BrowserFactory(TestConfig.os,TestConfig.browser);
		if(browserFactoryObj != null) 
		{
			this.driver=browserFactoryObj.getWebDriver();		
		}

		htmlRecords=new ArrayList<HtmlRecord>();
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
	public void checkoutVerificationTest()
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
			e.printStackTrace();
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

		List<CheckoutTestData> excelObjLst=null;
		try 
		{
			excelObjLst=(List<CheckoutTestData>)ExcelUtility.fn_GetExcelData(this.getClass().getSimpleName().toString(), CheckoutTestData.class);
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		MyAccountPage myAccountPageObj = null;

		try
		{
			myAccountPageObj=authenticationPageObj.login(excelObjLst.get(0).getEmailId(), excelObjLst.get(0).getPassword());
			htmlRecords.add(new HtmlRecord("Verify on clicking Sign In","PASS","Expected Value ~ My Account page should be displayed <BR>Actual Value ~ My Account page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify on clicking Sign In","PASS","Expected Value ~ My Account page should be displayed <BR>Actual Value ~ Issue while logging in",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
			e.printStackTrace();
		}

		WomenSectionPage womenSectionPageObj = null;
		try
		{
			womenSectionPageObj=myAccountPageObj.clickWomenSectionLink();
			htmlRecords.add(new HtmlRecord("Verify on clicking Women section link in My Account Page","PASS","Expected Value ~ Women Section page should be displayed <BR>Actual Value ~ Women Section page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Verify on clicking Women section link in My Account Page","FAIL","Expected Value ~ Women Section page should be displayed <BR>Actual Value ~ Issue while navigating to Women section page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}


		ProductDetailPage productDetailPageObj=null;
		try
		{
			productDetailPageObj=womenSectionPageObj.clickOnDressByTitle(excelObjLst.get(0).getDressTitle(),os);
			htmlRecords.add(new HtmlRecord("Select an item and click in Women Section Page","PASS","Expected Value ~ Product Detail page for that item should be displayed <BR>Actual Value ~ Product Details page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Select an item and click in Women Section Page","FAIL","Expected Value ~ Product Detail page for that item should be displayed <BR>Actual Value ~ Issue while navigating to Product Detail Page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}
		try
		{
			productDetailPageObj.selectSize(excelObjLst.get(0).getSize());
			productDetailPageObj.fillQuantity(excelObjLst.get(0).getQuantity());
			productDetailPageObj.selectColor("Blue");
			htmlRecords.add(new HtmlRecord("Select size, quantity, color of the item","PASS","Color, size and quantity is selected",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Select size, quantity, color of the item","FAIL","Issue while selecting color, size and quantity",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		CartSummaryPopUpPage cartSummaryPopUpPageObj=null;;

		try
		{
			cartSummaryPopUpPageObj=productDetailPageObj.clickAddToCart();
			htmlRecords.add(new HtmlRecord("Click Add To Cart in Product Detail Page","PASS","Expected Value ~ Cart Summary Popup page should be displayed <BR>Actual Value ~ Cart Summary Popup page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Add To Cart in Product Detail Page","FAIL","Expected Value ~ Cart Summary Popup page should be displayed <BR>Actual Value ~ Issue while navigating to Cart Summary Popup page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		ShoppingCartSummaryPage shoppingCartSummaryPageObj=null;
		try
		{
			shoppingCartSummaryPageObj=cartSummaryPopUpPageObj.clickProceedToCheckOutInPopUpPage();
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Cart Summary Popup Page","PASS","Expected Value ~ Shopping Cart Summary page should be displayed <BR>Actual Value ~ Shopping Cart Summary page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Cart Summary Popup Page","FAIL","Expected Value ~ Shopping Cart Summary page should be displayed <BR>Actual Value ~ Issue while navigating to Shopping Cart Summary page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		AddressesPage addressesPageobj=null;
		try
		{
			addressesPageobj=shoppingCartSummaryPageObj.clickProceedToCheckoutBtn();
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Shopping Cart Summary page","PASS","Expected Value ~ Addresses page should be displayed <BR>Actual Value ~ Addresses page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Shopping Cart Summary page","FAIL","Expected Value ~ Addresses page should be displayed <BR>Actual Value ~ Issue while navigating to Addresses page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		ShippingPage shippingPageObj=null;
		try
		{
			shippingPageObj=addressesPageobj.clickProceedToCheckoutBtn();
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Addresses page","PASS","Expected Value ~ Shipping page should be displayed <BR>Actual Value ~ Shipping page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Proceed To Checkout in Addresses page","FAIL","Expected Value ~ Shipping page should be displayed <BR>Actual Value ~ Issue while navigating to Shipping page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();	
		}


		PaymentPage paymentPageObj=null;
		try
		{
			shippingPageObj.agreeTermsConditionsCheckBox();
			paymentPageObj=shippingPageObj.clickProceedToCheckoutBtn();
			htmlRecords.add(new HtmlRecord("Select Agree to Terms check box and Click Proceed To Checkout in Shipping page","PASS","Expected Value ~ Payment page should be displayed <BR>Actual Value ~ Payment page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Select Agree to Terms check box and Click Proceed To Checkout in Shipping page","FAIL","Expected Value ~ Payment page should be displayed <BR>Actual Value ~ Issue while navigating to Payment page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		OrderSummaryPage orderSummaryPageObj=null;
		try
		{
			orderSummaryPageObj=paymentPageObj.clickBankWire();
			htmlRecords.add(new HtmlRecord("Click Bank Wire in Payment Page","PASS","Expected Value ~ Order Summary page should be displayed <BR>Actual Value ~ Order Summary page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Bank Wire in Payment Page","FAIL","Expected Value ~ Order Summary page should be displayed <BR>Actual Value ~ Issue while navigating to Order Summary Page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}


		OrderConfirmationPage orderConfirmPageObj=null;
		try
		{
			orderConfirmPageObj=orderSummaryPageObj.clickConfirmOrderBtn();
			htmlRecords.add(new HtmlRecord("Click Confirm Order in Order Summary Page","PASS","Expected Value ~ Order Confirmation page should be displayed <BR>Actual Value ~ Order Confirmation page is displayed",TestUtilities.takeScreenshot(driver)));
			tc.incrementPassCount();
		}
		catch(Exception e)
		{
			htmlRecords.add(new HtmlRecord("Click Confirm Order in Order Summary Page","FAIL","Expected Value ~ Order Confirmation page should be displayed <BR>Actual Value ~ Issue while navigating to Order Confirmation page",TestUtilities.takeScreenshot(driver)));
			tc.incrementFailCount();
		}

		//Order confirmation
		if(orderConfirmPageObj.checkHeaderTxt("ORDER CONFIRMATION"))
		{
			htmlRecords.add(new HtmlRecord("Verify order confirmation text","PASS","Expected Value ~ ORDER CONFIRMATION <BR>Actual Value ~ "+orderConfirmPageObj.getHeaderTxt(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify order confirmation text","FAIL","Expected Value ~ ORDER CONFIRMATION <BR>Actual Value ~ "+orderConfirmPageObj.getHeaderTxt(),"NA"));
			tc.incrementFailCount();
		}

		//Payment step
		if(orderConfirmPageObj.isPaymentStepDisplayed())
		{
			htmlRecords.add(new HtmlRecord("Verify if process is in Payment step","PASS","Expected Value ~ true <BR>Actual Value ~ "+orderConfirmPageObj.isPaymentStepDisplayed(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify if process is in Payment step","FAIL","Expected Value ~ true <BR>Actual Value ~ "+orderConfirmPageObj.isPaymentStepDisplayed(),"NA"));
			tc.incrementFailCount();
		}

		//Last Step verification
		if(orderConfirmPageObj.isLastStepMarkerDisplayed())
		{
			htmlRecords.add(new HtmlRecord("Verify if process is in Last Step","PASS","Expected Value ~ true <BR>Actual Value ~ "+orderConfirmPageObj.isLastStepMarkerDisplayed(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify if process is in Last Step","FAIL","Expected Value ~ true <BR>Actual Value ~ "+orderConfirmPageObj.isLastStepMarkerDisplayed(),"NA"));
			tc.incrementFailCount();
		}

		//Order confirmation
		if(orderConfirmPageObj.verifyConfirmationTxt("Your order on My Store is complete."))
		{
			htmlRecords.add(new HtmlRecord("Verify order completion text","PASS","Expected Value ~ Your order on My Store is complete. <BR>Actual Value ~ "+orderConfirmPageObj.getConfirmationTxt(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify order completion text","FAIL","Expected Value ~ Your order on My Store is complete. <BR>Actual Value ~ "+orderConfirmPageObj.getConfirmationTxt(),"NA"));
			tc.incrementFailCount();
		}

		//URL
		if(orderConfirmPageObj.verifyCurrentUrl("controller=order-confirmation"))
		{
			htmlRecords.add(new HtmlRecord("Verify current url of Order Confirmation Page contains expected value","PASS","Expected Value ~ controller=order-confirmation <BR>Actual Value ~ "+orderConfirmPageObj.getCurrentUrl(),"NA"));
			tc.incrementPassCount();
		}
		else
		{
			htmlRecords.add(new HtmlRecord("Verify current url of Order Confirmation Page contains expected value","FAIL","Expected Value ~ controller=order-confirmation <BR>Actual Value ~ "+orderConfirmPageObj.getCurrentUrl(),"NA"));
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

