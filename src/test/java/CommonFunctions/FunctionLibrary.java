package CommonFunctions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Reporter;

import Config.AppUtils;

public class FunctionLibrary extends AppUtils
{
	public static boolean adminLogin(String user,String pass) throws Throwable
	{
		driver.get(conpro.getProperty("Url"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath(conpro.getProperty("ObjReset"))).click();
		driver.findElement(By.xpath(conpro.getProperty("ObjUser"))).sendKeys(user);
		driver.findElement(By.xpath(conpro.getProperty("ObjPass"))).sendKeys(pass);
		driver.findElement(By.xpath(conpro.getProperty("ObjLogin"))).click();
		Thread.sleep(2000);		
		String Expected = "dashboard";
		String Actual = driver.getCurrentUrl();
		if(Actual.contains(Expected))
		{
			Reporter.log("Username And Password Valid::"+Expected+"   "+Actual,true);
			driver.findElement(By.xpath(conpro.getProperty("ObjLogout"))).click();
			return true;
		}
		else
		{
			//Capturing Error Message
			String message = driver.findElement(By.xpath(conpro.getProperty("ObjErrorMessage"))).getText();
			Thread.sleep(2000);
			driver.findElement(By.xpath(conpro.getProperty("ObjOkbutton"))).click();
			Reporter.log(message+"   "+Expected+"   "+Actual,true);
			return false;
		}		
	}
}
