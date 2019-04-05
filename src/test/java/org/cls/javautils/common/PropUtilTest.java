package org.cls.javautils.common;

import org.junit.Test;

import java.util.Properties;

/**
 * @author <a href="mailto:chenlushun@51nbapi.com">陈录顺</a>
 */
public class PropUtilTest {


    @Test
    public void test() {
        // 获取系统信息
        Properties prop = System.getProperties();
        prop.list(System.out);
    }

    @Test
    public void test1() {
        Properties properties = PropUtil.getProp("classpath:/jdbc.properties");

        String jdbcUrl = properties.getProperty("jdbcUrl");

        System.out.println(jdbcUrl);
    }
}
