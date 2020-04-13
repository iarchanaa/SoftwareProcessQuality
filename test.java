
import org.openqa.selenium.*;
import java.lang.String;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class test {
    static WebDriver driver;
    static WebDriverWait wait;
    static int maxWaitSeconds = 30;

    private static String url = "https://www.amazon.co.uk/";


    @Test(priority = 1, enabled = true)
    public void test_searchMask() {

        logInfo("-------------------------------------------------------");
        logInfo("@Test Running test_searchMask() ---------------");


        logInfo("Enter Covid-19 mask you wish to buy");
        FindElement(driver, By.id("twotabsearchtextbox"), 10).sendKeys("Mask N99");

        logInfo("Click search");
        FindElement(driver, By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input"), 10).click();

        logInfo("Click a mask of your choice");
        FindElement(driver, By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div/span[4]/div[1]/div[1]/div/span/div/div/div[2]/h2/a/span"), 10).click();

    }


    @Test(priority = 2, dependsOnMethods = {"test_searchMask"}, enabled = true)
    public void test_addCartCheckOut() {

        logInfo("-------------------------------------------------------");
        logInfo("@Test Running test_searchMask()------------------");

        logInfo("Current Handle : " + driver.getWindowHandle());
        logInfo("Current Title  : " + driver.getTitle());

        Set<String> handles = driver.getWindowHandles();
        for (String h : handles) {

            driver.switchTo().window(h);

            logInfo("Handle : " + driver.getWindowHandle());
            logInfo("Title  : " + driver.getTitle());

            logInfo("Add to basket ");

            FindElement(driver, By.xpath("//*[@id=\"add-to-cart-button\"]"), 30).click();


         /*   try {
                Thread.sleep(1000);
            } catch (Exception e) {
                logInfo("Thread.sleep Exception : " + e.getMessage());
            }*/

        }
    }

    @Test(priority = 3, dependsOnMethods = {"test_searchMask", "test_addCartCheckOut"}, enabled = true)
    public void deleteFromCart() {

        logInfo("Edit Basket");
        FindElement(driver, By.xpath("//*[@id=\"hlb-view-cart-announce\"]"), 10).click();

        logInfo("Empty basket by delete");
        FindElement(driver, By.xpath("/html/body/div[1]/div[4]/div/div[5]/div/div[2]/div[3]/form/div[2]/div[3]/div[4]/div/div[1]/div/div/div[2]/div[1]/span[2]/span/input"), 10).click();

    }



    @BeforeTest
    public void env_Setup() {

        logInfo("@BeforeTest Running env_Setup() ........");

        logInfo("browserSetup()  ********");
        browserSetup();

    }

    @AfterTest
    public void shutdown() {

        logInfo("@AfterTest Running shutdown() ........");


        logInfo("DONE!");
        browserClose();
    }


    private static void browserClose() {

        logInfo("Closing chrome");
        driver.quit();
    }

    private static void browserSetup() {

        logInfo("Start chrome browser");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\avkal\\selenium\\s1\\chromedriver.exe");
        driver = new ChromeDriver();

        logInfo("Set the page load timeout for any page load");
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);

        logInfo("Navigate to url : " + url);
        driver.navigate().to(url);

        logInfo("Maximize window");
        driver.manage().window().maximize();

    }

    private static WebElement FindElement(WebDriver driver, By by, int timeoutInSeconds) {

        try {

            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

            logInfo("FindElement *** " + by + " *** Found");
            return driver.findElement(by);

        } catch (Exception e) {
            //e.printStackTrace();
            logInfo("Exception : " + e.getMessage() + " x-x-x-x-x-x");
        }

       // logInfo("FindElement --> " + by + " --> Not found");
        return null;
    }

    private static void logInfo(String msg) {

        System.out.println(LocalDateTime.now() + " : " + msg);

    }

}
