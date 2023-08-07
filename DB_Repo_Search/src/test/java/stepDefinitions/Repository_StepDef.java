package stepDefinitions;

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
import org.openqa.selenium.chrome.ChromeOptions;
import pageObjects.AddEmployeePage;
import pageObjects.Repository_Page;
import utils.ConfigReader;

import java.time.Duration;
import java.util.Properties;

public class Repository_StepDef {
public WebDriver driver;
Repository_Page Rp;
Properties prop = new ConfigReader().intializeProperties();
    @Given("User launch chrome browser")
    public void userLaunchChromeBrowser() {
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", "" + path + "/Drivers/chromedriver_mac_arm64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Then("User should able to view Repository List portal")
    public void userShouldAbleToViewRepositoryListPortal() throws InterruptedException {
        Thread.sleep(2000);
        String actualTitle=driver.getTitle();
        String ExpectedTitle="Git Repository List";
        Assert.assertEquals(actualTitle,ExpectedTitle);
    }
    @When("Enter valid value in the search bar is {string} and click on Search bar")
    public void enterValidValueInTheSearchBarIsAndClickOnSearchBar(String Name) throws InterruptedException {
        Rp=new Repository_Page(driver);
        Rp.enterValue_InSearchbar(Name);
    }
    @Then("User should able to view related records")
    public void userShouldAbleToViewRelatedRecords() {
        Rp.relatedRecords();
    }
    @Then("Page should display {string} records by default")
    public void page_should_display_records_by_default(String value) {
        Rp.defaultRecords(value);
    }
//
    @Then("User should able to view related records ane Click on Next page button[>]")
    public void userShouldAbleToViewRelatedRecordsAneClickOnNextPageButton() throws InterruptedException {
        Rp.verifyAllRecords();
    }

    @And("User should navigate until last page with records")
    public void userShouldNavigateUntilLastPageWithRecords() {
        Rp.checkForLastPage();
    }

    @Then("close browser")
    public void closeBrowser() {
        driver.quit();
    }

    @And("Change the Rows per page value {string}")
    public void changeTheRowsPerPageValue(String NewValue) throws InterruptedException {
        Rp.changeRowsPerPageValue(NewValue);

    }

    @Then("Page should display number of records matching with rows per page value {string}")
    public void pageShouldDisplayNumberOfRecordsMatchingWithRowsPerPageValue(String NewValue) {
        Rp.displaynumberOfRecords();
    }

    @And("Click on each link available in the column link")
    public void clickOnEachLinkAvailableInTheColumnLink() throws InterruptedException {
        Rp.verifyAllLinksAvailable();
        
    }

    @Then("User should be navigate to the corresponding github page after clicking on the link")
    public void userShouldBeNavigateToTheCorrespondingGithubPageAfterClickingOnTheLink() throws InterruptedException {
        Rp.verifyNewPageOpenedOrNot();
    }


    @Then("Mouse hover on the each Details icon available in the column Details and verify Tooltip message {string}")
    public void mouseHoverOnTheEachDetailsIconAvailableInTheColumnDetailsAndVerifyTooltipMessage(String mouseHoverMessage) throws InterruptedException {
        Rp.verifyToolTipMessage(mouseHoverMessage);

    }

    @Then("User should click on each details icon and verify header message in dialogue box and close it")
    public void userShouldClickOnEachDetailsIconAndVerifyHeaderMessageInDialogueBoxAndCloseIt() throws InterruptedException {
        Rp.verifyDialogueBox();
    }

    @When("user open url")
    public void userOpenUrl() {
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
    }

    @And("Page should display No records by default")
    public void pageShouldDisplayNoRecordsByDefault() throws InterruptedException {
        Rp=new Repository_Page(driver);
        Rp.verifyNoRecords();

    }

    String searchName;
    @And("Handle Rate limit error if occurs and continue {string}")
    public void handleRateLimitErrorIfOccursAndContinue(String name) {
            searchName = name;
            try {
                WebElement pop = driver.findElement(By.xpath("//h2[@class='swal2-title']"));
                if (pop.getText().contains("API rate limit exceeded")) {
                    String text[] = pop.getText().split("\\. ");
                    String s[] = text[0].split(" ");
                    String ip[] = s[s.length - 1].split("\\.");
                    int x = Integer.parseInt(ip[ip.length - 1]) + 1;
                    ip[ip.length - 1] = Integer.toString(x);
                    String result = String.join(".", ip);
                    driver.close();
                    Thread.sleep(2000);
                    String path = System.getProperty("user.dir");
                    System.setProperty("webdriver.chrome.driver", "" + path + "/Drivers/chromedriver_mac_arm64/chromedriver");
                    String proxy_ip = result;
                    String proxy_port = "8080";
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--incognito");
                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--proxy-server=" + proxy_ip + ":" + proxy_port);
                    driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                    driver.get(prop.getProperty("url"));
                    driver.manage().window().maximize();
                    WebElement srchbar = driver.findElement(By.xpath("//input[@aria-label='search']"));
                    srchbar.sendKeys(searchName);
                    driver.findElement(By.xpath("//button[@type='submit']")).click();
                    Thread.sleep(2000);
                }
            } catch (Exception NoSuchElementException) {
                NoSuchElementException.getMessage();
            }

    }
}
