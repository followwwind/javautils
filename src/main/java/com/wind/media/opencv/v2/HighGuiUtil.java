package com.wind.media.opencv.v2;

import com.wind.common.Constants;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * opencv HighGui工具类
 * @author wind
 */
public class HighGuiUtil {

    static {
        System.loadLibrary(Constants.OPENCV_LIB_NAME_246);
    }

    /**
     * 获取人脸范围
     * @param fileName
     * @return
     */
    public static MatOfRect takeFace(String fileName) {
        CascadeClassifier faceDetector = new CascadeClassifier("libs/lbpcascade_frontalface.xml");
        Mat image = Highgui.imread(fileName);
        MatOfRect faceDetections = new MatOfRect();
        // 指定人脸识别的最大和最小像素范围
        Size minSize = new Size(120, 120);
        Size maxSize = new Size(250, 250);
        // 参数设置为scaleFactor=1.1f, minNeighbors=4, flags=0 以此来增加识别人脸的正确率
        faceDetector.detectMultiScale(image, faceDetections, 1.1f, 4, 0,
                minSize, maxSize);
        return faceDetections;
    }

    /**
     * Detects faces in an image, draws boxes around them, and writes the results
     * @param fileName
     * @param destName
     */
    public static void drawRect(String fileName, String destName){
        Mat image = Highgui.imread(fileName);
        // Create a face detector from the cascade file in the resources
        // directory.
        CascadeClassifier faceDetector = new CascadeClassifier("libs/lbpcascade_frontalface.xml");
        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        Highgui.imwrite(destName, image);

    }


    /**
     * 二值化
     *
     * @param oriImg
     * @param outputImg
     */
    public static void binarization(String oriImg, String outputImg) {
        Mat img = Highgui.imread(oriImg);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
        //
        Imgproc.adaptiveThreshold(img, img, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 25, 10);
        Highgui.imwrite(outputImg, img);
    }


    /**
     * 边缘检测的原理：检测出图像中所有灰度值变化较大的点，而且这些点连起来构成若干线条，这些线条就称之为图像的边缘。
     * @param oriImg
     * @param dstImg
     * @param threshold
     */
    public static void canny(String oriImg, String dstImg, int threshold) {
        Mat img = Highgui.imread(oriImg);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
         /**Canny(Mat image, Mat edges, double threshold1, double threshold2, int apertureSize, boolean L2gradient)
          * 第一个参数，InputArray类型的image，输入图像，即源图像，填Mat类的对象即可，且需为单通道8位图像。
          * 第二个参数，OutputArray类型的edges，输出的边缘图，需要和源图片有一样的尺寸和类型。
          * 第三个参数，double类型的threshold1，第一个滞后性阈值。
          * 第四个参数，double类型的threshold2，第二个滞后性阈值。
          * 第五个参数，int类型的apertureSize，表示应用Sobel算子的孔径大小，其有默认值3。
          * 第六个参数，bool类型的L2gradient，一个计算图像梯度幅值的标识，有默认值false。
          */
        Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
        Highgui.imwrite(dstImg, img);
    }
}
