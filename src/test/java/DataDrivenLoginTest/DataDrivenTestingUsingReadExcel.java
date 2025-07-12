package DataDrivenLoginTest;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class DataDrivenTestingUsingReadExcel {

    WebDriver driver;

    @BeforeMethod
    public void setDriver() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

    }

//    @Test
//    public void readExcel() throws IOException {
//
//        FileInputStream file = new FileInputStream("/Users/praveen/Documents/IntelliJ/DataDrivenFrameWork/TestData/TestData.xlsx");
//        XSSFWorkbook workbook = new XSSFWorkbook(file); // Create an object for workbook
//        XSSFSheet sheet = workbook.getSheetAt(0);  // workbook.getSheet("Sheet1")
//        int rowCount = sheet.getLastRowNum(); // get row count
//        int columnCount = sheet.getRow(0).getLastCellNum(); // get column count
//        System.out.println("Total Rows: " + rowCount); // Note - Row count always shows 1 less than actual row count
//        System.out.println("Total Columns: " + columnCount);
//
//
//        for(int i=0; i<=rowCount; i++) {
//
//            XSSFRow currentRow =  sheet.getRow(i);
//
//            for(int j=0; j<columnCount; j++) {
//
//                System.out.print(currentRow.getCell(j).toString() + "\t\t");
//            }
//
//            System.out.println();
//        }
//
//    }

    @Test(dataProvider = "testData")
    public void loginTest(String username, String password,String Validate) throws InterruptedException {

        driver.findElement(By.xpath("//input[@name=\"username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

        Thread.sleep(5000);
        boolean URL = driver.getCurrentUrl().contains("dashboard");

        if(Validate.equals("valid") ) {
            Assert.assertTrue(URL);
        }else {
            Assert.assertFalse(URL);
        }

    }

    @DataProvider(name = "testData")
    public Object[][] getExcelData() throws IOException {

        FileInputStream file = new FileInputStream("/Users/praveen/Documents/IntelliJ/DataDrivenFrameWork/TestData/TestData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();

        String[][] testData = new String[rowCount][columnCount];

        for (int i = 1; i <= rowCount; i++) {

            for (int j = 0; j < columnCount; j++) {

                testData[i - 1][j] = sheet.getRow(i).getCell(j).toString();
            }
        }


        workbook.close();
        file.close();

        return testData;


    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }
}
