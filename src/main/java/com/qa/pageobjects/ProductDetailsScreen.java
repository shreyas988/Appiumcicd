package com.qa.pageobjects;

import com.qa.BaseTest;
import com.qa.MenuScreen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductDetailsScreen extends MenuScreen {
	
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	@iOSXCUITFindBy (id = "Sauce Labs Backpack")
	private MobileElement SLBTitle;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Description\"]/child::XCUIElementTypeStaticText[2]")
	private MobileElement SLBText;
	
	@AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")
	@iOSXCUITFindBy (id="test-BACK TO PRODUCTS")
	private MobileElement backToProductsButton;
	
	public String getSlbTitle() {

		return getText(SLBTitle);
		
	}
	
	
	public String getSlbText() {

		return getText(SLBText);
		
	}
	
	public ProductsScreen pressBackToProductsButton() {

		clickElement(backToProductsButton);
		return new ProductsScreen();
		
	}
	
	

}
