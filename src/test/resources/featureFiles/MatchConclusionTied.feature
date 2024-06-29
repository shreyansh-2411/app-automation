Feature:  Match Conclusion Tied

  Background: Launch Gamesoye application
    Given user launch gamesoye app

  @MCT_TS_01
  Scenario: Verify in game, each user will be playing 5 rounds and each round would be named i.e. round-1 batting, round -2 bowling, round- 3 fielding, round -4 captaincy, round-5 fiitness
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 100     |


  @MCT_TS_02
  Scenario: Verify in game, if the user has won the round then in that case round won user will be given 12 pts
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting |
      | User 1    | 100     |
      | User 2    | 50      |
    Then User1 won the 1st round and won user will be given 12 pts

  @MCT_TS_03
  Scenario: Verify in game, if the user has lost the round then in that case round lost user will be given 6 pts
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting |
      | User 1    | 50      |
      | User 2    | 100     |
    Then User1 lost the 1st round and lost user will be given 6 pts

  @MCT_TS_04
  Scenario: Verify in game, if the user has tied the round then in that case both user will be given 9 pts
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting |
      | User 1    | 100     |
      | User 2    | 100     |
    Then User1 tied the 1st round and tied user will be given 9 pts

  @MCT_TS_05
  Scenario: Verify in the 1st tie breaker round, user will be playing 3 rounds and each would be named i.e. round-1 batting, round -2  bowling, round- 3 Fielding and round group is 2
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9950234565  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
        | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
        | User 1    | 100     | 100     | 100      | 100       | 100     |
        | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Given user submit the 1st tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |

  @MCT_TS_06
  Scenario: Verify in the 2nd tie breaker round, user will be playing 3 rounds and each would be named i.e. round-1 batting, round -2  bowling, round- 3 Fielding and round group is 3
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9799968212  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Given user submit the 1st tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds
    Given user submit the 2nd tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds

  @MCT_TS_07
  Scenario: Verify in game user remaining stars for levelup that are displayed on UI are coming from API
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391   | 999999 |
      | User 2    | 9799968212  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 90        | 90      |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Then user remaining stars for levelup that are displayed on UI are coming from API


  @MCT_TS_08
  Scenario: Verify if the point difference is 0 after 2nd tie breaker round then game should get concluded and user gets back there joining fees.
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9799968212  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Given user submit the 1st tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds
    Given user submit the 2nd tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds
    Then game got concluded and user gets back there joining fees

  @MCT_TS_09
  Scenario: Verify in game if the contest is lost by the user then in that case user would be getting only participation stars.
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9799968212  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 90      |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Then user would be getting only participation stars

  @MCT_TS_10
  Scenario: Verify in game if the contest is won by the user then in that case user would be getting participation stars and winning stars.
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 9799968212  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 90      |
    Then user is wait on current screen for 10 seconds
    Then user would be getting participation stars and winning stars

  @MCT_TS_11
  Scenario: Verify in game if the contest is tied by the user then in that case user would be getting only participation stars.
    And log in to device_1 and device_2
      | User Info | Phonenumber | otp    |
      | User 1    | 9784919391  | 999999 |
      | User 2    | 8822334455  | 999999 |
    When user clicks play now on cricoff banner in home page
    Then user clicks play now on contest
    Given user is landed on match making screen
    Then user is able to match with user before 15 seconds using phone number1 and phone number2
    Given user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy | Fitness |
      | User 1    | 100     | 100     | 100      | 100       | 100     |
      | User 2    | 100     | 100     | 100      | 100       | 100     |
    Then user is wait on current screen for 10 seconds
    Given user submit the 1st tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds
    Given user submit the 2nd tiebreaker bids for the respective rounds
      | User Info | Batting | Bowling | Fielding |
      | User 1    | 100     | 100     | 100      |
      | User 2    | 100     | 100     | 100      |
    Then user is wait on current screen for 10 seconds
    Then user would be getting only participation stars

  @MCT_TS_12
  Scenario: Verify once the match is made user is landed to the game screen
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    And user should land on game screen of the first round

  @MCT_TS_13
  Scenario: Verify the stadium name displayed on the game screen is same as the stadium name displayed at the matchmaking screen
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user should see the stadium name at the matchmaking screen
    Then user should see the stadium name at round start screen which should be same as matchmaking screen

  @MCT_TS_14
  Scenario: Verify in game each user will be given 500 cricos to play 5 rounds
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    And user should land on game screen of the first round
    Then user should have 500 cricos to play 5 rounds

  @MCT_TS_15
  Scenario: Verify if the difference in points after round 3 is more then 24, then match should get concluded
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user is able to match with user before 15 seconds
    When user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding
      | User 1    | 100     | 100     | 100
      | User 2    | NULL    | NULL    | 10
    Then user should land on game win screen

  @MCT_TS_16
  Scenario: Verify if the difference in points after round 4 is more then 12, then match should get concluded
    And log in using phone number as "1234500007" and otp as "999999"
    When user clicks play now on cricoff banner in home page
    And user clicks play now on contest
    And user is landed on match making screen
    Then user is able to match with user before 15 seconds
    When user submit the bids for the respective rounds
      | User Info | Batting | Bowling | Fielding | Captaincy
      | User 1    | 100     | 100     | 100      | 100
      | User 2    | NULL    | 20      | 20       | NULL
    Then user should land on game win screen