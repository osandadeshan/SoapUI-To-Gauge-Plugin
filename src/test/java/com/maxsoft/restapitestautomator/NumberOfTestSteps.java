package com.maxsoft.restapitestautomator;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.support.SoapUIException;
import com.thoughtworks.gauge.Gauge;
import org.apache.xmlbeans.XmlException;
import java.io.IOException;
import static com.maxsoft.restapitestautomator.ExecuteSoapUIAnyTestCase.FILE_PATH;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */

public abstract class NumberOfTestSteps {

    public NumberOfTestSteps() throws XmlException, IOException, SoapUIException {
    }

    public static int getNumberOfTestSteps(String TestSuite, String TestCase) {
        WsdlProject project = null;
        try {
            project = new WsdlProject(FILE_PATH);
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SoapUIException e) {
            e.printStackTrace();
        }
        WsdlTestCase testCase = project.getTestSuiteByName(TestSuite).getTestCaseByName(TestCase);
          int numberOfTestSteps = testCase.getTestStepCount();
        return numberOfTestSteps;
    }

    public static void printNumberOfTestSteps(String TestSuite, String TestCase) {
        int numberOfTestSteps = getNumberOfTestSteps(TestSuite, TestCase);
        System.out.println("Number of Test Steps in the Test Case = " + numberOfTestSteps + "\n");
        Gauge.writeMessage("Number of Test Steps in the Test Case = " + numberOfTestSteps + "\n");
    }

}
