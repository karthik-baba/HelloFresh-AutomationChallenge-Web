package com.hellofresh.testutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.ResourceUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
/**
 * 
 * @author kb
 * Basic Setup for Every Suite Run is setup here
 */
public class TestConfig {
	protected Properties prop;
	public static String appUrl;
	public static String os;
	public static String browser;

	/**
	 * Executes before the start of the test suite - 
	 * Command line argument can be passed for Environment - dev, test, prod
	 * Default Environment - test
	 * @param environment 
	 * @param context
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@BeforeSuite
	@Parameters("env")	
	public void setup(@Optional("test") String environment, ITestContext context) throws FileNotFoundException, IOException 
	{
		System.out.println("Test Execution Started");
		prop=new Properties();
		prop.load(new FileInputStream(ResourceUtils.getFile("classpath:Configuration.properties")));
		
		context.getSuite().getXmlSuite().setThreadCount(Integer.parseInt(prop.getProperty("parallelthreads","1")));

		os=prop.getProperty("os");
		browser=prop.getProperty("browser");
		
		switch(environment.toLowerCase())
		{
		case "dev":
			appUrl=prop.getProperty("devURL");
			break;
		case "test":
			appUrl=prop.getProperty("testURL");
			break;
		case "prod":
			appUrl=prop.getProperty("prodURL");
			break;
		}
	}

	@AfterSuite
	public void teardown()
	{
		prop.clear();
		System.out.println("Test Execution Completed");
	}
	
	
}
