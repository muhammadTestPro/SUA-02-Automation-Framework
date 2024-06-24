import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AllSongsTest extends BaseTest{

    @Test
    public void playWongWithContextClick(){
        provideEmail("demo@koel.dev");
        providePassword("demo");
        clickLogin();
        clickAllSongsList();
        contextClickFirstSong();
        choosePlayOptionFromContextMenu();
        //Assertions
        Assert.assertTrue(isSongPlaying());
    }

    @Test
    public void addSongToFavorites() throws InterruptedException {
        provideEmail("demo@koel.dev");
        providePassword("demo");
        clickLogin();
        Thread.sleep(1000);
        clickAllSongsList();
        Thread.sleep(1000);
        contextClickSong("1979");
        Thread.sleep(1000);
        hoverAddToOption();
        Thread.sleep(1000);
        chooseFavoritePlaylistFromContextMenu();
        Thread.sleep(1000);
        //verify
        openFavoritesPlaylist();
        Thread.sleep(1000);
        //Song is listed.
        Assert.assertTrue(isSongInFavorites("1979"));
    }

    @Test
    public void countSongsInPlaylist() throws InterruptedException {
        provideEmail("demo@koel.dev");
        providePassword("demo");
        clickLogin();
        Thread.sleep(1000);
        choosePlaylistByName("skillup");
        Thread.sleep(1000);
        displayAllSongsInLogs();
        Thread.sleep(1000);
        System.out.println("Playlist Details: "+getPlaylistDetails());
        //Assertion
        Assert.assertTrue(getPlaylistDetails().contains(String.valueOf(countSongs())));
    }

    public String getPlaylistDetails(){
        return driver.findElement(By.xpath("//section[@id='playlistWrapper']//span[@class='meta text-secondary']")).getText();
    }

    public void displayAllSongsInLogs() {
        List<WebElement> songList = driver.findElements(By.xpath
                ("//section[@id='playlistWrapper']//div[@class='song-list-wrap']//div[@class='song-item']"));
        System.out.println("Number of Songs found: "+countSongs());
        for (WebElement e: songList){
            System.out.println(e.getText());
        }

    }

    public int countSongs(){
        return driver.findElements(By.xpath
                ("//section[@id='playlistWrapper']//div[@class='song-list-wrap']//div[@class='song-item']")).size();
    }

    public void choosePlaylistByName(String playlistName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[contains(text(),'"+playlistName+"')]/.."))).click();
    }

    public boolean isSongInFavorites(String songName){
        WebElement songInFavoriteList = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//section[@id='favoritesWrapper']//span[@class='title text-primary']" +
                        "[text()='"+songName+"']")));
        return songInFavoriteList.isDisplayed();
    }

    public void openFavoritesPlaylist() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//section[@id='playlists']//a[@href='#/favorites']"))).click();
    }

    public void chooseFavoritePlaylistFromContextMenu() {
        WebElement favoritePlaylistInSubContextMenu = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li[@class='has-sub']//li[text()='Favorites']")));
        actions.moveToElement(favoritePlaylistInSubContextMenu).perform();
        favoritePlaylistInSubContextMenu.click();
    }

    public void hoverAddToOption() {
        WebElement addToOption = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//nav[@data-testid='song-context-menu']//li[@class='has-sub']")));
        actions.moveToElement(addToOption).perform();
    }

    public void contextClickSong(String songName) {
        WebElement songInAllSongs = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//section[@id='songsWrapper']" +
                                "//span[@class='title text-primary'][text()='"+songName+"']")));
        actions.contextClick(songInAllSongs).perform();
    }


    //Helper Methods

    public void choosePlayOptionFromContextMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//nav[@data-testid='song-context-menu']//ul//li/span"))).click();
    }

    public void contextClickFirstSong() {
        WebElement firstSongElement = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//section[@id='songsWrapper']//div[@data-testid='song-item'][1]")));
        actions.contextClick(firstSongElement).perform();
    }

    public void clickAllSongsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li//a//span[text()='All Songs']"))).click();
    }

}
