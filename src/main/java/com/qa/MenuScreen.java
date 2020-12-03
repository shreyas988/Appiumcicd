package com.qa;

import com.qa.BaseTest;
import com.qa.pageobjects.SettingsScreen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuScreen extends BaseTest {
	
	@AndroidFindBy (xpath="	\n" + 
			"//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
	private MobileElement settingsButton;

	
	public SettingsScreen pressSettingsButton() {
		
		clickElement(settingsButton);
		return new SettingsScreen();
		
	}
}
