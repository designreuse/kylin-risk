package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.AbnorIndustryBiz;
import com.rkylin.risk.core.entity.AbnorIndustry;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/27 version: 1.0
 */
@Component
@Slf4j
public class AbnorIndustryBizImpl implements AbnorIndustryBiz {

  @Override
  public Map uploadFile(String filename) {
    Map map = new HashMap();
    List errorlist = new ArrayList();
    String targetDirectory = "D:/upload/";
    String suffix = filename.substring(filename.lastIndexOf('.') + 1);
    File target = new File(targetDirectory, filename);
    List<AbnorIndustry> abnorIndustryList = new ArrayList();
    Workbook wb = null;
    try( FileInputStream fi = new FileInputStream(target)) {
      if ("xls".equalsIgnoreCase(suffix)) {
        wb = new HSSFWorkbook(fi);
      } else if ("xlsx".equalsIgnoreCase(suffix)) {
        wb = new XSSFWorkbook(fi);
      }
      Sheet sheet = wb.getSheetAt(0);
      int rowNum = sheet.getLastRowNum() + 1;
      Row row1 = sheet.getRow(0);
      if (row1 != null && row1.getCell(2) != null && "类型".equals(row1.getCell(2).toString())) {
        int[] flag = {1, 1, 1};
        for (int i = 1; i < rowNum; i++) {
          boolean status = true;
          Row row = sheet.getRow(i);
          Cell cell0 = row.getCell(0);
          if (cell0 == null) {
            break;
          }
          if (row != null) {
            short cellNum = 3;
            AbnorIndustry abnorIndustry = new AbnorIndustry();
            for (short j = 0; j < cellNum; j++) {
              Cell cell = row.getCell(j);
              String cellValue = null;
              String col = Integer.toString(j + 65);
              if (flag[j] == 1 && (cell == null
                  || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
                status = false;
                errorlist.add("第" + (i + 1) + "行第" + (j + 1) + "列(" + (col + (i + 1)) + ")不能为空");
                break;
              } else if (cell != null) {
                if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                  cellValue = "";
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                  // 是否为字符串型
                  cellValue = String.valueOf(cell.getStringCellValue());
                } else {
                  status = false;
                  errorlist.add("第["
                      + (i + 1)
                      + "]行"
                      + "第 ["
                      + (j + 1)
                      + "]列"
                      + "["
                      + (col + (i + 1))
                      + "] "
                      + "：数据类型不匹配");
                }
              } else {
                cellValue = "";
              }
              switch (j) {
                case 0:
                  if ("行业代码".equals(row1.getCell(0).toString())) {
                    abnorIndustry.setCodeid(cellValue);
                  }
                  break;

                case 1:
                  if ("行业名称".equals(row1.getCell(1).toString())) {
                    abnorIndustry.setCodenm(cellValue);
                  }
                  break;
                case 2:
                  if ("类型".equals(row1.getCell(2).toString())) {
                    abnorIndustry.setType(cellValue);
                  }
                  break;
                default:
                  throw new UnsupportedOperationException();
              }
            }
            if (status) {
              abnorIndustryList.add(abnorIndustry);
            }
          }
        }
      } else {
        errorlist.add("上传文件列数与模板规定列数不符，请按照模板填写数据");
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    map.put("errorlist", errorlist);
    map.put("abnorIndustryList", abnorIndustryList);
    return map;
  }
}
