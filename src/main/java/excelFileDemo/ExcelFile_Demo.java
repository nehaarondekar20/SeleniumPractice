package excelFileDemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import basepackage.BaseClass;

public class ExcelFile_Demo{
	static WebDriver driver;
	static String filePath="C:\\EclipseWorkspaces\\csse120\\NehaSeleniumAutomation\\testdata.xlsx";
	static FileInputStream fis;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static FileOutputStream fos;
	
	public static void main(String []args){	
	
	//get data from excel
		try {
			fis=new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb=new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet=wb.getSheet("itempurchasedata");
		String username=sheet.getRow(1).getCell(0).getStringCellValue();
		String password=sheet.getRow(1).getCell(1).getStringCellValue();
		String item=sheet.getRow(1).getCell(2).getStringCellValue();
		String fname=sheet.getRow(1).getCell(3).getStringCellValue();
		String lname=sheet.getRow(1).getCell(4).getStringCellValue();
		String zipcode=sheet.getRow(1).getCell(5).getStringCellValue();
	
		BaseClass base=new BaseClass();
		driver=base.openBrowser(driver);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Login
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.id("login-button")).click();
				
		//Apply filter:
		WebElement dropdown=driver.findElement(By.xpath("//select[@class='product_sort_container']"));
		Select select = new Select(dropdown);
		select.selectByValue("lohi");
				
		String itemname=driver.findElement(By.linkText("Sauce Labs Bolt T-Shirt")).getText();
		System.out.println("itemname: "+itemname);
		System.out.println("config itemname: "+item);
		if(itemname.equalsIgnoreCase(item) ) {
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
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys(fname);
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(lname);
		driver.findElement(By.xpath("//input[@name='postalCode']")).sendKeys(zipcode);
		driver.findElement(By.xpath("//input[contains(@value,'Continue')]")).click();
				
		String paymentId=driver.findElement(By.xpath("//div[contains(text(),'SauceCard #')]")).getText();
		String totalCost=driver.findElement(By.xpath("//div[contains(@class,'summary_total_label')]")).getText();
		
				
		driver.findElement(By.id("finish")).click();
		
		Row row=sheet.getRow(1);
		Cell cell1=row.createCell(6);
		cell1.setCellValue(paymentId);
		Cell cell2=row.createCell(7);
		cell2.setCellValue(totalCost);
		
		
		try {
			fos = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
