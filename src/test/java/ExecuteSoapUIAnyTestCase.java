import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.*;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.eviware.soapui.tools.SoapUITestCaseRunner;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import org.apache.xmlbeans.XmlException;
import org.junit.Assert;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Created by Osanda on 3/15/2017.
 */

public class ExecuteSoapUIAnyTestCase {

    protected static String FILE_PATH = System.getenv("SOAPUI_XML_PATH");

    int failureCount = 0;
    int totalCount = 0;
    String EXPECTED_RESULT, ACTUAL_RESULT;



    @Step("Execute TestCase of <TestCase> in TestSuite of <TestSuite>")
    public void executeSpecificTestCase(String TestCase, String TestSuite) throws Exception {
        EXPECTED_RESULT = "Success";
            try{
                SoapUITestCaseRunner soapUITestCaseRunner = new SoapUITestCaseRunner();
                soapUITestCaseRunner.setProjectFile(FILE_PATH);
                soapUITestCaseRunner.setTestSuite(TestSuite);
                soapUITestCaseRunner.setTestCase(TestCase);
                soapUITestCaseRunner.run();
                soapUITestCaseRunner.getAssertions();
                List <TestAssertion> assertions = soapUITestCaseRunner.getAssertions();

                for(TestAssertion assertion  : assertions) {
                    System.out.println(assertion.getName());
                    Gauge.writeMessage(assertion.getName());
                }

               // System.out.println(soapUITestCaseRunner.getAssertionResults());
                Map<TestAssertion, WsdlTestStepResult> temp = soapUITestCaseRunner.getAssertionResults();
                if(temp != null) {
                    for (WsdlTestStepResult result : temp.values()) {
                        System.out.println("********");
                        System.out.println(result.getStatus());
                        Gauge.writeMessage(String.valueOf(result.getStatus()));
                        System.out.println("\n");
                        Gauge.writeMessage("\n");
                    }
                }

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
        WsdlProject project = new WsdlProject( FILE_PATH );
        WsdlTestCase testCase = project.getTestSuiteByName( TestSuite ).getTestCaseByName( TestCase );

        int numberOfTestSteps = testCase.getTestStepCount();
        System.out.println("Number of TestSteps = " + numberOfTestSteps + "\n");
        Gauge.writeMessage("Number of TestSteps = " + numberOfTestSteps + "\n");

        for (int i=0; i<numberOfTestSteps; i++) {
            WsdlTestStep testStep = testCase.getTestStepAt(i);

            WsdlTestCaseRunner runner = new WsdlTestCaseRunner(testCase, new StringToObjectMap());
            runner.runTestStep(testStep);

            List<TestStepResult> resultList = runner.getResults();
            for (TestStepResult result : resultList) {
                System.out.println("=========================================================================================================");
                Gauge.writeMessage("=========================================================================================================");
                System.out.println(i + ") Executing TestStep '" + testStep.getName() + "'");
                Gauge.writeMessage("Executing TestStep '" + testStep.getName() + "'");
                System.out.println("_________________________________________________________________________________________________________");
                Gauge.writeMessage("_________________________________________________________________________________________________________");
                System.out.println("\n");

                // Print TestStep Request and Response
                String request = ((MessageExchange)result).getRequestContent();
                String response = ((MessageExchange)result).getResponseContent();
                System.out.println("Request is: \n" + request);
                Gauge.writeMessage("Request is: \n" + request);
                System.out.println("_________________________________________________________________________________________________________");
                Gauge.writeMessage("_________________________________________________________________________________________________________");
                System.out.println("Response is: \n" + response);
                Gauge.writeMessage("Response is: \n" + response);
                System.out.println("_________________________________________________________________________________________________________");
                Gauge.writeMessage("_________________________________________________________________________________________________________");
                System.out.println("Execution result = '" + result.getStatus() + "'");
                Gauge.writeMessage("Execution result = '" + result.getStatus() + "'");
                System.out.println("_________________________________________________________________________________________________________");
                Gauge.writeMessage("_________________________________________________________________________________________________________");
                System.out.println("Error Details = '" + result.getError() + "'");
                Gauge.writeMessage("Error Details = '" + result.getError() + "'");

                System.out.println("\n");
                Gauge.writeMessage("\n");
                System.out.println("=========================================================================================================");
                Gauge.writeMessage("=========================================================================================================");
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



