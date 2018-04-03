package com.wipro.chcare.ccc;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import com.relevantcodes.extentreports.LogStatus;

import Assignment1.GenMethods;


public class opencart008_PrintingtheNumberoflinks 
{
	WebDriver driver;
	private static final int TIME_UNIT = 30;
	WebDriverWait wait;
	Properties prop;
	
	@BeforeClass
	public void launchBrowser() throws InterruptedException, IOException
	{
		//System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "/Utils/chromedriver.exe" );
		System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\ChromeLatest\\chromedriver.exe" );
	 	   driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		 prop= new Properties();
			InputStream input;
			try {
				input = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
				prop.load(input);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	 @Test(description = "Launch openCart application")
	    public void openApp() throws InterruptedException
	    {		
	    	
	  	  driver.get(prop.getProperty("url"));
	  	  	  	  System.out.println("opencart app launched for TC_003");
	   	  	  	  
	  	 /* Step 002: Click on "Login" Link*/
	  	driver.findElement(By.xpath("(//a[text()='login'])")).click();
	  
	    }
	 
	 @DataProvider(name="Logindata")
	    public static  Object[][] ReadingData() throws IOException
	 {
		Object[][] obj=ReadingExcel.readExcel("Testcase008-login","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;	
	 }
		
	    @Test ( dataProvider="Logindata", description="Testcase008", dependsOnMethods="openApp" )
	    public void Login(String Username, String Password ) throws Exception
	    {
	    	
	   driver.findElement(By.xpath("//input[@name='email']")).sendKeys(Username);
	   driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
	   driver.findElement(By.xpath("//input[@class='button']")).click();
	   System.out.println("Logged in successfully");
		printNumOfLinks();
}
	    
		public void printNumOfLinks() throws Exception
	{
			GenMethods gm=new GenMethods();
		/*
		 * TC04: Display a Message Box containing the number of links present in the User Home page
		 */
		wait = new WebDriverWait(driver, TIME_UNIT);
		driver.manage().timeouts().implicitlyWait(TIME_UNIT, TimeUnit.SECONDS);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		JOptionPane pane = new JOptionPane("Total number of links present in the page are:  "+links.size(), JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = pane.createDialog(null, "Number of Links");
		dialog.setModal(false);
		dialog.setVisible(true);
			Thread.sleep(3000);
		dialog.setVisible(false);
		System.out.println("Number of Links in UserPage: "+links.size());
		Thread.sleep(3000);
		gm.getScreenShot(driver, "TC08_links");
	}

		@AfterClass
	public void closeBrowser()
	{
		driver.close();
		System.out.println("END OF TESTCASE 08");
	}
		
		
		
}
