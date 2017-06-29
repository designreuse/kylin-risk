package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.biz.OperatorBiz;
import com.rkylin.risk.boss.util.PasswordHash;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.DictionaryBean;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.OperatorRole;
import com.rkylin.risk.core.entity.Role;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.OperatorService;
import com.rkylin.risk.core.service.RoleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by 201506290344 on 2015/8/14.
 */
@Controller
@RequestMapping("operator")
public class OperatorAction {
    @Resource
    private OperatorService operatorService;
    @Resource
    private RoleService roleService;

    @Resource
    private OperatorBiz operatorBiz;
    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping("toQueryOperator")
    public ModelAndView toQueryOperator() {
        ModelAndView modelAndView = new ModelAndView("operator/operatorQuery");
        return modelAndView;
    }

    @RequestMapping("toAjaxAddOperator")
    public ModelAndView toAddOperator() {
        ModelAndView modelAndView = new ModelAndView("operator/operatorAdd");
        List<Role> list = roleService.queryAll();
        modelAndView.addObject("rolelist", list);

        //渠道和产品的显示
        List<DictionaryBean> listdb = new ArrayList<DictionaryBean>();
        List<DictionaryCode> channels = dictionaryService.queryByDictCode("channel");
        for (DictionaryCode channel : channels) {
            DictionaryBean bean = new DictionaryBean();
            bean.setDictionaryCode(channel);
            List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
            bean.setDictionaryCodes(mercs);

            listdb.add(bean);
        }
        modelAndView.addObject("list", listdb);
        return modelAndView;
    }

    @RequestMapping("toAjaxOperatorDetail")
    public ModelAndView operatorDetail(String operid) {
        ModelAndView modelAndView = new ModelAndView("operator/operatorModify");
        Operator oper = operatorService.queryById(operid);
        List<Role> list = roleService.queryAll();
        List<OperatorRole> operRolelist = operatorService.queryByOperid(Short.valueOf(operid));
        //List<Role> newList = new ArrayList<Role>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < operRolelist.size(); j++) {
                if (list.get(i).getId().equals(operRolelist.get(j).getRoleid())) {
                    //借用remark属性在前台页面做判断
                    list.get(i).setRemark("checked");
                }
            }
            //newList.add(list.get(i));
        }

        //获取操作员已经包含的产品
        Set<String> proSet = new HashSet<String>();
        String productId = null;
        if (oper.getProducts() != null && oper.getProducts().length() > 0) {
            String[] products = oper.getProducts().split(",");
            Collections.addAll(proSet, products);
            //判断产品ID字段是否包含-1
            if (Arrays.asList(products).contains(Constants.ALL_PRODUCT)) {
                productId = Constants.ALL_PRODUCT;
            }
        }

        //查找所有的渠道，并设置是否与模型相关联
        List<DictionaryBean> dicList = new ArrayList<DictionaryBean>();
        List<DictionaryCode> chans = dictionaryService.queryByDictCode("channel");
        for (DictionaryCode channel : chans) {
            DictionaryBean bean = new DictionaryBean();

            bean.setDictionaryCode(channel);

            List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
            if (mercs != null) {
                for (DictionaryCode merc : mercs) {
                    //如果商户存在mset中，则设置1
                    if (proSet.contains(merc.getCode())) {
                        merc.setDictname("1");
                        //子节点选中则父节点自动选中
                        channel.setDictname("1");
                    }
                }
            }
            bean.setDictionaryCodes(mercs);
            dicList.add(bean);
        }

        modelAndView.addObject("list", dicList);
        modelAndView.addObject("newList", list);
        modelAndView.addObject("operator", oper);
        modelAndView.addObject("productId", productId);
        return modelAndView;
    }

    @RequestMapping("toModifyPassWd")
    public ModelAndView toModifyPassWdPage() {
        return new ModelAndView("operator/passwdModify");
    }

    @RequestMapping("toModifyPwd")
    public ModelAndView toModifyPassWd(@RequestParam String modifyType,
                                       @RequestParam String username) {
        ModelAndView view = new ModelAndView("pwdexpupdate");
        view.addObject("modifyType", modifyType);
        view.addObject("username", username);
        return view;
    }

    @RequestMapping("pwdupdate")
    public ModelAndView pwdUpdate(@ModelAttribute Operator oper, @RequestParam String modifyType)
        throws Exception {
        ModelAndView view = null;
        Operator operatorO = operatorService.queryOperatorByUsername(oper.getUsername());
        oper.setId(operatorO.getId());
        oper.setPasswd(PasswordHash.createHash(oper.getPasswd()));
        oper.setPasswdexpdate(LocalDate.now().plusDays(Constants.PWDEXPDATE));
        operatorService.updatePassWord(oper);
        if ("first".equals(modifyType)) {
            view = new ModelAndView("main");
        }
        if ("invalid".equals(modifyType)) {
            view = new ModelAndView("login");
        }
        return view;
    }
}
