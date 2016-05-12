Feature: Reference Rate tests

  Scenario: Market has source and povider
    Given I have a price source with value "source2"
    And  I have a price provider with value "bloomberg"
    When I create a market
    Then a market is successfully created


  Scenario: Market has souce and not a provider
    Given I have a price source with value "source2"
    And  I have a price provider with value "null"
    When I create a market
    Then a market is successfully created


  Scenario: Market has null source and provider
    Given I have a price source with value "null"
    And  I have a price provider with value "null"
    When I create a market
    Then the market is not succesfully created

  Scenario: No duplicate market
    Given I have markets in a list
    When I add them to the Set
    Then I assert that there is no duplication

  Scenario: Market Updates correctly
    Given I have markets in a list
    When when an update occurs
    Then I assert that the market was updated and is displaying correctly prices