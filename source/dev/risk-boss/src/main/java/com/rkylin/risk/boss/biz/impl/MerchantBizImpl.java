package com.rkylin.risk.boss.biz.impl;

import com.rkylin.risk.boss.biz.MerchantBiz;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Merchant;
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
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.boss.util.ImportExcelUtis.readNumberValue;
import static com.rkylin.risk.boss.util.ImportExcelUtis.readValue;

/**
 * Created by cuixiaofang on 2016-6-15.
 */
@Component
@Slf4j
public class MerchantBizImpl implements MerchantBiz {
    @Override
    public Map uploadFile(String filename) {
        Map map = new HashMap();
        List errorlist = new ArrayList();
        String targetDirectory = "D:/upload/";
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        File target = new File(targetDirectory, filename);
        List<Merchant> merchantList = new ArrayList<>();
        Workbook wb = null;
        try (FileInputStream fi = new FileInputStream(target)){
            if ("xls".equalsIgnoreCase(suffix)) {
                wb = new HSSFWorkbook(fi);
            } else if ("xlsx".equalsIgnoreCase(suffix)) {
                wb = new XSSFWorkbook(fi);
            }
            Sheet sheet = wb.getSheetAt(0);
            //遍历所有单元格，读取单元格
            int rowNum = sheet.getLastRowNum() + 1;
            Row row1 = sheet.getRow(0);
            if (row1 != null && row1.getCell(0) != null
                    && "机构编号".equals(row1.getCell(0).toString())) {
                for (int i = 1; i < rowNum; i++) {
                    Row row = sheet.getRow(i);
                    Merchant merchant = new Merchant();
                    if ("企业编号".equals(row1.getCell(0).toString())
                            || "机构编号".equals(row1.getCell(0).toString())) {
                        merchant.setMerchantid(readValue(row.getCell(0)));
                    }
                    if ("企业名称".equals(row1.getCell(1).toString())
                            || "机构名称".equals(row1.getCell(1).toString())) {
                        merchant.setMerchantname(readValue(row.getCell(1)));
                    }
                    if ("年培训人次".equals(row1.getCell(2).toString())) {
                        merchant.setTrainnumyear(new Amount(readNumberValue(row.getCell(2))));
                    }
                    if ("年培训收入（万元）".equals(row1.getCell(3).toString())) {
                        merchant.setTrainincomeyear(new Amount(readNumberValue(row.getCell(3))));
                    }
                    if ("成立年限（年）".equals(row1.getCell(4).toString())) {
                        merchant.setFoundage(new Amount(readNumberValue(row.getCell(4))));
                    }
                    if ("教学总面积（㎡）".equals(row1.getCell(5).toString())) {
                        merchant.setAreateach(new Amount(readNumberValue(row.getCell(5))));
                    }
                    if ("授信额度（万元）".equals(row1.getCell(6).toString())) {
                        merchant.setCreditline(new Amount(readNumberValue(row.getCell(6))));
                    }
                    if ("授信期限".equals(row1.getCell(7).toString())) {
                        merchant.setCreditend(readValue(row.getCell(7)));
                    }
                    if ("其他审核意见（展示到前端）".equals(row1.getCell(8).toString())) {
                        merchant.setDescription(readValue(row.getCell(8)));
                    }
                    merchant.setCreatetime(DateTime.now());
//                    if ("企业到期日期".equals(row1.getCell(7).toString())) {
//                        merchant.setMerchduedate(readValueToDate(row.getCell(7)));
//                    }
//                    if ("营业起始日期".equals(row1.getCell(8).toString())) {
//                        merchant.setBizbegindate(readValueToDate(row.getCell(8)));
//                    }
//                    if ("营业终止日期".equals(row1.getCell(9).toString())) {
//                        merchant.setBizenddate(readValueToDate(row.getCell(9)));
//                    }
//                    if ("公司营业执照号".equals(row1.getCell(10).toString())) {
//                        merchant.setCorporationid(readValue(row.getCell(10)));
//                    }
//                    if ("法人证件号".equals(row1.getCell(11).toString())) {
//                        merchant.setOwnercertid(readValue(row.getCell(11)));
//                    }
//                    if ("企业状态".equals(row1.getCell(12).toString())) {
//                        merchant.setMerchantstatus(readValue(row.getCell(12)));
//                    }
//                    if ("组织机构代码".equals(row1.getCell(13).toString())) {
//                        merchant.setOrginstdid(readValue(row.getCell(13)));
//                    }
                    merchantList.add(merchant);
                }
            } else {
                errorlist.add("上传文件列数与模板规定列数不符，请按照模板填写数据");
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        map.put("errorlist", errorlist);
        map.put("merchantList", merchantList);
        return map;
    }
}
