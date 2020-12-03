package com.qa.pageobjects;

import org.apache.logging.log4j.Level;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginScreen extends BaseTest {
	
	TestUtils utils = new TestUtils();

	@AndroidFindBy(accessibility = "test-Username")
	@iOSXCUITFindBy(id = "test-Username")
	private MobileElement userNameField;

	@AndroidFindBy(accessibility = "test-Password")
	@iOSXCUITFindBy(id = "test-Password")
	private MobileElement passwordField;

	@AndroidFindBy(accessibility = "test-LOGIN")
	@iOSXCUITFindBy(id = "test-LOGIN")
	private MobileElement loginButton;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
	private MobileElement errorText;

	// android.view.ViewGroup[@content-desc='test-Error
	// message']/android.widget.TextView")
	public LoginScreen enterUsername(String username) {

		clearFields(userNameField);
		sendKeys(userNameField, username);
		utils.log().log(Level.INFO,"Entered with username : " + username);
		return this;
	}

	public LoginScreen enterPassword(String password) {

		clearFields(passwordField);
		sendKeys(passwordField, password);
		utils.log().log(Level.INFO,"Entered with password : " + password);
		return this;
	}

	public ProductsScreen clickLoginButton() {

		clickElement(loginButton);
		utils.log().log(Level.INFO,"Login button is clicked");
		return new ProductsScreen();
	}

	public String getErrorText() {

		return getText(errorText);
	}
	
	public ProductsScreen validLogin(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		return clickLoginButton();
	}

}
