package Configurations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

    public static WebDriver driver = Hooks.getInstance().getDriver();
    static WebDriverWait wait = new WebDriverWait(driver, 45);

    public void waitElementToBeClickable (String locator, String element) {
        if (locator.toUpperCase().equals("NAME")) {
            wait.until(ExpectedConditions.elementToBeClickable(By.name(element)));
        } else if (locator.toUpperCase().equals("ID")) {
            wait.until(ExpectedConditions.elementToBeClickable(By.id(element)));
        } else if (locator.toUpperCase().equals("XPATH")) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        } else if (locator.toUpperCase().equals("CLASS")) {
            wait.until(ExpectedConditions.elementToBeClickable(By.className(element)));
        }
    }

    public void waitVisibilityOfElementLocated (String locator, String element) {
        try {
            if (locator.toUpperCase().equals("NAME")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
            } else if (locator.toUpperCase().equals("ID")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));
            } else if (locator.toUpperCase().equals("XPATH")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
            }else if (locator.toUpperCase().equals("CLASS")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.className(element)));
            }
        }
        catch (Exception e) {
            System.out.println("------------------- " + e.getMessage());
        }
    }

    public void waitInVisibilityOfElementLocated (String locator, String element) {
        if (locator.toUpperCase().equals("NAME")) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(element)));
        } else if (locator.toUpperCase().equals("ID")) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(element)));
        } else if (locator.toUpperCase().equals("XPATH")) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));
        }else if (locator.toUpperCase().equals("CLASS")) {
            wait.until(ExpectedConditions.elementToBeClickable(By.className(element)));
        }
    }

    public void waitPresenceOfElementLocated (String locator, String element) {
        if (locator.toUpperCase().equals("NAME")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
        } else if (locator.toUpperCase().equals("ID")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
        } else if (locator.toUpperCase().equals("XPATH")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
        } else if (locator.toUpperCase().equals("CLASS")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(element)));
        }
    }

    public void select(WebElement cmb, String by, String text) {
        if(by.equals("index"))
            new Select(cmb).selectByIndex(Integer.parseInt(text));
        else if(by.equals("value"))
            new Select(cmb).selectByValue(text);
        else if(by.equals("element"))
            new Select(cmb).selectByVisibleText(text);
    }

    public WebElement returnFirstSelectedOption(WebElement select) {
        WebElement elementCombo = ((Select) select).getFirstSelectedOption();
        return elementCombo;
    }
}
