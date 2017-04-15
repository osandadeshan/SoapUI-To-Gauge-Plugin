package com.maxsoft.osanda.soapui_test_reporting_lib;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionRegistry;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.eviware.soapui.tools.SoapUITestCaseRunner;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * Created by Osanda on 3/15/2017.
 */

public class ExecuteSoapUIAnyTestCase {

    protected static String FILE_PATH = System.getenv("soapui_xml_path");

    int failureCount = 0;
    int totalCount = 0;
    String EXPECTED_RESULT, ACTUAL_RESULT;
    Boolean isVisible = true;



    @Step("Execute <TestCase> of <TestSuite>")
    public void executeSpecificTestCase(String TestCase, String TestSuite) throws Exception {
        EXPECTED_RESULT = "Success";
        try{
            SoapUITestCaseRunner soapUITestCaseRunner = new SoapUITestCaseRunner();
            soapUITestCaseRunner.setProjectFile(FILE_PATH);
            soapUITestCaseRunner.setTestSuite(TestSuite);
            soapUITestCaseRunner.setTestCase(TestCase);
            soapUITestCaseRunner.run();
            soapUITestCaseRunner.setPrintReport(true);
            ACTUAL_RESULT = "Success";
            System.out.print(ACTUAL_RESULT);
            findTestStepsExecutionDetails(TestCase, TestSuite);
            Assert.assertTrue("TestCase execution result is 'FAIL'", EXPECTED_RESULT.equals(ACTUAL_RESULT) );

        } catch (Exception e) {
            ACTUAL_RESULT = "Fail";
            findTestStepsExecutionDetails(TestCase, TestSuite);
            Assert.assertTrue("TestCase execution result is 'FAIL'", EXPECTED_RESULT.equals(ACTUAL_RESULT) );
            System.err.println("Checking the TestSuite '" + TestSuite + "' and TestCase '" + TestCase + " is failed!");
            failureCount++;
            e.printStackTrace();
        }finally{
            totalCount++;
        }
    }



    public void findTestStepsExecutionDetails(String TestCase, String TestSuite) throws Exception {
        //TestAssertionRegistry.getInstance().addAssertion(new JsonPathContentAssertion.Factory());
        WsdlProject project = new WsdlProject( FILE_PATH );
        WsdlTestCase testCase = project.getTestSuiteByName( TestSuite ).getTestCaseByName( TestCase );

        int numberOfTestSteps = testCase.getTestStepCount();

        // Print Number of TestSteps
        if (isVisible == readingFromPropertyFile("visibility_of_number_of_test_steps")) {
            System.out.println("Number of TestSteps = " + numberOfTestSteps + "\n");
            Gauge.writeMessage("Number of TestSteps = " + numberOfTestSteps + "\n");
        }

        for (int i=0; i<numberOfTestSteps; i++) {
            WsdlTestStep testStep = testCase.getTestStepAt(i);

            WsdlTestCaseRunner runner = new WsdlTestCaseRunner(testCase, new StringToObjectMap());
            runner.runTestStep(testStep);

            List<TestStepResult> resultList = runner.getResults();
            for (TestStepResult result : resultList) {
                System.out.println("=================================================================================================================================================");
                Gauge.writeMessage("=================================================================================================================================================");

                // Print TestStep Name
                if (isVisible == readingFromPropertyFile("visibility_of_test_step_name")) {
                    System.out.println((i + 1) + ") Executing TestStep [" + testStep.getName() + "]");
                    Gauge.writeMessage((i + 1) + ") Executing TestStep [" + testStep.getName() + "]");
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print TestStep Request
                if (isVisible == readingFromPropertyFile("visibility_of_request")) {
                    String request = ((MessageExchange) result).getRequestContent();
                    System.out.println("Request is: " + request);
                    Gauge.writeMessage("Request is: " + request);
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print TestStep Response
                if (isVisible == readingFromPropertyFile("visibility_of_response")) {
                    String response = ((MessageExchange) result).getResponseContent();
                    System.out.println("Response is: \n" + response);
                    Gauge.writeMessage("Response is: \n" + response);
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print Assertions and Assertion Results
                if (isVisible == readingFromPropertyFile("visibility_of_assertions")) {
                    List<TestAssertion> assertion;
                    if (testStep.getClass().getName() == "com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep") {
                        assertion = ((RestTestRequestStep) testStep).getAssertionList();
                    } else {
                        assertion = ((JdbcRequestTestStep) testStep).getAssertionList();
                    }
                    for (TestAssertion testAssert : assertion) {
                        Assertable.AssertionStatus assertionStatus = testAssert.getStatus();
                        System.out.println("Assertion [" + testAssert.getName() + "] has status '" + assertionStatus.toString() + "'");
                        Gauge.writeMessage("Assertion [" + testAssert.getName() + "] has status '" + assertionStatus.toString() + "'");
                    }
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print TestStep Execution Result
                if (isVisible == readingFromPropertyFile("visibility_of_test_step_execution_result")) {
                    System.out.println("Test Step Execution Result = [" + result.getStatus() + "]");
                    Gauge.writeMessage("Test Step Execution Result = [" + result.getStatus() + "]");
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print TestStep Execution Time
                if (isVisible == readingFromPropertyFile("visibility_of_test_step_execution_time")) {
                    System.out.println("Test Step Execution Time = " + result.getTimeTaken() + " ms");
                    Gauge.writeMessage("Test Step Execution Time = " + result.getTimeTaken() + " ms");
                    System.out.println("_________________________________________________________________________________________________________");
                    Gauge.writeMessage("_________________________________________________________________________________________________________");
                }

                // Print TestStep EndPoint
                if (isVisible == readingFromPropertyFile("visibility_of_end_point")){
                    String endPoint = ((MessageExchange) result).getEndpoint();
                    System.out.println("End Point = " + endPoint);
                    Gauge.writeMessage("End Point = " + endPoint);
                    System.out.println("\n");
                    Gauge.writeMessage("\n");
            }
                System.out.println("=================================================================================================================================================");
                Gauge.writeMessage("=================================================================================================================================================");
                System.out.println("\n");
                Gauge.writeMessage("\n");
                System.out.println("\n");
                Gauge.writeMessage("\n");
                System.out.println("\n");
                Gauge.writeMessage("\n");
            }

        }
    }

    public Boolean readingFromPropertyFile(String property) throws IOException {
        File file = new File("env\\default\\default.properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        Boolean VISIBILITY_OF_PROPERTY = Boolean.valueOf(properties.getProperty(property));
        return VISIBILITY_OF_PROPERTY;
    }


}
