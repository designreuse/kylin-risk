package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.AbnorCountryBiz;
import com.rkylin.risk.core.entity.AbnormalCountrycode;
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
public class AbnorCountryBizImpl implements AbnorCountryBiz {

  @Override
  public Map uploadFile(String filename) {
    Map map = new HashMap();
    List errorlist = new ArrayList();
    String targetDirectory = "D:/upload/";
    String suffix = filename.substring(filename.lastIndexOf('.') + 1);
    File target = new File(targetDirectory, filename);
    List<AbnormalCountrycode> abnormalCountrycodeList = new ArrayList();
    Workbook wb = null;
    try (FileInputStream  fi = new FileInputStream(target)) {
      if ("xls".equalsIgnoreCase(suffix)) {
        wb = new HSSFWorkbook(fi);
      } else if ("xlsx".equalsIgnoreCase(suffix)) {
        wb = new XSSFWorkbook(fi);
      }
      Sheet sheet = wb.getSheetAt(0);
      int rowNum = sheet.getLastRowNum() + 1;
      Row row1 = sheet.getRow(0);
      if (row1 != null && row1.getCell(7) != null && "类型".equals(row1.getCell(7).toString())) {
        int[] flag = {1, 1, 1, 1, 1, 1, 1, 1};
        for (int i = 1; i < rowNum; i++) {
          boolean status = true;
          Row row = sheet.getRow(i);
          Cell cell0 = row.getCell(0);
          if (cell0 == null) {
            break;
          }
          if (row != null) {
            short cellNum = 8;
            AbnormalCountrycode abnormalCountrycode = new AbnormalCountrycode();
            for (short j = 0; j < cellNum; j++) {
              Cell cell = row.getCell(j);
              String cellValue = null;
              String col = Integer.toString(j + 65);
              /*col = (char) (j + 65) + "";*/
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
                  cellValue = String.valueOf(cell.getStringCellValue()).trim();
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
                  if ("中文全称".equals(row1.getCell(0).toString())) {
                    abnormalCountrycode.setCnnm(cellValue);
                  }
                  break;

                case 1:
                  if ("中文简称".equals(row1.getCell(1).toString())) {
                    abnormalCountrycode.setCnabb(cellValue);
                  }
                  break;

                case 2:
                  if ("英文全称".equals(row1.getCell(2).toString())) {
                    abnormalCountrycode.setEnnm(cellValue);
                  }
                  break;
                case 3:
                  if ("英文简称".equals(row1.getCell(3).toString())) {
                    abnormalCountrycode.setEnabb(cellValue);
                  }
                  break;
                case 4:
                  if ("两位字母".equals(row1.getCell(4).toString())) {
                    abnormalCountrycode.setTwoletter(cellValue);
                  }
                  break;
                case 5:
                  if ("三位字母".equals(row1.getCell(5).toString())) {
                    abnormalCountrycode.setThreelteeer(cellValue);
                  }
                  break;
                case 6:
                  if ("三位数字".equals(row1.getCell(6).toString())) {
                    abnormalCountrycode.setThreenum(cellValue);
                  }
                  break;
                case 7:
                  if ("类型".equals(row1.getCell(7).toString())) {
                    abnormalCountrycode.setType(cellValue);
                  }
                  break;
                default:
                  throw new UnsupportedOperationException();
              }
            }
            if (status) {
              abnormalCountrycodeList.add(abnormalCountrycode);
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
    map.put("abnormalCountrycodeList", abnormalCountrycodeList);
    return map;
  }
}
