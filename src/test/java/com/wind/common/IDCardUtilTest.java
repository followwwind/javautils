package com.wind.common;

import org.junit.Test;

/**
 * 身份证校验
 *
 * @author <a href="mailto:chenlushun@51nbapi.com">陈录顺</a>
 */
public class IDCardUtilTest {

    @Test
    public void test() {
        String card = "122424199109133419";

        String age = IDCardUtil.getAge(card);
        String gender = IDCardUtil.getGender(card);
        String hideCard = IDCardUtil.hideCard(card);
        boolean isIdCardLegal = IDCardUtil.isIdCardLegal(card);

        System.out.println(age);
        System.out.println(gender);
        System.out.println(hideCard);
        System.out.println(isIdCardLegal);

    }


}
