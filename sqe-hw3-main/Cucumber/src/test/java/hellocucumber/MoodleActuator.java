package hellocucumber;

import io.cucumber.java.an.E;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
//import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class MoodleActuator {
    private WebDriver driver;
    private WebDriverWait wait;


    public void initSession(String webDriver, String path){

        webDriver = "webdriver.chrome.driver";
        path = "C:\\Users\\My\\Downloads\\Bsce\\g\\a\\echotTochna\\sqe-hw3-main\\Selenium\\chromedriver.exe";
        System.setProperty(webDriver, path);
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, 40);
        driver.get("http://localhost/");
        driver.manage().window().maximize();
        System.out.println("Driver setup finished for - " + driver.getTitle());
    }

    public void goToLogin(){
        driver.findElement(By.linkText("Log in")).click();
    }

    public void enterLoginInfo(String username, String password) {
        try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='username']"))).sendKeys(username);
        try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
        driver.findElement(By.xpath("//*[@name='password' and @type='password']")).sendKeys(password);
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        driver.findElement(By.id("loginbtn")).click();
    }

    public void teacherWelcomeMessage(){
        driver.findElement(By.xpath("//*[contains(text(),'Welcome back, Teacher!')]"));
    }

    public void loginSequence(String username, String password){
        goToLogin();
        enterLoginInfo(username, password);
        teacherWelcomeMessage();
    }

    public void logout(){
        driver.findElement(By.id("user-menu-toggle")).click();
        driver.findElement(By.linkText("Log out")).click();

    }

    public void logoutAll() throws InterruptedException {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
                driver.wait(5);
                logout();
        }
    }

    public void checkNotLoggedIn(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Log in')][1]")));

    }

    public void checkNotLoggedInAll() throws InterruptedException {
        //Store the ID of the original window
        String originalWindow = driver.getWindowHandle();
        System.out.println("num of windows" + driver.getWindowHandles().size());
        //Loop through until we find a new window handle
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            driver.wait(5);

            checkNotLoggedIn();
        }
    }

    public void logoutAndCheck(){
        logout();
        checkNotLoggedIn();
    }

    public void editModeOn(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@type='checkbox' and @name='setmode']"))).click();
    }

    public void myCoursesTab(){

        WebElement myCoursesTab = driver.findElement(By.xpath("//*[contains(text(),'My courses') and @role='menuitem']"));
        myCoursesTab.click();
    }

    public void goToCourse(String courseName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='multiline' and contains(text(),'" + courseName + "')]"))).click();
    }


    private String createRandomString(int stringlength){
        if(stringlength < 1)
            return "";
        Random rnd = new Random();
        StringBuilder newString = new StringBuilder(stringlength);
        newString.append((char)('A' + rnd.nextInt(26)));
        for(int i = 0; i < stringlength - 1; i++){
            char c = (char) ('a' + rnd.nextInt(26));
            newString.append(c);
        }
        return newString.toString();
    }

    private String createRandomSentence(int wordCount){
        if(wordCount < 1)
            return "";

        Random rnd = new Random();
        StringBuilder newString = new StringBuilder(wordCount);

        // rest of letters are lower cased
        for(int i = 0; i < wordCount; i++){
            String word = createRandomString(5);
            newString.append(word);
            newString.append(" ");
        }
        return newString.toString();
    }

    public void terminateDriver(){
        driver.quit();
    }

    public void generalWelcomeMessage(String welcomeWord) {

        driver.findElement(By.xpath("//*[contains(text(),'Welcome back, " + welcomeWord + "!')]"));
    }

    //////////////////////////// MINE //////////////////////////////



    public void addChoice(){
        editModeOn();
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        myCoursesTab();
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        goToCourse("Echot");
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'Add an activity or resource')]"))).get(0).click();
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'Choice') and @class='optionname clamp-2']"))).get(0).click();
        String choiseName = createRandomString(8);
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='id_name']"))).sendKeys(choiseName);
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        String choiseDescription = createRandomSentence(8);
        driver.findElement(By.xpath("//*[@id='id_introeditoreditable']")).sendKeys(choiseDescription);
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        WebElement customSelectButton = driver.findElement(By.className("custom-select"));
        customSelectButton.click();
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        WebElement optionElement = driver.findElement(By.xpath("//*[text()='Yes']"));
        optionElement.click();
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("option[0]"))).sendKeys("0");
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("option[1]"))).sendKeys("1");
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("option[2]"))).sendKeys("2");
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("option[3]"))).sendKeys("3");
        try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_submitbutton2"))).click();
        System.out.println("Added Choice Activity to the Course");

    }
    public void pick_choice(){
        Random rand = new Random();
        int choice = rand.nextInt(4);
        myCoursesTab();
        goToCourse("Echot");
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        driver.findElements(By.className("activityname")).get(0).click();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        driver.findElements(By.name("answer")).get(choice).click();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@type='submit']"))).click();
        System.out.println("Your choice has been submitted");
    }

    public void preventUpdating(){
        editModeOn();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        driver.findElements(By.className("activityname")).get(0).click();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        editModeOn();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(),'Settings') and @role='menuitem']"))).get(0).click();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        WebElement optionElement = driver.findElement(By.xpath("//*[text()='No']"));
        optionElement.click();
        try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_submitbutton2"))).click();

    }
    public void clickChoice(){
        Random rand = new Random();
        int choice = rand.nextInt(4);
        try{
            driver.findElements(By.name("answer")).get(choice).click();
        }
        catch (Exception e){
            System.out.println(e);
        }
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@type='submit']"))).click();

        }
        catch (Exception e){
            System.out.println(e);
        }
        Assertions.assertNotNull( driver.findElement(By.className("errorcode")));
    }



}




    // to clear users input from text box: driver.findElement(By.name("q")).clear();
    // to navigate backward in browser history: driver.navigate().back();
    // to navigate forward in browser history: driver.navigate().forward();
    // to refresh a web page: driver.navigate().refresh();
    // to close a browser: driver.close();
    // to close a browser and all other windows associated with the driver: driver.quit();
    // to move between windows: driver.switchTo().window("windowName");
    // to move between frames: driver.switchTo().frame("frameName");
    // to drag and drop elements:
    //     WebElement element = driver.findElement(By.name("source"));
    //     WebElement target = driver.findElement(By.name("target"));
    //     (new Actions(driver)).dragAndDrop(element, target).perform();
