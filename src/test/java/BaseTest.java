import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {

    public static WebDriver driver;

    public WebDriverWait wait;

    public FluentWait<WebDriver> fluentWait;

    Actions actions;

    //public String url = "https://demo.koel.dev/";

    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return threadDriver.get();
    }



    @BeforeSuite
    static void setupClass() {

        //WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
    }

    /*@BeforeMethod
    @Parameters({"BaseURL"})
    public void launchBrowser(String baseUrl) throws MalformedURLException {
        *//*ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);*//*
        //driver = new FirefoxDriver();
        driver = pickBrowser(System.getProperty("browser"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //fluent Wait
        fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(150));
        actions = new Actions(driver);
        navigateToPage(baseUrl);
    }*/

    @BeforeMethod
    @Parameters({"BaseURL"})
    public void launchBrowser(String baseUrl) throws MalformedURLException {
       threadDriver.set(pickBrowser(System.getProperty("browser")));
        //driver = pickBrowser(System.getProperty("browser"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        //fluent Wait
        fluentWait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(150));
        actions = new Actions(getDriver());
        navigateToPage(baseUrl);
    }

    /*@AfterMethod
    public void closeBrowser(){
        driver.quit();
    }*/

    @AfterMethod
    public void tearDown(){
        threadDriver.get().close();
        threadDriver.remove();
    }


    /*public void navigateToPage(){

        driver.get(url);

    }*/

    /*public void navigateToPage(String url){
        driver.get(url);

    }*/
    public void navigateToPage(String url){
        getDriver().get(url);

    }

    public void provideEmail(String email){

        //WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        WebElement emailField = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        emailField.clear();
        emailField.sendKeys(email);

    }

    public void providePassword(String password){

        //WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        WebElement passwordField = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);

    }

    public void clickLogin(){

        //WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement loginButton = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        loginButton.click();

    }

    public void logOut(){

        WebElement logoutButton = driver.findElement(By.cssSelector("button[data-title='Log out']"));
        logoutButton.click();

    }

    public boolean isSongPlaying(){
        WebElement soundDiskSpinning = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[@class='song-info playing']")));
        return soundDiskSpinning.isDisplayed();
    }

    public static WebDriver pickBrowser(String browser) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://10.0.0.206:4444";

        switch(browser){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return driver = new FirefoxDriver();
            case "MicrosoftEdge": WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                return driver = new EdgeDriver(edgeOptions);
            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);
            case "cloud" : return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                return driver = new ChromeDriver(chromeOptions);
        }
    }

    public static WebDriver lambdaTest() throws MalformedURLException {
        String hubURL = "https://hub.lambdatest.com/wd/hub";

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("126");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "muhammadtestpro");
        ltOptions.put("accessKey", "SE8iAUT7KcFw8hrr9shssoC2PQCg4CTki1fpmP3OX6VDNr5ksJ");
        ltOptions.put("build", "TESTCloud");
        ltOptions.put("project", "SUA_TEST_CLOUD");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        browserOptions.setCapability("LT:Options", ltOptions);

        return new RemoteWebDriver(new URL(hubURL), browserOptions);

    }

}