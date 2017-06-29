package com.rkylin.risk.credit;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto;
import com.rkylin.risk.commons.entity.ExternalCreditResult;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import com.rkylin.risk.credit.biz.CreditResultPersistent;
import com.rkylin.risk.credit.service.CreditRequestParam;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tomalloc on 16-8-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:risk-credit-core-test.xml"})
public class JsonTest {
  private List<CreditRequestParam> requestParams = new ArrayList<>();

  @Resource
  private CreditResultPersistent creditResultPersistent;

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
  public void test() throws ParseException {
    //12
    Gson gson = new Gson();
    //13339
    Stopwatch stopWatch = Stopwatch.createStarted();
    for (CreditRequestParam requestParam : requestParams) {
      ExternalCreditResult
          dbCeditResult =
          creditResultPersistent.queryCommonCreditResult(requestParam.getIdNumber(),
              CreditProductType.PY, "19121");
      String json = dbCeditResult.getResult();
      String data = JSONObject.fromObject(json)
          .getString("com.rkylin.gateway.dto.personHistoryQuery.PersonHistoryQueryRespDto");
      JSONReader jsonReader = new JSONReader(new StringReader(data));
      PersonHistoryQueryRespDto respDto = gson.fromJson(data, PersonHistoryQueryRespDto.class);
      PersonHistoryQueryRespDto parse = JSON.parseObject(data, PersonHistoryQueryRespDto.class);
    }
    stopWatch.stop();
    System.out.println(stopWatch.elapsed(TimeUnit.MILLISECONDS));
  }
}
