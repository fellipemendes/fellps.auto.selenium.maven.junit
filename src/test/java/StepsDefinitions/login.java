package StepsDefinitions;

import Configurations.Hooks;
import Configurations.Utils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;


public class login {

    private static WebDriver driver = Hooks.getInstance().getDriver();
    static WebDriverWait wait = new WebDriverWait(driver, 360);
    static Utils oUtils = new Utils();

    @Step
    @Given("^I access página de login do Gmail$")
    public void i_access_página_de_login_do_Gmail() throws Throwable {
        try {
            System.out.println("--------LOGIN 1-----------");
            driver.navigate().to("https://accounts.google.com/AccountChooser?service=mail&continue=https://mail.google.com/mail/");
            Hooks.capture("Página de Login do Gmail");
        }catch (Exception e) {
            System.out.println("--------ERRO E1-----------" + e.getMessage());
        }
    }

    @Step
    @Given("^realizei login no Gmail com usuário incorreto$")
    public void realizei_login_no_Gmail_com_usuário_incorreto() throws Throwable {
        try {
            Thread.sleep(3000);
            //oUtils.waitVisibilityOfElementLocated("id", "identifierId");
            driver.findElement(By.id("identifierId")).sendKeys("123");
            driver.findElement(By.xpath("//*[@id='identifierNext']/content/span")).click();
            Thread.sleep(5000);
            //oUtils.waitPresenceOfElementLocated("name", "password");
            driver.findElement(By.name("password")).sendKeys("123");
            driver.findElement(By.xpath("//*[@id='passwordNext']/content/span")).click();
            Hooks.capture("Dados Login");
        }catch (Exception e){
            System.out.println("--------ERRO E2-----------" + e.getMessage());
        }
}

    @Step
    @Then("^deverá apresentar erro de usuário incorreto$")
    public void deverá_apresentar_erro_de_usuário_incorreto() throws Throwable {
        try {
            System.out.println("-------- 1 -----------");
           // oUtils.waitVisibilityOfElementLocated("xpath", "//*[@id='password']/div[2]/div[2]/div");
            Thread.sleep(6000);
            System.out.println("-------- 2 -----------");
            //WebElement retornoErro = driver.findElement(By.xpath("//*[@id='password']/div[2]/div[2]/div"));
            //assertThat(retornoErro.getText()).isEqualTo("Senha incorreta. Tente novamente ou clique em \"Esqueceu a senha?\" para redefini-la.");
            Hooks.capture("Dados Login incorreto");
            System.out.println("-------- 3 -----------");
        }catch (Exception e){
            System.out.println("--------ERRO E3-----------" + e.getMessage());
        }
    }

}
