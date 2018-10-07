package tests;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Generator;
import support.Screenshot;

public class BuscarPorProdutosTest {
    private WebDriver mybrowser;
    private String directory_path;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
        directory_path = System.getProperty("user.dir") + "/reports/" + Generator.dateHourNow() + ".png";
        mybrowser = new ChromeDriver();
        mybrowser.get("http://automationpractice.com");
        mybrowser.manage().window().maximize();
    }

    @Test
    public void testSearchForValidProduct() {
        mybrowser.findElement(By.id("search_query_top")).sendKeys("shirt");
        mybrowser.findElement(By.className("button-search")).click();
        WebElement product = mybrowser.findElement(By.className("product-container"));
        Assert.assertNotNull(product);
        Screenshot.take(mybrowser, directory_path);
    }

    @Test
    public void testAccessContactUs(){
        mybrowser.findElement(By.linkText("Contact us")).click();
        WebElement formContactUs = mybrowser.findElement(By.className("contact-form-box"));
        Assert.assertNotNull(formContactUs);
        Screenshot.take(mybrowser, directory_path);
    }

    @Test
    public void testFillFormContactUs(){
        mybrowser.findElement(By.linkText("Contact us")).click();
        WebElement formContactUs = mybrowser.findElement(By.className("contact-form-box"));

        WebElement selectSubject = formContactUs.findElement(By.id("id_contact"));
        new Select(selectSubject).selectByVisibleText("Webmaster");

        WebElement messageField = formContactUs.findElement(By.id("message"));
        messageField.sendKeys("Lorem ipsum tuntz");

        WebElement emailField = formContactUs.findElement(By.id("email"));
        emailField.sendKeys("automacaoautomatinho@teste.com");

        formContactUs.findElement(By.id("submitMessage")).click();

        WebDriverWait wait = new WebDriverWait(mybrowser, 10);
        WebElement messageSuccess = mybrowser.findElement(By.cssSelector("p.alert"));
        wait.until(ExpectedConditions.visibilityOf(messageSuccess));

        Assert.assertEquals("Your message has been successfully sent to our team.", messageSuccess.getText());

        Screenshot.take(mybrowser, directory_path);
    }

    @After
    public void tearDown(){
        mybrowser.quit();
    }
}
