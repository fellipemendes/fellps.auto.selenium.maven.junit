Feature: basic access Google
  As a [role]
  I want [feature]
  So that [benefit/business reason]
@basic
  Scenario: access Google
    Given I access Google
    Then The main page will show up

  Scenario: access Google searching Palmeiras
    Given I access Google
    And Search Palmeiras
    Then The main page will show up