package com.maxsoft.restapitestautomator;

import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.thoughtworks.gauge.Gauge;

import java.io.IOException;
import java.util.List;

import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */
public abstract class TestStepAssertions {

    public static void getTestStepAssertions(WsdlTestStep testStep) throws IOException {
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

}
