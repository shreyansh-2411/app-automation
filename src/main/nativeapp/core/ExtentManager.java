package main.nativeapp.core;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;

public class ExtentManager {

    private static final ThreadLocal<ExtentTest> extentTestPool = new ThreadLocal<>();
    private static final ThreadLocal<ExtentReports> extentReportsPool = new ThreadLocal<>();

   public ExtentManager(String filePath) throws IOException {
      ExtentReports reports = new ExtentReports();
      ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
       final File CONF = new File("html-config.xml");
       sparkReporter.loadXMLConfig(CONF);
      reports.attachReporter(sparkReporter);
       setExtentReportsPool(reports);
    }

    public void setExtentReportsPool(ExtentReports reporter){
        extentReportsPool.set(reporter);
    }

    public static ExtentReports getExtentReportsPool(){
        return extentReportsPool.get();
    }

    public void setExtentTestPool(ExtentTest test){
        extentTestPool.set(test);
    }

    public static ExtentTest getExtentTestPool(){
       return extentTestPool.get();
    }

    public static void unLoad(){
        extentTestPool.remove();
        extentReportsPool.remove();
    }



}
