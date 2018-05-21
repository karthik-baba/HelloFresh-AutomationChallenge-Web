package com.hellofresh.testdatapojo;



import com.hellofresh.excelfactory.ExcelBean;
import com.hellofresh.excelfactory.ExcelColumnHeader;
/**
 * CheckOuttestData - Test Data for Checkout test case will use this Class's object
 * @author kb
 *
 */
@ExcelBean
public class CheckoutTestData {
	@ExcelColumnHeader(columnHeader="EmailID", dataType="String")
	private String emailId;
	
	@ExcelColumnHeader(columnHeader="Password", dataType="String")
	private String password;
	
	@ExcelColumnHeader(columnHeader="FullName", dataType="String")
	private String fullName;
	
	@ExcelColumnHeader(columnHeader="DressTitle", dataType="String")
	private String dressTitle;
	
	@ExcelColumnHeader(columnHeader="Quantity", dataType="String")
	private String quantity;
	
	@ExcelColumnHeader(columnHeader="Size", dataType="String")
	private String size;
	
	@ExcelColumnHeader(columnHeader="Color", dataType="String")
	private String color;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDressTitle() {
		return dressTitle;
	}

	public void setDressTitle(String dressTitle) {
		this.dressTitle = dressTitle;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	
	
	

}
