package com;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", 
    glue = { "stepDefinitions" }, 
    plugin = { "pretty","html:target/cucumber-reports.html" },
    monochrome = true
    )

public class TestRunner {
    // This class remains emptyit is used only as a holder for the above annotations
}