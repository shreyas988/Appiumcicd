package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReporter;
import com.qa.utils.TestUtils;

public class TestListener implements ITestListener {
	
	TestUtils utils = new TestUtils();

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		BaseTest base=new BaseTest();
		ExtentReporter.startTest(result.getName(), result.getMethod().getDescription())
		.assignCategory(base.getPlatform() + "_" + base.getDeviceName()).assignAuthor("Shreyas");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReporter.getTest().log(Status.PASS, "Test Passed");
		//ExtentReporter.flushTest();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
		if(result.getThrowable() != null) {
			StringWriter sw=new StringWriter();
			PrintWriter pw=new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}
		
		BaseTest base = new BaseTest();
		File file=base.getDriver().getScreenshotAs(OutputType.FILE);
		byte[] encoded=null;
		String imgScreenshot=null;
		
		try {
			encoded = FileUtils.readFileToByteArray(file);
			imgScreenshot=Base64.getEncoder().encodeToString(encoded);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		Map<String,String> params=result.getTestContext().getCurrentXmlTest().getAllParameters();
		String imagePath="Screenshots" + File.separator + params.get("platformName")+"_"+ params.get("deviceName")+"_" + base.getDateTime() +File.separator + result.getTestClass().getRealClass().getSimpleName()+
				File.separator + result.getName() +".png";
		String completeImagePath=System.getProperty("user.dir") + File.separator + imagePath;
		try {
			FileUtils.copyFile(file, new File(imagePath));
			Reporter.log("This is sample screenshot");
			Reporter.log("<a href='"+completeImagePath+"'> <img src='"+completeImagePath+"' height='100' width='100'/></a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ExtentReporter.getTest().fail("Test Failed",MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
			ExtentReporter.getTest().fail("Test Failed",MediaEntityBuilder.createScreenCaptureFromBase64String(imgScreenshot).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExtentReporter.getTest().fail(result.getThrowable());
		//ExtentReporter.flushTest();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReporter.getTest().log(Status.SKIP, "Test Skipped");
		//ExtentReporter.flushTest();
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReporter.flushTest();
		
	}
	

}
