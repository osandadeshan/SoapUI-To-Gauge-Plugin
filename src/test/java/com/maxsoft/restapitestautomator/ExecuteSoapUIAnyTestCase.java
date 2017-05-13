package com.maxsoft.restapitestautomator;

import com.eviware.soapui.tools.SoapUITestCaseRunner;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import static com.maxsoft.restapitestautomator.TestStepsExecutionResults.getTestStepsExecutionResults;

/**
 * Created by Osanda on 3/15/2017.
 */

public class ExecuteSoapUIAnyTestCase {

    protected static String FILE_PATH = System.getenv("soapui_xml_path");

    int failureCount = 0;
    int totalCount = 0;
    String EXPECTED_RESULT, ACTUAL_RESULT;


    @Step("Execute <TestCase> of <TestSuite>")
    public void executeSpecificTestCase(String TestCase, String TestSuite) throws Exception {
        EXPECTED_RESULT = "Success";
        try {
            SoapUITestCaseRunner soapUITestCaseRunner = new SoapUITestCaseRunner();
            soapUITestCaseRunner.setProjectFile(FILE_PATH);
            soapUITestCaseRunner.setTestSuite(TestSuite);
            soapUITestCaseRunner.setTestCase(TestCase);
            soapUITestCaseRunner.run();
            soapUITestCaseRunner.setPrintReport(true);
            ACTUAL_RESULT = "Success";
            System.out.print(ACTUAL_RESULT);
            getTestStepsExecutionResults(TestCase, TestSuite);
            Assert.assertTrue("TestCase execution result is 'FAIL'", EXPECTED_RESULT.equals(ACTUAL_RESULT));

        } catch (Exception e) {
            ACTUAL_RESULT = "Fail";
            getTestStepsExecutionResults(TestCase, TestSuite);
            Assert.assertTrue("TestCase execution result is 'FAIL'", EXPECTED_RESULT.equals(ACTUAL_RESULT));
            System.err.println("Checking the TestSuite '" + TestSuite + "' and TestCase '" + TestCase + " is failed!");
            failureCount++;
            e.printStackTrace();
        } finally {
            totalCount++;
        }
    }

}
