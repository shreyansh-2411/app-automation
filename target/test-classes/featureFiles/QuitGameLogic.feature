Feature: Quit Logic Feature

  Background: Launch Gamesoye application
    Given user launch gamesoye app

  @QL_TS_01
  Scenario: Verify if user quits the game then user should land on Home page
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round
    And user clicks on Quit game button and accepts the consent modal
    Then user should land on Home page

  @QL_TS_02
  Scenario: Verify if user joins a game after quitting it than a new contest of that game should be started
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round
    And user clicks on Quit game button and accepts the consent modal
    Then user should land on Home page
    And user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round

  @QL_TS_03
  Scenario: Verify if one user quits the game than game should continue as normal for other user
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 1234500007  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    And user is able to match with user before 15 seconds using phone number1 and phone number2
    Then user should land on game screen of the first round
    And user with phone number2 quits the game
    And Ui user submit the bids for the respective rounds of "roundGroup2"
      | User Info | Batting | Bowling | Fielding
      | User 1    | 100     | 100     | 100
    Then user should land on game win screen

  @QL_TS_04
  Scenario: Verify if user quits the game from a tie breaker round than user should be able to join new contest of that game
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    And user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    And user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    And user is wait on current screen for 10 seconds
    And user clicks on Quit game button and accepts the consent modal
    Then user should land on Home page
    And user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round

  @QL_TS_05
  Scenario: Verify if user quits the game then user's balance should get deducted
    And log in using phone number as "1234500007" and otp as "999999"
    And user gets the wallet balance from home page
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round
    And user clicks on Quit game button and accepts the consent modal
    And user should land on Home page
    Then user should get the bolt balance with deducted fee of 100

  @QL_TS_06
  Scenario: Verify if user quits the game then user's contest count should be increased in the profile page
    And log in using phone number as "1234500007" and otp as "999999"
    And user gets the contests played count from the user profile page
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user should land on game screen of the first round
    And Ui user submit the bids for the respective rounds of "fourRoundMatch"
      | User Info | Batting | Bowling | Fielding | Captaincy
      | User 1    | 100     | 100     | 100      | 100
    And user clicks on Quit game button and accepts the consent modal
    And user should land on Home page
    And user is wait on current screen for 30 seconds
    Then contests played count should be increased in the profile page

  @QL_TS_07
  Scenario: Verify user should be allowed to join existing contest of the game after putting the game in background if the contest has not ended yet
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    And user should land on game screen of the first round
    And user runs the app in background for 30 seconds
    And user relaunch the app
    And user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is wait on current screen for 10 seconds
    Then user should land on the game screen of existing contest