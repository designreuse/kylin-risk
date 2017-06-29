package com.rkylin.risk.credit.excel;

import com.rkylin.risk.credit.excel.annotations.ExcelColumn;
import com.rkylin.risk.credit.excel.annotations.ExcelSheet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Created by tomalloc on 16-5-23.
 */
public class ExcelReader {
  private final Workbook workbook;

  public ExcelReader(InputStream is) throws IOException, InvalidFormatException {
    this(WorkbookFactory.create(is));
  }

  public ExcelReader(Workbook workbook) {
    this.workbook = workbook;
  }

  private ExcelSheet readSheetAnnotation(Class<T> tClass) {
    ExcelSheet excelSheet = tClass.getAnnotation(ExcelSheet.class);
    Objects.requireNonNull(excelSheet, tClass.getName() + "不存在@ExcelSheet");
    return excelSheet;
  }

  private static class FieldLocation {
    private Field field;
    private int columnIndex;

    FieldLocation(int columnIndex, Field field) {
      this.columnIndex = columnIndex;
      this.field = field;
    }
  }

  private List<FieldLocation> readFieldAnnotation(Class c) {
    Field[] fields = c.getDeclaredFields();
    List<FieldLocation> fieldLocations = new ArrayList<>(fields.length);
    for (Field field : fields) {
      ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
      if (excelColumn != null) {
        fieldLocations.add(new FieldLocation(excelColumn.index(), field));
      }
    }
    return fieldLocations;
  }

  //private

  public List<T> readAll(Class<T> tClass) {
    int sheetCount = workbook.getNumberOfSheets();
    if (sheetCount == 0) {
      throw new RuntimeException("sheet为空");
    }

    ExcelSheet excelSheet = readSheetAnnotation(tClass);

    List<FieldLocation> fieldLocations = readFieldAnnotation(tClass);

    Sheet sheet;
    String sheetName = excelSheet.name();
    if (sheetName != null) {
      sheet = workbook.getSheet(sheetName);
    } else {
      int accessSheetIndex = excelSheet.index();
      if (accessSheetIndex > sheetCount - 1) {
        throw new RuntimeException("不存在索引为" + accessSheetIndex + "sheet");
      }
      sheet = workbook.getSheetAt(accessSheetIndex);
    }
    List<T> list = new LinkedList<T>();
    try {
      int rowCount = sheet.getPhysicalNumberOfRows();
      for (int i = excelSheet.startRowIndex(); i < rowCount; i++) {
        T t = tClass.newInstance();
        Row row = sheet.getRow(i);
        for (FieldLocation fieldLocation : fieldLocations) {
          int columIndex = fieldLocation.columnIndex;
          Cell cell = row.getCell(columIndex);

          //if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
          //  idStr=(long)cell.getNumericCellValue()+"";
          //  bean.setId(idStr);
          //}else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
          //  idStr=cell.getStringCellValue();
          //  bean.setId(idStr);
          //}

        }
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } finally {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return list;
  }
}
