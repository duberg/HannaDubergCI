Feature: Login
  I need to test the login screen so I can't do silly mistakes

  @login
  Scenario Outline: Sign up to https://login.mailchimp.com/signup/


    Given I have entered "<email>"
    And entered "<username>"
    And I have put in "<password>"
    When I press sign up
    Then the result should be "<result>" on the screen
    Examples:
      | email                | username     | password  | result                                                                             |
      | hannaduberg@mail.com | alreadytaken | Passw0rd! | Another user with this username already exists. Maybe it's your evil twin. Spooky. |
      | hannaduberg@mail.com | working      | Passw0rd! | Check your email                                                                   |
      | hannaduberg@mail.com | random100    | Passw0rd! | Enter a value less than 100 characters long                                        |
      |                      | working      | Passw0rd! | Please enter a value                                                               |

