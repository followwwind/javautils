package com.wind.image;

import com.wind.common.Const;
import com.wind.image.opencv.HighGuiUtils;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * opencv图像处理工具类
 * @author wind
 */
public class ImageUtils {
    /**
     * 读取图片，获取BufferedImage对象
     * @param fileName
     * @return
     */
    public static BufferedImage getImage(String fileName){
        File picture = new File(fileName);
        BufferedImage sourceImg = null;
        try {
            sourceImg = ImageIO.read(new FileInputStream(picture));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceImg;
    }

    /**
     * 读取图片，获取ImageReader对象
     * @param fileName
     * @return
     */
    public static ImageReader getImageReader(String fileName){
        if(fileName != null){
            String suffix = "";
            for(String str : ImageIO.getReaderFormatNames()){
                if(fileName.lastIndexOf(Const.POINT_STR + str) > 0){
                    suffix = str;
                }
            }

            if(!"".equals(suffix)){
                try {
                    // 将FileInputStream 转换为ImageInputStream
                    ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(fileName));
                    // 根据图片类型获取该种类型的ImageReader
                    Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffix);
                    ImageReader reader = readers.next();
                    reader.setInput(iis, true);
                    return reader;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }

    /**
     * 图片截取 Graphic通过画布绘画
     * @param image 源图片
     * @param rect 待截取区域的坐标位置
     */
    public static BufferedImage cutImage(File image, Rectangle rect) {
        if (image.exists() && rect != null) {
            // 用ImageIO读取字节流
            BufferedImage bufferedImage = getImage(image.getAbsolutePath());
            int width = rect.width;
            int height = rect.height;
            int x = rect.x;
            int y = rect.y;
            BufferedImage dest = new BufferedImage(width, height, Transparency.TRANSLUCENT);
            Graphics g = dest.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, x, y, x + width, height + y, null);
            return dest;
        }
        return null;
    }

    /**
     * 图片截取 ImageReader截取 速度比Graphic通过画布绘画快很多
     * @param image 源图片
     * @param rect 待截取区域的坐标位置
     * @return
     */
    public static BufferedImage cutImage2(File image, Rectangle rect) {
        if (image.exists() && rect != null) {
            BufferedImage bi = null;
            try {
                ImageReader reader = getImageReader(image.getAbsolutePath());
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                bi = reader.read(0, param);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bi;
        }
        return null;
    }



    /**
     * BufferedImage生成目标图片
     * @param destImage
     * @param bufferedImage
     */
    public static void writeImage(File destImage, BufferedImage bufferedImage){
        try {
            ImageIO.write(bufferedImage, Const.IMAGE_PNG, destImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "src/main/resources/image/idcard.jpg";
        MatOfRect matRect = HighGuiUtils.detectFace(fileName);
        Rect rect = matRect.toArray()[0];
        String descImg = "src/main/resources/image/idcardbak.png";
        String descImg2 = "src/main/resources/image/idcardbak2.png";
        Rectangle r = new Rectangle(rect.x, rect.y, rect.width, rect.height);
        long s = System.currentTimeMillis();
        writeImage(new File(descImg), cutImage(new File(fileName), r));
        long e = System.currentTimeMillis();
        System.out.println(e - s);
        long s2 = System.currentTimeMillis();
        writeImage(new File(descImg2), cutImage2(new File(fileName), r));
        long e2 = System.currentTimeMillis();
        System.out.println(e2 - s2);
    }
}

