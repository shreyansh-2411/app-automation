package test.stepdefintions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.DriverOptions;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeContestPage;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeCricOffRounds;
import main.websocket.*;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static main.utils.JsonReader.getJsonArray;

public class MatchConclusions extends DriverManager {

    private final AndroidDriver driver;
    gamesOyeCricOffRounds roundsPage;
    gamesOyeContestPage contestPage;
    ObjectMapper mapper = new ObjectMapper();
    utilities component = new utilities();
    SocketServiceData context;
    String beforeStarBalance;
    static ThreadLocal<SocketServiceData> contextPool;
    static ThreadLocal<String> contestSessionIdPool;
    String contestSessionId;
    MatchmakingSuccess result;
    WebClient socketClient;
    public String matchMakingStadium="";
    public String roundStartStadium="";

    public static String getContestSessionId(){
        return contestSessionIdPool.get();
    }

    public static SocketServiceData getContextPool(){
        return contextPool.get();
    }

    public MatchConclusions() throws IOException, URISyntaxException, InterruptedException {
        this.driver =  DriverManager.getDriverPool();
    }

    @After
    public void tearDown() {
        DriverManager.getDriverPool().quit();
    }

    @Then("user is able to match with user before {int} seconds using phone number1 and phone number2")
        public void user_is_able_to_match_with_before_seconds_using_phone_number1_and_phone_number2(int timeinSeconds) throws URISyntaxException, InterruptedException, IOException, CustomException {
            contestPage = new gamesOyeContestPage(driver);
            context = new SocketServiceData();
            context.URI = getJsonArray("WebSocket").get("WEBSOCKET_URI").getAsString();
            context.requestHeaders.put("app-device-id", getJsonArray("WebSocket").get("DEVICE_ID").getAsString());
            context.requestHeaders.put("authorization", "Bearer " + DriverOptions.bearer2);
            contextPool.set(context);
            socketClient = component.webSocketClientReq(context, getJsonArray("WebSocket").get("DEVICE_ID").getAsString());
            contestPage.matchMakingWithUser(timeinSeconds);
            result = mapper.readValue(context.messageList.get(0), MatchmakingSuccess.class);
            contestSessionId = String.valueOf(result.getData().getId());
            HashMap<String, String> starBalanceMap = component.fetchstarBalance(DriverOptions.bearer1);
            beforeStarBalance = starBalanceMap.get("starBalance");
            contestSessionIdPool.set(contestSessionId);
        }

    @Given("user submit the bids for the respective rounds")
    public void user_submit_the_bids_for_the_respective_rounds(DataTable table) throws IOException, URISyntaxException, InterruptedException, CustomException {
        if (result.getEventType().equals("MATCH_MAKING_SUCCESS") || result.getEventType().equals("NEXT_ROUND")) {
            roundsPage = new gamesOyeCricOffRounds(driver);
            String username1 = result.getData().getUsers().get(0).getUserId();
            Assert.assertTrue(DriverOptions.walletInfo.stream().anyMatch(map -> map.containsValue(username1)));
            socketClient = component.webSocketClientReq(context, getJsonArray("WebSocket").get("DEVICE_ID").getAsString());
            roundsPage.submitBidsForRounds(driver, table, context, contestSessionId, socketClient, "1", roundsPage);
        }
    }

    @Then("user is wait on current screen for {int} seconds")
    public void user_waits_on_current_screen(Integer timeInSeconds) throws InterruptedException {
        Thread.sleep(timeInSeconds * 1000);
    }

    @Given("user submit the 1st tiebreaker bids for the respective rounds")
    public void user_submit_the_1st_tiebreaker_bids_for_the_respective_rounds(DataTable table) throws JsonProcessingException, URISyntaxException, InterruptedException, MalformedURLException, CustomException {
        roundsPage = new gamesOyeCricOffRounds(driver);
        roundsPage.submitBidsForRounds(driver, table, context, contestSessionId, socketClient, "2", roundsPage);
    }

    @Given("user submit the 2nd tiebreaker bids for the respective rounds")
    public void user_submit_the_2nd_tiebreaker_bids_for_the_respective_rounds(DataTable table) throws JsonProcessingException, URISyntaxException, InterruptedException, MalformedURLException, CustomException {
        roundsPage = new gamesOyeCricOffRounds(driver);
        roundsPage.submitBidsForRounds(driver, table, context, contestSessionId, socketClient, "3", roundsPage);
    }

    @Then("User1 won the 1st round and won user will be given 12 pts")
        public void user1_won_the_1st_round_and_won_user_will_be_given_12_pts() throws CustomException {
        component.UserWonTheRound(driver);
    }

    @Then("User1 lost the 1st round and lost user will be given 6 pts")
    public void user1_lost_the_1st_round_and_lost_user_will_be_given_6_pts() throws CustomException {
        component.UserLostTheRound(driver);
    }

    @Then("User1 tied the 1st round and tied user will be given 9 pts")
    public void user_1_tied_the_1st_round_and_tied_user_will_be_given_9_pts() throws CustomException {
        component.UserTiedTheRound(driver);
    }

    @Then("user remaining stars for levelup that are displayed on UI are coming from API")
    public void user_remaining_stars_for_levelup_that_are_displayed_on_UI_are_coming_from_API() throws CustomException {
        HashMap<String, String> starBalanceMap = component.fetchstarBalance(DriverOptions.bearer1);
        String remainingStars = starBalanceMap.get("starsRequiredForNextLevel");
        component.RemainingStars(driver, remainingStars);
    }

    @Then("game got concluded and user gets back there joining fees")
    public void game_got_concluded_and_user_gets_back_there_joining_fees() throws CustomException, InterruptedException {
        HashMap<String, String> walletBalanceMap = component.fetchWalletBalance(DriverOptions.bearer1);
        String walletBalance = walletBalanceMap.get("boltBalance");
        component.WalletBalance(driver, walletBalance);
    }

    @Then("user would be getting only participation stars")
    public void user_would_be_getting_only_participation_stars() throws IOException {
        HashMap<String, String> starBalanceMap = component.fetchstarBalance(DriverOptions.bearer1);
        String afterStarBalance = starBalanceMap.get("starBalance");
        component.ParticipationStars(afterStarBalance ,beforeStarBalance);
    }

    @Then("user would be getting participation stars and winning stars")
    public void user_would_be_getting_only_participation_stars_and_winning_stars() throws IOException {
        HashMap<String, String> starBalanceMap = component.fetchstarBalance(DriverOptions.bearer1);
        String afterStarBalance = starBalanceMap.get("starBalance");
        component.WinningStars(afterStarBalance ,beforeStarBalance);
    }

    @And("user should see the stadium name at the matchmaking screen")
    public void userShouldSeeTheStadiumNameAtTheMatchmakingScreen() throws CustomException {
        matchMakingStadium= roundsPage.getStadiumName(roundsPage.stadiumMatchMaking);
    }

    @Then("user should see the stadium name at round start screen which should be same as matchmaking screen")
    public void userShouldSeeTheStadiumNameAtRoundStartScreenWhichShouldBeSameAsMatchmakingScreen() throws CustomException {
        roundStartStadium= roundsPage.getStadiumName(roundsPage.stadiumRoundStart);
        Assert.assertEquals(matchMakingStadium,roundStartStadium);
    }

    @Then("user should have {int} cricos to play {int} rounds")
    public void userShouldHaveCricosToPlayRounds(int cricos, int rounds) {
        // cricos element not accessible yet, step definition yet to be written.
    }
}