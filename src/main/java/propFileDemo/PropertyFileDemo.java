package propFileDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import basepackage.BaseClass;


public class PropertyFileDemo {
	static WebDriver driver;
	static File file =new File("C:\\EclipseWorkspaces\\csse120\\SauceDemo\\config.properties");
	static FileInputStream inputFile;
	static Properties prop;
	public static void main(String []args) {
		
		//initiate property file
		try {
			inputFile = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		prop=new Properties();
		try {
			prop.load(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//OpenBrowser
		BaseClass base=new BaseClass();
		driver=base.openBrowser(driver);
	
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Login
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.id("login-button")).click();
		
		//Apply filter:
		WebElement dropdown=driver.findElement(By.xpath("//select[@class='product_sort_container']"));
		Select select = new Select(dropdown);
		select.selectByValue("lohi");
		
		String itemname=driver.findElement(By.linkText("Sauce Labs Bolt T-Shirt")).getText();
		System.out.println("itemname: "+itemname);
		System.out.println("config itemname: "+prop.getProperty("itemName"));
		if(itemname.equalsIgnoreCase(prop.getProperty("itemName")) ) {
			System.out.println("Condition satisfied");
			driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
		}
		else {
			System.out.println("Condition not satisfied");
		}
		
		//click on shopping cart:
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		
		//checkout and finish
		driver.findElement(By.id("checkout")).click();
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.xpath("//input[@name='postalCode']")).sendKeys(prop.getProperty("zipCode"));
		driver.findElement(By.xpath("//input[contains(@value,'Continue')]")).click();
		
		String paymentId=driver.findElement(By.xpath("//div[contains(text(),'SauceCard #')]")).getText();
		System.out.println("Payment Information: "+paymentId);
		String totalCost=driver.findElement(By.xpath("//div[contains(@class,'summary_total_label')]")).getText();
		System.out.println("Total cost: "+totalCost);
		
		driver.findElement(By.id("finish")).click();
		}

	
	
}
