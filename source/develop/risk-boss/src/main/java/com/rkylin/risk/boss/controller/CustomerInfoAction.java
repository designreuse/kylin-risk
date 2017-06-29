package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.CustomerinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerInfoAction {
    @Resource
    private CustomerinfoService customerinfoService;

    /**
     * 进入查询客户信息页面
     */
    @RequestMapping("toQueryCustomer")
    public ModelAndView queryCustomer() {
        return new ModelAndView("customer/customerQuery");
    }

    /**
     * 按条件查询客户信息
     */
    @RequestMapping("queryCustomer")
    public ModelAndView queryAll(@ModelAttribute Customerinfo customer) {
        List<Customerinfo> customerList = customerinfoService.queryAll(customer);
        ModelAndView view = new ModelAndView("customer/customerQuery");
        view.addObject(customerList);
        return view;
    }

    @RequestMapping("customerDetail")
    public ModelAndView queryDetail(@RequestParam String ids) {
        Customerinfo customerinfo = customerinfoService.queryOne(ids);
        ModelAndView view = new ModelAndView("customer/customerDetail");
        view.addObject(customerinfo);
        return view;
    }

    @RequestMapping("_toImportCustomer")
    public ModelAndView toImportCustomer() {
        return new ModelAndView("customer/customerImport");
    }
}
