package org.cls.javautils.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.cls.javautils.common.DateUtil.*;

/**
 * @author <a href="mailto:chenlushun@51nbapi.com">陈录顺</a>
 */
public class DateUtilTest {

    @Test
    public void test() {
        Date start = parse("2018-08-03", DATE_STR);
        Date end = parse("2018-08-04", DATE_STR);

        long between = between(start, end);
        Assert.assertEquals(24 * 60 * 60 * 1000, between);
        Assert.assertEquals("2018-08-04", format(end, DATE_STR));
    }
}
