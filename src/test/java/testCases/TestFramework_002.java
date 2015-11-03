package test.java.testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.java.pageObjects.BaseClass;
import test.java.pageObjects.ProductListing_Page;
import test.java.utility.Constant;
import test.java.utility.ExcelUtils;
import test.java.utility.Log;
import test.java.utility.Utils;
import test.java.appModules.CheckOut_Action;
import test.java.appModules.Confirmation_Action;
import test.java.appModules.PaymentDetails_Action;
import test.java.appModules.ProductSelect_Action;
import test.java.appModules.SignIn_Action;
import test.java.appModules.Verification_Action;

public class TestFramework_002 {
	public WebDriver driver;
	private String sTestCaseName;
	private int iTestCaseRow;
	
  @BeforeMethod
  public void beforeMethod() throws Exception {
	  	sTestCaseName = this.toString();
	  	sTestCaseName = Utils.getTestCaseName(this.toString());
		Log.startTestCase(sTestCaseName);
		ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"Sheet1");
		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,Constant.Col_TestCaseName);
		driver = Utils.OpenBrowser(iTestCaseRow);
		new BaseClass(driver);  
        }
  
  @Test
  public void f() throws Exception {
	  try{
		SignIn_Action.Execute(iTestCaseRow);
		ProductSelect_Action.productType(iTestCaseRow);
		ProductSelect_Action.productNumber(iTestCaseRow);
		ProductListing_Page.PopUpAddToCart.btn_GoToCart().click();
		CheckOut_Action.Execute();
		PaymentDetails_Action.execute(iTestCaseRow);
		Confirmation_Action.Execute();
		Verification_Action.Execute();
		ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.Col_Result);
	  }catch (Exception e){
		  ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.Col_Result);
		  Utils.takeScreenshot(driver, sTestCaseName);
		  Log.error(e.getMessage());
		  throw (e);
	  }
		
  }
		
		
  @AfterMethod
  public void afterMethod() {
	    Log.endTestCase(sTestCaseName);
	    driver.close();
  		}
}
