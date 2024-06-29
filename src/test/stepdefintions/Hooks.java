package test.stepdefintions;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import main.nativeapp.core.ExtentManager;

import java.io.IOException;

public class Hooks {

    ExtentManager extentManager = new ExtentManager("test.html");

    public Hooks() throws IOException {
    }

    @Before
    public void beforeScenario(Scenario scenario){
        ExtentTest test = ExtentManager.getExtentReportsPool().createTest(scenario.getName());
        extentManager.setExtentTestPool(test);
    }

    @AfterAll
    public static void beforeScenario(){
        ExtentManager.getExtentReportsPool().flush();
    }


}
