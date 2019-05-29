package Configurations;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Attachment;


public class Hooks{
    public static String chromeDriverPath_linux = "src/test/resources/driver/chromedriver";

    public Hooks()
    {

    }
    public static Hooks instance = new Hooks();

    public static Hooks getInstance()
    {
        if (instance == null){
            instance = new Hooks();
        }
        return instance;
    }

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
    {
        @Override
        public RemoteWebDriver initialValue() {
            //System.setProperty("webdriver.chrome.driver", chromeDriverPath_linux);

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();

            try {
                driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //options.addArguments("webdriver.chrome.driver", chromeDriverPath_linux);
            options.addArguments("window-size=1366,768", "--no-sandbox");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return new ChromeDriver(options); // can be replaced with other browser drivers
        }
    };

    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        return driver.get();
    }

    public void removeDriver() // Quits the driver and closes the browser
    {
        driver.remove();
    }

    @Before
    public void TestInitialize() {
        Hooks.getInstance().getDriver();
    }

    @After
    public void TearDownTest(Scenario scenario) throws InterruptedException {
        if (scenario.isFailed()) {
            System.out.println("--------Scenario Failed----------- " + scenario.getStatus());
        }
        Hooks.getInstance().removeDriver();
    }

    private static byte[] screenShot() throws InterruptedException {
        Thread.sleep(300);
        byte[] screenshot;
        screenshot = ((TakesScreenshot) Hooks.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
        return screenshot;
        //scenario.embed(screenshot, "image/png"); //Embed image in reports
    }

    @Attachment(value = "{namePrint}", type="image/jpg")
    public static byte[] capture(String namePrint) throws InterruptedException {
        return screenShot();
    }

}
