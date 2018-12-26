package lab_6;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    private static ChromeDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "/home/ezubriichuk/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--start-fullscreen");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.google.com");
    }

    @org.junit.Test
    public void testGoogle1() throws Exception {
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"));
        String title = "Cloudmade";
        searchField.sendKeys(title);
        searchField.submit();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String Css = "//a[contains(@href, 'https://cloudmade.com/')]";
        PageLinkSearch(title + "1", Css);
    }

    @org.junit.Test
    public void testGoogle2() throws Exception {
        driver.get("https://www.google.com");
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"));
        String title = "Cloudmade";
        searchField.sendKeys(title);
        searchField.submit();
        String Css = "//a[contains(@href, 'https://cogniance.com/case_customer/cloudmade/')]";
        PageLinkSearch(title, Css);
    }

    @org.junit.Test
    public void testGoogle3() throws Exception {
        driver.get("https://www.google.com");
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"));
        String title = "клаудмаде";
        searchField.sendKeys(title);
        searchField.submit();
        String Css = "//a[contains(@href, 'https://cogniance.com/case_customer/cloudmade/')]";
        PageLinkSearch(title, Css);
    }


    public void PageLinkSearch(String tit, String CSS) throws Exception {
        for (int pageNumber = 1; pageNumber <= 19; pageNumber++) {
            if (driver.findElements(By.xpath(CSS)).size() > 0) {
                System.out.println("page_number is " + pageNumber);
                File file = driver.getScreenshotAs(OutputType.FILE);
                get_screenshot("Screen" + tit + pageNumber, file);
                break;
            } else {
                File file = driver.getScreenshotAs(OutputType.FILE);
                get_screenshot("Screen" + tit + pageNumber, file);
                driver.findElement(By.cssSelector("#pnnext > span:nth-child(2)")).click();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        }
    }

    public static void get_screenshot(String name, File scrFile)throws Exception{
        try {
            FileUtils.copyFile(scrFile, new File(name + ".png"));

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }
}
