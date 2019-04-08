package com.wind.compress;

import org.junit.Test;

/**
 * @Title: ZipTest
 * @Package com.wind.compress
 * @Description: TODO
 * @author wind
 * @date 2019/4/8 10:38
 * @version V1.0
 */
public class ZipTest {

    @Test
    public void testGZip(){
        String source = "src/main/resources/image/head.jpg";
        String target = "src/main/resources/";
        GZipUtil.compress(source, target);
        GZipUtil.uncompress(target + "head.jpg.gz", target);
    }
}
