package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pageobjects.LoginScreen;
import com.qa.pageobjects.ProductsScreen;
import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest {
	
	LoginScreen loginScreen;
	ProductsScreen productsScreen;
	
	JSONObject loginUsers;
	
 
  @BeforeMethod
  public void beforeMethod(Method m) {
	  loginScreen =new LoginScreen();
	  System.out.println("\n"+ "************ Starting test : " +m.getName()+ "*********" + "/n");
  }
  

  @AfterMethod
  public void afterMethod(Method m) {
	  loginScreen =new LoginScreen();
	  System.out.println("\n"+ "************ Ending test : " +m.getName()+ "*********" + "/n");
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
  public void invalidusername() {
	  
	  loginScreen.enterUsername(loginUsers.getJSONObject("invalidUser").getString("username"));
	  loginScreen.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
	  loginScreen.clickLoginButton();
	  
	  
	 
	  String actualErrorTxt=loginScreen.getErrorText();
	  String expectedErrorTxt=getStrings().get("err_invalid_username_or_password");
	  System.out.println("Actual text is : " + actualErrorTxt + "\n" + "Expected error text is : " + expectedErrorTxt);
	  Assert.assertEquals(actualErrorTxt, expectedErrorTxt);
	  
	  
	  
  }
  @Test
  public void invalidPassword() {
	  
	  loginScreen.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
	  logInfo("Username entered");
	  
	  loginScreen.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
	  logInfo("Password entered");
	  
	  loginScreen.clickLoginButton();
	  logInfo("Login button clicked");
	  String actualErrorTxt=loginScreen.getErrorText();
	  String expectedErrorTxt=getStrings().get("err_invalid_username_or_password")+"new";
	  logInfo("Actual text is : " + actualErrorTxt);
	  logInfo("Expected error text is : " + expectedErrorTxt);
	  if(actualErrorTxt!=expectedErrorTxt) {
		  logFail("Texts are not equal");
	  }
	  
	  //System.out.println("Actual text is : " + actualErrorTxt + "\n" + "Expected error text is : " + expectedErrorTxt);
	  Assert.assertEquals(actualErrorTxt, expectedErrorTxt); 
	  
	  
  }
  @Test
  
  public void successLogin() {
	  
	  loginScreen.enterUsername(loginUsers.getJSONObject("validUser").getString("username"));
	  loginScreen.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
	  productsScreen=loginScreen.clickLoginButton();
	  
	   
		if(productsScreen.getElementTitle().isDisplayed()) {
			  System.out.println("Login successful");
		  }
		
		
}
}
