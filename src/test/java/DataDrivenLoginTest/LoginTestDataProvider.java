package DataDrivenLoginTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTestDataProvider {

    WebDriver driver;

    @BeforeMethod
    public void setDriver() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

    }

    @Test(dataProvider = "data")
    public void loginTest(String username, String password, String valid) throws InterruptedException {

        driver.findElement(By.xpath("//input[@name=\"username\"]")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

        Thread.sleep(5000);
        boolean URL = driver.getCurrentUrl().contains("dashboard");

        if (valid.equals("valid")) {
            Assert.assertTrue(URL);
        } else {
            Assert.assertFalse(URL);
        }


    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    @DataProvider(name = "data")
    public Object[][] dataProvider() {
        String[][] data = {
                {"Admin", "admin123", "valid"},
                {"admin", "Admin123", "Invalid"},
                {"aadmin123", "admin", "Invalid"},
                {"Admin123", "aadmin123", "Invalid"},
        };
        return data;

    }
}
