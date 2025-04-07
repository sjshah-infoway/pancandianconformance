package com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CAeReC_Steps {
   private static final String BASE_URI = "https://pancanadiano.ca:10001/fhir/cafex-iua";
   private Response response;
   private static String serviceRequestId;

   @Given("the FHIR server is running")
   public void the_fhir_server_is_running() {
      RestAssured.baseURI = BASE_URI;
   }

   @Given("I am an authorized Request system") 
         public void i_am_an_authorized_Request_system() 
         {  Request system authentication logic (if needed) }

   @Given("I am an authorized Performer system") 
         public void i_am_an_authorized_Performer_system()
         { Performer system authentication logic (if needed) }

   @When("I send a POST request to {string} with valid details") 
         Public void i_send_a_post_request_to_with_valid_details(String endpoint) 
         { String requestBody = "{ \"resourceType\": \"ServiceRequest\", \"status\": \"active\", \"intent\": \"order\", \"subject\": { \"reference\": \"Patient/123\" }, \"code\": { \"coding\": [ { \"system\": \"http://loinc.org\", \"code\": \"12345-6\", \"display\": \"Consultation\" } ] } }";
          response = given() .contentType(ContentType.JSON) 
          .header("Authorization", "Bearer YOUR_ACCESS_TOKEN") 
          .body(requestBody) .when() .post(endpoint); 
          erviceRequestId = response.jsonPath().getString("id");
         }

   @When("I send a GET request to {string}")
   public void i_send_a_get_request_to(String endpoint) {
      response = given().header("Authorization", "Bearer YOUR_ACCESS_TOKEN").when()
            .get(endpoint.replace("{id}", serviceRequestId));
   }

   @When("I send a PUT request to {string} with updated status")
   public void i_send_a_put_request_to_with_updated_status(String endpoint) {
      String requestBody = "{ \"resourceType\": \"ServiceRequest\", \"status\": \"completed\", \"intent\": \"order\", \"subject\": { \"reference\": \"Patient/123\" }, \"code\": { \"coding\": [ { \"system\": \"http://loinc.org\", \"code\": \"12345-6\", \"display\": \"Consultation\" } ] } }";
      response = given().contentType(ContentType.JSON).header("Authorization", "Bearer YOUR_ACCESS_TOKEN")
            .body(requestBody).when().put(endpoint.replace("{id}", serviceRequestId));
   }

   @Then("the response status code should be {int}")
   public void the_response_status_code_should_be(int statusCode) {
      response.then().statusCode(statusCode);
   }

   @Then("the response should contain a valid ServiceRequest")
   public void the_response_should_contain_a_valid_ServiceRequest() {
      response.then().body("resourceType", equalTo("ServiceRequest"));
   }

   @Then("the response should contain a valid updated ServiceRequest")
   public void the_response_should_contain_a_valid_updated_ServiceRequest() {
      response.then().body("resourceType", equalTo("ServiceRequest"));
      response.then().body("status", equalTo("completed"));
   }

   @Then("the response should conform to the ServiceRequest schema")
   public void the_response_should_conform_to_the_ServiceRequest_schema() {
      File schema = new File("src/test/resources/schemas/ServiceRequestSchema.json");
      response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
   }

   @Then("the response should reflect the updated status")
   public void the_response_should_reflect_the_updated_status() {
      response.then().body("status", equalTo("completed"));
   }
}