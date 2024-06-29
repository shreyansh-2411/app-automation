package main.nativeapp.games.cricoff.pageobjects;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.ExtentManager;
import main.nativeapp.core.LogsManager;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeContestPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.time.Duration;
import java.util.logging.Level;

public class gamesOyeContestPage extends DriverManager {

    public gamesOyeContestPage(AndroidDriver driver){
        this.driver = driver;
    }

    protected AndroidDriver driver;
    private final By boltBalance = By.xpath("//android.view.ViewGroup[@resource-id='CustomButton-RubyBalanceBoxSmall-Header-Bolt-Button']");
    private final By blingBalance = By.xpath("//android.view.ViewGroup[@resource-id='CustomButton-RubyBalanceBoxSmall-Header-Bling-Button']");
    private final By quickSingleContest = AppiumBy.accessibilityId("QUICK SINGLE, Win, 200, Entry , 100");
    private final By findingAWorthyOpponent = By.xpath("//android.widget.TextView[@text=\"Finding you a worthy opponent..\"]");
    private final By howToPlay = By.xpath("//android.widget.TextView[@text=\"How to play?\"]");
    private final By howToPlayPage = By.xpath("//android.widget.TextView[@text='How to play']");
    private By notEnoughBoltsMessage;
    private final By userViewName = By.xpath("//android.view.ViewGroup[@resource-id='CricOffMatchMakingWidget-UserMainView']//android.widget.TextView[@resource-id='CricOffMatchMakingItem-PlayerNameTextView']");
    private final By opponentViewName = By.xpath("//android.view.ViewGroup[@resource-id=\"CricOffMatchMakingWidget-OppnentContainerView”]//android.widget.TextView[@resource-id=\"CricOffMatchMakingItem-PlayerNameTextView\"]");

    public void howToPlay() throws CustomException, InterruptedException {
        waitUntilPresenceOfElement(this.howToPlay,Duration.ofSeconds(10));
        click_element(this.howToPlay);
        waitUntilPresenceOfElement(this.howToPlayPage,Duration.ofSeconds(10));
        boolean howToPlayIsDisplayed = driver.findElement(this.howToPlayPage).isDisplayed();
        Assert.assertTrue(howToPlayIsDisplayed);
        Thread.sleep(1000);
    }

    public String getBoltBalance() throws CustomException {
        waitUntilPresenceOfElement(this.boltBalance,Duration.ofSeconds(30));
        WebElement boltBal = driver.findElement(this.boltBalance);
        return boltBal.getAttribute("content-desc");
    }

    public String getBlingBalance() throws CustomException {
        waitUntilPresenceOfElement(this.blingBalance,Duration.ofSeconds(10));
        WebElement blingBal = driver.findElement(this.blingBalance);
        return blingBal.getAttribute("content-desc");
    }

    public void joinContest() throws CustomException, InterruptedException {
        waitUntilPresenceOfElement(this.quickSingleContest,Duration.ofSeconds(10));
        click_element(quickSingleContest);
    }

    private By getNotEnoughBoltsMessage() {
        return notEnoughBoltsMessage;
    }

    private void setNotEnoughBoltsMessage(String notEnoughBoltsMessage) {
        this.notEnoughBoltsMessage = By.xpath("//android.widget.TextView[@text='"+notEnoughBoltsMessage+"']");
    }

    public void assertNotEnoughBoltsMessage(String notEnoughBoltsMessage) throws CustomException {
        setNotEnoughBoltsMessage(notEnoughBoltsMessage);
        boolean elementFlag = false;
        try {
            elementFlag = driver.findElement(getNotEnoughBoltsMessage()).isDisplayed();
        } catch (Exception e){
            ExtentManager.getExtentTestPool().fail("Unable to find element - Finding a Worthy Opponent");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
    }

    public void matchMakingScreen() throws CustomException {
        boolean elementFlag = false;
        try {
            waitUntilPresenceOfElement(this.findingAWorthyOpponent,Duration.ofSeconds(5));
            elementFlag = true;
        }catch (Exception e){
            ExtentManager.getExtentTestPool().fail("Unable to find element - Finding a Worthy Opponent");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
    }

    public void matchMakingWithUser(int timeOut) throws  CustomException {
        try {
//            Assert.assertEquals(driver.findElement(userViewName).getAttribute("text"),"You");
//            LogsManager.getLogger().log(Level.INFO,"Opponent Name - "+ driver.findElement(opponentViewName).getAttribute("text"));
            waitUntilInVisibilityOfElement(this.findingAWorthyOpponent,Duration.ofSeconds(timeOut));
            LogsManager.getLogger().log(Level.INFO,"Match Making is successful with User!!");
        } catch (Exception e) {
            LogsManager.getLogger().log(Level.INFO,"Match Making is not done with User..Please wait to match with Bot!!");
            waitUntilInVisibilityOfElement(this.findingAWorthyOpponent,Duration.ofSeconds(10));
            LogsManager.getLogger().log(Level.INFO,"Match Making is successful with Bot!!");
            Assert.assertEquals(driver.findElement(userViewName).getAttribute("text"),"You");
            LogsManager.getLogger().log(Level.INFO,"Opponent Name - "+ driver.findElement(opponentViewName).getAttribute("text"));
        }
    }

}
