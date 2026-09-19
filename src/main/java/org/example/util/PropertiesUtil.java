package org.example.util;

import javax.management.RuntimeMBeanException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil(){

    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
        PROPERTIES.load(inputStream);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }


}
