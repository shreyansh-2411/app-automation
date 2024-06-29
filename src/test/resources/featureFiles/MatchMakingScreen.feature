Feature: Match making screen

  Background: Launch Gamesoye application
    Given user launch gamesoye app

  @MMS_TS_01
  Scenario: Verify on contest page if user balance is less than contest joining fee than user is unable to join that contest
    And log in using phone number as "9603722890" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    Then contest is disabled and message should be shown as "Not Enough Bolts to Enter a Match"

  @MMS_TS_02
  Scenario: Verify on contest page if user clicks on contest then user is landed to match making screen
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    Then user is landed on match making screen

  @MMS_TS_03
  Scenario: Verify if the user has backgrounded the application and opened the application after the match making then the user should get landed to that contest
    And log in using phone number as "1234500008" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    Then user is able to match with user before 15 seconds
    When user runs the app in background for 30 seconds
    And user relaunch the app
    Then user is landed on active running contest

  @MMS_TS_04
  Scenario: Verify if the user has backgrounded the application and opened the application after the match making then the user should get landed to that contest
    And log in using phone number as "9989821306" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user toggle the wifi
    And user runs the app in background for 45 seconds
    And user toggle the wifi
    And user relaunch the app
    Then user is landed on active running contest

  @MMS_TS_05
  Scenario: Verify in the match making screen, if user enters the match making screen and backgrounded the application and switch off the network, now user lands to the application and switch on the internet then in this case user should be landed to its active contest.
    And log in using phone number as "9989821306" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user runs the app in background for 45 seconds
    And user toggle the wifi
    And user relaunch the app
    And user toggle the wifi
    Then user is landed on active running contest