package com.wind.office.poi;

import com.wind.common.Constants;
import com.wind.common.DateUtil;
import com.wind.common.IoUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Title: ExcelUtil
 * @Package com.wind.office.poi
 * @Description: poi组件excel工具类
 * @author wind
 * @date 2018/10/13 10:50
 * @version V1.0
 */
public class ExcelUtil {

    /**
     * 写入文件
     * @param path 文件路径
     * @param list
     * @param sign true表示新建  false表示读取已存在新建sheet
     */
    public static void writeExcel(String path, List<Object> list, boolean sign){
        OutputStream out = null;
        Workbook wb = getWorkbook(path, sign);
        try {
            out = new FileOutputStream(path);
            Sheet sheet = wb.createSheet();
            putSheet(sheet, list);
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(out);
        }
    }


    /**
     * 读取excel指定页的数据
     * @param path
     * @param sheetNum
     * @return
     */
    public static List<List<String>> readExcel(String path, int sheetNum) {
        List<List<String>> listSheet = null;
        Workbook wb = getWorkbook(path, false);
        int sheets = wb.getNumberOfSheets();
        if(sheetNum <= sheets && sheetNum >=0){
            //获取sheet
            Sheet sheet = wb.getSheetAt(sheetNum);
            System.out.println(sheet.getLastRowNum());
            listSheet = getSheet(sheet);
        }
        return listSheet;
    }


    /**
     * 获取指定页sheet的数据
     * @param sheet
     * @return
     */
    private static List<List<String>> getSheet(Sheet sheet){
        List<List<String>> list = new ArrayList<>();
        // 获得表单的迭代器
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            // 获得行数据
            Row row = rows.next();
            // 获得行的迭代器
            Iterator<Cell> cells = row.cellIterator();
            List<String> rowList = new ArrayList<>();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                if(cell != null) {
                    String value = getCellValue(cell);
                    rowList.add(value);
                }
            }

            list.add(rowList);

        }

        return list;
    }


    /**
     * 获取cell数据
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell){
        String value = "";
        if(cell != null) {
            switch (cell.getCellTypeEnum()) {
                case FORMULA:
                    value += cell.getCellFormula();
                    break;

                case NUMERIC:
                    double cellValue = cell.getNumericCellValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        Date date = HSSFDateUtil.getJavaDate(cellValue);
                        value += DateUtil.format(date, DateUtil.DATE_TIME);
                    }else{
                        value += cell.getNumericCellValue();
                    }
                    break;

                case STRING:
                    value += cell.getStringCellValue();
                    break;
                case BLANK:
                    break;
                case BOOLEAN:
                    value += cell.getBooleanCellValue();
                    break;
                case ERROR:
                    break;
                default:break;
            }
        }
        return value;
    }

    /**
     * 向指定页中写入数据
     * @param sheet
     * @param list
     */
    private static void putSheet(Sheet sheet, List<Object> list){
        Row row;
        Cell c;
        if(sheet != null && list != null){
            for(int i = 0; i < list.size(); i++){
                row =  sheet.createRow(i);
                Object obj = list.get(i);
                if(obj instanceof List){
                    List rowData = (List) obj;
                    for(int j = 0; j < rowData.size(); j++){
                        Object colData = rowData.get(j);
                        c = row.createCell(j);
                        String value = colData != null ? colData.toString() : "";
                        if(colData instanceof String){
                            c.setCellValue(value);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取工作簿workbook
     * @param path
     * @param sign true表示新建 false表示读取
     * @return
     */
    private static Workbook getWorkbook(String path, boolean sign){
        Workbook wb = null;
        InputStream input = null;
        try {
            if(sign){
                if(path.endsWith(Constants.EXCEL_XLS)){
                    //2003-2007
                    wb = new HSSFWorkbook();
                }else{
                    //2007+
                    wb = new XSSFWorkbook();
                }
            }else{
                input = new FileInputStream(path);
                if(path.endsWith(Constants.EXCEL_XLSX)){
                    //2007+
                    wb = new XSSFWorkbook(input);
                }else{
                    //2003-2007
                    wb = new HSSFWorkbook(input);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IoUtil.close(input);
        }
        return wb;
    }


    public static void main(String[] args) {
//        String path = "E:\\doc\\导入收费记录模板.xlsx";
        String path = "E:\\doc\\导入账单模板.xlsx";
        int i = 0;
        List<List<String>> list = readExcel(path, i);
        System.out.println(list);
    }
}
