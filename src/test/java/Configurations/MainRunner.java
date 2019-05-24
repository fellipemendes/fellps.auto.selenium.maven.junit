package Configurations;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        dryRun = false,
        features = { "src/test/java/Feature" },
        glue = { "Configurations", "StepsDefinitions" },
        tags = {},
        plugin = {
                "pretty", "junit:target/rel.xml" ,
                "json:target/cucumber.json",
        }
)

public class MainRunner {


}
