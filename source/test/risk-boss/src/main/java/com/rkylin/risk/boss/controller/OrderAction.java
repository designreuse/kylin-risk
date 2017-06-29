package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.OrderScoreExportBean;
import com.rkylin.risk.core.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2016-4-10.
 */
@Controller
@RequestMapping("order")
@Slf4j
public class OrderAction {

    @Resource
    private OrderService orderService;

    @RequestMapping("toQueryOrder")
    public ModelAndView toQueryOrder() {
        return new ModelAndView("order/orderQueryAll");
    }

    @RequestMapping("exportOrderExcel")
    public void exportExcel(HttpServletResponse response, @ModelAttribute Order order,
                            String ordertimes, String ordertimee, String customnum,
                            String risklevel, String productall,
                            String[] products, String productnull) {
        response.setContentType(Constants.CONTENT_TYPE);
        try {
            response.addHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode("交易评分.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.info("coding exception", e);
        }

        /*DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime start = StringUtils.isEmpty(ordertimes) ? null
                : DateTime.parse(ordertimes, dtFormat);
        DateTime end = StringUtils.isEmpty(ordertimee) ? null
                : DateTime.parse(ordertimee, dtFormat);
         */

        List<Map> exportList = orderService.exportByCondition(order, ordertimes,
                ordertimee, risklevel, customnum,
                productall, products, productnull);

        String[] headers = {"交易日期", "内部订单号", "客户编号", "客户名称", "交易金额",
                "交易评分", "商品名称", "商品描述", "商品数量", "商品单价", "风险等级"};
        String[] filedNames = {"ordertime", "orderid", "userid", "customername",
                "amount", "score", "goodsname", "goodsdetail", "goodscnt",
                "unitprice", "risklevel"};

        List<OrderScoreExportBean> beanList = new ArrayList<>();
        if (exportList != null && !exportList.isEmpty()) {
            for (Map map : exportList) {
                OrderScoreExportBean bean = new OrderScoreExportBean();
                bean.setOrdertime((Timestamp) map.get("ordertime"));
                bean.setOrderid((String) map.get("orderid"));
                bean.setUserid((String) map.get("userid"));
                bean.setCustomername((String) map.get("customername"));
                bean.setAmount((BigDecimal) map.get("amount"));
                bean.setScore((BigDecimal) map.get("score"));
                bean.setGoodsname((String) map.get("goodsname"));
                bean.setGoodsdetail((String) map.get("goodsdetail"));
                bean.setGoodscnt((Integer) map.get("goodscnt"));
                bean.setUnitprice((BigDecimal) map.get("unitprice"));
                bean.setRisklevel((String) map.get("risklevel"));
                beanList.add(bean);
            }
        }

        ExportExcel<OrderScoreExportBean> export = new ExportExcel<>();
        try {
            OutputStream out = response.getOutputStream();
            export.exportExcel("交易评分", headers, beanList, filedNames, out, null);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage(), e);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }
}
