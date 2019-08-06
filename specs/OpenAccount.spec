FILAccountOpeningPublicSite
=====================
Created by deepakvig on 2019-07-18

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
OpenAccountFIL
----------------
*user launch a browser "grid"
*user click on "Continue" having "xpath" "//button[@class='button primary button--accept']"
*user click on "OpenAccount" having "xpath" "//a[@href='/open-account/?intcmp=enhancedlist_multiple_openaccount_may_2019']"
*Take Sceenshot
*user click on "OpenanInvestment" having "xpath" "//a[text()='Open an Investment Account']"

*user click on "OpenanInvestmentButton" having "xpath" "//a[text()='Open an Investment Account']"
*user click on "IamReady" having "xpath" "//a[contains(text(),'m ready')]"
*user click on "BeneficiaryYes" having "xpath" "//label[@for='beneficiary-details-yes']"
*user enters value "ChooseName" having "xpath" "//input[@id='oa-account-designation']" and enter "test"

*user selects dropdown on "Title" having "xpath" "//*[contains(@id,'oa-personal-details-title-view')]" "Mr"
*user enters value "FirstName" having "xpath" ".//*[@name='firstName']" and enter "John"
*user enters value "LastName" having "xpath" ".//*[@name='lastName']" and enter "Mary"

*user enters value "Day" having "xpath" "//*[@name='day']" and enter "03"
*user enters value "Month" having "xpath" "//*[@name='month']" and enter "11"
*user enters value "year" having "xpath" "//*[@name='year']" and enter "1987"
*user click on "UKBornYes" having "xpath" ".//*[@id='oa-pd-born-in-uk-yes']"
*user click on "UKNationalYes" having "xpath" ".//*[@id='oa-pd-british-national-yes']"

*user click on "UKTaxResidentYes" having "xpath" ".//*[@id='oa-pd-tax-resident-yes']"

*user enters value "Email" having "xpath" "//*[@name='emailId']" and enter "John@gmail.com"
*user enters value "ConfirmEmail" having "xpath" "//*[@name='confirmEmailId']" and enter "John@gmail.com"

*user enters value "MobileNo" having "xpath" "//*[@name='mobileNo']" and enter "4488888888888"
*user enters value "username" having "xpath" "//input[@id='ei-registration-username']" and enter "JohnMary10"
*user click on "Next" having "id" "button-oa-about-you-next"
*Take Sceenshot
*quits browser