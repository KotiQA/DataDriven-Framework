package DriverFactory;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class AppTest extends FunctionLibrary
{
	String inputpath ="./FileInput/LoginData.xlsx";
	String outputpath ="./FileOutput/DataDrivenResults.xlsx";
	ExtentReports reports;
	ExtentTest logger;
	
	@Test
	public void startTest() throws Throwable
	{
		//Define path of html Reports
		reports = new ExtentReports("./Target/ExtentReports/Login.html");
		//Create Object For ExcelFileUtil Class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//Count No Of Rows in Login Sheet
		int rc = xl.rowCount("Login");
		Reporter.log("No of rows are ::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			logger = reports.startTest("Validate Login");
			logger.assignAuthor("Koti");
			String username = xl.getCellData("Login", i, 0);
			String password = xl.getCellData("Login", i, 1);
			logger.log(LogStatus.INFO,username+"   "+password);
			//call Login Method from FunctionLibrary class
			boolean res = FunctionLibrary.adminLogin(username, password);
			if(res)
			{
				//if res is true write as valid user name and password into results cell
				xl.SetCellData("Login", i, 2, "Valid UserName And Password", outputpath);
				//if res is true write as PASS into status cell
				xl.SetCellData("Login", i, 3, "Pass", outputpath);
				logger.log(LogStatus.PASS, "UserName and Password are Valid");			
			}
			else
			{
				//Take Screen shot
				File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen, new File("./Screenshot/Iterations/"+i+"Loginpage.png"));
				//res is false write as invalid user name and password into results cell
				xl.SetCellData("Login", i, 2, "Invalid UserName and Password", outputpath);
				//res is False Write as FAIL into status cell
				xl.SetCellData("Login", i, 3, "Fail", outputpath);
				logger.log(LogStatus.FAIL, "UserName and Password are Invalid");				
			}
		}
		reports.endTest(logger);
		reports.flush();
	}
}
