package pageObjects;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeePage extends PageFactory {
    WebDriver driver;

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//ul[@class='oxd-main-menu']//li[2]/a/span[text()='PIM']")
    public WebElement element;

    @FindBy(xpath = "//input[@name='username']")
    public WebElement username;

    @FindBy(xpath="//input[@name='password']")
    public WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement login;

    @FindBy(xpath = "//ul//li/a[text()='Add Employee']")
    public WebElement Addemployee_btn;

    @FindBy(name = "firstName")
    public WebElement FirstName;

    @FindBy(name = "lastName")
    public WebElement LastName;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement Savebtn;

    @FindBy(xpath = "//ul//li/a[text()='Employee List']")
    public WebElement EmployeeListbtn;

    @FindBy(className = "oxd-userdropdown-name")
    public WebElement profile;

    @FindBy(xpath = "//ul[@class='oxd-dropdown-menu']/li[4]/a[text()='Logout']")
    public WebElement logoutbtn;

    public void clickonLogin() {
        login.click();
    }
    public void clickLogout()
    {
        profile.click();
        logoutbtn.click();
    }

    public void setUserName_password(String user, String pwd) {
        username.sendKeys(user);
        password.sendKeys(pwd);
    }

    public void clickOnPIM() throws InterruptedException {
        Actions act=new Actions(driver);
        act.moveToElement(element).build().perform();
        Thread.sleep(2000);
        element.click();
    }

    public void clickOnAddEmployee() {
        Addemployee_btn.click();
    }

    List<String> empNames=new ArrayList<String>();
    public void enterEmployeeDetails() throws IOException, CsvException, InterruptedException {
        String path1 = System.getProperty("user.dir");
        String csvPath = path1 + "/TestData/EmployeeDetails.csv";
        Reader reader = new FileReader(csvPath);
        CSVReader csvreader = new CSVReader(reader);
        csvreader.readNext();
        String[] csvCell;
        while ((csvCell = csvreader.readNext()) != null){
            clickOnAddEmployee();
            String firstName = csvCell[0];
            String lastName  = csvCell[1];
            FirstName.sendKeys(firstName);
            empNames.add(firstName);
            LastName.sendKeys(lastName);
            Savebtn.click();
            Thread.sleep(5000);
        }
        System.out.println("Newly added Employees :"+empNames);

    }

    public void clickOnEmployeeList() {
        EmployeeListbtn.click();
    }
    public void verifyAddedEmployeeNames() {
            List<WebElement> list=driver.findElements(By.xpath("(//div[@class='oxd-table-card'])//div[3]"));
            for(WebElement l1:list)
            {
                String name=l1.getText();
                if(empNames.contains(name)){
                System.out.println("Employee verified in employee list:" + name);
            }
            }
            goToNextPage();
    }

    public void goToNextPage(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            WebElement nextPage = driver.findElement(By.xpath("//i[@class='oxd-icon bi-chevron-right']"));
            js.executeScript("arguments[0].scrollIntoView(true);", nextPage);
            if(nextPage.isDisplayed()){
                nextPage.click();
                Thread.sleep(5000);
                verifyAddedEmployeeNames();
            }
        }catch (Exception ex){
            ex.getMessage();
        }

    }

}
