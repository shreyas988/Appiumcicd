package com.qa;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReporter;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;



public class BaseTest {

	protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal <HashMap<String, String>>();
	
	protected static ThreadLocal<String> platform = new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
	
	TestUtils utils = new TestUtils();
	AppiumDriverLocalService server;
	int defaultPort=4723;

	
	 public BaseTest() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	 
	 public AppiumDriver getDriver() {
			return driver.get();
			
		}
	 
	 public void setDriver(AppiumDriver driver2) {
			driver.set(driver2);;
			
		}
		
		public String getDateTime() {
			return dateTime.get();
			
		}
		
		public void setDateTime( String dateTime2) {
			dateTime.set(dateTime2);
			
		}
		public String getPlatform() {
			return platform.get();
			
		}
		
		public void setPlatform( String platform2) {
			platform.set(platform2);
			
		}
		
		public Properties getProps() {
			return props.get();
			
		}
		
		public void setProps( Properties props2) {
			props.set(props2);
			
		}
		
		public HashMap<String,String> getStrings() {
			return strings.get();
			
		}
		
		public void setStrings( HashMap<String, String> strings2) {
			strings.set(strings2);
			
		}
		
		public String getDeviceName() {
			return deviceName.get();
			
		}
		
		public void setDeviceName( String deviceName2) {
			deviceName.set(deviceName2);
		}
	 
	 
	 @BeforeSuite
	 public void beforeSuite() {
		 server=getAppiumService();
		 if(!checkIfServerIsRunnning(defaultPort)) {
		 server.start();
		 }
		 
	 }
	 
	 @AfterSuite
	 public void afterSuite() {
		 
		 server.stop();
		 
	 }
	 
	 public AppiumDriverLocalService getAppiumServerDefault() {
		 
		 return AppiumDriverLocalService.buildDefaultService();
	 }
	 
public AppiumDriverLocalService getAppiumService() {
	
	HashMap<String, String> environment=new HashMap<String, String>();
	String pathVariable="/Users/shreyas/opt/anaconda3/bin:/Users/shreyas/opt/anaconda3/condabin:/usr/local/opt/openssl@1.1/bin:/usr/local/opt/openssl@1.1/bin:/Library/Java/JavaVirtualMachines/jdk-14.0.1.jdk/Contents/Home/bin:/Users/shreyas/Library/Android/sdk/tools:/Users/shreyas/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Users/shreyas/Library/Android/sdk/platform-tools:/Users/shreyas/Library/Android/sdk/tools:/Users/shreyas/Library/Android/sdk:/Library/Apple/usr/bin";
	environment.put("PATH", pathVariable);
	environment.put("ANDROID_HOME", "/Users/shreyas/Library/Android/sdk/");
	environment.put("JAVA_HOME", "/Library/Java/JavaVirtualMachines/jdk-14.0.1.jdk/Contents/Home");
	
		 
		 return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				 .usingDriverExecutable(new File("/usr/local/bin/node"))
				 .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				 .usingPort(4723)
				 .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				 .withEnvironment(environment)
				 .withLogFile(new File("serverlogs/server.log")));
				
				 
	 }

	@Parameters({ "platformName", "deviceName", "systemPort", "chromeDriverPort","wdaLocalPort","webKitDebugProxyPort" })
	@BeforeTest
	public void beforeTest(String platformName,String deviceName,@Optional("AndroidOnly")String systemPort, @Optional("AndroidOnly")String chromeDriverPort,@Optional("iOSOnly")String wdaLocalPort ,@Optional("iOSOnly")String webKitDebugProxyPort) throws IOException {
		
		
		utils=new TestUtils();
		setDateTime(utils.getFormattedDateTime());
		URL url;
		setPlatform(platformName);
		InputStream inputStream = null;
		InputStream stringsis = null;
		Properties props = new Properties();
		setDeviceName(deviceName);
		AppiumDriver driver;
		
		String strFile = "logs" + File.separator + getPlatform() + "_" + getDeviceName();
		
		File logFile=new File(strFile);
		
		if(!logFile.exists()) {
			logFile.mkdirs();
		}
		ThreadContext.put("ROUTINGKEY", strFile);

		try {
			props = new Properties();
			String propFileName = "config.properties";
			String xmlFileName="strings/strings.xml";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);
			setProps(props);
			
			stringsis=getClass().getClassLoader().getResourceAsStream(xmlFileName);
			
			
			setStrings(utils.parseStringXml(stringsis));
			
			
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			url = new URL(props.getProperty("appiumURL")+"4723/wd/hub");
			
			switch(platformName) {
			
			case "Android":
				//startEmulator();
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
				cap.setCapability("appPackage", props.getProperty("androidAppPackage"));
				cap.setCapability("systemPort", systemPort);
				cap.setCapability("chromeDriverPort", chromeDriverPort);
				cap.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");
				URL androidAppUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
				cap.setCapability(MobileCapabilityType.APP, androidAppUrl);
				driver = new AndroidDriver(url, cap);
				utils.log().info("Android");
				break;
				
			case "iOS":
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("iOSAutomationName"));
				URL iOSAppUrl = getClass().getClassLoader().getResource(props.getProperty("iOSAppLocation"));
				//cap.setCapability("app", iOSAppUrl);
				cap.setCapability("bundleId", props.getProperty("iOSBundleId"));
				cap.setCapability("wdaLocalPort", wdaLocalPort);
				cap.setCapability("webKitDebugProxyPort", webKitDebugProxyPort);

				driver = new IOSDriver(url, cap);
				utils.log().info("iOS");
				break;
				
				default:
					throw new Exception("Invalid Platform : "+ platformName);
			
			}
			
			setDriver(driver);
			
			
			// cap.setCapability(MobileCapabilityType.APP,
			// "/Users/Shreyas/Downloads/Android_SauceLabs_Mobile_Sampleapp.apk");
			

			
			String sessionId = driver.getSessionId().toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(inputStream !=null) {
				inputStream.close();
			}
			if(stringsis !=null) {
				stringsis.close();
			}
		}
	}
	
	public void waitForVisibility(MobileElement e) {
		
		try {
			WebDriverWait wait=new WebDriverWait(getDriver(), TestUtils.WAIT);
			wait.until(ExpectedConditions.visibilityOf(e));
			utils.log().log(Level.INFO, "Element " + e.getText() +"is visible");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			utils.log().log(Level.ERROR, "Element " + e.getText() +"is not visible");
			e1.printStackTrace();
		}
	}
	
	public void clickElement(MobileElement e) {
		waitForVisibility(e);
		e.click();
		utils.log().log(Level.INFO, "Element " + e.getText() +"is clicked");
		
	}
	
	public void sendKeys(MobileElement e,String text) {
		waitForVisibility(e);
		e.sendKeys(text);
		utils.log().log(Level.INFO, "Element " + e.getText() +"is sent value: " + text);
		
	}
	
	public String getAttribute(MobileElement e,String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
		
		
	}
	
	public String getText(MobileElement e) {
		
		String retrievedText=null;
		switch(getPlatform()) {
		
		case "Android" :
			retrievedText = getAttribute(e,"text");
			break;
			
		case "iOS" :
			retrievedText = getAttribute(e,"label");
			break;
			
		}
		utils.log().log(Level.INFO, "Text obtianed is : " + retrievedText);
		return retrievedText;
	}
	
	public void clearFields(MobileElement e) {
		
		e.clear();
		utils.log().log(Level.INFO, "Field " + e.getText() +"is cleared");
	}
	
	public void closeApp() {
		getDriver().closeApp();
	}
	
	public void launchApp() {
		getDriver().launchApp();
	}
	
	
	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
	
	public static void logInfo(String message) {
		ExtentReporter.getTest().log(Status.INFO, message);
	}
	
	public static void logFail(String message) {
		ExtentReporter.getTest().log(Status.FAIL, message);
	}
	
	public static void startEmulator()  {
		//Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\startEmulator.bat");
		
		try {
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//main//resources//startEmulator.sh");
			Thread.sleep(6000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Device not found");
		} 
	}
	
	public void killAllNodes() throws IOException, InterruptedException {
		//Windows
		//Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		//Mac
		Runtime.getRuntime().exec("killall node");
		Thread.sleep(3000);
		
	}
	
	

		

	@AfterTest
	public void afterTest() throws IOException, InterruptedException {
		getDriver().quit();
		//killAllNodes();
	}

}
