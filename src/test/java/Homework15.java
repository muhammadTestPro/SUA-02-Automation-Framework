import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Homework15 extends BaseTest{

    @Test
    public void logOut() throws InterruptedException {


        //EdgeOptions options = new EdgeOptions();
        //options.addArguments("--remote-allow-origins=*");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        String url = "https://demo.koel.dev/";
        driver.get(url);


        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.clear();
        emailField.sendKeys("demo@koel.dev");
        Thread.sleep(2000);


        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.clear();
        passwordField.sendKeys("demo");
        Thread.sleep(2000);


        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        Thread.sleep(2000);


        WebElement logoutButton = driver.findElement(By.cssSelector("button[data-title='Log out']"));
        Thread.sleep(3000);
        logoutButton.click();

        Assert.assertEquals(driver.getCurrentUrl(),url);

        driver.quit();
    }

}
