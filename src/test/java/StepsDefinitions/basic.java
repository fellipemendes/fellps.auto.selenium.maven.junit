package StepsDefinitions;

import Configurations.Hooks;
import Configurations.Utils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Epic("AGoogle")
@Feature("Google's Search")
public class basic {

    public static WebDriver driver = Hooks.getInstance().getDriver();
    static WebDriverWait wait = new WebDriverWait(driver, 360);
    static Utils oUtils = new Utils();

    @Step
    @Description("Googleeeeee")
    @Given("^I access Google$")
    public void i_access_Google() {
        try {
            driver.navigate().to("https://www.google.com/");
            Thread.sleep(120000);
            oUtils.waitVisibilityOfElementLocated("name", "q");
            Hooks.capture("google's search page");
        }catch (Exception e) {
            System.out.println("--------ERRO 1-----------" + e.getStackTrace());
        }
    }

    @Step
    @Then("^The main page will show up$")
    public void the_main_page_will_show_up() throws Throwable {
        Thread.sleep(2000);
        Hooks.capture("google's search page");
    }

    @Step
    @Given("^Search Palmeiras$")
    public void search_Palmeiras() throws Throwable {
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys("PALMEIRAS");
        driver.findElement(By.name("q")).click();
        Hooks.capture("Palmeiras search result");
    }
}
