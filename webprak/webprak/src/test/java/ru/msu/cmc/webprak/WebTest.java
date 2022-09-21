package ru.msu.cmc.webprak;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {
    @Test
    public void homeTest() {
        System.setProperty("webdriver.gecko.driver",
                "C:\\Users\\lakre\\Downloads\\geckodriver-v0.31.0-win64\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8081/");
        driver.findElement(By.id("loginInput")).sendKeys("aboba1");
        driver.findElement(By.id("passwordInput")).sendKeys("password");
        driver.findElement(By.id("pass")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("nameInput")).sendKeys("chapterAboba");
        driver.findElement(By.id("inputDate")).sendKeys("1488");
        driver.findElement(By.id("inputDeleteCreate")).sendKeys("create");
        driver.findElement(By.id("deleteCreate")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("nameInput")).sendKeys("chapterAboba");
        driver.findElement(By.id("inputDate")).sendKeys("1488");
        driver.findElement(By.id("inputDeleteCreate")).sendKeys("delete");
        driver.findElement(By.id("deleteCreate")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("chapter:chapter1")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("inputName")).sendKeys("themeAboba");
        driver.findElement(By.id("inputDate")).sendKeys("1488");
        driver.findElement(By.id("inputCreateDelete")).sendKeys("create");
        driver.findElement(By.id("createDelete")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("inputName")).sendKeys("themeAboba");
        driver.findElement(By.id("inputDate")).sendKeys("1488");
        driver.findElement(By.id("inputCreateDelete")).sendKeys("delete");
        driver.findElement(By.id("createDelete")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("theme:theme1")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("inputContent")).sendKeys("AAAA");
        driver.findElement(By.id("inputDate")).sendKeys("1488");
        driver.findElement(By.id("createMessage")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("delete:aboba1:AAAA")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("ref:aboba1:message1")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("inputName")).sendKeys("fileAboba");
        driver.findElement(By.id("inputContent")).sendKeys("contentAboba");
        driver.findElement(By.id("createFile")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("delete:fileAboba")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.close();
    }
}