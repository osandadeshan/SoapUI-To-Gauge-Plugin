package com.maxsoft.restapitestautomator;

import com.eviware.soapui.model.testsuite.TestStepResult;
import com.thoughtworks.gauge.Gauge;
import java.io.IOException;
import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */

public abstract class TestStepExecutionResult {

    public static void getTestStepExecutionResult(TestStepResult result) throws IOException {
        System.out.println("Test Step Execution Result = [" + result.getStatus() + "]");
        Gauge.writeMessage("Test Step Execution Result = [" + result.getStatus() + "]");
        System.out.println(readingFromPropertyFile("messages", "straight_line"));
        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
    }

}
