Feature: CA:eReC Conformance Testing for Request and Performer Systems 

Background: Given the FHIR server is running 

Scenario: Request system creates a new Service Request 
Given I am an authorized Request system 
When I send a POST request to "/ServiceRequest" with valid details 
Then the response status code should be 201 
And the response should contain a valid ServiceRequest 
And the response should conform to the ServiceRequest schema 

Scenario: Performer system retrieves the Service Request 
Given I am an authorized Performer system 
When I send a GET request to "/ServiceRequest/{id}" 
Then the response status code should be 200 
And the response should contain a valid ServiceRequest 

Scenario: Performer system updates the Service Request status 
Given I am an authorized Performer system 
When I send a PUT request to "/ServiceRequest/{id}" with updated status 
Then the response status code should be 200 
And the response should contain a valid updated ServiceRequest 

Scenario: Request system retrieves updated Service Request 
Given I am an authorized Request system 
When I send a GET request to "/ServiceRequest/{id}" 
Then the response status code should be 200 
And the response should reflect the updated status