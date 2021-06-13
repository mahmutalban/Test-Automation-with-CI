package stepDef;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
/*
 * This class is used to Run cucumber test cases and binding between feature file and step definitions
 * This class can be run as Junit test to trigger cucumber test cases
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		features= {"classpath:features"},
		glue= {"stepDef"},
		tags = "@MailChimpTests",
		publish = true

		)
public class RunnerTest {

}
