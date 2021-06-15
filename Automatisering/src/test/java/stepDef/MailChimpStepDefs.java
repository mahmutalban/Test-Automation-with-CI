package stepDef;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MailChimpStepDefs {

	//Web Driver is the main element to drive selenium activities on browser
	WebDriver driver;
	//to generate random number till 0-99999 
	int range = 99999;
	String email;
	String userName;
	
	/*
	 * Create users - everything goes as expected
	 */
	@Given("Open the Chrome Browser")
	public void open_the_chrome_browser() {
		System.out.println("openn browser");
		
		//Initialize webdriver property to indicate which driver to create
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		//initialize as chromeBrowser Driver (can be other browsers as well)
		driver = new ChromeDriver();
		
		//Set defult timeout on pages 
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//maximize the window to get more visibility when browser opens
		driver.manage().window().maximize();
	}
	
	@Given("Start Application")
	public void start_application() throws InterruptedException {
		System.out.println("Start application");
		
		//Open a specific URL on the browser
		driver.get("https://login.mailchimp.com/signup/");
		
		//wait for 2000 ms, change it if required
		Thread.sleep(2000);
	}

	@When("I enter email as {string}")
	public void i_enter_email_as(String email) {
		System.out.println("enter email");
		//generate a random number and append it to what is sent via feature file
		// this is to make data unique for reusibility
		if(!email.equalsIgnoreCase("test1@mail.com"))
			this.email = email.replace("@", new Random().nextInt(range)+"@");
		else 
			this.email = email;
		
		//find email element and put above fabricated email
		driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(this.email);
	}

	@When("I enter userName as {string}")
	public void i_enter_user_name_as(String userName) {
		System.out.println("enter userName");
		//generate a random number and append it to what is sent via feature file
		// this is to make data unique for reusibility
		if(userName.equalsIgnoreCase("test1@mail.com"))
			this.userName = userName;
		else
			this.userName = new Random().nextInt(range) + ""+userName;

		//find email element and put above fabricated username
		driver.findElement(By.xpath("//*[@id=\"new_username\"]")).sendKeys(this.userName);
	}

	@When("I enter password as {string}")
	public void i_enter_password_as(String password) throws InterruptedException {
		System.out.println("enter password");
		
		//find email element and put above fabricated password
		driver.findElement(By.xpath("//*[@id=\"new_password\"]")).sendKeys(password);
	}

	@Then("Verify that signup process is {string}")
	public void verify_that_signup_is(String response) throws AWTException{
		System.out.println("Verify signup");
		
		pressTab();
		pressEnter();
		
		if(response.equalsIgnoreCase("SUCCESS")) {
			
			//after successfull signup, get the email on success signup page
		    String emailAfterSignup = driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/p/strong")).getText();
		    
		    //and compare it with what user entered at signup page, and if it matches, test case is passed
		    Assert.assertEquals(emailAfterSignup, this.email);
			
		}
		else if (response.equalsIgnoreCase("MAX_CHARACTERS_EXCEED")) {
			
			//get error message text while error on long userName
		    String userMaxCharacterExceedErrorMessage = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")).getText();
		    
		    //verify that error exist
		    Assert.assertTrue(userMaxCharacterExceedErrorMessage.contains("Enter a value less than 100 characters"));
		}
		else if(response.equalsIgnoreCase("USER_ALREADY_EXIST")) {
			System.out.println("alreadyyyyyy" + email);
			//get error message text when user already exists
		    String userAlreadyExistsErrorMessage = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")).getText();
		    
		    //verify that valid error exists
		    Assert.assertTrue(userAlreadyExistsErrorMessage.contains("username already exists"));

		}
		else if(response.equalsIgnoreCase("MISSING_EMAIL")) {
			//get error message text when email is missing
		    String missingEmailErrorMessage = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span")).getText();
		    System.err.println("ERRR>>" + missingEmailErrorMessage);
		  
		    //verify that valid error message is displayed
		    Assert.assertTrue(missingEmailErrorMessage.contains("Please enter a value"));
		    Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"av-flash-errors\"]/ul/li")).isDisplayed());
		}
	    
	    driver.quit();
	    
	}
	
	private void pressEnter() throws AWTException {
		//using Java AWT, press Enter key while cursor is at current position
		//this will make it press enter
		Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.delay(200);
	}

	private void pressTab() throws AWTException {
		//using Java AWT, press Tab key while cursor is at current position
		//this will make it focus on Signup button
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    robot.delay(500);
	}

}
