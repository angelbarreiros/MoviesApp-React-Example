package es.udc.paproject.e2etest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for simple App.
 */
public class AppTest {


    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
    private void login(String username,String password){
        WebElement authLink=driver.findElement(By.id("auth-link"));
        authLink.click();
        WebElement userNameInput=driver.findElement(By.id("userName"));
        WebElement userNamePassword=driver.findElement(By.id("password"));
        WebElement loginButton=driver.findElement(By.id("login"));
        userNameInput.sendKeys(username);
        userNamePassword.sendKeys(password);
        loginButton.click();

    }

    //[TT-0]
    @Test
    void LoginTest(){
        driver.get("http://localhost:3000/");
        login("viewer", "pa2223");
    }
//[TT-1]
    @Test
    void DetailSessionTest(){
        driver.get("http://localhost:3000/");
        login("viewer","pa2223");
        WebElement billboardDropdown=driver.findElement(By.id("billboardDate"));
        billboardDropdown.click();
        Select select= new Select(billboardDropdown);
        select.selectByIndex(1);
        billboardDropdown.click();
        List<WebElement> tds=driver.findElements(By.tagName("td"));
        String movieName = tds.get(0).findElement(By.tagName("a")).getText();
        WebElement session = tds.get(1).findElement(By.tagName("a"));
        String sessionTime = session.getText();
        session.click();
        assertNotNull(driver.findElement(By.tagName("h3")));
        assertNotNull(driver.findElement(By.id("movieDuration")));
        assertNotNull(driver.findElement(By.id("moviePrice")));
        assertNotNull(driver.findElement(By.id("sessionDate")));
        assertNotNull(driver.findElement(By.id("movieTheatherName")));
        assertNotNull(driver.findElement(By.id("movieSeatsAvaliable")));

        String[] sessionDetailsTime= driver.findElement(By.id("sessionDate")).getText().split("-");

        assertNotNull(driver.findElements(By.id("purchaseForm")));
        assertEquals(driver.findElement(By.tagName("h3")).getText(),movieName);
        assertEquals(sessionDetailsTime[1].trim(),sessionTime);
        driver.quit();

    }

    //[TT-2]
    @Test
    void BuyTicketsTest(){
        driver.get("http://localhost:3000/");
        login("viewer","pa2223");

        List<WebElement> tds=driver.findElements(By.tagName("td"));
        WebElement session = tds.get(1).findElement(By.tagName("a"));
        session.click();

        String movieName= driver.findElement(By.tagName("h3")).getText();
        WebElement ticketImput= driver.findElement(By.id("tickets"));
        ticketImput.clear();
        ticketImput.sendKeys("2");
        WebElement cardImput= driver.findElement(By.id("card"));
        cardImput.sendKeys("2834950432098");
        WebElement submit = driver.findElement(By.id("submitbuy"));
        submit.click();//compra realizada con éxito

        WebElement divElement = driver.findElement(By.className("alert-success"));
        WebElement purchaseIdElement = divElement.findElement(By.tagName("p"));
        String PurchaseId = purchaseIdElement.getAttribute("innerText");

        WebElement dropdown = driver.findElement(By.className("dropdown-toggle"));
        dropdown.click();

        WebElement history = driver.findElement(By.id("history"));
        history.click();

        WebElement tableElement = driver.findElement(By.tagName("table"));
        WebElement firstRow = tableElement.findElement(By.tagName("tr"));
        WebElement idElement = firstRow.findElement(By.xpath("//td[2]")); // Localiza el elemento "id" en la primera fila
        String idValue = idElement.getText();
        WebElement movieTitleElement = firstRow.findElement(By.xpath("//td[3]"));
        String movieTitleValue = movieTitleElement.getText();

        assertNotNull(movieName);
        assertNotNull(PurchaseId);

        assertEquals(movieTitleValue,movieName);
        assertEquals(PurchaseId,idValue);

        driver.quit();
    }

    @Test
    void DeliveryTicketsTest() {
        driver.get("http://localhost:3000/");
        login("ticketseller","pa2223");

        WebElement spanElement = driver.findElement(By.id("span"));
        spanElement.click();

        WebElement ticketDeliveryLink = driver.findElement(By.id("ticketDeliveryLink"));
        ticketDeliveryLink.click();

        WebElement purchaseIdField = driver.findElement(By.id("purchase-id"));
        WebElement creditCardField = driver.findElement(By.id("card"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        purchaseIdField.sendKeys("3");
        creditCardField.sendKeys("12345678901234");
        submitButton.click();

        WebElement successMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert-success')]"));
        assertNotNull(successMessage);

        purchaseIdField.sendKeys("3");
        creditCardField.sendKeys("12345678901234");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert-danger')]"));
        assertNotNull(errorMessage);

        driver.quit();
    }

}
