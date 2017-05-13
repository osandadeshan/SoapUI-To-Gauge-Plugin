package com.maxsoft.restapitestautomator;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.thoughtworks.gauge.Gauge;
import java.util.List;
import static com.maxsoft.restapitestautomator.ExecuteSoapUIAnyTestCase.FILE_PATH;
import static com.maxsoft.restapitestautomator.NumberOfTestSteps.getNumberOfTestSteps;
import static com.maxsoft.restapitestautomator.NumberOfTestSteps.printNumberOfTestSteps;
import static com.maxsoft.restapitestautomator.TestStepAssertions.getTestStepAssertions;
import static com.maxsoft.restapitestautomator.TestStepEndPoint.getTestStepEndPoint;
import static com.maxsoft.restapitestautomator.TestStepExecutionResult.getTestStepExecutionResult;
import static com.maxsoft.restapitestautomator.TestStepExecutionTime.getTestStepExecutionTime;
import static com.maxsoft.restapitestautomator.TestStepName.getTestStepName;
import static com.maxsoft.restapitestautomator.TestStepRequest.getTestStepRequest;
import static com.maxsoft.restapitestautomator.TestStepResponse.getTestStepResponse;
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

            int numberOfTestSteps = getNumberOfTestSteps(TestSuite, TestCase);

                // Print Number of TestSteps
                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_number_of_test_steps"))) {
                        printNumberOfTestSteps(TestSuite, TestCase);
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
                                        getTestStepName(i, testStep);
                                    }

                                // Print TestStep Request
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_request"))) {
                                        getTestStepRequest(result);
                                    }

                                // Print TestStep Response
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_response"))) {
                                        getTestStepResponse(result);
                                    }

                                // Print Assertions and Assertion Results
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_assertions"))) {
                                        getTestStepAssertions(testStep);
                                    }

                                // Print TestStep Execution Result
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_test_step_execution_result"))) {
                                        getTestStepExecutionResult(result);
                                    }

                                // Print TestStep Execution Time
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_test_step_execution_time"))) {
                                        getTestStepExecutionTime(result);
                                    }

                                // Print TestStep EndPoint
                                    if (isVisible == Boolean.valueOf(readingFromPropertyFile("default", "visibility_of_end_point"))) {
                                        getTestStepEndPoint(result);
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
