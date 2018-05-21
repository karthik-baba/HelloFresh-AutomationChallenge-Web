package com.hellofresh.testutils;

/**
 * HtmlRecord - To store the values of each step description, status, screenshot, actual result
 * @author kb
 *
 */
public class HtmlRecord {
	private String step;
	private String status;
	private String description;
	private String screenshotPath;
	
	public HtmlRecord(String step, String status, String description, String screenshotPath) {
		super();
		this.step = step;
		this.status = status;
		this.description = description;
		this.screenshotPath = screenshotPath;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScreenshotPath() {
		return screenshotPath;
	}
	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
	@Override
	public String toString() {
		return "HtmlRecord [step=" + step + ", status=" + status + ", description=" + description + ", screenshotPath="
				+ screenshotPath + "]";
	}
	

}
