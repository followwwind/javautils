package com.wind.common;

public interface Const {

    String POINT_STR = ".";

    /**
     * 日期常量
     */
    String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    String DATE_STR = "yyyy-MM-dd";

    /**
     * 编码格式
     */
    String UTF8 = "UTF-8";


    /**
     * 加密格式
     */
    String MD5_KEY = "MD5";

    String SHA_KEY = "SHA1";

    String DES_KEY = "DES";

    String AES_KEY = "AES";


    /**
     * 正则表达式校验
     */
    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    String PHONE_NUM = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    String CHINESE = "^[\u4e00-\u9fa5]*$";

    String NUMBER = "^[0-9]*$";

    String FLOAT = "^\\d+\\.\\d+$";

    String IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";

    /**
     * 进制常量
     */
    String HEX_STR = "0123456789ABCDEF";

    /**
     * 文件后缀
     */
    String EXCEL_XLS = ".xls";

    String EXCEL_XLSX = ".xlsx";

    String IMAGE_PNG = "png";

    String IMAGE_JPG = "jpg";

    String FILE_ZIP = ".zip";
    String FILE_GZ = ".gz";


    /**
     * io流
     */
    int BUFFER_1024 = 1024;


}
