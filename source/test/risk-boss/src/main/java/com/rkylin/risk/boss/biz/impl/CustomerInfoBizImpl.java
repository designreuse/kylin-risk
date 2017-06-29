package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.CustomerInfoBiz;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Customerinfo;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.boss.util.ImportExcelUtis.readValue;

/**
 * Created by cuixiaofang on 2016-6-14.
 */
@Component
@Slf4j
public class CustomerInfoBizImpl implements CustomerInfoBiz {

  @Override
  public Map uploadFile(String filename) {
    Map map = new HashMap();
    List errorlist = new ArrayList();
    String targetDirectory = "D:/upload/";
    String suffix = filename.substring(filename.lastIndexOf('.') + 1);
    File target = new File(targetDirectory, filename);
    List<Customerinfo> customerList = new ArrayList<>();
    Workbook wb = null;
    try(FileInputStream fi = new FileInputStream(target)) {
      if ("xls".equalsIgnoreCase(suffix)) {
        wb = new HSSFWorkbook(fi);
      } else if ("xlsx".equalsIgnoreCase(suffix)) {
        wb = new XSSFWorkbook(fi);
      }
      Sheet sheet = wb.getSheetAt(0);
      //遍历所有单元格，读取单元格
      int rowNum = sheet.getLastRowNum() + 1;
      Row row1 = sheet.getRow(0);
      if (row1 != null && row1.getCell(2) != null
          && "证件类型".equals(row1.getCell(2).toString())) {
        for (int i = 1; i < rowNum; i++) {
          Row row = sheet.getRow(i);
          Customerinfo customerinfo = new Customerinfo();
          if ("客户号".equals(row1.getCell(0).toString())) {
            customerinfo.setCustomerid(readValue(row.getCell(0)));
          }
          if ("客户名称".equals(row1.getCell(1).toString())) {
            customerinfo.setCustomername(readValue(row.getCell(1)));
          }
          if ("证件类型".equals(row1.getCell(2).toString())) {
            customerinfo.setCertificatetype(readValue(row.getCell(2)));
          }
          if ("证件号码".equals(row1.getCell(3).toString())) {
            customerinfo.setCertificateid(readValue(row.getCell(3)));
          }
                    /*if ("融资年限".equals(row1.getCell(4).toString())) {
                        customerinfo.setFinancingtime((int) Double.parseDouble(
                                readValue(row.getCell(4))));
                    }*/
          if ("来源渠道".equals(row1.getCell(5).toString())) {
            customerinfo.setChannel(readValue(row.getCell(5)));
          }
          if ("分数".equals(row1.getCell(6).toString())) {
            customerinfo.setScore(new Amount(readValue(row.getCell(6))));
          }
          customerList.add(customerinfo);
        }
      } else {
        errorlist.add("上传文件列数与模板规定列数不符，请按照模板填写数据");
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    map.put("errorlist", errorlist);
    map.put("customerList", customerList);
    return map;
  }

  //判断是否为整数
  public static boolean isEqual2(String num) {
    try {
      Double.valueOf(num);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
