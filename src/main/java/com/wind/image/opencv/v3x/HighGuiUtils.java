package com.wind.image.opencv.v3x;

import com.wind.common.Const;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

/**
 * opencv HighGui工具类
 * @author wind
 */
public class HighGuiUtils {

    static {
        System.loadLibrary(Const.OPENCV_LIB_NAME_330);
    }

    /**
     * 获取人脸范围
     * @param fileName
     * @return
     */
    public static MatOfRect detectFace(String fileName) {
        CascadeClassifier faceDetector = new CascadeClassifier("libs/lbpcascade_frontalface.xml");
        Mat image = Imgcodecs.imread(fileName);
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
        Mat image = Imgcodecs.imread(fileName);
        // Create a face detector from the cascade file in the resources
        // directory.
        CascadeClassifier faceDetector = new CascadeClassifier("libs/lbpcascade_frontalface.xml");
        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        Imgcodecs.imwrite(destName, image);

    }


    public static void main(String[] args) {
        String fileName = "src/main/resources/image/face.jpg";
        String destName = "src/main/resources/image/face2.jpg";
        drawRect(fileName, destName);
    }
}
