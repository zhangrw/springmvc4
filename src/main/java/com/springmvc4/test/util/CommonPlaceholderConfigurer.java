package com.springmvc4.test.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class CommonPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
    private static Map<String, Object> ctxPropertiesMap;

    public CommonPlaceholderConfigurer() {
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap();
        Iterator var4 = props.keySet().iterator();

        while(var4.hasNext()) {
            Object key = var4.next();
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }

    }

    public static String getContextProperty(String name) {
        return (String)ctxPropertiesMap.get(name);
    }
}
