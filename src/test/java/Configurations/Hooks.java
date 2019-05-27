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
            System.out.println("override 1");

//            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//            ChromeOptions options = new ChromeOptions();
//            //options.addArguments("--headless", "window-size=1366,768", "--no-sandbox");
//            options.addArguments("window-size=1366,768");
//            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            System.out.println("override 2");
            try {
                driver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), capabilities));
                System.out.println("override 3");
            } catch (Exception e) {
                System.out.println("erro driver set: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("override 4");
            return new ChromeDriver(); // can be replaced with other browser drivers
            //return new InternetExplorerDriver();
            //return new ChromeDriver(); // can be replaced with other browser drivers
        }
    };

    public WebDriver getDriver() // call this method to get the driver object and launch the browser
    {
        return driver.get();
    }

    public void removeDriver() // Quits the driver and closes the browser
    {
//	      driver.get().quit();
//	      driver.remove();
//	      driver = null;
    }

    @Before
    public void TestInitialize() {
        System.out.println("passou navegador 1");
        Hooks.getInstance().getDriver();
        System.out.println("passou navegador 2");
        //driver = new ChromeDriver();
        //ObjFact.start();
//        try {
//            if (Navegador == "C" && Headless == true) {
//                if (Headless) {
//                    ChromeOptions option = new ChromeOptions();
//                    option.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
//                    option.addArguments("disable-popup-blocking");
//                    //driver = new ChromeDriver(option);
//                }
//            }else if (Navegador == "C" && Headless == false) {
//                System.out.println("passou navegador");
//                Hooks.getInstance().getDriver();
//
//
//
//                //driver = new ChromeDriver();
//
//				/*
//				ChromeDriverService cdservice = new ChromeDriverService.Builder().build();
//				cdservice.start();
//				ChromeOptions options = new ChromeOptions();
//				options.setExperimentalOption("detach", true);
//				driver = new ChromeDriver(cdservice,options);
//				*/
//
//            }else if (Navegador == "F" && Headless == false) {
//                if (CI) {
//                    DesiredCapabilities dc = DesiredCapabilities.firefox();
//                    dc.setCapability("screenResolution", "1366x768");
//                    //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
//                }else {
//                    System.setProperty("webdriver.gecko.driver", "C:\\uteis\\ToolsQA\\WebDrivers\\geckodriver.exe");
//                    FirefoxOptions options = new FirefoxOptions();
//                    options.setBinary("C:\\Program Files\\Firefox Developer Edition\\firefox.exe");
//                    //driver = new FirefoxDriver(options);
//                }
//            }else if (Navegador == "F" && Headless == true) {
//                FirefoxBinary firefoxBinary = new FirefoxBinary();
//                firefoxBinary.addCommandLineOptions("--headless");
//                System.setProperty("webdriver.gecko.driver", "C:\\uteis\\ToolsQA\\WebDrivers\\geckodriver.exe");
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                firefoxOptions.setBinary(firefoxBinary);
//                //driver = new FirefoxDriver(firefoxOptions);
//            }else if (Navegador == "IE") {
//                Hooks.getInstance().getDriver();
//                //InternetExplorerDriver driver = new InternetExplorerDriver();
//            }
//            new Dimension(1366, 768);
//        } catch (Exception e) {
//            System.out.println("--------ERRO Navegador-----------" + e.getMessage());
//        }

    }



    @After
    public void TearDownTest(Scenario scenario) throws InterruptedException {
        if (scenario.isFailed()) {
            capture("Falha");
        }
        //cdservice.stop();
        Hooks.getInstance().removeDriver();
        //driver.quit();
    }

    public static byte[] screenShot() throws InterruptedException {
        Thread.sleep(300);
        byte[] screenshot;
        screenshot = ((TakesScreenshot) Hooks.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
        return screenshot;
        //scenario.embed(screenshot, "image/png"); //Embed image in reports
    }

    public static byte[] screenShot_Navigator() {
        byte[] out = null;
        try {
            BufferedImage screencapture = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ImageIO.write(screencapture, "jpg", bo);
            out = bo.toByteArray();
            bo.close();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Attachment(value = "{namePrint}", type="image/jpg")
    public static byte[] capture(String namePrint) throws InterruptedException {
        return screenShot();
    }

}
