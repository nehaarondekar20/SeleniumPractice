package basepackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

	File file =new File("C:\\EclipseWorkspaces\\csse120\\SauceDemo\\config.properties");
	FileInputStream inputFile;
	Properties prop;
	
public WebDriver openBrowser(WebDriver driver) {
	
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

		System.setProperty("webdriver.chrome.driver", "D:\\Nehase\\chromedriver_win32 (2)\\chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		driver.get(prop.getProperty("URL"));
		String titleName=driver.getTitle();
		System.out.println("Page tile: "+titleName);
		/*
		 * //Waitforelement WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		 * explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//input[contains(@type,'submit')")));
		 */
		return driver;
}
}