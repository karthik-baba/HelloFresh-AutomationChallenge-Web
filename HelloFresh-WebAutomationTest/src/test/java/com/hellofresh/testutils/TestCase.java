package com.hellofresh.testutils;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * TestCase - To store different variables during the run of the test case
 * @author kb
 *
 */
public class TestCase {
	public String testCaseName;
	private String appName;
	

	private String outputFolderPath;
	private String outputPath;
	private String StartTime;
	private long StartNano;
	private int failCount;
	private int passCount;
	private int warnCount;
	private long endNano;
	private String execDate;
	private String endTime;
	private String reportPath;
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getExecDate() {
		return execDate;
	}
	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}
	public TestCase(String testCaseName, String os)
	{
		
		this.passCount = 0;
		this.failCount = 0;
		this.warnCount = 0;
		this.StartNano = System.nanoTime();
		this.testCaseName=testCaseName;
		String dateFolderName=new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime()).toString();
		
		if(os.equals("win"))
		{
			this.reportPath=System.getProperty("user.dir")+"\\custom-reports\\" + dateFolderName + "\\" + this.testCaseName + "\\";
		}
		else			
		{
			this.reportPath=System.getProperty("user.dir")+"/custom-reports/" + dateFolderName + "/" + this.testCaseName + "/";
		}
		
		this.StartTime=new Timestamp(System.currentTimeMillis()).toString();
		this.execDate=dateFolderName;
		
	}
	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getEndNano() {
		return endNano;
	}

	public void setEndNano(long endNano) {
		this.endNano = endNano;
	}
	public void setEndNano()
	{
		endNano= System.nanoTime();
		
	}
	
	public String calculateExecTime()
	{
		long elapsedTime=endNano-StartNano;
		double secs=Math.round((elapsedTime/1000000000.0)*100)/100.0;
		return Double.toString(secs/60) + "min(s)";		
	}
	
	public String getOutputFolderPath() {
		return outputFolderPath;
	}
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
	
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	
	public long getStartNano() {
		return StartNano;
	}
	public void setStartNano(long startNano) {
		StartNano = startNano;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getWarnCount() {
		return warnCount;
	}
	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}
	
	
	public void incrementPassCount()
	{
		this.passCount++;
	}
	public void incrementFailCount()
	{
		this.failCount++;
	}
	public void incrementWarnCount()
	{
		this.warnCount++;
	}
	
	
}
