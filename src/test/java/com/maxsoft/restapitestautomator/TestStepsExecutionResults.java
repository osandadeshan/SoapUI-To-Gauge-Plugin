package com.maxsoft.restapitestautomator;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.thoughtworks.gauge.Gauge;
import java.util.List;
import static com.maxsoft.restapitestautomator.ExecuteSoapUIAnyTestCase.FILE_PATH;
import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;


/**
 * Created by Osanda Deshan on 5/11/2017.
 */


public abstract class TestStepsExecutionResults {

    static Boolean isVisible = true;

    public static void getTestStepsExecutionResults(String TestCase, String TestSuite) throws Exception {
        //TestAssertionRegistry.getInstance().addAssertion(new JsonPathContentAssertion.Factory());
        WsdlProject project = new WsdlProject(FILE_PATH);
        WsdlTestCase testCase = project.getTestSuiteByName(TestSuite).getTestCaseByName(TestCase);

        int numberOfTestSteps = testCase.getTestStepCount();

        // Print Number of TestSteps
        if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_number_of_test_steps"))) {
            System.out.println("Number of Test Steps in the Test Case = " + numberOfTestSteps + "\n");
            Gauge.writeMessage("Number of Test Steps in the Test Case = " + numberOfTestSteps + "\n");
        }

        for (int i = 0; i < numberOfTestSteps; i++) {
            WsdlTestStep testStep = testCase.getTestStepAt(i);

            WsdlTestCaseRunner runner = new WsdlTestCaseRunner(testCase, new StringToObjectMap());
            runner.runTestStep(testStep);

            List<TestStepResult> resultList = runner.getResults();
            for (TestStepResult result : resultList) {
                    System.out.println(readingFromPropertyFile("messages", "double_dashed_line"));
                    Gauge.writeMessage(readingFromPropertyFile("messages", "double_dashed_line"));

                // Print TestStep Name
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_test_step_name"))) {
                    System.out.println((i + 1) + ") Executing Test Step [" + testStep.getName() + "]");
                    Gauge.writeMessage((i + 1) + ") Executing Test Step [" + testStep.getName() + "]");
                    System.out.println(readingFromPropertyFile("messages", "straight_line"));
                    Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                }

                // Print TestStep Request
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_request"))) {
                    try {
                        String request = ((MessageExchange) result).getRequestContent();
                        System.out.println("Request is: " + request);
                        Gauge.writeMessage("Request is: " + request);
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    } catch (ClassCastException ex) {
                        System.out.println(readingFromPropertyFile("messages", "class_cast_exception_message_for_request"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "class_cast_exception_message_for_request"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    } catch (Exception ex) {
                        System.out.println(readingFromPropertyFile("messages", "common_exception_message_for_request"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "common_exception_message_for_request"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    }
                }

                // Print TestStep Response
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_response"))) {
                    try {
                        String response = ((MessageExchange) result).getResponseContent();
                        System.out.println("Response is: \n" + response);
                        Gauge.writeMessage("Response is: \n" + response);
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    } catch (ClassCastException ex) {
                        System.out.println(readingFromPropertyFile("messages", "class_cast_exception_message_for_response"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "class_cast_exception_message_for_response"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    } catch (Exception ex) {
                        System.out.println(readingFromPropertyFile("messages", "common_exception_message_for_response"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "common_exception_message_for_response"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    }
                }

                // Print Assertions and Assertion Results
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_assertions"))) {
                    List<TestAssertion> assertion;
                    if (testStep.getClass().getName() == "com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep") {
                        assertion = ((RestTestRequestStep) testStep).getAssertionList();
                    } else if (testStep.getClass().getName() == "com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep") {
                        assertion = ((JdbcRequestTestStep) testStep).getAssertionList();
                    } else if (testStep.getClass().getName() == "com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep") {
                        assertion = null;
                    } else {
                        assertion = null;
                        System.out.println(readingFromPropertyFile("messages", "class_cast_exception_message_for_assertions"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "class_cast_exception_message_for_assertions"));
                    }
                    try {
                        for (TestAssertion testAssert : assertion) {
                            Assertable.AssertionStatus assertionStatus = testAssert.getStatus();
                            System.out.println("Assertion [" + testAssert.getName() + "] has status '" + assertionStatus.toString() + "'");
                            Gauge.writeMessage("Assertion [" + testAssert.getName() + "] has status '" + assertionStatus.toString() + "'");
                        }
                    } catch (ClassCastException ex) {
                        System.out.println(readingFromPropertyFile("messages", "class_cast_exception_message_for_assertions"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "class_cast_exception_message_for_assertions"));
                    } catch (NullPointerException ex) {
                        System.out.println(readingFromPropertyFile("messages", "null_pointer_exception_message_for_assertions"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "null_pointer_exception_message_for_assertions"));
                    } catch (Exception ex) {
                        System.out.println(readingFromPropertyFile("messages", "common_exception_message_for_assertions"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "common_exception_message_for_assertions"));
                    }
                    System.out.println(readingFromPropertyFile("messages", "straight_line"));
                    Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                }

                // Print TestStep Execution Result
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_test_step_execution_result"))) {
                    System.out.println("Test Step Execution Result = [" + result.getStatus() + "]");
                    Gauge.writeMessage("Test Step Execution Result = [" + result.getStatus() + "]");
                    System.out.println(readingFromPropertyFile("messages", "straight_line"));
                    Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                }

                // Print TestStep Execution Time
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_test_step_execution_time"))) {
                    System.out.println("Test Step Execution Time = " + result.getTimeTaken() + " ms");
                    Gauge.writeMessage("Test Step Execution Time = " + result.getTimeTaken() + " ms");
                    System.out.println(readingFromPropertyFile("messages", "straight_line"));
                    Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                }

                // Print TestStep EndPoint
                if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_end_point"))) {
                    try {
                        String endPoint = ((MessageExchange) result).getEndpoint();
                        System.out.println("End Point = " + endPoint);
                        Gauge.writeMessage("End Point = " + endPoint);
                        System.out.println("\n");
                        Gauge.writeMessage("\n");
                    } catch (ClassCastException ex) {
                        System.out.println(readingFromPropertyFile("messages", "class_cast_exception_message_for_endpoint"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "class_cast_exception_message_for_endpoint"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    } catch (Exception ex) {
                        System.out.println(readingFromPropertyFile("messages", "common_exception_message_for_endpoint"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "common_exception_message_for_endpoint"));
                        System.out.println(readingFromPropertyFile("messages", "straight_line"));
                        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
                    }
                }
                System.out.println(readingFromPropertyFile("messages", "double_dashed_line"));
                Gauge.writeMessage(readingFromPropertyFile("messages", "double_dashed_line"));
                System.out.println("\n");
                Gauge.writeMessage("\n");
                System.out.println("\n");
                Gauge.writeMessage("\n");
                System.out.println("\n");
                Gauge.writeMessage("\n");
            }

        }
    }
}
