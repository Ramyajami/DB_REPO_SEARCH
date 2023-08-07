Feature: Repository List
  Scenario: Verify user able to open Repository search portal
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    And Page should display No records by default
    Then close browser

  Scenario Outline: Verify user able see zero records after entering space in the search bar
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    And Page should display No records by default
    Then close browser
    Examples:
    |Name|
    |    |

  Scenario Outline: Verify user able see zero records after entering invalid value in the search bar
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    And Page should display No records by default
    Then close browser
    Examples:
      |Name|
      |tst3d4f5g|

  Scenario Outline: Verify user able to view records after entering the correct value in Search bar
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    Then User should able to view related records
    And Page should display "<value>" records by default
    Then close browser
    Examples:
     |Name       |value|
     |kubernetes |10   |

  Scenario Outline: Verify user able to view number of records in one page after changing the Rows per page value
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    And Page should display "<value>" records by default
    And Change the Rows per page value "<NewValue>"
    Then Page should display number of records matching with rows per page value "<NewValue>"
    Then close browser
    Examples:
      |Name       |value|NewValue|
      |kubernetes |10   |25      |

  Scenario Outline: Verify user able to navigate to next page until last page if output records are more than one page
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    Then User should able to view related records ane Click on Next page button[>]
    And User should navigate until last page with records
    Then close browser
    Examples:
      |Name |
      |T1234|

  Scenario Outline: Verify user able to click on links avilable in the output records
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    And Click on each link available in the column link
    Then User should be navigate to the corresponding github page after clicking on the link
    And close browser
    Examples:
      |Name       |
      |D1234      |

  Scenario Outline:   Verify user able view tooltip message after mouse hover the Details icon available in the output records
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    Then  Mouse hover on the each Details icon available in the column Details and verify Tooltip message "<MouseHoverMessage>"
    And close browser
    Examples:
      |Name       |MouseHoverMessage|
      |D1234      |Get Details      |

  Scenario Outline:Verify user able to click on each Details icon and verify Header message with owner and name column values and close the dialogue box
    Given User launch chrome browser
    When user open url
    Then User should able to view Repository List portal
    When Enter valid value in the search bar is "<Name>" and click on Search bar
    And Handle Rate limit error if occurs and continue "<Name>"
    Then User should click on each details icon and verify header message in dialogue box and close it
    Examples:
      |Name     |
      |D123     |