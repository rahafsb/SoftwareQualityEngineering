package hellocucumber;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class StepDefinitions {
    private String TEACHER_USERNAME = "rahafadmin";
    private String TEACHER_PASSWORD = "RahafAdmin12$";
    private static List<MoodleActuator> allMoodles;
    private static MoodleActuator moodle;
    private String webDriver = "webdriver.chrome.driver";
    private String path = "C:\\Users\\My\\Downloads\\chromedriver_win32\\chromedriver.exe";
    public void moodleInit() {
        System.out.println("--------------- INITIALIZING MOODLE TEST - OPENING WEBPAGE ---------------");
        if(allMoodles == null){
            allMoodles = new ArrayList<>();
        }
        moodle = new MoodleActuator();
        allMoodles.add(moodle);
        moodle.initSession(webDriver, path);
    }
/////////////////////////// LOG IN //////////////////////////////

    @Given("^User is on Home Page$")
    public void init(){
        moodleInit();
    }

    @When("^User Navigate to LogIn Page$")
    public void navigateToLogin(){
        moodle.goToLogin();

    }

    @When("^User enters UserName \"([^\"]*)\" and Password \"([^\"]*)\"$")
    public void enterLoginInfo(String username, String password){
        moodle.enterLoginInfo(username, password);
    }

    @When("^Welcome back, \"([^\"]*)\"! message displays$")
    public void teacherWelcomeMessage(String welcomeWord){
        moodle.generalWelcomeMessage(welcomeWord);
    }

    @Then("^Message displayed Login successful$")
    public void messageDisplayedLoginSuccessfully(){
        System.out.println("Login Successfully");
    }
////////////////////// LOG OUT //////////////////////////////


    @When("^each User presses logout$")
    public void usersLogout(){
        for(MoodleActuator m : allMoodles)
            m.logout();
    }

    @When("^Login option is displayed for all$")
    public void checkIfAllLoggedOut(){
        for(MoodleActuator m : allMoodles)
            m.checkNotLoggedIn();
        terminate();
    }

    @Then("^Message displayed logout successful$")
    public void messageDisplayedLogoutSuccessfully(){
        System.out.println("Logged out Successfully");
    }

    private void terminate(){
        System.out.println("in terminate");
        for(MoodleActuator m : allMoodles)
            m.terminateDriver();
    }

    //TODO* The teacher provides/opens a choice Activity in the Echot Course $$
    @When("^There is a choice activity$")
    public void makeChoice(){
        allMoodles.get(0).addChoice();

    }
    //TODO* The student picks a choice and submits it $$
    @When("^The student chooses a choice$")
    public void pickAchoice(){
        allMoodles.get(1).pick_choice();
    }

    //TODO* after the choice activity has been done(made and voted) a message shown that the scenario was successful $$
    @Then("^Message displayed choice successful$")
    public void successfulChoice(){
        System.out.println("choice picked successfully");
    }


    //TODO* The teacher updates the option of updating choice to No $$
    @When("^The teacher prevents the updating choice option$")
    public void stopUpdateChoice(){
        allMoodles.get(0).preventUpdating();
    }

    //TODO* The student revotes/resubmits his choice in the choice activity we used assert to make sure that we got an error message when the student submits his choice$$
    @When("^The student alters his choice$")
    public void pickAnotherChoice(){
        allMoodles.get(1).clickChoice();
    }

    //TODO* the choice of the students has not been updated message shown $$
    @Then("^Message choice hasn't been updated$")
    public void showMessageUpdate(){
        System.out.println("choice picked successfully");
    }



}
