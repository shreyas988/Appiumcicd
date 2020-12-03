package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pageobjects.LoginScreen;
import com.qa.pageobjects.ProductDetailsScreen;
import com.qa.pageobjects.ProductsScreen;
import com.qa.pageobjects.SettingsScreen;

import io.appium.java_client.MobileElement;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ProductsTest extends BaseTest {
	
	LoginScreen loginScreen;
	ProductsScreen productsScreen;
	SettingsScreen settingsScreen;
	ProductDetailsScreen productDetailsScreen;
	JSONObject loginUsers;
	
	

 
  @BeforeMethod
  public void beforeMethod(Method m) {
	  loginScreen =new LoginScreen();
	  System.out.println("\n"+ "************ Starting test : " +m.getName()+ "*********" + "/n");
	  productsScreen= loginScreen.validLogin(loginUsers.getJSONObject("validUser").getString("username"), 
				loginUsers.getJSONObject("validUser").getString("password"));
  }
  

  @AfterMethod
  public void afterMethod() {
	  settingsScreen=productsScreen.pressSettingsButton();
	  loginScreen=settingsScreen.pressLogoutButton();
  }

  @BeforeClass
  public void beforeClass() throws IOException {
	  
	  InputStream datais=null;
	  
	  try {
		String dataFileName="data/loginUsers.json";
		  datais=getClass().getClassLoader().getResourceAsStream(dataFileName);
		  JSONTokener tokener=new JSONTokener(datais);
		  loginUsers=new JSONObject(tokener);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw e;
	} finally {
		if(datais!=null) {
			datais.close();
		}
	}
	  closeApp();
	  launchApp();
  }

  @AfterClass
  public void afterClass() {
  }
  
  @Test
  public void validateProductsOnProductsScreen() {
	  
	  SoftAssert sa=new SoftAssert();
	  
	
	  String slbTitle=productsScreen.getSLBTitle();
	  sa.assertEquals(slbTitle, getStrings().get("products_screen_slb_title"));
	  String slbPrice=productsScreen.getSLBPrice();
	  sa.assertEquals(slbPrice, getStrings().get("products_screen_slb_price"));
	  
	  
	  sa.assertAll();	  
  }
  
  @Test
  public void validateProductsOnProductDetailsScreen() {
	  
	  SoftAssert sa=new SoftAssert();
	  
	  productDetailsScreen=productsScreen.pressSLBTitle();
	  
	  String slbTitle=productDetailsScreen.getSlbTitle();
	  sa.assertEquals(slbTitle, getStrings().get("product_details_screen_slb_title"));
	  String slbDescription=productDetailsScreen.getSlbText();
	  sa.assertEquals(slbDescription, getStrings().get("product_details_screen_slb_description"));
	  productsScreen = productDetailsScreen.pressBackToProductsButton();
	 
	  sa.assertAll();	  
  }
 
}
