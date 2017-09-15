package com.wind.office.poi;

import com.wind.common.Const;
import com.wind.common.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * poi组件excel工具类
 */
public class ExcelUtils {



    public static List<Object> readExcel(String path, int sheetnum) {
        /*Workbook wb = null;
        List<List<String>> listsheet = new ArrayList<List<String>>();
        wb = getWorkbook(path);
        int sheets = wb.getNumberOfSheets();
        if(sheetnum <= sheets && sheetnum >=0){
            //获取sheet
            Sheet sheet = wb.getSheetAt(sheetnum);
            // 获得第一个表单的迭代器
            Iterator<Row> rows = sheet.rowIterator();
            //存储excel单个sheet
            while (rows.hasNext()) {
                // 获得行数据
                Row row = rows.next();
                // 获得第一行的迭代器
                Iterator<Cell> cells = row.cellIterator();
                //存储excel单行数据
                List<String> listcell = new ArrayList<String>();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    String cellValue = "";
                    switch (cell.getCellType()) { // 根据cell中的类型来输出数据
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            DecimalFormat df = new DecimalFormat("#.#######");
                            cellValue = df.format(cell.getNumericCellValue());
                            // System.out.println(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue = String.valueOf(cell.getStringCellValue());
                            // System.out.println(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            // System.out.println(cell.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellValue = String.valueOf(cell.getCellFormula());
                            // System.out.println(cell.getCellFormula());
                            break;
                        default:
                            cellValue = String.valueOf("");
                            // System.out.println("unsuported sell type");
                            break;
                    }
                    listcell.add(cellValue);
                }
                listsheet.add(listcell);
            }
        }*/
        return null;
    }


    /**
     * 获取指定sheet的数据
     * @param sheet
     * @return
     */
    public static List<Object> getSheet(Sheet sheet){
        List<Object> list = new ArrayList<>();
        // 获得表单的迭代器
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            // 获得行数据
            Row row = rows.next();
            // 获得行的迭代器
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
            }

        }

        return list;
    }

    /**
     * 获取工作簿workbook
     * @param path
     * @return
     */
    public static Workbook getWorkbook(String path){
        Workbook wb = null;
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            if(path.endsWith(Const.EXCEL_XLSX)){
                //2007+
                wb = new XSSFWorkbook(input);
            }else{
                //2003-2007
                wb = new HSSFWorkbook(input);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IOUtils.close(input);
        }
        return wb;
    }
}
