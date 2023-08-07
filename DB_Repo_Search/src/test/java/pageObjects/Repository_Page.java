package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Repository_StepDef;
import utils.ConfigReader;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Repository_Page extends PageFactory {
    public WebDriver driver;
    Properties prop = new ConfigReader().intializeProperties();

    public Repository_Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@aria-label='search']")
    public WebElement SearchBar;
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement Click_On_SearchBar;
    @FindBy(xpath = "//div[@aria-haspopup='listbox']")
    public WebElement DefaultValue;
    @FindBy(xpath = "//button[@aria-label='Go to next page']")
    public WebElement NextButton;
    @FindBy(xpath = "//div[@class='MuiTablePagination-root css-doc006-MuiTablePagination-root']//div//div[2]")
    public WebElement ArrowDropdown;

    @FindBy(xpath = "//h6[text()='No Data Found']")
    public WebElement NoRecords;
    String searchName;
    String handle;
    public void enterValue_InSearchbar(String name) throws InterruptedException {
        searchName = name;
        WebElement srchbar = driver.findElement(By.xpath("//input[@aria-label='search']"));
        srchbar.sendKeys(searchName);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(3000);
    }

    public void relatedRecords() {
        List<WebElement> list = driver.findElements(By.xpath("//tbody/tr"));
        for (WebElement l : list) {
            Assert.assertTrue(l.isDisplayed());
        }
        String text = DefaultValue.getText();
        int defaultValue = Integer.parseInt(text);
        if(NextButton.isEnabled()) {
            Assert.assertEquals(list.size(), defaultValue);
        }else{
            Assert.assertTrue(defaultValue>=list.size());
        }
    }

    public void defaultRecords(String value) {
        Assert.assertEquals(DefaultValue.getText(), value);
    }

    public void verifyAllRecords() throws InterruptedException {
        relatedRecords();
        while (NextButton.isEnabled()) {
            NextButton.click();
            Thread.sleep(2000);
            relatedRecords();
        }

    }

    public void checkForLastPage() {
        Assert.assertEquals(NextButton.isEnabled(), false);
    }

    public void changeRowsPerPageValue(String newValue) throws InterruptedException {
        Thread.sleep(3000);
        ArrowDropdown.click();
        Thread.sleep(2000);
        List<WebElement> list = driver.findElements(By.xpath("//ul/li"));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equals(newValue)) {
                list.get(i).click();
            }
        }
        Thread.sleep(5000);
    }

    public void displaynumberOfRecords() {
        relatedRecords();
    }

    String Name_Owner;

    public void verifyAllLinksAvailable() throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//tbody/tr"));
        for (int i = 1; i < list.size(); i++) {
            String td1 = driver.findElement(By.xpath("(//tbody/tr[" + i + "]/td)[1]")).getText();
            String td2 = driver.findElement(By.xpath("(//tbody/tr[" + i + "]/td)[2]")).getText();
            Name_Owner = td2.concat("/" + td1);
            driver.findElement(By.xpath("((//tbody/tr)/td[4])[" + i + "]/a")).click();
            Thread.sleep(500);
            verifyNewPageOpenedOrNot();
        }
        while (NextButton.isEnabled()) {
            NextButton.click();
            verifyAllLinksAvailable();
        }
    }

    public void verifyNewPageOpenedOrNot() throws InterruptedException {
        String mainwindow = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String childWindow = iterator.next();
            if (!mainwindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Assert.assertTrue(driver.getTitle().contains(Name_Owner));
                Thread.sleep(500);
                driver.close();
            }
        }
        driver.switchTo().window(mainwindow);
        Thread.sleep(500);
    }

    public void verifyToolTipMessage(String mouseHoverMessage) throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//tbody/tr"));
        Actions act = new Actions(driver);
        for (int i = 1; i < list.size(); i++) {
            act.moveToElement(driver.findElement(By.xpath("((//tbody/tr)/td[5])[" + i + "]/span"))).build().perform();
            // verifyToolTipMessage();
            String ExpectedTolltip = driver.findElement(By.xpath("((//tbody/tr)/td[5])[" + i + "]/span")).getAttribute("aria-label");
            Assert.assertEquals(ExpectedTolltip, mouseHoverMessage);
            Thread.sleep(500);
        }
        while (NextButton.isEnabled()) {
            NextButton.click();
            verifyToolTipMessage(mouseHoverMessage);
        }
    }
    int count = 0;
    public void verifyDialogueBox() throws InterruptedException {
        List<WebElement> list = driver.findElements(By.xpath("//div[@id='root']/descendant::tbody/descendant::tr"));
        first:
        for (int i = 1; i <=list.size(); i++) {
            String td1 = driver.findElement(By.xpath("(//tbody/tr[" + i + "]/td)[1]")).getText();
            String td2 = driver.findElement(By.xpath("(//tbody/tr[" + i + "]/td)[2]")).getText();
            Name_Owner = td2.concat("/" + td1);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='root']/descendant::tbody/descendant::tr[" + i + "]/td[5]/span/*[name()='svg']")));
            element.click();
            try {
                WebElement pop=driver.findElement(By.xpath("//h2[@class='swal2-title']"));
                if (pop.isDisplayed()) {
                    if(pop.getText().contains("Git Repository is empty")){
                    Thread.sleep(5000);
                    continue first;
                    }
                    if(pop.getText().contains("API rate limit exceeded")){
                        String text[]= pop.getText().split("\\. ");
                        String s[]=text[0].split(" ");
                        String ip[]=s[s.length-1].split("\\.");
                        int x=Integer.parseInt(ip[ip.length-1])+1;
                        ip[ip.length-1]=Integer.toString(x);
                        String result =String.join(".",ip);
                        driver.close();
                        Thread.sleep(2000);
                        String path = System.getProperty("user.dir");
                        System.setProperty("webdriver.chrome.driver", "" + path + "/Drivers/chromedriver_mac_arm64/chromedriver");
                        String proxy_ip = result;
                        String proxy_port = "8080";
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--incognito");
                        options.addArguments("--remote-allow-origins=*");
                        options.addArguments("--proxy-server=" + proxy_ip + ":"+proxy_port);
                        driver=new ChromeDriver(options);
                        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                        driver.get(prop.getProperty("url"));
                        driver.manage().window().maximize();
                        WebElement srchbar = driver.findElement(By.xpath("//input[@aria-label='search']"));
                        srchbar.sendKeys(searchName);
                        driver.findElement(By.xpath("//button[@type='submit']")).click();
                        if (count>0){
                            for(int j=1;j<=count;j++){
                                driver.findElement(By.xpath("//button[@aria-label='Go to next page']")).click();
                                Thread.sleep(2000);
                            }
                        }
                        continue first;
                    }
                }
            } catch (Exception NoSuchElementException) {
                NoSuchElementException.getMessage();
                try {
                    Thread.sleep(2000);
                    String dialogueHeader = driver.findElement(By.xpath("//h2[@id='customized-dialog-title']")).getText();
                    Thread.sleep(1000);
                    Assert.assertTrue(dialogueHeader.contains(Name_Owner));
                } catch(Exception NoSuchElementException2){
                    NoSuchElementException2.getMessage();
                    element.click();
                    Thread.sleep(1000);
                    String dialogueHeader2 = driver.findElement(By.xpath("//h2[@id='customized-dialog-title']")).getText();
                    Thread.sleep(1000);
                    Assert.assertTrue(dialogueHeader2.contains(Name_Owner));
                }
                Thread.sleep(1000);
                driver.findElement(By.xpath("//button[text()='Ok']")).click();
            }
        }

        if (driver.findElement(By.xpath("//button[@aria-label='Go to next page']")).isEnabled()) {
            driver.findElement(By.xpath("//button[@aria-label='Go to next page']")).click();
            count+=1;
            verifyDialogueBox();
        }else{driver.close();}

    }
    public void verifyNoRecords() throws InterruptedException {
        Assert.assertTrue(NoRecords.isDisplayed());
    }

}






