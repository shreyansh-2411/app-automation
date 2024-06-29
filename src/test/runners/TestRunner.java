package test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import main.nativeapp.core.DriverManager;
import main.nativeapp.core.ExtentManager;
import org.testng.annotations.AfterSuite;


@CucumberOptions(features = "src/test/resources/featureFiles/"
        ,glue = "test.stepdefintions"
        ,tags = "@MCT_TS_11")
public class TestRunner extends  AbstractTestNGCucumberTests{

    @AfterSuite
    public void afterSuite(){
        DriverManager.unLoad();
        ExtentManager.unLoad();
    }


}
