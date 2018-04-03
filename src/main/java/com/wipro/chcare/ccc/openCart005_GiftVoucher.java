package com.wipro.chcare.ccc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ReadExcel.ReadingExcel;
import Assignment1.GenMethods;

public class openCart005_GiftVoucher {

	private static final String depends =null;
	private static final String dependsOnMethods=null;
	private WebDriver driver;
	 private Properties prop;
	 private String value;
	 private static int WAIT_TIME = 10000 ;
	 GenMethods gm=new GenMethods();
	 
	@BeforeClass
	public void initialize()
	{
	System.setProperty("webdriver.chrome.driver","D:\\Selenium_Jars\\Jars\\ChromeLatest\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.MILLISECONDS);
	prop=new Properties();
	InputStream input;
	
	try
	{
		input = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/wipro/chcare/ccc/config.properties");
		prop.load(input);
	}
	catch (IOException e)
	{
		
		e.printStackTrace();
	}
			}

	/* Step:001  Launch  Open Cart application http://10.207.182.108:81/opencart/*/
	
	@Test
	public void openApp()
	{
		driver.get(prop.getProperty("url"));
		System.out.println("opencart app launched");
		 /* Step 002: Click on "Login" Link*/
		driver.findElement(By.xpath("(//a[text()='login'])")).click();
	}
	
	 /*---------------------------------------------------------------*/
	
	//Data Provider
	
	@DataProvider(name="Mydatprovide")
	public static Object[][] ReadingData() throws IOException
	{
		Object[][] obj= ReadingExcel.readExcel("LoginPage","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;
			
	}
	
	@DataProvider(name="giftvocherdata")
	public static Object[][] Giftvocherdataread() throws IOException
	{
		Object[][] obj= ReadingExcel.readExcel("TC005_giftvocher","D:\\SELENIUM_PRACTISE\\TestData.xlsx");
		return obj;
			
	}
	
	 /*---------------------------------------------------------------*/
	  /* This method is for opening the opencart application
	   * Enter Email Address and Password and click on "Login" Button.
	   * Step:003
	   * */
	
   @Test(dataProvider="Mydatprovide",dependsOnMethods = "openApp")
   public void loginapp(String Username,String Password, String changequantity, String Faultycomment)
   {
	   driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys(Username);
	   System.out.println(Username);
	   driver.findElement(By.xpath(prop.getProperty("password"))).sendKeys(Password);
	   System.out.println(Password);
		driver.findElement(By.xpath("//input[@class='button']")).click();
		System.out.println("Logged in successfully");
		/*=====================
		 * Check point: User's first name as a link
		 */
			String e=driver.findElement(By.linkText("dhana")).getText();
				Assert.assertEquals("dhana", e);
		System.out.println("Validation is passed");
   }
   
	@Test(dependsOnMethods="loginapp")
	public void Gift_Vouchers() throws InterruptedException, IOException
	{
		List<String> data=new ArrayList<String>();
		FileReader fr=new FileReader("D:\\SELENIUM_PRACTISE\\giftvocher_testdata.txt");
		BufferedReader br= new BufferedReader(fr);
		String x="";
		while ((x=br.readLine()) != null)
		{
	    data.add(x);	
		System.out.println(x);
		}
		System.out.println("List is" +data);
		br.close();
		//Click on "Gift Vouchers" from the extras - footer of the page.
		driver.findElement(By.linkText("Gift Vouchers")).click();
		System.out.println("Purchase a Gift Certificate Page is displayed.");
		gm.getScreenShot(driver, "TC005_Gift_Certificate");
		driver.findElement(By.name("to_name")).sendKeys(data.get(0));
		System.out.println("The Toname is " + data.get(0));
		driver.findElement(By.name("to_email")).sendKeys(data.get(1));
		System.out.println("The ToEmail is " + data.get(1));
		driver.findElement(By.name("from_name")).clear();
		driver.findElement(By.name("from_name")).sendKeys(data.get(2));
		System.out.println("The from_name is" + data.get(2));
		driver.findElement(By.name("from_email")).clear();
		driver.findElement(By.name("from_email")).sendKeys(data.get(3));
		System.out.println("The from_Email is " + data.get(3));
		driver.findElement(By.id("voucher-7")).click();
		driver.findElement(By.name("message")).sendKeys(data.get(4));
		driver.findElement(By.name("amount")).clear();
		driver.findElement(By.name("amount")).sendKeys(data.get(5));
		gm.getScreenShot(driver, "TC005_Details_Filled");
		/*==================Step005 validation==============*/
		
		 String  toname=driver.findElement(By.name("to_name")).getAttribute("value");
		 System.out.println("attribute data is"+toname);
		 Assert.assertEquals(data.get(0), toname, "toname validation unSuccessful");
		 
		 String  to_email=driver.findElement(By.name("to_email")).getAttribute("value");
		System.out.println("attribute data is"+to_email);
		Assert.assertEquals(data.get(1), to_email, "to_email validation unSuccessful");
		
		 String  from_name=driver.findElement(By.name("from_name")).getAttribute("value");
		System.out.println("attribute data is "+from_name);
		Assert.assertEquals(data.get(2), from_name, "from_name validation unSuccessful");
		
		String  from_email=driver.findElement(By.name("from_email")).getAttribute("value");
		System.out.println("attribute data is" + from_email);
		Assert.assertEquals(data.get(3), from_email, "from_email validation unSuccessful");
		
		/*==================Step005 validation==============*/
		
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.className("button")).click();
		System.out.println("Thank you for purchasing a gift certificate");
		gm.getScreenShot(driver, "TC005_Gift_Certificate_Purchased");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println("Shopping Cart Page is displayed.");
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//div[@id='content']/form/div/table/tbody/tr/td[4]/a/img")).click();
		//System.out.println("The message displayed is: Your shopping cart is sempty");
		List<WebElement> shoppingcartprods= driver.findElements(By.xpath(".//*[@id='content']/form/div/table"));
		System.out.println("The no of products in the shopping cart is : " + shoppingcartprods.size());
		driver.findElement(By.xpath("//*[@id='content']/div[5]/div[2]/a")).click();
		System.out.println("Home page is displayed--1");
		driver.findElement(By.linkText("Contact Us")).click();
		gm.getScreenShot(driver, "TC005_Contact_us");
		/*===========Step010===================*/
		

		 String  firstname=driver.findElement(By.name("name")).getAttribute("value");
		 System.out.println("The first name of the Contact Page is " + firstname);
		String  email=driver.findElement(By.name("email")).getAttribute("value");
		System.out.println("The email of the Contact Page is " + email);
		
		
		driver.findElement(By.name("enquiry")).sendKeys(data.get(6));
		System.out.println("Please enter the Captcha : ");
		Scanner input=new Scanner(System.in);
		String Captcha=input.nextLine();
		driver.findElement(By.name("captcha")).clear();
		driver.findElement(By.name("captcha")).sendKeys(Captcha);
	    //driver.findElement(By.xpath(".//*[@name='captcha']")).sendKeys(Captcha);
		driver.findElement(By.xpath("//div[@id='content']/form/div[3]/div/input")).click();
		System.out.println("Contact us page is displayed");
		/*=============STEP13 Validation===============*/
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='content']/h1")).getText().contains("Contact"),"Not Navigated to Contact Us Page");			
		driver.findElement(By.xpath("//*[@id='content']/div[2]/div/a")).click();
		/*=============STEP13 Validation ===============*/
		Assert.assertTrue(driver.findElement(By.linkText("Home")).getText().contains("Home"),"Not Navigated to Home Page");
		
		System.out.println("Home page is dispalyed--2");
		driver.findElement(By.linkText("Wish List")).click();
		System.out.println(" My Wish list is opened ");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println(" My account page is displayed--1 ");
		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).getText().contains("Edit your account"),"Edit your account link is not displayed");
		driver.findElement(By.linkText("Edit your account information")).click();
		/*==============STEP015 Validation============*/
		
		System.out.println("My account information page is displayed");
		
		/*==========Step016 Validation===============*/
		String Perfirstname=driver.findElement(By.name("firstname")).getAttribute("value");
		Assert.assertEquals("dhana", Perfirstname, "First name validation unSuccessful");
		String Perlastname=driver.findElement(By.name("lastname")).getAttribute("value");
		Assert.assertEquals("lakshmi", Perlastname, "Last name validation unSuccessful");
		String Peremail=driver.findElement(By.name("email")).getAttribute("value");
		Assert.assertEquals("dhana.amalakanta@gmail.com", Peremail, "to_email validation unSuccessful");
		String Perphone=driver.findElement(By.name("telephone")).getAttribute("value");
		Assert.assertEquals("9000788668", Perphone, "Phone validation unSuccessful");
		
		/*=============Step17  validation ===============*/
		String existingno=driver.findElement(By.name("telephone")).getText();
		driver.findElement(By.name("telephone")).clear();
		driver.findElement(By.name("telephone")).sendKeys(data.get(7));
		System.out.println("The old and changed no is " + existingno + " " + data.get(7)  );
		Assert.assertNotEquals(existingno, data.get(7));
		driver.findElement(By.className("button")).click();
		gm.getScreenShot(driver, "TC005_Second_time_details_updated");
		System.out.println("Your account has been successfully updated.");
		
		/*======Step 17 validation=====================*/
		Assert.assertTrue(driver.findElement(By.linkText("View your return requests")).getText().contains("return requests"),"View your return requests link is not displayed");
		driver.findElement(By.linkText("View your return requests")).click();
		System.out.println("Product Returns page is displayed");
		
		gm.getScreenShot(driver, "TC005_Products_ReturnsPage");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='content']/div[2]/div[3]/div[3]/a/img")).click();
		System.out.println("Return Information page is displayed");
		
		/* Saving the orderid to a file */
				
		String orderid = driver.findElement(By.xpath("//div[@id='content']/table[1]/tbody/tr/td[2]")).getText();
		System.out.println("The order id is : " + orderid);
		
				File orderfile = new File(System.getProperty("user.dir") + "/target/OutputFiles/TC05_orderid.txt");
		        BufferedWriter pworderfile = new BufferedWriter(new FileWriter(orderfile));
		        pworderfile.write("The order id is  : " + orderid);
		         pworderfile.close();
		
		     String orderidsubstr= orderid.substring(11, orderid.length()-22);
		     System.out.println(orderidsubstr);
		    driver.findElement(By.linkText("Continue")).click();
		System.out.println("First continue button is clicked");
		driver.findElement(By.linkText("Continue")).click();
		System.out.println("My Account Page is displayed--2");
		//Validation-- Step021
		Assert.assertTrue(driver.findElement(By.linkText("Modify your address book entries")).getText().contains("address book"),"address book link is not displayed");
		driver.findElement(By.linkText("Modify your address book entries")).click();
				
		System.out.println("Address Book page is displayed");
		gm.getScreenShot(driver, "TC005_AddressBook");
		driver.findElement(By.linkText("Edit")).click();
		System.out.println("Edit address tab is displayed.");
		//Edit address tab==Step022 validation
		String fname= driver.findElement(By.name("firstname")).getAttribute("value");
		System.out.println("The first name of the edit address " +  fname);
		
		String lname= driver.findElement(By.name("lastname")).getAttribute("value");
		System.out.println("The last name of the edit address " +  lname);
		
		String company= driver.findElement(By.name("company")).getAttribute("value");
		System.out.println("The first name of the edit address " +  company);
		
		String company_id= driver.findElement(By.name("company_id")).getAttribute("value");
		System.out.println("The first name of the edit address " +  company_id);
		
		String address_1= driver.findElement(By.name("address_1")).getAttribute("value");
		System.out.println("The first name of the edit address " +  address_1);
		
		// Passing the new Values 
		driver.findElement(By.name("firstname")).sendKeys(data.get(8));
		System.out.println("First name is changed");
		driver.findElement(By.name("lastname")).sendKeys(data.get(9));
		System.out.println("last name is changed");
		driver.findElement(By.name("company")).sendKeys(data.get(10));
		System.out.println("company name is changed");
		driver.findElement(By.name("company_id")).sendKeys(data.get(11));
		System.out.println("company id name is changed");
		driver.findElement(By.name("address_1")).sendKeys(data.get(1));
		System.out.println("address1 name is changed");
		driver.findElement(By.name("city")).sendKeys(data.get(1));
		System.out.println("city name is changed");
		driver.findElement(By.className("button")).click();
		System.out.println("Your address has been successfully updated");
		gm.getScreenShot(driver, "TC005_AddressUpdated");
		//Compare the address displayed and and the file contents.
		driver.findElement(By.linkText("Logout")).click();
		System.out.println("Account Logout page is displayed");
	}
	   	
}
