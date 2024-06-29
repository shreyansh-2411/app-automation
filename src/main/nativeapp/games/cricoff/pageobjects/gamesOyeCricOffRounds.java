package main.nativeapp.games.cricoff.pageobjects;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.ExtentManager;
import main.nativeapp.core.LogsManager;
import main.websocket.SocketServiceData;
import main.websocket.WebClient;
import main.websocket.bidSubmission;
import main.websocket.bidSubmissionPayload;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

public class gamesOyeCricOffRounds extends DriverManager {
    bidSubmissionPayload bidpayload = new bidSubmissionPayload();
    bidSubmission bid = new bidSubmission();
    ObjectMapper mapper = new ObjectMapper();

    protected AndroidDriver driver;

    public gamesOyeCricOffRounds(AndroidDriver driver){
        this.driver = driver;
    }

    private static final By swipeRightToLockCricos = By.xpath("//android.widget.TextView[@text=\"Swipe Right to Lock Cricos for the Round\"]");
    private static final By roundWin = By.xpath("//android.widget.TextView[@text=\"You Won!\"]");
    private static final By roundLost = By.xpath("//android.widget.TextView[@text=\"You Lost!\"]");
    private static final By roundTied = By.xpath("//android.widget.TextView[@text=\"Tied!\"]");
    private static final By playAgain = By.xpath("//android.widget.TextView[@text=\"Play Again\"]");
    private static final By exit = By.xpath("//android.widget.TextView[@text=\"Exit\"]");
    private static final By swipeToLock = By.xpath("//android.widget.TextView[@text=\"Swipe Right to Lock Cricos for the Round\"]/following-sibling::android.view.ViewGroup//android.widget.ImageView");
    private static final By roundPoints = By.xpath("//android.widget.TextView[@resource-id='PointsContainer-RoundResult-PointsContainer-PointsText']");
    public static final By roundName = By.xpath("//android.widget.TextView[@resource-id='CricOffMatchScreen-AnimatedRoundName']");
    private static final By firstRound = By.xpath("//android.widget.TextView[@text=\"Round 1\"]");
    private static final By firstRoundName = By.xpath("//android.widget.TextView[@text=\"Batting\"]");
    private static final By quitGameButton = By.xpath("(//*[contains(@class, 'com.horcrux.svg.s')])[3]");
    private static final By acceptQuitGame = By.xpath("//android.widget.TextView[@resource-id=\"ConsentModal-SecondaryButtonText\"]");
    private static final By contestWinText = By.xpath("//android.widget.TextView[@resource-id=\"CricoffMatchResult-StatusText\"]");
    public static final By stadiumMatchMaking = By.xpath("//android.widget.TextView[contains(@text,\"Stadium\")]");
    public static final By stadiumRoundStart = By.xpath("//android.widget.TextView[contains(@text,'Stadium')]");
    public static final By currentPoints = By.xpath("//android.widget.TextView[@resource-id='PointsContainer-RoundResult-PointsContainer-PointsText']");
    private static final By roundName = By.xpath("//android.widget.TextView[@resource-id='CricOffMatchScreen-AnimatedRoundName']");
    private static final By batting = By.xpath("//android.widget.TextView[@text='Batting']");
    private static final By bowling = By.xpath("//android.widget.TextView[@text='Bowling']");

    public String getStadiumName(By stadiumName) throws CustomException {
        String stadium="";
        try{
            waitUntilPresenceOfElement(stadiumName,Duration.ofSeconds(30));
            stadium= driver.findElement(stadiumName).getText();
            ExtentManager.getExtentTestPool().pass("Stadium name is being displayed as "+stadium);
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - stadium name");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        return stadium;
    }

    public void assertContestWinScreen() throws CustomException {
        try{
            waitUntilPresenceOfElement(contestWinText,Duration.ofSeconds(20));
            ExtentManager.getExtentTestPool().pass("Contest Win Text is being displayed");
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - Round win text");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
    }

    public void assertMatchStart() throws CustomException {
        boolean elementFlag= false;
        try{
            waitUntilPresenceOfElement(this.firstRound,Duration.ofSeconds(30));
            waitUntilPresenceOfElement(this.firstRoundName, Duration.ofSeconds(30));
            elementFlag= true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - Round 1 text or Batting text");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(elementFlag);
    }

    public void clickQuitGameButton() throws CustomException {
        boolean clickAction= false;
        try{
            waitUntilPresenceOfElement(this.quitGameButton,Duration.ofSeconds(10));
            click_element(this.quitGameButton);
            clickAction= true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to click element - Quit game button");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(clickAction);
    }

    public void acceptQuitGameConsent() throws CustomException {
        boolean clickAction= false;
        try{
            waitUntilPresenceOfElement(this.acceptQuitGame,Duration.ofSeconds(10));
            click_element(this.acceptQuitGame);
            clickAction= true;
        } catch (CustomException e) {
            ExtentManager.getExtentTestPool().fail("Unable to click element - Quit game consent accept button");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
        }
        Assert.assertTrue(clickAction);
    }

    public boolean playAgain() throws CustomException {
        waitUntilPresenceOfElement(playAgain,Duration.ofSeconds(20));
        waitUntilPresenceOfElement(exit,Duration.ofSeconds(20));
        boolean roundWinFlag = false;
        try {
            roundWinFlag = driver.findElement(roundWin).isDisplayed();
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element");
            ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
            throw new CustomException("Unable to find the element - Pl check the object locator");
        }
        return roundWinFlag;

    }

    public void assertRoundAfterAppBackground() throws CustomException {
        waitUntilPresenceOfElement(swipeRightToLockCricos,Duration.ofSeconds(20));
        boolean roundStatus = false;
        try {
            roundStatus = driver.findElement(batting).isDisplayed();
        } catch (Exception e) {
             roundStatus = driver.findElement(bowling).isDisplayed();
        }
        Assert.assertTrue(roundStatus);
    }

    public void submitBid(String bidValue) throws CustomException {
        waitUntilPresenceOfElement(swipeRightToLockCricos,Duration.ofSeconds(20));
        String[] bidNumbers = bidValue.split("");
        for (String bidNumber : bidNumbers) {
            click_element(By.xpath("//android.view.ViewGroup[@content-desc='" + bidNumber + "']"));
        }
        waitUntilPresenceOfElement(swipeToLock,Duration.ofSeconds(20));
        WebElement we = driver.findElement(swipeToLock);
        Actions act = new Actions(driver);
        act.clickAndHold(we).moveByOffset(1000,0).release().build().perform();
    }

    public void bidPayload(SocketServiceData context, String bidValue, String contestSessionId, int roundNumber, String roundGroup) {
        bidpayload.setBidValue(String.valueOf(bidValue));
        bidpayload.setContestSessionId(contestSessionId);
        bidpayload.setRoundNumber(String.valueOf(roundNumber));
        bidpayload.setRoundGroup(roundGroup);
        bid.setAction("submitBid");
        bid.setPayload(bidpayload);
        JsonNode messageJson = mapper.valueToTree(bid);
        context.actualMessage = messageJson.toPrettyString();
    }

    public void opponentsubmitBid(SocketServiceData context, String bidValue, String contestSessionId, int roundNumber, String roundGroup, WebClient socketClient) {
        bidPayload(context, bidValue, contestSessionId, roundNumber, roundGroup);
    }

    public void submitBidsForRounds(AndroidDriver driver, DataTable table, SocketServiceData context, String contestSessionId, WebClient socketClient, String roundGroup, gamesOyeCricOffRounds roundsPage) throws JsonProcessingException, URISyntaxException, InterruptedException, MalformedURLException, CustomException {
        List<List<String>> rows = table.asLists(String.class);
        int roundWins = 0;
        for (int i = 1; i < rows.get(0).size(); i++) {
            waitUntilPresenceOfElement(roundName,Duration.ofSeconds(20));
            WebElement roundNameElement = driver.findElement(roundName);
            String roundName = rows.get(0).get(i);
            String bidValue = rows.get(1).get(i);
            String appBidValue = rows.get(2).get(i);
            if (roundName.equals(roundNameElement.getText())) {
                opponentsubmitBid(context, appBidValue, contestSessionId, i, roundGroup, socketClient);
                socketClient.connectAndListen(context);
                roundWins = roundBidSubmission(String.valueOf(i), roundName, bidValue, roundWins);
                context.messageList.remove(0);
            }
        }
    }

    public void checkRoundResult(AndroidDriver driver, int expectedPoints) throws CustomException {
        waitUntilPresenceOfElement(roundPoints,Duration.ofSeconds(20));
        WebElement roundPointsElement = driver.findElement(roundPoints);
        int roundPts = Integer.parseInt(roundPointsElement.getText());
        Assert.assertEquals(roundPts, expectedPoints, "User should have ");
    }

    public int roundBidSubmission(String roundNumber, String roundName, String bidValue, int roundWins) throws CustomException {
        if (roundWins < 3) {
            submitBid(bidValue);
            By[] roundFlags = {roundWin, roundLost, roundTied};
            String[] logMessages = {"Won the Round", "Lost the Round", "Tied the Round"};

            for (int i = 0; i < roundFlags.length; i++) {
                try {
                    WebElement element = driver.findElement(roundFlags[i]);
                    if (element != null && element.isDisplayed()) {
                        if (i == 0) {
                            roundWins++;
                        }
                        LogsManager.getLogger().log(Level.INFO, logMessages[i] + " - '" + roundNumber + "' and Round Name - " + roundName);
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return roundWins;
    }


}