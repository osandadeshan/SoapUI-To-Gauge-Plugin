package com.maxsoft.restapitestautomator;

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.thoughtworks.gauge.Gauge;
import java.io.IOException;
import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */

public abstract class TestStepName {

    public static void getTestStepName(int i, WsdlTestStep testStep) throws IOException {
        System.out.println((i + 1) + ") Executing Test Step [" + testStep.getName() + "]");
        Gauge.writeMessage((i + 1) + ") Executing Test Step [" + testStep.getName() + "]");
        System.out.println(readingFromPropertyFile("messages", "straight_line"));
        Gauge.writeMessage(readingFromPropertyFile("messages", "straight_line"));
    }

}
