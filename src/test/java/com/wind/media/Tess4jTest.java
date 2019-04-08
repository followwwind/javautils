package com.wind.media;

import com.wind.common.Constants;
import org.junit.Test;

public class Tess4jTest {

    @Test
    public void test(){
        /*String path = "src/main/resources/image/text.png";
        System.out.println(take(path));*/

        String ch = "src/main/resources/image/ch.png";
        System.out.println(Tess4jUtil.take(ch, "src/main/resources/tessdata", Constants.CHI_SIM));
    }
}
