import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
//options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }
    //Вариант без тестовых меток
//@Test
//void autoTest() {
//List<WebElement> inputs = driver.findElements(By.className("input__control"));

//inputs.get(0).sendKeys("Иванов Иван");
//inputs.get(1).sendKeys("+79532466666");
//driver.findElement(By.className("checkbox__box")).click();
//driver.findElement(By.className("button__content")).click();
//WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));

//assertTrue(result.isDisplayed());
//assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
//}

    //Вариант с тестовыми метками
    @Test
    void autoTest() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532466666");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));

        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void autoTestSadPathNoPhone() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));

        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void autoTestSadPathNoName() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532466666");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));

        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void autoTestSadPathNoCheckBox() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532466666");
        //form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));

        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", result.getText().trim());
    }

    @Test
    void autoTestInvalidDataInName() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("DaniilKolbasenko");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79532466666");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));

        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void autoTestInvalidDataInPhone() {

        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89532466666");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button__content")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));

        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

}

