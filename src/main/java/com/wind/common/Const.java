package com.wind.common;

import java.io.File;

/**
 * 常量字符集合
 * @author wind
 */
public interface Const {

    /**********************************************分隔符常量************************************************/

    String POINT_STR = ".";

    String BLANK_STR = "";

    String SPACE_STR = " ";

    String NEWLINE_STR = "\n";

    String SYS_SEPARATOR = File.separator;

    String FILE_SEPARATOR = "/";

    String BRACKET_LEFT = "[";

    String BRACKET_RIGHT = "]";

    String UNDERLINE = "_";



    /**********************************************编码格式************************************************/

    String UTF8 = "UTF-8";


    /**********************************************文件后缀************************************************/

    String EXCEL_XLS = ".xls";

    String EXCEL_XLSX = ".xlsx";

    String IMAGE_PNG = "png";

    String IMAGE_JPG = "jpg";

    String FILE_ZIP = ".zip";
    String FILE_GZ = ".gz";


    /**********************************************io流************************************************/

    int BUFFER_1024 = 1024;

    String USER_DIR = "user.dir";

    /**********************************************tesseract for java语言字库************************************************/

    String ENG = "eng";

    String CHI_SIM = "chi_sim";


    /**********************************************opencv************************************************/
    String OPENCV_LIB_NAME_246 = "libs/x64/opencv_java246";

    String OPENCV_LIB_NAME_330 = "libs/x64/opencv_java330";
}
