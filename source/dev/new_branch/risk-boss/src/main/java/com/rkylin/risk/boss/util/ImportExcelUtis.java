package com.rkylin.risk.boss.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;

/**
 * Created by cuixiaofang on 2016-6-15.
 */
public class ImportExcelUtis {

    public static String readValue(Cell cell) {
        String value = "";
        if (cell == null) {
            return "NULL";
        }
        int rowType = cell.getCellType();
        if (rowType == Cell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        } else if (rowType == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(HSSFDateUtil.getJavaDate(
                        cell.getNumericCellValue())).toString();
                value = date.toString();
//            } else {
//                Number v = cell.getNumericCellValue();
                //如果是身份证(第三列)（因为身份证全是数字）则把小数部分去掉
//                if (cell.getColumnIndex() == 3) {
//                    double va = Double.valueOf(v.toString());
//                    int val = (int) va;
//                    value = String.valueOf(val);
//                } else {
//                    value = v.toString();
//                }
//                value = v.toString();
            }
        }
        return value;
    }

    public static Double readNumberValue(Cell cell) {
        Double dou = 0d;
        if (cell == null) {
            return dou;
        }
        int rowType = cell.getCellType();
        if (rowType == Cell.CELL_TYPE_NUMERIC) {
            dou = cell.getNumericCellValue();
        }
        return dou;
    }

    public static LocalDate readValueToDate(Cell cell) {
        LocalDate localDate = null;
        if (cell == null) {
            return null;
        }
        int rowType = cell.getCellType();
        if (rowType == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                localDate = LocalDate.fromDateFields(HSSFDateUtil.getJavaDate(
                        cell.getNumericCellValue()));
            }
        }
        return localDate;
    }

}
