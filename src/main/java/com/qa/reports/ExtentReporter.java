package com.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	static ExtentReports extent;
	 static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public static synchronized ExtentReports getExtentReportObject() {

		String workingDir = System.getProperty("user.dir");
		System.out.println(workingDir);
		String path = workingDir + "/reports/Results.html";

		// String path=System.getProperty("user.dir")+"/reports/indextent.html";
		if(extent==null) {

		ExtentSparkReporter esp = new ExtentSparkReporter(path);
		esp.config().setReportName("MobileApp Automation Framework Results");
		esp.config().setDocumentTitle("AppiumFramework");

		extent = new ExtentReports();
		extent.attachReporter(esp);
		extent.setSystemInfo("OS", "MAC");
		}
		
		return extent;
	}
	
	
	 
	 public static synchronized ExtentTest getTest() {
	        return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	    }

	   
	    
	    public static synchronized void flushTest() {
	        extent.flush();
	        
	    }

	    public static synchronized ExtentTest startTest(String testName, String desc) {
	        ExtentTest test = getExtentReportObject().createTest(testName, desc);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	        return test;
	    }

}
