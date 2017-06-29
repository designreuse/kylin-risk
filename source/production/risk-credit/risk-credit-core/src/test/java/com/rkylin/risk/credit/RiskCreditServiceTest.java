package com.rkylin.risk.credit;

import com.google.common.collect.Table;
import com.rkylin.risk.credit.service.CreditRequestParam;
import com.rkylin.risk.credit.service.RiskCreditService;
import com.rkylin.risk.credit.service.report.ReportItem;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tomalloc on 16-8-2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:risk-credit-test.xml"})
public class RiskCreditServiceTest {
  private List<CreditRequestParam> requestParams = new ArrayList<>();

  @Resource
  private RiskCreditService riskCreditService;

  @Before
  public void before() throws IOException, InvalidFormatException {
    String excelFile = "/home/tomalloc/data.xlsx";
    int sheetIndex = 1;
    InputStream inputStream = new FileInputStream(excelFile);
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    try {
      int rowCount = sheet.getPhysicalNumberOfRows();
      for (int i = 1; i < rowCount; i++) {
        Row row = sheet.getRow(i);
        CreditRequestParam bean = new CreditRequestParam();
        Cell id = row.getCell(1);
        String idStr = null;
        if (id.getCellType() == Cell.CELL_TYPE_NUMERIC) {
          idStr = (long) id.getNumericCellValue() + "";
          bean.setIdNumber(idStr);
        } else if (id.getCellType() == Cell.CELL_TYPE_STRING) {
          idStr = id.getStringCellValue();
          bean.setIdNumber(idStr);
        }
        bean.setName(row.getCell(0).getStringCellValue());
        Cell mobile = row.getCell(2);
        String mobileStr = null;
        if (mobile.getCellType() == Cell.CELL_TYPE_NUMERIC) {
          mobileStr = (long) mobile.getNumericCellValue() + "";
          bean.setMobile(mobileStr);
        } else if (mobile.getCellType() == Cell.CELL_TYPE_STRING) {
          mobileStr = mobile.getStringCellValue();
          bean.setMobile(mobileStr);
        }
        Cell bankCard = row.getCell(3);
        String cardStr = null;
        if (bankCard.getCellType() == Cell.CELL_TYPE_NUMERIC) {
          cardStr = (long) bankCard.getNumericCellValue() + "";
          bean.setBankCard(cardStr);
        } else if (bankCard.getCellType() == Cell.CELL_TYPE_STRING) {
          cardStr = bankCard.getStringCellValue();
          bean.setBankCard(cardStr);
        }
        requestParams.add(bean);
      }
    } finally {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    inputStream.close();
  }

  @Test
  public void queryTest() throws IOException {
    String savePath = "/home/tomalloc/test1.xlsx";
    //List<CreditRequestParam> requestParams = new ArrayList<>();
    //CreditRequestParam param = new CreditRequestParam();
    //param.setName("陈津威");
    //param.setIdNumber("330226199001155277");
    //param.setMobile("13777265718");
    //param.setBankCard("6214831216219755");

    //requestParams.add(param);
    Table<Integer, ReportItem, Object> table = riskCreditService.query(requestParams);
    System.out.println(table);
    Workbook wb = new XSSFWorkbook();
    Sheet sheet = wb.createSheet();

    short currentRow = 0;
    Row row0 = sheet.createRow(currentRow);

    currentRow++;
    Row row1 = sheet.createRow(currentRow);

    ReportItem[] columns = table.columnKeySet().toArray(new ReportItem[] {});
    for (int i = 0; i < columns.length; i++) {
      ReportItem item = columns[i];
      Cell cell0 = row0.createCell(i);
      if (item.getCreditProduct() != null) {
        cell0.setCellValue(item.getCreditProduct().title());
      }
      Cell cell1 = row1.createCell(i);
      cell1.setCellValue(item.getName());
    }

    currentRow++;
    Set<Integer> rowSet = table.rowKeySet();
    for (Integer rowKey : rowSet) {
      Row row = sheet.createRow(currentRow);
      for (int i = 0; i < columns.length; i++) {
        Cell cell = row.createCell(i);
        Object o = table.get(rowKey, columns[i]);
        if (o != null) {
          cell.setCellValue(o.toString());
        }
      }
      currentRow++;
    }

    FileOutputStream fileOutputStream = new FileOutputStream(savePath);
    wb.write(fileOutputStream);
    wb.close();
    if (fileOutputStream != null) {
      fileOutputStream.close();
    }
  }
}
