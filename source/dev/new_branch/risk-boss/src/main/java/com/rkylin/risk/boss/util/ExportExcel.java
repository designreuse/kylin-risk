package com.rkylin.risk.boss.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类 注意这里为了简单起见，
 * boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 */
@Slf4j
public class ExportExcel<T> {

  /**
   * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL
   * 的形式输出到指定IO设备上
   *
   * @param sheetName sheet名字
   * @param headers 表格属性列名数组
   * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
   * 此方法支持的 javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
   * @param filedNames 对应列明的实体字段名字
   * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
   * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
   */
  public void exportExcel(String sheetName, String[] headers,
      Collection<T> dataset, String[] filedNames,
      OutputStream out, String pattern) {
    // 声明一个工作薄
    HSSFWorkbook workbook = new HSSFWorkbook();
    // 生成一个表格
    HSSFSheet sheet = workbook.createSheet(sheetName);
    // 设置表格默认列宽度为15个字节
    sheet.setDefaultColumnWidth(15);
    // 生成一个样式
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置这些样式
    style.setFillForegroundColor(HSSFColor.WHITE.index);
    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    // 生成一个字体
    HSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.VIOLET.index);
    font.setFontHeightInPoints((short) 12);
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    // 把字体应用到当前的样式
    style.setFont(font);
    // 生成并设置另一个样式
    HSSFCellStyle style2 = workbook.createCellStyle();
    style2.setFillForegroundColor(HSSFColor.WHITE.index);
    style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
    style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
    style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    // 生成另一个字体
    HSSFFont font2 = workbook.createFont();
    font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
    // 把字体应用到当前的样式
    style2.setFont(font2);

    // 声明一个画图的顶级管理器
    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    // 产生表格标题行
    HSSFRow row = sheet.createRow(0);
    for (int i = 0; i < headers.length; i++) {
      HSSFCell cell = row.createCell(i);
      cell.setCellStyle(style);
      HSSFRichTextString text = new HSSFRichTextString(headers[i]);
      cell.setCellValue(text);
    }

    // 遍历集合数据，产生数据行
    Iterator<T> it = dataset.iterator();
    int index = 0;
    while (it.hasNext()) {
      index++;
      row = sheet.createRow(index);
      T t = (T) it.next();
      // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
      //Field[] fields = t.getClass().getDeclaredFields();
      for (int i = 0; i < filedNames.length; i++) {
        HSSFCell cell = row.createCell(i);
        cell.setCellStyle(style2);
        // Field field = fields[i];
        String fieldName = filedNames[i];
        String getMethodName = "get"
            + fieldName.substring(0, 1).toUpperCase()
            + fieldName.substring(1);
        try {
          Class tCls = t.getClass();
          Method getMethod = tCls.getMethod(getMethodName,
              new Class[] {});
          Object value = getMethod.invoke(t, new Object[] {});
          Class methodReturnType = getMethod.getReturnType();
          // 判断值的类型后进行强制类型转换
          String textValue = null;
          if (methodReturnType == Boolean.class || methodReturnType == Boolean.TYPE) {
            boolean bValue = (Boolean) value;
            textValue = "男";
            if (!bValue) {
              textValue = "女";
            }
          } else if (methodReturnType == Date.class) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
          } else if (methodReturnType == byte[].class) {
            // 有图片时，设置行高为60px;
            row.setHeightInPoints(60);
            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            sheet.setColumnWidth(i, (short) (35.7 * 80));
            // sheet.autoSizeColumn(i);
            byte[] bsValue = (byte[]) value;
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                1023, 255, (short) 6, index, (short) 6, index);
            anchor.setAnchorType(2);
            patriarch.createPicture(anchor, workbook.addPicture(
                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
          } else if (methodReturnType == Double.class || methodReturnType == Double.TYPE) {
            double data = 0D;
            if (value != null) {
              data = ((Double) value).doubleValue();
            }
            cell.setCellValue(data);
          } else if (methodReturnType == Integer.class || methodReturnType == Integer.TYPE) {
            int data = 0;
            if (value != null) {
              data = ((Integer) value).intValue();
            }
            cell.setCellValue(data);
          } else {
            // 其它数据类型都当作字符串简单处理
            textValue = String.valueOf(value == null ? "" : value);
          }
          // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
          if (textValue != null) {
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher = p.matcher(textValue);
            if (matcher.matches()) {
              // 是数字当作double处理
              cell.setCellValue(Double.parseDouble(textValue));
            } else {
              cell.setCellValue(textValue);
            }
          }
        } catch (final SecurityException  | NoSuchMethodException
            | IllegalArgumentException | IllegalAccessException
            | InvocationTargetException e) {
          log.info(e.getMessage(), e);
        } finally {
          // 清理资源
        }
      }
    }
    try {
      workbook.write(out);
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    }
  }

  public void exportExcelX(String sheetName, String[] headers,
      Collection<T> dataset, String[] filedNames,
      OutputStream out, String pattern) {
    // 声明一个工作薄
    XSSFWorkbook workbook = new XSSFWorkbook();
    // 生成一个表格
    XSSFSheet sheet = workbook.createSheet(sheetName);
    // 设置表格默认列宽度为15个字节
    sheet.setDefaultColumnWidth((short) 15);
    // 生成一个样式
    XSSFCellStyle style = workbook.createCellStyle();
    // 设置这些样式
    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
    style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    style.setBorderRight(XSSFCellStyle.BORDER_THIN);
    style.setBorderTop(XSSFCellStyle.BORDER_THIN);
    style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    // 生成一个字体
    XSSFFont font = workbook.createFont();
    font.setColor(HSSFColor.VIOLET.index);
    font.setFontHeightInPoints((short) 12);
    font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
    // 把字体应用到当前的样式
    style.setFont(font);
    // 生成并设置另一个样式
    XSSFCellStyle style2 = workbook.createCellStyle();
    style2.setFillForegroundColor(HSSFColor.WHITE.index);
    style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
    style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
    style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
    // 生成另一个字体
    XSSFFont font2 = workbook.createFont();
    font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
    // 把字体应用到当前的样式
    style2.setFont(font2);

    // 声明一个画图的顶级管理器
    XSSFDrawing patriarch = sheet.createDrawingPatriarch();
    // 产生表格标题行
    XSSFRow row = sheet.createRow(0);
    for (short i = 0; i < headers.length; i++) {
      XSSFCell cell = row.createCell(i);
      cell.setCellStyle(style);
      XSSFRichTextString text = new XSSFRichTextString(headers[i]);
      cell.setCellValue(text);
    }

    // 遍历集合数据，产生数据行
    Iterator<T> it = dataset.iterator();
    int index = 0;
    while (it.hasNext()) {
      index++;
      row = sheet.createRow(index);
      T t = (T) it.next();
      // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
      Field[] fields = t.getClass().getDeclaredFields();
      for (short i = 0; i < filedNames.length; i++) {
        XSSFCell cell = row.createCell(i);
        cell.setCellStyle(style2);
        // Field field = fields[i];
        String fieldName = filedNames[i];
        String getMethodName = "get"
            + fieldName.substring(0, 1).toUpperCase()
            + fieldName.substring(1);
        try {
          Class tCls = t.getClass();
          Method getMethod = tCls.getMethod(getMethodName,
              new Class[] {});
          Object value = getMethod.invoke(t, new Object[] {});
          Class methodReturnType = getMethod.getReturnType();
          // 判断值的类型后进行强制类型转换
          String textValue = null;
          if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            textValue = "男";
            if (!bValue) {
              textValue = "女";
            }
          } else if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
          } else if (value instanceof byte[]) {
            // 有图片时，设置行高为60px;
            row.setHeightInPoints(60);
            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            sheet.setColumnWidth(i, (short) (35.7 * 80));
            // sheet.autoSizeColumn(i);
            byte[] bsValue = (byte[]) value;
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0,
                1023, 255, (short) 6, index, (short) 6, index);
            anchor.setAnchorType(2);
            patriarch.createPicture(anchor, workbook.addPicture(
                bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG));
          } else if (methodReturnType == Double.class || methodReturnType == Double.TYPE) {
            double data = 0D;
            if (value != null) {
              data = ((Double) value).doubleValue();
            }
            cell.setCellValue(data);
            cell.setCellValue(value == null ? 0D : Double
                .parseDouble(String.valueOf(value)));
          } else if (methodReturnType == Integer.class || methodReturnType == Integer.TYPE) {
            int data = 0;
            if (value != null) {
              data = ((Integer) value).intValue();
            }
            cell.setCellValue(data);
          } else {
            // 其它数据类型都当作字符串简单处理
            textValue = String.valueOf(value == null ? "" : value);
          }
          // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
          if (textValue != null) {
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher = p.matcher(textValue);
            if (matcher.matches()) {
              // 是数字当作double处理
              cell.setCellValue(Double.parseDouble(textValue));
            } else {
              XSSFRichTextString richString = new XSSFRichTextString(
                  textValue);
              cell.setCellValue(richString);
            }
          }
        } catch (SecurityException e) {
          log.info(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
          log.info(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
          log.info(e.getMessage(), e);
        } catch (IllegalAccessException e) {
          log.info(e.getMessage(), e);
        } catch (InvocationTargetException e) {
          log.info(e.getMessage(), e);
        } finally {
          // 清理资源
        }
      }
    }
    try {
      workbook.write(out);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
  }
}