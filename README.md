# DB_Repo_Search
Repo Search workflows automation

Test execution Framework: Selenium using cucumber framework (Java program language) 

List of all pages included in this test:

1. ConfigReader-Used to read the data from config.properties file
2. Features -> Repository_List.feature (Test steps are written here)
3. StepDefinitions -> Repository_StepDef (Test step Definitions of feature files are created here)
4. PageObjects -> Repository_Page (Code implementation of test steps written here)
5. testRunner -> TestRunner (Using TestRunner class, we can run the feature)

How to test this feature:
Prerequisite:
1. Make sure you are using the Chrome browser version >= 114.0.5735.90
2. Repo Search Portal Setup: 
   a) Please download the code from: https://github.com/kesavan-rangan/repo-search
   b) Extract the downloaded zip folder from step(a)
   c) Navigate to the project directory path in the terminal 
   d) Run the below command to add dependencies and start the application
      npm install && npm start


Run the Automation test suite using any one of the below approaches:

Approach 1:
   a) Please download the test suite from https://github.com/Ramyajami/DB_Repo_Search
   b) Extract the downloaded zip folder from step(a)
   c) Open terminal and navigate to the project path
   d) Run the below command to add dependencies
      npm install
   e) Trigger the test suite using the below command
      npm run test
   f) Test Report is available in target/CucumberReports/CucumberReport.html


Approach 2:
   a) Please download the test suite from https://github.com/Ramyajami/DB_Repo_Search
   b) Extract the downloaded zip folder from step a
   c) Open the project folder in IntelliJ
   d) Navigate to the TestRunner class (in IntelliJ) from Path: src/test/java/testRunner/TestRunner.java
   e) Click on 'Run Test' available at the TestRunner class
   f) Click on 'Run TestRunner'
   g) Test Report is available in target/CucumberReports/CucumberReport.html

