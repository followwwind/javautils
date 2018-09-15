//package com.wind.qrcode.qrcode;
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//import javax.imageio.ImageIO;
//
//import com.swetake.util.Qrcode;
//import com.wind.common.Const;
//import com.wind.media.ImageUtil;
//import jp.sourceforge.qrcode.QRCodeDecoder;
//import jp.sourceforge.qrcode.data.QRCodeImage;
//import jp.sourceforge.qrcode.exception.DecodingFailedException;
//
///**
// * QrCode工具类
// * @author wind
// */
//public class QrCodeUtil {
//
//	/**
//	 * 获取二维码输出内容
//	 * @param content
//	 * version 共有版本号1-40
//	 * @return
//	 */
//	public static boolean[][] getCodeOut(String content){
//		boolean[][] result = null;
//		Qrcode qrcodeHandler = new Qrcode();
//		 /*
//		  *设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，
//		  *但对二维码清晰度的要求越小
//		  */
//		qrcodeHandler.setQrcodeErrorCorrect('M');
//		//编码模式：Numeric 数字, Alphanumeric 英文字母,Binary 二进制,Kanji 汉字(第一个大写字母表示)
//		qrcodeHandler.setQrcodeEncodeMode('B');
//		/*
//	            二维码的版本号：也象征着二维码的信息容量；二维码可以看成一个黑白方格矩阵，版本不同，矩阵长宽方向方格的总数量分别不同。
//	     1-40总共40个版本，版本1为21*21矩阵，版本每增1，二维码的两个边长都增4；
//	            版本2 为25x25模块，最高版本为是40，是177*177的矩阵；
//       */
//		qrcodeHandler.setQrcodeVersion(7);
//		try {
//			byte[] contentBytes = content.getBytes("utf-8");
//			if (contentBytes.length > 0 && contentBytes.length < 120) {
//				result = qrcodeHandler.calQrcode(contentBytes);
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * BufferedImage加载二维码输出内容并获取BufferedImage对象
//	 * @param content
//	 * @param logo 若为null，则表示不带logo，否则输入logo路径
//	 * @return
//	 */
//	public static BufferedImage getQRCodeImage(String content, String logo){
//		boolean[][] codeOut = getCodeOut(content);
//		BufferedImage img = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);
//		Graphics2D gs = img.createGraphics();
//
//		gs.setBackground(Color.WHITE);
//		gs.clearRect(0, 0, 140, 140);
//		// 设定图像颜色> BLACK
//		gs.setColor(Color.BLACK);
//		// 设置偏移量 不设置可能导致解析出错
//		int pixoff = 2;
//		// 输出内容> 二维码
//		for (int i = 0; i < codeOut.length; i++) {
//			for (int j = 0; j < codeOut.length; j++) {
//				if (codeOut[j][i]) {
//					gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//				}
//			}
//		}
//		if(logo != null){
//			Image image = null;
//			try {
//				//实例化一个Image对象。
//				image = ImageIO.read(new File(logo));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//            gs.drawImage(image, 55, 55, 30, 30, null);
//		}
//		gs.dispose();
//		img.flush();
//		return img;
//	}
//
//	/**
//	 * 生成png类型的二维码
//	 * @param path 生成二维码图片路径
//	 * @param img
//	 */
//	public static void writeQRCode(String path, BufferedImage img){
//		ImageUtil.writeImage(new File(path), img);
//	}
//
//	/**
//	 * 解析二维码
//	 * @param imgPath
//	 * @return
//	 */
//	public static String parseQRCode(String imgPath) {
//
//        // QRCode 二维码图片的文件
//        File imageFile = new File(imgPath);
//        String decodedData = null;
//        try {
//			BufferedImage bufImg = ImageIO.read(imageFile);
//            QRCodeDecoder decoder = new QRCodeDecoder();
//			QRCodeImage qrCodeImage = new QRCodeImageImpl(bufImg);
//            decodedData = new String(decoder.decode(qrCodeImage), Const.UTF8);
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        } catch (DecodingFailedException dfe) {
//            System.out.println("Error: " + dfe.getMessage());
//            dfe.printStackTrace();
//        }
//
//        return decodedData;
//    }
//
//	public static void main(String[] args) {
//		System.out.println(parseQRCode("src/main/resources/image/code2.png"));
//		/*String path = "src/main/resources/image/code2.png";
//		writeQRCode(path, getQRCodeImage("hello", null));*/
//	}
//}
