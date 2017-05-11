package com.maxsoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Created by Osanda Deshan on 5/11/2017.
 */


public abstract class PropertyReader {

    public static String readingFromPropertyFile(String propertyFileName, String property) throws IOException {
        File file = new File("env\\default\\"+propertyFileName+".properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        String VISIBILITY_OF_PROPERTY = String.valueOf(properties.getProperty(property));
        return VISIBILITY_OF_PROPERTY;
    }

}
