@api
Feature: Operations Support
  As an Operations specialist
  In order to verify the proper deployment and health of the system
  I need to be able to obtain status information from the service

  Scenario: Health Check
    Given a standing server
    When I call the health check endpoint
    Then I get a 200 HTTP status code
    And detailed health information

  Scenario: Environment Check
    Given a standing server
    When I call the environment endpoint
    Then I get a 200 HTTP status code
    And detailed environmental information

  Scenario: Version Check
    Given a standing server
    When I call the info endpoint
    Then I get a 200 HTTP status code
    And detailed version information

  Scenario: Metrics Check
    Given a standing server
    When I call the metrics endpoint
    Then I get a 200 HTTP status code
    And detailed metrics information
