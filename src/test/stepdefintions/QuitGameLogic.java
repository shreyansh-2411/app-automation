package test.stepdefintions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeContestPage;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeCricOffRounds;
import main.nativeapp.gamesoye.pageobjects.gamesOyeHomePage;
import main.websocket.SocketServiceData;
import main.websocket.WebClient;
import main.websocket.utilities;
import org.testng.Assert;

import java.io.IOException;
import java.net.URISyntaxException;

import static main.utils.JsonReader.getJsonArray;

public class QuitGameLogic extends DriverManager {

    gamesOyeCricOffRounds roundsPage= new gamesOyeCricOffRounds(DriverManager.getDriverPool());
    gamesOyeHomePage homePage= new gamesOyeHomePage(DriverManager.getDriverPool());
    gamesOyeContestPage contestPage= new gamesOyeContestPage(DriverManager.getDriverPool());
    SocketServiceData context= MatchConclusions.getContextPool();
    WebClient socketClient;
    utilities component = new utilities();
    public String boltBalance;
    public String contestsPlayed;

    @After
    public void tearDown() {
        DriverManager.getDriverPool().quit();
    }

    @Then("user should land on game screen of the first round")
    public void user_should_land_on_game_screen_of_first_round() throws CustomException {
        roundsPage.assertMatchStart();
    }

    @And("user clicks on Quit game button")
    public void user_clicks_on_quit_game_button() throws CustomException {
        roundsPage.clickQuitGameButton();
    }

    @And("user clicks on Quit game button and accepts the consent modal")
    public void user_clicks_and_accepts_quit_game_consent() throws CustomException {
        roundsPage.clickQuitGameButton();
        roundsPage.acceptQuitGameConsent();
    }

    @Then("user should land on Home page")
    public void userShouldLandOnHomePage() throws CustomException {
        homePage.assertHomePageIsDisplayed();
    }

    @Then("user should land on game win screen")
    public void userShouldLandOnGameWinScreen() throws CustomException {
        roundsPage.assertContestWinScreen();
    }

    @And("user with phone number{int} quits the game")
    public void userWithPhoneNumberQuitsTheGame() throws IOException, URISyntaxException, InterruptedException {
        String deviceId = getJsonArray("WebSocket").get("DEVICE_ID").getAsString();
        socketClient = component.webSocketClientReq(context, deviceId);
        component.quitGame(MatchConclusions.getContestSessionId(),deviceId,context,socketClient);
    }

    @And("user gets the wallet balance from home page")
    public void userGetsTheWalletBalanceFromHomePage() throws CustomException {
        boltBalance= contestPage.getBoltBalance();
    }

    @Then("user should get the bolt balance with deducted fee of {int}")
    public void userShouldGetTheBoltBalanceWithDeductedFeeOf(int joiningFee) throws CustomException {
        int balanceBeforeMatch= Integer.parseInt(boltBalance);
        int balanceAfterMatch= Integer.parseInt(contestPage.getBoltBalance());
        Assert.assertEquals(balanceAfterMatch,balanceBeforeMatch-joiningFee);
    }

    @And("Ui user submit the bids for the respective rounds of {string}")
    public void uiUserSubmitTheBidsForTheRespectiveRoundsOf(DataTable table, String roundGroupName) throws CustomException {
        component.submitBidsForRounds(DriverManager.getDriverPool(), table, roundGroupName);
    }

    @And("user gets the contests played count from the user profile page")
    public void userGetsTheContestsPlayedCountFromTheUserProfilePage() throws CustomException {
        homePage.clickProfileButton();
        contestsPlayed= homePage.getContestsPlayed();
        homePage.clickHomeButton();
    }

    @Then("contests played count should be increased in the profile page")
    public void contestsPlayedCountShouldBeIncreasedInTheProfilePage() throws CustomException {
        homePage.clickProfileButton();
        int currentContestsPlayed = Integer.parseInt(homePage.getContestsPlayed());
        Assert.assertEquals(currentContestsPlayed,Integer.parseInt(contestsPlayed));
    }

    @Then("user should land on the game screen of existing contest")
    public void userShouldLandOnTheGameScreenOfExistingContest() {
        // write code to verify is user have landed on existing contest
    }
}