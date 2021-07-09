package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.WaitOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.appium.AppiumServerJava;
import utils.driver.AndroidDriverManager;
import utils.driver.DriverManager;

import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

public class AutoRuTest {
    AppiumDriver driver;
    AppiumDriverLocalService appiumServer;

    @Test
    public void searchTest() {
        DriverManager driverManager = new AndroidDriverManager();
        appiumServer = AppiumServerJava.startServer();
        driver = driverManager.getDriver(appiumServer,"New_Device_1_API_30", "Android");
        driver.findElementByXPath("//*[@text='Марка и модель']").click();
        driver.findElementByXPath("//*[@text='Audi']").click();
        driver.findElementByXPath("//*[@text='A4']/../..//android.widget.CheckBox").click();
        driver.findElementById("ru.auto.ara:id/tvText").click();

        int height = (driver.findElementById("ru.auto.ara:id/snippetContainer").getSize().getHeight());
        int width = (driver.findElementById("ru.auto.ara:id/snippetContainer").getSize().getWidth());
        int startX = width / 2;
        int startY = (int) (height * 0.9);
        int endY = (int) (height * 0.1);
        TouchAction action = new TouchAction(driver);
        action.press(point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(10))).moveTo(point(startX, endY)).release().perform();

        driver.findElementById("ru.auto.ara:id/image").click();
        String titleText = driver.findElementById("ru.auto.ara:id/name").getText();
        boolean isTextPresent = titleText.contains("Audi A4");
        Assert.assertTrue(isTextPresent);

        //ru.auto.ara:id/tvText
        //
        //open app
        //choose make and model
        //click 'show'
        //click on 1st car from search results
        //assert car name
    }

    @AfterMethod
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
        appiumServer.stop();
    }
}