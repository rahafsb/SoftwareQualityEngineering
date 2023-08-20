Feature: Choice Action

  Scenario Outline: Successful user login
    Given User is on Home Page
    When User Navigate to LogIn Page
    And User enters UserName <UserName> and Password <Password>
    And Welcome back, <WelcomeWord>! message displays
    Then Message displayed Login successful
    Examples:
      | UserName    | Password        | WelcomeWord |
      | "rahafadmin"   | "RahafAdmin12$"  | "Admin"   |
      | "student"   | "Student1999$"  | "Student"   |


  Scenario: Successful student pick a choice
    When There is a choice activity
    And The student chooses a choice
    Then Message displayed choice successful


  Scenario: Successful updating choice
    When The teacher prevents the updating choice option
    And The student alters his choice
    Then Message choice hasn't been updated


  Scenario: Successful users logout
    When each User presses logout
    And Login option is displayed for all
    Then Message displayed logout successful
