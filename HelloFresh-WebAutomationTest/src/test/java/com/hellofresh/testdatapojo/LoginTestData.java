package com.hellofresh.testdatapojo;

import com.hellofresh.excelfactory.ExcelBean;
import com.hellofresh.excelfactory.ExcelColumnHeader;

/**
 * LoginTestData - Test Data for Login test case will use this Class's object
 * @author kb
 *
 */
@ExcelBean
public class LoginTestData {
	@ExcelColumnHeader(columnHeader="EmailID", dataType="String")
	private String emailId;
	
	@ExcelColumnHeader(columnHeader="Password", dataType="String")
	private String password;
	
	@ExcelColumnHeader(columnHeader="FullName", dataType="String")
	private String fullName;

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


	
	
	

}
