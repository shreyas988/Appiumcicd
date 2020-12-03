package com.qa.pageobjects;

import org.apache.logging.log4j.Level;

import com.qa.BaseTest;
import com.qa.MenuScreen;
import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsScreen extends MenuScreen {
	
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart drop zone']/android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath ="//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
	private MobileElement productTitle;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	@iOSXCUITFindBy (xpath = "	\n" + 
			"(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
	private MobileElement SLBTitle;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")
	private MobileElement SLBPrice;
	
	public String getTitle() {

		return getText(productTitle);
		
	}
	public MobileElement getElementTitle() {

		return productTitle;
		
	}
	
	public String getSLBTitle() {

		return getText(SLBTitle);
		
	}
	
	public String getSLBPrice() {

		return getText(SLBPrice);
		
	}
	
	public ProductDetailsScreen pressSLBTitle() {
		
		clickElement(SLBTitle);
		utils.log().log(Level.INFO, "SLB Title is clicked");
		return new ProductDetailsScreen();
		
	}

}
