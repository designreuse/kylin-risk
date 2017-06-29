package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.AbnormalAreaBiz;
import com.rkylin.risk.core.entity.AbnormalArea;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/27 version: 1.0
 */
@Component
@Slf4j
public class AbnormalAreaBizImpl implements AbnormalAreaBiz {
  @Override
  public Map uploadFile(String filename) {
    Map map = new HashMap();
    List errorlist = new ArrayList();
    String targetDirectory = "D:/upload/";
    String suffix = filename.substring(filename.lastIndexOf(".") + 1);
    File target = new File(targetDirectory, filename);
    List<AbnormalArea> abnormalAreaList = new ArrayList();
    Workbook wb = null;
    try {
      FileInputStream fi = new FileInputStream(target);
      if ("xls".equalsIgnoreCase(suffix)) {
        wb = new HSSFWorkbook(fi);
      } else if ("xlsx".equalsIgnoreCase(suffix)) {
        wb = new XSSFWorkbook(fi);
      }
      Sheet sheet = wb.getSheetAt(0);
      int rowNum = sheet.getLastRowNum() + 1;
      Row row1 = sheet.getRow(0);
      if (row1 != null && row1.getCell(5) != null && "所属反洗钱组织名单类型".equals(
          row1.getCell(5).toString())) {
        int i = 1;
        short j = 0;
        int[] flag = {1, 1, 1, 1, 1, 1};
        for (i = 1; i < rowNum; i++) {
          boolean status = true;
          Row row = sheet.getRow(i);
          Cell cell0 = row.getCell(0);
          if (cell0 == null) {
            break;
          }
          if (row != null) {
            short cellNum = 6;
            AbnormalArea abnormalArea = new AbnormalArea();
            for (j = 0; j < cellNum; j++) {
              Cell cell = row.getCell(j);
              String cellValue = null;
              String col = "";
              col = Integer.toString(j + 65);
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
                  if ("地区代码".equals(row1.getCell(0).toString())) {
                    abnormalArea.setCode(cellValue);
                  }
                  break;

                case 1:
                  if ("所在省".equals(row1.getCell(1).toString())) {
                    abnormalArea.setProvnm(cellValue);
                  }
                  break;

                case 2:
                  if ("所在市".equals(row1.getCell(2).toString())) {
                    abnormalArea.setCitynm(cellValue);
                  }
                  break;
                case 3:
                  if ("所在县区".equals(row1.getCell(3).toString())) {
                    abnormalArea.setCountynm(cellValue);
                  }
                  break;
                case 4:
                  if ("地区名称".equals(row1.getCell(4).toString())) {
                    abnormalArea.setName(cellValue);
                  }
                  break;
                case 5:
                  if ("所属反洗钱组织名单类型".equals(row1.getCell(5).toString())) {
                    abnormalArea.setType(cellValue);
                  }
                  break;
                default:
                  throw new UnsupportedOperationException();
              }
            }
            if (status) {
              abnormalAreaList.add(abnormalArea);
            }
          }
        }
      } else {
        errorlist.add("上传文件列数与模板规定列数不符，请按照模板填写数据");
      }
      fi.close();
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    map.put("errorlist", errorlist);
    map.put("abnormalAreaList", abnormalAreaList);
    return map;
  }
}
