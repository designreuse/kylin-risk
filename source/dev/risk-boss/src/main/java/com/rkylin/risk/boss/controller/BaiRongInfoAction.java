package com.rkylin.risk.boss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author wanglingzhi
 * @Date 2016-12-13 15:58
 */
@RequestMapping("bairong")
@Controller
public class BaiRongInfoAction {
    @RequestMapping("toQueryBaiRong")
    public ModelAndView toQueryBaiRong() {
        return new ModelAndView("bairong/bairongQuery");
    }


}
