@MailChimpTests
Feature: Test MailChimp Website Signup Process
	In order to verify the signp MailChimp Signup page works fine
	We want to input *email*, *userName* and *Password* with different set of data 


@VerifyThatSignupPageWorksWithDifferentData
Scenario Outline: To Verify that signup processes with multiple input data
	Given Open the Chrome Browser 
	And Start Application
	When I enter email as <email>
	And I enter userName as <userName>
	And I enter password as <password>
	Then Verify that signup process is <response>
	Examples: lm
		|	email	|	userName	|	password	|	response	|
		|	"test@mail.com"	|	"test@mail.com"	|	"Pass@123#"	|	"SUCCESS"	|
		|	"test@mail.com"	|	"testttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt@mail.com"	|	"Pass@123#"	|	"MAX_CHARACTERS_EXCEED"	|
		|	"test1@mail.com"	|	"test1@mail.com"	|	"Pass@123#"	|	"USER_ALREADY_EXIST"	|
		|	""	|	"test@mail.com"	|	"Pass@123#"	|	"MISSING_EMAIL"	|
