package com.wind.office.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * @author wind
 */
public class SXSSFUtil {

    public static void main(String[] args) throws Throwable {
        test1();
//        test2();
    }

    public static void test1() throws Throwable{
        long start = System.currentTimeMillis();
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        common(wb, "E:\\doc\\3.xlsx");
        wb.dispose();

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    public static void test2() throws Throwable{
        long start = System.currentTimeMillis();
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        Workbook wb = new XSSFWorkbook();
        common(wb, "E:\\doc\\2.xlsx");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    private static void common(Workbook wb, String filename) throws Throwable{
        Sheet sh = wb.createSheet();
        for(int rownum = 0; rownum < 1000; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 10; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

        }
        // Rows with rownum < 900 are flushed and not accessible
        for(int rownum = 0; rownum < 900; rownum++){
            //Assert.assertNull(sh.getRow(rownum));
        }

        // ther last 100 rows are still in memory
        for(int rownum = 900; rownum < 1000; rownum++){
            //Assert.assertNotNull(sh.getRow(rownum));
        }

        FileOutputStream out = new FileOutputStream(filename);
        wb.write(out);
        out.close();
    }


}
