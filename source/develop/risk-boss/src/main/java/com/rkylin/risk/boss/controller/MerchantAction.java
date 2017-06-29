package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by cuixiaofang on 2016-6-15.
 */
@Controller
@RequestMapping("merchant")
public class MerchantAction {
    @Resource
    private MerchantService merchantService;

    @RequestMapping("toQueryMerchant")
    public ModelAndView toQueryMerchant() {
        return new ModelAndView("merchant/merchantQuery");
    }

    @RequestMapping("queryMerchant")
    public ModelAndView queryAll(@ModelAttribute Merchant merchant) {
        ModelAndView modelAndView = new ModelAndView("merchant/merchantQuery");
        return modelAndView;
    }

    @RequestMapping("_toImportMerchant")
    public ModelAndView importCustomer() {
        ModelAndView modelAndView = new ModelAndView("merchant/importMerchant");
        return modelAndView;
    }

    @RequestMapping("merchantDetail")
    public ModelAndView merchantDetail(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView("merchant/merchantDetail");
        Merchant merchant = merchantService.queryByMerchantid(id);
        modelAndView.addObject(merchant);
        return modelAndView;
    }
}
