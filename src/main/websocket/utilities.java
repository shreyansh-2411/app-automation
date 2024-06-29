package main.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.ExtentManager;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeCricOffRounds;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.utils.JsonReader.getJsonArray;

public class utilities extends DriverManager {
    gamesOyeCricOffRounds roundsPage = new gamesOyeCricOffRounds(DriverManager.getDriverPool());
    initBuilder otpInit = new initBuilder();
    ObjectMapper mapper = new ObjectMapper();
    bidSubmissionPayload bidpayload = new bidSubmissionPayload();
    bidSubmission bid = new bidSubmission();
    gameQuitPayload quitPayload = new gameQuitPayload();
    quitGame quit = new quitGame();
    gamesOyeCricOffRounds roundPage = new gamesOyeCricOffRounds(DriverManager.getDriverPool());
    private static List<String> gameRounds= new ArrayList<>(){
        {
            add("Batting");
            add("Bowling");
            add("Fielding");
            add("Captaincy");
            add("Fitness");
        }
    };
    private static HashMap<String,Integer> roundGroup= new HashMap<>(){
        {
            put("roundGroup1",5);
            put("roundGroup2",3);
            put("fourRoundMatch",4);
        }
    };

    public void quitGame(String contestSessionId, String deviceId, SocketServiceData context, WebClient socketClient) throws InterruptedException {
        quitGamePayload(contestSessionId,deviceId,context);
        socketClient.connectAndListen(context);
        context.messageList.remove(0);
    }

    public void quitGamePayload(String contestSessionId, String deviceId, SocketServiceData context){
        quitPayload.setDeviceId(deviceId);
        quitPayload.setContestSessionId(contestSessionId);
        quit.setAction("quitContestSession");
        quit.setPayload(quitPayload);
        JsonNode messageJson = mapper.valueToTree(quit);
        context.actualMessage = messageJson.toPrettyString();
    }

    public void generateOtp(String phoneNumber, String dreamAuth, String connectionName) {
        init otpInitObj = otpInit.initBuilder("SIGNINUP", "sms", phoneNumber);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-DREAMAUTH-AUTHORIZATION", dreamAuth);
        headers.put("Content-Type", "application/json");
        headers.put("x-auth-connection", connectionName);
        RestAssured.given()
                .headers(headers).log().all()
                .baseUri("https://guardian-nv.d11platform.com/auth/passwordless/init")
                .body(otpInitObj)
                .post();
    }

    public String generateBearerToken(String phoneNUmber, String dreamAuth, String connectionName) {
        bearerToken otpVerification = new bearerTokenBuilder().bearerTokenBuilder(phoneNUmber, "999999");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-DREAMAUTH-AUTHORIZATION", dreamAuth);
        headers.put("Content-Type", "application/json");
        headers.put("x-auth-connection", connectionName);
        Response res = RestAssured.given()
                .headers(headers)
                .baseUri("https://guardian-nv.d11platform.com/auth/passwordless/complete")
                .body(otpVerification).log().all()
                .post();
        return res.body().jsonPath().getString("accessToken");
    }

    public HashMap<String, String> fetchWalletBalance(String bearer) {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("authorization", "Bearer " + bearer);
        headers.put("Content-Type", "application/json");
        Response res = RestAssured.given()
                .headers(headers)
                .baseUri("https://api-cric.staging.rario.com/wallet/balance/v2")
                .log().all()
                .get();
        map.put("userId", res.body().jsonPath().getString("data.bling.userId"));
        map.put("blingBalance", res.body().jsonPath().getString("data.bling.availableBalance"));
        map.put("boltBalance", res.body().jsonPath().getString("data.bolt.availableBalance"));
        map.put("bearerToken", bearer);
        return map;
    }

    public WebClient webSocketClientReq(SocketServiceData context, String deviceId) throws URISyntaxException, InterruptedException {
        WebClient socketClient = new WebClient(context);
        joinContest contestJoin = new joinContest();
        payloadObj payload = new payloadObj();
        payload.setContestId("1");
        payload.setDeviceId(deviceId);
        contestJoin.setPayload(payload);
        contestJoin.setAction("joinContest");
        JsonNode node = mapper.valueToTree(contestJoin);
        context.actualMessage = node.toPrettyString();
        socketClient.connectAndListen(context);
        return socketClient;
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

    public void submitBid(SocketServiceData context, String bidValue, String contestSessionId, int roundNumber, String roundGroup, WebClient socketClient) {
        bidPayload(context, bidValue, contestSessionId, roundNumber, roundGroup);
    }

    public void submitBidsForRounds(AndroidDriver driver, DataTable table, SocketServiceData context, String contestSessionId, WebClient socketClient, String roundGroup) throws JsonProcessingException, URISyntaxException, InterruptedException, MalformedURLException, CustomException {
        List<List<String>> rows = table.asLists(String.class);
        for (int i = 1; i < rows.get(0).size(); i++) {
            waitUntilPresenceOfElement(roundPage.roundName, Duration.ofSeconds(30));
            WebElement roundNameElement = driver.findElement(roundPage.roundName);
            String roundName = rows.get(0).get(i);
            String bidValue = rows.get(1).get(i);
            String appBidValue = rows.get(2).get(i);
            if (roundName.equals(roundNameElement.getText())) {
                submitBid(context, appBidValue, contestSessionId, i, roundGroup, socketClient);
                socketClient.connectAndListen(context);
                roundPage.submitBid(bidValue);
                context.messageList.remove(0);
            }
        }
    }

    public void submitBidsForRounds(AndroidDriver driver, DataTable table, String roundGroupName) throws CustomException {
        List<Map<String, String>> rows= table.asMaps();
        for(int i=0;i<roundGroup.get(roundGroupName);i++){
            waitUntilPresenceOfElement(roundPage.roundName, Duration.ofSeconds(30));
            WebElement roundNameElement = driver.findElement(roundPage.roundName);
            String roundName = gameRounds.get(i);
            String bidValue = rows.get(0).get(roundName);
            if(roundName.equals(roundNameElement.getText())){
                roundPage.submitBid(bidValue);
            }else{
                ExtentManager.getExtentTestPool().fail("Round name Miss match exception");
                ExtentManager.getExtentTestPool().addScreenCaptureFromPath(takeScreenshot());
            }
        }
    }

    public void checkRoundResult(AndroidDriver driver, int expectedPoints) throws CustomException {
        waitUntilPresenceOfElement(roundsPage.currentPoints, Duration.ofSeconds(30));
        WebElement roundPoints = driver.findElement(roundsPage.currentPoints);
        int roundPts = Integer.parseInt(roundPoints.getText());
        Assert.assertEquals(roundPts, expectedPoints, "User should have "+ expectedPoints + " points");
    }

    public void UserWonTheRound(AndroidDriver driver) throws CustomException {
        roundsPage.checkRoundResult(driver, 12);
    }

    public void UserLostTheRound(AndroidDriver driver) throws CustomException {
        roundsPage.checkRoundResult(driver, 6);
    }

    public void UserTiedTheRound(AndroidDriver driver) throws CustomException {
        roundsPage.checkRoundResult(driver, 9);
    }

    public HashMap<String, String> fetchstarBalance(String bearer) {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "Bearer " + bearer);
        headers.put("Content-Type", "application/json");
        Response res = RestAssured.given().headers(headers)
                .baseUri("https://api-cric.staging.rario.com/loyalty/profile")
                .log().all()
                .get();
        map.put("userId", res.body().jsonPath().getString("data.userId"));
        map.put("starsRequiredForNextLevel", res.body().jsonPath().getString("data.starsRequiredForNextLevel"));
        map.put("starBalance", res.body().jsonPath().getString("data.starBalance"));
        return map;
    }

    public static String getTextFromElement(AndroidDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public static void assertTextMatches(String fetchedText, String regex, String expectedValue) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fetchedText);
        if (matcher.find()) {
            String matchedValue = matcher.group(1);
            Assert.assertEquals(matchedValue, expectedValue, "Values do not match!");
        } else {
            Assert.fail("Pattern not found in text: " + fetchedText);
        }
    }

    public void RemainingStars(AndroidDriver driver, String remainingStars) {
        By levelInfoLocator = AppiumBy.xpath("//android.widget.TextView[@resource-id='CricoffMatchResult-LevelInfoText']");
        String levelInfoText = getTextFromElement(driver, levelInfoLocator);
        assertTextMatches(levelInfoText, "(\\d+) stars", remainingStars);
    }

    public void WalletBalance(AndroidDriver driver, String walletBalance) {
        By boltBalanceLocator = AppiumBy.id("RubyBalanceBoxSmall-Header-Bolt-Image"); // Adjust locator as needed
        String boltDisplayed = getTextFromElement(driver, boltBalanceLocator);
        assertTextMatches(boltDisplayed, "(\\d+)", walletBalance);
    }

    public void ParticipationStars(String afterStarBalance, String beforeStarBalance) throws IOException {
        int AfterStarBalance = Integer.parseInt(afterStarBalance);
        int BeforeStarBalance = Integer.parseInt(beforeStarBalance);
        int ParticipationStars = Integer.parseInt(getJsonArray("StarsData").get("participation_stars").getAsString());
        Assert.assertEquals(AfterStarBalance, BeforeStarBalance + ParticipationStars);
    }

    public void WinningStars(String afterStarBalance, String beforeStarBalance) throws IOException {
        int AfterStarBalance = Integer.parseInt(afterStarBalance);
        int BeforeStarBalance = Integer.parseInt(beforeStarBalance);
        int ParticipationStars = Integer.parseInt(getJsonArray("StarsData").get("participation_stars").getAsString());
        int WinningStars = Integer.parseInt(getJsonArray("StarsData").get("winning_stars").getAsString());
        Assert.assertEquals(AfterStarBalance, BeforeStarBalance + WinningStars + ParticipationStars);
    }
}
