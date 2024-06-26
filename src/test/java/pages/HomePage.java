package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    //WebElement

    By userAvatarIcon = By.cssSelector("a.view-profile img");

    //Helper Methods
    public WebElement getUserAvatar(){
        return findElement(userAvatarIcon);
    }

}
