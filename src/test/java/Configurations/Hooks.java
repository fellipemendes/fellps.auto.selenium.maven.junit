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
        //Hooks.driver = (ThreadLocal<WebDriver>) driver;
        //Do-nothing..Do not allow to initialize this class from outside
    }
    public static Hooks instance = new Hooks();

    public static Hooks getInstance()
    {
        if (instance == null){
            instance = new Hooks();
        }
        return instance;
    }

    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>() // thread local driver object for webdriver
    {
        @Override
        public RemoteWebDriver initialValue() {
            System.out.println("Driver Propertie");
            //System.setProperty("webdriver.chrome.driver", chromeDriverPath_linux);

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();

            System.out.println("override 1");
            try {
                driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
                System.out.println("override 2");
            } catch (MalformedURLException e) {
                System.out.println("override 2 ERRO " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("override 3");
            //options.addArguments("webdriver.chrome.driver", chromeDriverPath_linux);
            options.addArguments("window-size=1366,768", "--no-sandbox");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            System.out.println("override 4");
            return new ChromeDriver(options); // can be replaced with other browser drivers
        }
    };

    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        System.out.println("--------RETURN 1-----------");
        return driver.get();
    }

    public void removeDriver() // Quits the driver and closes the browser
    {
        try {
            System.out.println("--------Remove driver 1-----------");
            driver.remove();
            System.out.println("--------Remove driver 2-----------");
        }catch (Exception e) {
            System.out.println("--------removeDriver ERRO-----------" + e.getMessage());
        }

    }

    @Before
    public void TestInitialize() {
        System.out.println("--------TEST INITIALIZE 1-----------");
        try {
            Hooks.getInstance().getDriver();
            System.out.println("--------TEST INITIALIZE 2-----------");
        }catch (Exception e) {
            System.out.println("--------TEST INITIALIZE ERRO-----------" + e.getMessage());
        }
    }

    @After
    public void TearDownTest(Scenario scenario) throws InterruptedException {
//        if (scenario.isFailed()) {
//            System.out.println("--------Scenario Failed----------- " + scenario.getStatus());
//        }
        try {
            System.out.println("--------TearDown 1-----------");
            Hooks.getInstance().removeDriver();
            System.out.println("--------TearDown 2-----------");
        }catch (Exception e) {
            System.out.println("--------TearDownTest ERRO-----------" + e.getMessage());
        }
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
