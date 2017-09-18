package com.wind.qrcode.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wind.common.Const;

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
 * google zxing二维码工具类
 */
public class ZxingUtils {


    /**
     * 生成指定目标文件的二维码
     * @param content 内容
     * @param width 宽度
     * @param height 高度
     * @param filePath 图片文件路径
     * @param imageType 图片类型
     */
    public static void writeCode(String content, int width, int height, String filePath, String imageType){

        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, Const.UTF8);
            //设置白边
            hints.put(EncodeHintType.MARGIN, 1);
            // 生成矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            // 输出图像
            MatrixToImageWriter.writeToPath(bitMatrix, imageType, path);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成正方形二维码
     * @param content 内容
     * @param length 边长
     * @param filePath 图片文件路径
     * @param imageType 图片类型
     */
    public static void writeCode(String content, int length, String filePath, String imageType){
        writeCode(content, length, length, filePath, imageType);
    }


    /**
     * 添加居中logo
     * @param filePath
     * @param imageType 图片类型
     * @param logo logo路径
     * @param logoLength
     * @param target 目标路径
     */
    public static void addLogo(String filePath, String imageType, String logo, int logoLength, String target){

        File codeFile = new File(filePath);
        File logoFile = new File(logo);

        if(!codeFile.exists() || !logoFile.exists()){
            System.out.println("文件未找到!!!");
            return;
        }
        /**
         * 读取二维码图片，并构建绘图对象
         */
        BufferedImage codeImage = null;
        try {
            codeImage = ImageIO.read(codeFile);
            int codeWidth = codeImage.getWidth();
            int codeHeight = codeImage.getHeight();
            Graphics2D g = codeImage.createGraphics();
            /**
             * 读取Logo图片
             */
            BufferedImage logoImage = ImageIO.read(logoFile);

            int logoWidth = logoLength;
            int logoHeight = logoLength;
            /**
             * logo放在中心
             */
            int x = (codeWidth - logoWidth) / 2;
            int y = (codeHeight - logoHeight) / 2;
            //开始绘制图片
            g.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
            g.setColor(Color.BLACK);
            g.setBackground(Color.WHITE);
            g.dispose();
            logoImage.flush();
            codeImage.flush();

            ImageIO.write(codeImage, imageType, new File(target));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void parseCode(String filePath){
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, Const.UTF8);
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            System.out.println("图片中内容:" + result.getText());
            System.out.println("encode:" + result.getBarcodeFormat());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        writeCode("hello", 300, "E:\\3.png", Const.IMAGE_PNG);
//        addLogo("E:\\3.png", Const.IMAGE_PNG, "E:\\resource\\picture\\3.png", 30, "E:\\1.png");
//        parseCode("E:\\3.png");
    }

}
