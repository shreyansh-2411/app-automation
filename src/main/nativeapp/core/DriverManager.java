package main.nativeapp.core;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import main.nativeapp.core.DriverOptions;
import main.nativeapp.core.ExtentManager;
import main.nativeapp.core.CustomException;

import static main.utils.JsonReader.*;

public class DriverManager extends DriverOptions{

   private static final ThreadLocal<AndroidDriver> driverPool = new ThreadLocal<>();

    public static AndroidDriver getDriverPool() {
        return driverPool.get();
    }

    public void setDriverPool(AndroidDriver driverPool) {
        DriverManager.driverPool.set(driverPool);
    }


    public static void unLoad(){
        driverPool.remove();
    }

    public void launchAndroidDriver() throws IOException {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(APP, System.getProperty("user.dir")+getJsonArray(LOCAL_OPTIONS).get(APP).getAsString());
            capabilities.setCapability(PLATFORM_NAME, getJsonArray(LOCAL_OPTIONS).get(PLATFORM_NAME).getAsString());
            capabilities.setCapability(DEVICE_NAME, getJsonArray(LOCAL_OPTIONS).get(DEVICE_NAME).getAsString());
            capabilities.setCapability(IDLE_TIMEOUT, getJsonArray(LOCAL_OPTIONS).get(IDLE_TIMEOUT).getAsString());
            capabilities.setCapability(GRANT_PERMISSIONS, getJsonArray(LOCAL_OPTIONS).get(GRANT_PERMISSIONS).getAsString());
            capabilities.setCapability(AUTOMATION_NAME, getJsonArray(LOCAL_OPTIONS).get(AUTOMATION_NAME).getAsString());
            ExtentManager.getExtentReportsPool().setSystemInfo(PLATFORM_NAME, getJsonArray(LOCAL_OPTIONS).get(PLATFORM_NAME).getAsString());
            ExtentManager.getExtentReportsPool().setSystemInfo(DEVICE_NAME, getJsonArray(LOCAL_OPTIONS).get(DEVICE_NAME).getAsString());
            ExtentManager.getExtentReportsPool().setSystemInfo(IDLE_TIMEOUT, getJsonArray(LOCAL_OPTIONS).get(IDLE_TIMEOUT).getAsString());
            ExtentManager.getExtentReportsPool().setSystemInfo(GRANT_PERMISSIONS, getJsonArray(LOCAL_OPTIONS).get(GRANT_PERMISSIONS).getAsString());
            ExtentManager.getExtentReportsPool().setSystemInfo(AUTOMATION_NAME, getJsonArray(LOCAL_OPTIONS).get(AUTOMATION_NAME).getAsString());
            setDriverPool(new AndroidDriver(new URL(getJsonData().get("appiumLocalUrl").getAsString()), capabilities));
    }

    public void launchLambdaTest() throws IOException {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            HashMap<String, Object> ltOptions = new HashMap<>();
            ltOptions.put(BUILD_NAME, getJsonArray(LT_OPTIONS).get(BUILD_NAME).getAsString());
            ltOptions.put(PLATFORM_NAME, getJsonArray(LT_OPTIONS).get(PLATFORM_NAME).getAsString());
            ltOptions.put(DEVICE_NAME, getJsonArray(LT_OPTIONS).get(DEVICE_NAME).getAsString());
            ltOptions.put(PLATFORM_VERSION, getJsonArray(LT_OPTIONS).get(PLATFORM_VERSION).getAsString());
            ltOptions.put(IS_REAL_MOB, getJsonArray(LT_OPTIONS).get(IS_REAL_MOB).getAsString());
            ltOptions.put(APP, System.getProperty("user.dir")+getJsonArray(LT_OPTIONS).get(APP).getAsString());
            capabilities.setCapability("lt:options", ltOptions);
            String url = "https://" + getJsonData().get("lt_username").getAsString() + ":" + getJsonData().get("lt_accessKey").getAsString() + getJsonData().get("lt_gridUrl").getAsString();
            setDriverPool(new AndroidDriver(new URL(url), capabilities));
    }

    public void waitUntilPresenceOfElement(By elementLocator, Duration maxTime) throws CustomException {
        try {
            new WebDriverWait(getDriverPool(),maxTime)
                    .until(ExpectedConditions.presenceOfElementLocated( elementLocator));
            ExtentManager.getExtentTestPool().pass("Waited until presence of element located - MaxDuration "+ maxTime);
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to find the presence of element - MaxDuration "+ maxTime);
            ExtentManager.getExtentTestPool().fail(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
            throw new CustomException("Unable to find the element before the max wait time- Pl check the object locator");
        }
    }

    public void waitUntilVisibilityOfElement(By elementLocator, Duration maxTime) throws CustomException {
        try {
            new WebDriverWait(getDriverPool(),maxTime)
                    .until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to find the visibilty of element - MaxDuration "+ maxTime);
            ExtentManager.getExtentTestPool().fail(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
            throw new CustomException("Unable to find the element - Pl check the object locator");
        }
    }

    public void waitUntilInVisibilityOfElement(By elementLocator, Duration maxTime) throws CustomException {
        try {
            new WebDriverWait(getDriverPool(),maxTime)
                    .until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to find element - MaxDuration "+ maxTime);
            ExtentManager.getExtentTestPool().fail(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
            throw new CustomException("Element is Invisible - Pl check the object locator");
        }
    }

    public void click_element(By elementLocator) throws CustomException {
        try {
            driverPool.get().findElement(elementLocator).click();
            ExtentManager.getExtentTestPool().pass("performed click element "+ elementLocator);
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to click element "+ elementLocator);
            ExtentManager.getExtentTestPool().fail(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
            throw new CustomException("Unable to perform click action on the element - Pl check the object locator");
        }
    }

    public void send_keys(By elementLocator,String text) throws CustomException {
        try {
            driverPool.get().findElement(elementLocator).sendKeys(text);
            ExtentManager.getExtentTestPool().pass("performed send text for element "+ elementLocator+" text = "+text);
        } catch (Exception e) {
            ExtentManager.getExtentTestPool().fail("Unable to send text to element "+ elementLocator);
            ExtentManager.getExtentTestPool().fail(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
            throw new CustomException("Unable to find the element - Pl check the object locator");
        }
    }

    protected String takeScreenshot() throws CustomException {
        try {
            TakesScreenshot screenshot = driverPool.get();
            File file = screenshot.getScreenshotAs(OutputType.FILE);
            long count = System.currentTimeMillis();
            File targetFile=new File("img_"+ count +".jpg");
            FileUtils.copyFile(file,targetFile);
            return "img_"+ count +".jpg";
        } catch (IOException e) {
            throw new CustomException("Unable to takeScreenshot or unable to create a img file");
        }
    }

}