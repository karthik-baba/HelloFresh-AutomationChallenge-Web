WEB AUTOMATION CHALLENGE
========================

**Pre-condition:** Maven and Java 1.8 required

To Executes Scripts
------------------------
mvn clean compile test-compile test -Denv="test"

**-Denv** - command line argument to select the environement

To Change Browsers, Operating Systems and Application Environment URLs
-------------------

Modify the **Configuration.properties** file in **HelloFresh-WebAutomationTest/src/test/resources** folder

To Change the Run Mode
-----------------------

Modify **Configuration.properties** file

* Parallel => Specify parallelthreads should be greater than 1
* Sequential => Specify parallelthreads should be equal to 1

To View Reports
-------------

HTML reports for each test script with steps linked to screenshots can be found in **HelloFresh-WebAutomationTest/custom-reports** folder
