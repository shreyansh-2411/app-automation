package test.stepdefintions;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.DriverOptions;
import main.nativeapp.core.LogsManager;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeContestPage;
import main.nativeapp.games.cricoff.pageobjects.gamesOyeCricOffRounds;
import main.nativeapp.gamesoye.pageobjects.gamesOyeHomePage;
import main.nativeapp.gamesoye.pageobjects.gamesOyeInfoPage;
import main.websocket.utilities;

import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static main.utils.JsonReader.getJsonArray;

public class MatchMakingScreen  extends DriverManager{
    gamesOyeInfoPage infoPage;
    gamesOyeHomePage homePage;
    gamesOyeCricOffRounds roundsPage;
    gamesOyeContestPage contestPage;
    utilities component = new utilities();
    @After
    public void tearDown() {
        DriverManager.getDriverPool().quit();
    }

    @Given("user launch gamesoye app")
    public void user_launch_gamesoye_app() throws IOException {
        launchAndroidDriver();
        infoPage = new gamesOyeInfoPage(DriverManager.getDriverPool());
        homePage = new gamesOyeHomePage(DriverManager.getDriverPool());
        roundsPage = new gamesOyeCricOffRounds(DriverManager.getDriverPool());
        contestPage = new gamesOyeContestPage(DriverManager.getDriverPool());
    }

    @Given("log in using phone number as {string} and otp as {string}")
    public void log_in_using_phone_number_as_and_otp_as(String phoneNumber, String otp) throws CustomException, InterruptedException {
        infoPage.loginUsingPhoneNumber(phoneNumber, otp);
    }

    @Given("log in to device_1 and device_2")
    public void log_in_to_device_1_and_device_2(DataTable userData) throws CustomException, InterruptedException, IOException {
        List<Map<String, String>> map = userData.asMaps();
        infoPage.loginUsingPhoneNumber(map.get(0).get("Phonenumber"), map.get(0).get("otp"));
        component.generateOtp(map.get(0).get("Phonenumber"), getJsonArray("WebSocket").get("DREAM_AUTH_AUTHORIZATION").getAsString(), getJsonArray("WebSocket").get("CONNECTION_NAME").getAsString());
        DriverOptions.bearer1 = component.generateBearerToken(map.get(0).get("Phonenumber"), getJsonArray("WebSocket").get("DREAM_AUTH_AUTHORIZATION").getAsString(), getJsonArray("WebSocket").get("CONNECTION_NAME").getAsString());
        component.generateOtp(map.get(1).get("Phonenumber"), getJsonArray("WebSocket").get("DREAM_AUTH_AUTHORIZATION").getAsString(), getJsonArray("WebSocket").get("CONNECTION_NAME").getAsString());
        DriverOptions.bearer2 = component.generateBearerToken(map.get(1).get("Phonenumber"), getJsonArray("WebSocket").get("DREAM_AUTH_AUTHORIZATION").getAsString(), getJsonArray("WebSocket").get("CONNECTION_NAME").getAsString());
        DriverOptions.walletInfo.add(component.fetchWalletBalance(DriverOptions.bearer1));
        DriverOptions.walletInfo.add(component.fetchWalletBalance(DriverOptions.bearer2));

    }
    @When("user clicks play now on cricoff banner in home page")
    public void user_clicks_play_now_on_cricoff_banner_in_home_page() throws CustomException, InterruptedException {
        homePage.playGame();
    }

    @Then("contest is disabled and message should be shown as {string}")
    public void contest_is_disabled_and_message_should_be_shown_as(String message) throws CustomException {
        contestPage.assertNotEnoughBoltsMessage(message);
    }

    @Then("user clicks play now on contest")
    public void user_clicks_play_now_on_contest() throws CustomException, InterruptedException {

        LogsManager.getLogger().log(Level.INFO,"Available Bling Balance - "+contestPage.getBlingBalance());
        LogsManager.getLogger().log(Level.INFO,"Available Bolt Balance - "+contestPage.getBoltBalance());
//        contestPage.howToPlay();
//        DriverManager.getDriverPool().pressKey(new KeyEvent(AndroidKey.BACK));
        contestPage.joinContest();
    }

    @Then("user is landed on match making screen")
    public void user_is_landed_on_match_making_screen() throws CustomException {
        contestPage.matchMakingScreen();
    }

    @Then("user is able to match with user before {int} seconds")
    public void user_is_able_to_match_with_before_seconds(int timeinSeconds) throws Exception {
        contestPage.matchMakingWithUser(timeinSeconds);
    }

    @When("user runs the app in background for {int} seconds")
    public void user_runs_the_app_in_background_for_seconds(Integer timeInSeconds) {
        DriverManager.getDriverPool().runAppInBackground(Duration.ofSeconds(timeInSeconds));
    }
    @When("user relaunch the app")
    public void user_relaunch_the_app() {
        DriverManager.getDriverPool().activateApp("com.gamesoye");

    }
    @Then("user is landed on active running contest")
    public void user_is_landed_on_active_running_contest() throws CustomException {
        roundsPage.assertRoundAfterAppBackground();
    }

    @When("user toggle the wifi")
    public void user_toggle_the_wifi() throws InterruptedException {
        Thread.sleep(1000);
        DriverManager.getDriverPool().toggleData();
        DriverManager.getDriverPool().toggleWifi();
    }


}
