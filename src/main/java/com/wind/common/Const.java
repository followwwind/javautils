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

    String SYS_SEPARATOR = File.separator;

    String FILE_SEPARATOR = "/";

    String BRACKET_LEFT = "[";

    String BRACKET_RIGHT = "]";

    /**********************************************日期时间常量************************************************/

    String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    String DATE_STR = "yyyy-MM-dd";

    int SECOND = 1000;

    int MINUTE = 60 * SECOND;

    int HOUR = 60 * MINUTE;

    int DAY = 24 * HOUR;

    /**********************************************编码格式************************************************/

    String UTF8 = "UTF-8";


    /**********************************************加密格式************************************************/

    String MD5_KEY = "MD5";

    String SHA_KEY = "SHA1";

    String DES_KEY = "DES";

    String AES_KEY = "AES";


    /**********************************************正则表达式************************************************/

    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    String PHONE_NUM = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**中文正则表达式验证*/
    String CHINESE = "^[\u4e00-\u9fa5]*$";

    String NUMBER = "^[0-9]*$";

    String FLOAT = "^\\d+\\.\\d+$";

    String IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";

    /**********************************************进制常量************************************************/

    /**十六进制常量*/
    String HEX_STR = "0123456789ABCDEF";

    int MAP_SIZE = 16;

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

    /**********************************************身份证信息************************************************/
    /**身份证18位*/
    int IDCARD_LEN18 = 18;
    /**身份证15位*/
    int IDCARD_LEN15 = 15;


    /**********************************************系统信息************************************************/
    String OS_NAME = System.getProperty("os.name");

    String WIN_DIR = System.getenv("windir");

    String STR_SPACE = " ";

    String STR_NEWLINE = "\n";


    /**
     * linux, window查看CPU进程命令
     */
    String WIN_CPU_PROC = WIN_DIR + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
    String LINUX_CPU_PROC = "cat /proc/stat";

    /**
     * linux查看内存脚本命令
     */
    String LINUX_MEM_PROC = "cat /proc/meminfo";

    String LINUX_IO_PROC = "iostat -d -x";

    /**
     * linux查看带宽脚本命令
     */
    String LINUX_NET_PROC = "cat /proc/net/dev";

    /**
     * 网口带宽,Mbps
     */
    float TotalBandwidth = 1;

    /**
     * linux查看磁盘脚本命令
     */
    String LINUX_DISK_PROC = "df";

    String LINUX_UNIT_SIZE = "kb";

    String WIN_UNIT_SIZE = "b";
    /**
     * window盘符序号  A-Z
     */
    char CH_A = 'A';
    char CH_Z = 'Z';


    /**********************************************opencv************************************************/
    String OPENCV_LIB_NAME_246 = "libs/x64/opencv_java246";

    String OPENCV_LIB_NAME_330 = "libs/x64/opencv_java330";
}
