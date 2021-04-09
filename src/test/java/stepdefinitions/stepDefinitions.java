package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.nio.charset.*;


public class stepDefinitions {
    private WebDriver driver;


    @Before
    public void startBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();


    }

    @Given("I have entered {string}")
    public void i_have_entered(String email) throws InterruptedException {

        WebElement inputEmail;
        inputEmail = driver.findElement(By.cssSelector("input[id=email]"));
        inputEmail.sendKeys(email);
        Thread.sleep(2000);
    }

    @Given("entered {string}")
    public void entered(String username) {

        WebElement inputUsername;
        inputUsername = driver.findElement(By.cssSelector("input[id=new_username]"));
        if (username.contains("alreadytaken")) {
            inputUsername.sendKeys("hejsan");
        } else if (username.contains("random")) {
            inputUsername.sendKeys(getAlphaNumericString(55) + getAlphaNumericString(50));
        } else {
            inputUsername.sendKeys(username + getAlphaNumericString(4));
        }
    }

    @And("I have put in {string}")
    public void iHavePutIn(String password) {
        WebElement inputPassword = driver.findElement(By.cssSelector("input[id=new_password]"));
        inputPassword.sendKeys(password);
    }

    @When("I press sign up")
    public void i_press_sign_up() {

        click(driver, By.id("onetrust-accept-btn-handler")); //klicka bort cookies då de var i vägen om man hade mindre skärm

        WebElement checkbox = driver.findElement(By.cssSelector("#marketing_newsletter")); //Vill inte ha nyhetsbrev
        if (!checkbox.isSelected())
            checkbox.click();

        click(driver, By.id("create-account"));


    }

    @Then("the result should be {string} on the screen")
    public void the_result_should_be_on_the_screen(String result) {
        if (result.contains("username already")) {

            WebElement invalidUsername = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"));

            Assert.assertEquals(result, invalidUsername.getText());

        } else if (result.contains("Check your email")) {
            WebElement signUpWorks = driver.findElement(By.cssSelector("#signup-content > div > div > div > h1"));

            Assert.assertEquals(result, signUpWorks.getText());

        } else if (result.contains("value less than")) {
            WebElement tooLongUsername = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"));

            Assert.assertEquals(result, tooLongUsername.getText());


        } else if (result.contains("enter a value")) {
            WebElement missingEmailValue = driver.findElement(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span"));
            Assert.assertEquals(result, missingEmailValue.getText());

        } else {
            System.out.println("hej");
        }


    }

    @After
    public void quitTheBrowser() {
        driver.quit();
    }

    static String getAlphaNumericString(int n) {

        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, StandardCharsets.UTF_8);


        StringBuffer r = new StringBuffer();

        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        return r.toString();
    }

    private static void click(WebDriver driver, By by) {


        new WebDriverWait(driver, 10).until(ExpectedConditions.


                elementToBeClickable(by));


        driver.findElement(by).click();

    }
}
