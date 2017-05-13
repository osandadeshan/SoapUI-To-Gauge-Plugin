package com.maxsoft.restapitestautomator;

import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.thoughtworks.gauge.Gauge;

import java.io.IOException;

import static com.maxsoft.util.PropertyReader.readingFromPropertyFile;

/**
 * Created by Osanda Deshan on 5/13/2017.
 */

public abstract class TestStepEndPoint {

    public static void getTestStepEndPoint(TestStepResult result) throws IOException {
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

}
