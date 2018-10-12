package com.wind.media;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wind.common.Constants;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: ZxingUtil
 * @Package com.wind.media
 * @Description: google zxing二维码工具类
 * @author wind
 * @date 2018/9/15 15:29
 * @version V1.0
 */
public class ZxingUtil {


    /**
     * 设置二维码的参数
     * @param content
     * @param width
     * @param height
     * @return
     */
    private static BitMatrix getBitMatrix(String content, int width, int height){
        /**
         * 设置二维码的参数
         */
        BitMatrix bitMatrix = null;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, Constants.UTF8);
            //设置白边
            hints.put(EncodeHintType.MARGIN, 1);
            // 生成矩阵
            bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE,width,height,hints);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitMatrix;
    }

    /**
     * 生成指定目标文件的二维码
     * @param content 内容
     * @param width 宽度
     * @param height 高度
     * @param target 图片文件路径
     * @param imageType 图片类型
     */
    public static void genCode(String content, int width, int height, String imageType, String target){
        // 生成矩阵
        BitMatrix bitMatrix = getBitMatrix(content, width, height);
        genCode(bitMatrix, imageType, target);
    }

    /**
     * 根据bitMatrix生成二维码
     * @param bitMatrix
     * @param target
     * @param imageType
     */
    public static void genCode(BitMatrix bitMatrix, String imageType, String target){
        try {
            Path path = FileSystems.getDefault().getPath(target);
            // 输出图像
            MatrixToImageWriter.writeToPath(bitMatrix, imageType, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成正方形二维码
     * @param content 内容
     * @param length 边长
     * @param target 图片文件路径
     * @param imageType 图片类型
     */
    public static void genCode(String content, int length, String imageType, String target){
        genCode(content, length, length, imageType, target);
    }


    /**
     * 添加居中logo
     * @param content
     * @param imageType 图片类型
     * @param logo logo路径
     * @param logoLength
     * @param target 目标路径
     */
    public static void genCode(String content, int length, String imageType, String logo, int logoLength, String target){

        BitMatrix matrix = getBitMatrix(content, length, length);
        File logoFile = new File(logo);
        if(!logoFile.exists()){
            genCode(matrix, target, imageType);
        }else{
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage codeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < length; y++) {
//                    codeImage.setRGB(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }


            Graphics2D g = codeImage.createGraphics();
            /**
             * 读取Logo图片
             */
            try {
                BufferedImage logoImage = ImageIO.read(logoFile);
                /**
                 * logo放在中心
                 */
                int x = (length - logoLength) / 2;
                int y = (length - logoLength) / 2;
                g.drawImage(logoImage, x, y, logoLength, logoLength, null);
                g.dispose();
                codeImage.flush();
                logoImage.flush();
                ImageIO.write(codeImage, imageType, new File(target));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 解析二维码
     * @param filePath
     */
    public static void parseCode(String filePath){
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, Constants.UTF8);
            // 对图像进行解码
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            System.out.println("图片中内容:" + result.getText());
            System.out.println("encode:" + result.getBarcodeFormat());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String target = "src/main/resources/image/code.png";
        /*String content = "hello world";
        int length = 300;
        String logo = "src/main/resources/image/head.jpg";
        int logoLength = 50;
        writeCode(content, length, Constants.IMAGE_PNG, logo, logoLength, target);*/
        parseCode(target);
    }

}
