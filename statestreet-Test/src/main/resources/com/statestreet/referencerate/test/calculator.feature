Feature: To calculate the median/rate fx price

  Scenario: calculate mid fx price
    Given I have an offer
    And I have an bid
    When I divide them by 2
    Then result is the mid price



  Scenario: To Calculate rate
    Given I have a list of market prices
    And I have two mid prices
   When I add and divide the total by 2
    And I assert that price is not stale
    Then I get the reference rate


  Scenario: To Calculate with stale rate
    Given I have a list of market prices
    And I have two mid prices
    When I add and divide the total by 2
    And the rate are stale
    Then I discount from the calculation
    And  I assert that reference rate is stale