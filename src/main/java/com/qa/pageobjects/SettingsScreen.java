package com.qa.pageobjects;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsScreen extends BaseTest {
	
	@AndroidFindBy (accessibility = "test-LOGOUT")
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private MobileElement logoutButton;

	
	public LoginScreen pressLogoutButton() {
		
		clickElement(logoutButton);
		return new LoginScreen();
		
	}
}
