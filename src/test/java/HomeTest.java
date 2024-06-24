import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomeTest extends BaseTest{


    public void clickAllSongsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li//a//span[text()='All Songs']"))).click();
    }

}
