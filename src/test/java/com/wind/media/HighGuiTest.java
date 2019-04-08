package com.wind.media;

import com.wind.media.opencv.v3.HighGuiUtil;
import org.junit.Test;

public class HighGuiTest {

    @Test
    public void test(){
        String fileName = "src/main/resources/image/face.jpg";
        String destName = "src/main/resources/image/face2.jpg";
        HighGuiUtil.drawRect(fileName, destName);
    }
}
