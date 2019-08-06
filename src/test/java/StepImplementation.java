import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class StepImplementation {

    public WebDriver driver = null;

    @Step("user launch a browser <browserName>")
    public void invokeBrowser(String browserName) {
        DesiredCapabilities dcap = new DesiredCapabilities();
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                dcap.setCapability(ChromeOptions.CAPABILITY, options);
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver");
                driver = new ChromeDriver();
            } else if (browserName.equalsIgnoreCase("grid")) {
                ChromeOptions options = new ChromeOptions();
                dcap.setCapability(ChromeOptions.CAPABILITY, options);
                URL url = new URL("http://zalenium-f7350eafb482f934.elb.eu-west-1.amazonaws.com/wd/hub");
              //  URL url = new URL("http://172.17.0.2:4445/wd/hub");

                driver = new RemoteWebDriver(url, dcap);
            }

            driver.get("https://www.fidelity.co.uk/");
            driver.manage().window().maximize();

        } catch (Exception ex) {
            System.out.println("Exception while invoking browser : " + ex);
        }

    }

    @Step("user search <text>")
    public void searchText(String text) {
        try {
            driver.findElement(By.name("q")).sendKeys(text);
        } catch (Exception ex) {
            System.out.println("Exception occured : " + ex);
        }
    }

    @Step("user click button <Google Search>")
    public void click(String Key) {
        try {
            driver.findElement(By.name("q")).sendKeys(Keys.RETURN);
            // driver.findElement(By.name("btnK")).click();
        } catch (Exception ex) {
            System.out.println("Exception occured on clickin button " + Key + " : " + ex);
        }
    }

    @Step("user click link <HubDocker>")
    public void clickLink(String link) {
        try {
            driver.findElement(By.xpath("//a[@href='https://www.docker.com/']")).click();
        } catch (Exception ex) {
            System.out.println("Exception occured while clickin link " + link + " : " + ex);
        }

    }

    @Step("quits browser")
    public void closeBrowser() {
        driver.quit();

    }

    @Step("user click <Explore>")
    public void implementation1(String text) {
        try {
            driver.findElement(By.xpath("//a[@href='/search?&amp;q=']")).click();
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }
    }

    @Step("Sleep")
    public void implementation2() throws InterruptedException {
        Thread.sleep(10000);
    }


    @Step("user click on <labelName> having <locatorType> <locatorValue>")
    public void clickEvent(String labelName, String locatorType, String locatorValue) throws IOException {
        try {
            if (locatorType.equalsIgnoreCase("class")) {
                driver.findElement(By.className(locatorValue)).click();
            } else if (locatorType.equalsIgnoreCase("id")) {
                driver.findElement(By.id(locatorValue)).click();
            } else if (locatorType.equalsIgnoreCase("xpath")) {
                driver.findElement(By.xpath(locatorValue)).click();
            }
        } catch (Exception ex) {
            takeScreenshot();
            System.out.println("locator not found " + labelName + " : " + ex);
            Gauge.writeMessage("locator not found " + labelName + " : " + ex);

        }
    }


    @Step("user selects dropdown on <labelName> having <locatorType> <locatorValue> <dropdownText>")
    public void dropdown(String labelName, String locatorType, String locatorValue,String dropdownText) throws IOException {
        try {
            Select selectlist=null;

            if (locatorType.equalsIgnoreCase("class")) {
                 selectlist = new Select(driver.findElement(By.className(locatorValue)));
                selectlist.selectByVisibleText(dropdownText);
            } else if (locatorType.equalsIgnoreCase("id")) {
                 selectlist = new Select(driver.findElement(By.id(locatorValue)));
                selectlist.selectByVisibleText(dropdownText);
            } else if (locatorType.equalsIgnoreCase("xpath")) {
                 selectlist = new Select(driver.findElement(By.xpath(locatorValue)));
                selectlist.selectByVisibleText(dropdownText);
            }
        } catch (Exception ex) {
            takeScreenshot();
            System.out.println("locator not found " + labelName + " : " + ex);
            Gauge.writeMessage("locator not found " + labelName + " : " + ex);

        }
    }
    @Step("user enters value <labelName> having <locatorType> <locatorValue> and enter <text>")
    public void setInput(String labelName, String locatorType, String locatorValue,String text) throws IOException {
        try {
            if (locatorType.equalsIgnoreCase("class")) {
                driver.findElement(By.className(locatorValue)).sendKeys(text);
            } else if (locatorType.equalsIgnoreCase("id")) {
                driver.findElement(By.id(locatorValue)).sendKeys(text);
            } else if (locatorType.equalsIgnoreCase("xpath")) {
                driver.findElement(By.xpath(locatorValue)).sendKeys(text);
            }
        } catch (Exception ex) {
            takeScreenshot();
            System.out.println("locator not found " + labelName + " : " + ex);
            Gauge.writeMessage("locator not found " + labelName + " : " + ex);

        }
    }
    @Step("Take Sceenshot")
    public void takeScreenshot() throws IOException {
        try {
            String fileName = "/images" + String.valueOf(System.currentTimeMillis()) + ".png";
            final Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
            final BufferedImage image = screenshot.getImage();        
            String path = System.getProperty("user.dir")+"/reports/html-report/images";
            new File(path).mkdir();
            ImageIO.write(image, "PNG", new File(path+fileName));
            Gauge.writeMessage("<img src=../../../../" + fileName + ">");
            Gauge.writeMessage("<img src=../../../../../" + fileName + ">");
            Gauge.writeMessage("<img src=../../../" + fileName + ">");
            Gauge.writeMessage("<img src=../../" + fileName + ">");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}

