package com.fxcrm.feign;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class FeignRequestUrl {
    public String URL() {
        try {
            InputStream is = FeignRequestUrl.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(is);
            return properties.getProperty("feign-url");
        } catch (Exception e) {
            return "";
        }
    }
}
