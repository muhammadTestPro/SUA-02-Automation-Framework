import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {
    @Test
    public void loginEmptyEmailPassword() {

        String url = "https://demo.koel.dev/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
        //driver.quit();
    }

    @Test
    public void logicSuccess() throws InterruptedException {

        provideEmail("demo@koel.dev");
        providePassword("demo");
        clickLogin();
        //WebElement profileAvatar = driver.findElement(By.cssSelector("a.view-profile img"));
        //WebElement profileAvatar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.view-profile img")));
        WebElement profileAvatar = fluentWait.until
                (ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.view-profile img")));
        Assert.assertTrue(profileAvatar.isDisplayed());
    }

    @Test
    public void incorrectEmail() throws InterruptedException {

        String expectedUrl = "https://demo.koel.dev/";
        //navigateToPage();

        provideEmail("google@koel.dev");
        Thread.sleep(1000);

        providePassword("demo");
        Thread.sleep(1000);

        clickLogin();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);

    }

    @Test
    public void incorrectPassword() throws InterruptedException {
        String expectedUrl = "https://demo.koel.dev/";

        //navigateToPage();


        provideEmail("demo@koel.dev");
        Thread.sleep(1000);


        providePassword("12345");
        Thread.sleep(1000);


        clickLogin();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);


    }


    @Test(enabled = true, description = "Not Yet due to issue")
    public void logOutTest() throws InterruptedException {

        String expectedUrl = "https://demo.koel.dev/";

        //navigateToPage();

        provideEmail("demo@koel.dev");
        Thread.sleep(1000);

        providePassword("demo");
        Thread.sleep(1000);

        clickLogin();
        Thread.sleep(1000);

        logOut();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(),expectedUrl);

    }

    @Test(dataProvider = "NegativeLoginTestData", dataProviderClass = TestDataSets.class)
    public void negativeLoginTest(String email, String password) throws InterruptedException {

        String expectedUrl = "https://demo.koel.dev/";
        //navigateToPage();

        provideEmail(email);
        Thread.sleep(1000);

        providePassword(password);
        Thread.sleep(1000);

        clickLogin();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);

    }

    @Test
    public void loginTest(){
        //Page Objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        //Test
        loginPage.provideEmail("demo@koel.dev");
        loginPage.providePassword("demo");
        loginPage.clickLogin();
        //Assertions
        Assert.assertTrue(homePage.getUserAvatar().isDisplayed());
    }

}
