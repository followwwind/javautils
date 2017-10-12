package com.wind.image.opencv;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 * opencv图像处理工具类
 */
public class ImageUtils {
    /**
     * 获取图片的宽高
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
     * 获取人脸范围
     * @param imageFileName
     * @return
     */
    public static int[] detectFace(String imageFileName) {

        int[] RectPosition = new int[4];

        CascadeClassifier faceDetector = new CascadeClassifier("libs/lbpcascade_frontalface.xml");

        Mat image = Highgui.imread(imageFileName);

        MatOfRect faceDetections = new MatOfRect();

        // 指定人脸识别的最大和最小像素范围

        Size minSize = new Size(120, 120);

        Size maxSize = new Size(250, 250);

        // 参数设置为scaleFactor=1.1f, minNeighbors=4, flags=0 以此来增加识别人脸的正确率

        faceDetector.detectMultiScale(image, faceDetections, 1.1f, 4, 0,
                minSize, maxSize);

        // 对识别出来的头像画个方框，并且返回这个方框的位置坐标和大小

        for (Rect rect : faceDetections.toArray()) {

            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x

                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

            RectPosition[0] = rect.x;

            RectPosition[1] = rect.y;

            RectPosition[2] = rect.width;

            RectPosition[3] = rect.height;

            System.out.println(rect.x + " " + rect.y + " " + rect.width + " "
                    + rect.height);

        }

        // 下面注释掉的三行可以用来生成识别出的人脸图像，保存下来以便Debug用

        // String filename = "face.png";

        // System.out.println(String.format("Writing %s", filename));

        // Highgui.imwrite(filename, image);

        return RectPosition;
    }


    /**
     *
     * @param srcImg 源图片
     * @param destImg 图片输出流
     * @param rect 需要截取部分的坐标和大小
     *
     */
    public static void cutImage(File srcImg, File destImg, Rectangle rect) {
        if (srcImg.exists()) {
            java.io.FileInputStream fis = null;
            ImageInputStream iis = null;
            OutputStream output = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
                // JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if (srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                } // 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase() + ",") < 0) {
                    return;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReaders(new FileImageInputStream(srcImg)).next(); // ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                output = new FileOutputStream(destImg);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null)
                        fis.close();
                    if (iis != null)
                        iis.close();
                    if (output != null)
                        output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
        }
    }


    /*public static void cutImage(String srcImg, String destImg, int x, int y, int width, int height) {
        cutImage(new File(srcImg), new File(destImg), new java.awt.Rectangle(x, y, width, height));
    } */

    public static void main(String[] args) {

        Double avatarSpacePer = 0.16;
        Double avatarPer = 0.28;
        System.loadLibrary("libs/x64/opencv_java246");
        String filename = "img/idcard.jpg";
        int[] rectPosition = detectFace(filename);
        System.out.println("x=" + rectPosition[0] + " y=" + rectPosition[1] + " width=" + rectPosition[2] + " height="
                + rectPosition[3]);

        int x = rectPosition[0];
        int y = rectPosition[1];
        int w = rectPosition[2];
        int h = rectPosition[3];

        BufferedImage imgRect = getImage(filename);

        if (x == 0 || y == 0 || w == 0 || h == 0) {

            x = imgRect.getWidth();
            y = imgRect.getHeight();
            w = (int) (x * avatarPer);
            h = w;
            System.out.println("人脸识别失败:" + filename + " 采用默认识别");

        }

        int cutX = x / 2 - 20;

        String destFile = "img/idcard10.jpg";

        cutImage(new File(filename), new File("img/face10.jpg"), new java.awt.Rectangle(x, y, w, h));

        int width = imgRect.getWidth() - cutX - 30;
        // ImageUtils.cut(fileName, destFile, cutX, cutY, width/2, 120);
        // ImageUtils.cut(fileName, "d:/data/avatar.jpg", x, y, w, h);
        int cutHeight = 140;
        int imgHieght = imgRect.getHeight();
        int topSpace = (int) (imgHieght * avatarPer);
        int height = (int) (imgHieght * avatarSpacePer);
        int cutY = y + h + height;
        if (imgHieght - cutY < cutHeight - 5) {
            cutY = imgHieght - (int) (imgHieght / 3.8) - 10;
        }
        if (imgHieght < 500) {
            cutY = cutY + 20;
        }

        cutImage(new File(filename), new File(destFile), new java.awt.Rectangle(cutX, cutY, width, cutHeight));
    }
}

