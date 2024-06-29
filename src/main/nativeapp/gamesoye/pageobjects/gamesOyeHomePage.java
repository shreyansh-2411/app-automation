package main.nativeapp.gamesoye.pageobjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.ExtentManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;

public class gamesOyeHomePage extends DriverManager {
    protected AndroidDriver driver;

    public gamesOyeHomePage(AndroidDriver driver){
        this.driver = driver;
    }

//  private final By cricOff_firstFold_Banner_playNow = By.xpath("//android.widget.TextView[@text=\"Play Now\"]");
    private final By cricOff_featuredGame_playNow = By.xpath("(//android.widget.TextView[@text=\"Win Rewards\"])[1]");
    private final By home_page_learn_more_text = By.xpath("//android.widget.TextView[@text=\"Learn More\"]");
    private final By home_page_wallet_bolt = By.xpath("//android.widget.TextView[@resource-id=\"RubyBalanceBoxSmall-Header-Bolt-ClosingBalance\"]");
    private final By profile_button_bottom_nav = By.xpath("//android.widget.TextView[@text=\"Me\"]");
    private final By contests_played_profile_page = By.xpath("//android.widget.TextView[@resource-id=\"Profile-Contests-Played-Value\"]");
    private final By home_button_bottom_nav = By.xpath("//android.widget.TextView[@text=\"Home\"]");

    public void clickHomeButton() throws CustomException {
        boolean clickFlag = false;
        try{
            waitUntilVisibilityOfElement(this.home_button_bottom_nav,Duration.ofSeconds(20));
            driver.findElement(this.home_button_bottom_nav).click();
            clickFlag = true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to click element - Home button bottom nav bar");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(clickFlag);
    }

    public String getContestsPlayed() throws CustomException {
        boolean elementFlag = false;
        String contestsPlayed="";
        try{
            waitUntilPresenceOfElement(this.contests_played_profile_page,Duration.ofSeconds(20));
            contestsPlayed= driver.findElement(this.contests_played_profile_page).getText();
            elementFlag = true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - Contests played count");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
        return contestsPlayed;
    }

    public void clickProfileButton() throws CustomException {
        boolean clickFlag = false;
        try{
            waitUntilVisibilityOfElement(this.profile_button_bottom_nav,Duration.ofSeconds(20));
            driver.findElement(this.profile_button_bottom_nav).click();
            clickFlag = true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to click element - Profile button bottom nav bar");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(clickFlag);
    }

    public String getBoltBalance() throws CustomException {
        boolean elementFlag = false;
        String bolt="";
        try{
            waitUntilPresenceOfElement(this.home_page_wallet_bolt,Duration.ofSeconds(20));
            bolt= driver.findElement(this.home_page_wallet_bolt).getAttribute("content-desc");
            elementFlag= true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - Bolt wallet Home page");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
        return bolt;
    }

    public void assertHomePageIsDisplayed() throws CustomException {
        boolean elementFlag = false;
        try{
            waitUntilPresenceOfElement(this.home_page_learn_more_text,Duration.ofSeconds(20));
            elementFlag = true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - Learn more text at home page");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
    }

    public void playGame() throws CustomException, InterruptedException {
        String command = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).flingToEnd(2)";
        driver.findElement(AppiumBy.androidUIAutomator(command));
        click_element(this.cricOff_featuredGame_playNow);
    }

}
