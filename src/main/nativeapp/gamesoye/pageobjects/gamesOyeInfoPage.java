package main.nativeapp.gamesoye.pageobjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import main.nativeapp.core.CustomException;
import main.nativeapp.core.DriverManager;
import org.openqa.selenium.By;
import java.time.Duration;

public class gamesOyeInfoPage extends DriverManager {

    protected AndroidDriver driver;

    public gamesOyeInfoPage(AndroidDriver driver){
        this.driver = driver;
    }

    private final By usePhoneNumber = By.xpath("//android.widget.TextView[@resource-id=\"info-continueWithPhone_text\"]");
    private final By phoneNumberTextbox = By.xpath("//android.widget.EditText[@text=\"Your 10-digit Phone Number\"]");
    private final By continueButton = AppiumBy.accessibilityId("Continue");
    private final By otpTextBox = By.xpath("//android.widget.EditText[@text=\"Enter Code\"]");
    private final By termsAndConditionsCheckbox = By.xpath("//android.widget.TextView[@text=\"I have read and accept the \"]/preceding-sibling::android.view.ViewGroup");
    private final By verifyButton = AppiumBy.accessibilityId("Verify");


    public void loginUsingPhoneNumber(String phoneNumber,String otpCode) throws InterruptedException, CustomException {
        waitUntilPresenceOfElement(this.usePhoneNumber,Duration.ofSeconds(30));
        click_element(this.usePhoneNumber);
        send_keys(this.phoneNumberTextbox,phoneNumber);
        click_element(this.continueButton);
        waitUntilPresenceOfElement(this.otpTextBox,Duration.ofSeconds(10));
        Thread.sleep(2000);
        send_keys(this.otpTextBox,otpCode);
        Thread.sleep(1000);
        click_element(this.termsAndConditionsCheckbox);
        click_element(this.verifyButton);
    }
}
