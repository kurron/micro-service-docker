@api
Feature: Echo Service
  As an API user
  In order to store my messages for later retrieval
  I need to be able to upload and retrieve messages

  Scenario: Send A Message
    Given a text message I want to store
    When I call the echo message service
    Then my message should be stored in the system

  Scenario: Retrieve A Message
    Given a previously echoed message
    When I call the fetch message service
    Then my message should be downloaded
