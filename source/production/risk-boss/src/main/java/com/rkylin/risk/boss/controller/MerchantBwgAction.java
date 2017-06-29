package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.MerchantbwgList;
import com.rkylin.risk.core.service.MerchantbwgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/14.
 */

@Controller
@RequestMapping("mercbwg")
public class MerchantBwgAction {

    @Resource
    private MerchantbwgService merchantbwgService;

    @RequestMapping("toQueryMercbwg")
    public ModelAndView tomerchantbwgQuery(HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        String flag = "";
        for (int i = 0; i < auth.getRoles().size(); i++) {
            if (auth.getRoles().get(i).toString().contains(Constants.RISK_CHECK_USER)) {
                flag = "01";
                break;
            } else if (auth.getRoles().get(i).toString().contains(Constants.RISK_OPER)) {
                flag = "02";
                break;
            } else {
                flag = "03";
            }
        }
        ModelAndView mv = new ModelAndView("merchantbwglist/merchantbwglistQuery");
        mv.addObject("flag", flag);
        return mv;
    }


    @RequestMapping("toAjaxQueryMerBWG")
    public ModelAndView toAddMerchantbwg(String type) {
        ModelAndView modelAndView = new ModelAndView("merchantbwglist/merchantbwglistAdd");
        modelAndView.addObject("type", type);
        return modelAndView;
    }

    @RequestMapping("_toModifyMerbwg")
    public ModelAndView toCustbwgModify(String id) {
        ModelAndView modelAndView = new ModelAndView("merchantbwglist/merchantbwgModify");
        MerchantbwgList bwg = merchantbwgService.queryById(Integer.valueOf(id));
        modelAndView.addObject("merchantbwglist", bwg);
        return modelAndView;
    }

    @RequestMapping("_toAddMerBwg")//添加页面 弹出框填写名单有效期
    public ModelAndView toAddMerBwgs(String ids, String type) {
        ModelAndView modelAndView = new ModelAndView("merchantbwglist/merchantbwgConfirmAdd");
        modelAndView.addObject("ids", ids);
        modelAndView.addObject("type", type);
        return modelAndView;
    }


}
