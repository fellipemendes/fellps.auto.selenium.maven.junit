package Configurations;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Attachment;


public class Hooks{
    public static ChromeDriverService cdservice = null;
    public static String chromeDriverPath = "/usr/bin/chromedriver";
    boolean CI = false;
    String Navegador = "C";
    static boolean Headless = false;
    public static String parentWindow = null;

    public Hooks()
    {
        //Hooks.driver = (ThreadLocal<WebDriver>) driver;
        //Do-nothing..Do not allow to initialize this class from outside
    }
    public static Hooks instance = new Hooks();

    public static Hooks getInstance()
    {
        return instance;
    }

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() // thread local driver object for webdriver
    {
        @Override
        public RemoteWebDriver initialValue() {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            System.out.println("override 1");
            try {
                driver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capabilities));
                System.out.println("override 2");
            } catch (MalformedURLException e) {
                System.out.println("override 2 ERRO " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("override 3");

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
        //-----
    }

    @Before
    public void TestInitialize() {
        System.out.println("--------TEST INITIALIZE 1-----------");
    }

    @After
    public void TearDownTest(Scenario scenario) throws InterruptedException {
        Hooks.getInstance().removeDriver();
    }

    public static byte[] screenShot() throws InterruptedException {
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
