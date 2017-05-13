package com.maxsoft.restapitestautomator;

import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.thoughtworks.gauge.Gauge;
import java.io.IOException;
import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */

public abstract class TestStepRequest {

    public static void getTestStepRequest(TestStepResult result) throws IOException {
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

}
