package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.service.DubioustxnService;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by v-wangwei on 2015/9/19 0019.
 */
@RestController
@RequestMapping("/api/1/dubioustxn")
public class DubioustxnRestAction {
    @Resource
    DubioustxnService dubioustxnService;

    @RequestMapping(value = "dealDubioustxn", method = RequestMethod.POST)
    public void dealDubioustxn(@RequestParam("ids") String ids, HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        Integer id = Integer.parseInt(ids);
        dubioustxnService.updateWarnstatus(Constants.WARN_STATUS_01, id, auth);
    }

    @RequestMapping(value = "queryDubioustxn", method = RequestMethod.GET)
    public List<Dubioustxn> queryDubioustxn(HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        Dubioustxn dubioustxn = new Dubioustxn();
        dubioustxn.setWarndate(new LocalDate());
        List<Dubioustxn> list = dubioustxnService.queryAllDubioustxnMap(dubioustxn, auth);
        return list;
    }

    @RequestMapping(value = "addCustomeBlackList", method = RequestMethod.POST)
    public void addCustomeBlackList(@RequestParam("id") String id, HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        dubioustxnService.addCustomeBlackList(Integer.valueOf(id), auth);
    }

    @RequestMapping(value = "addIdCardBlackList", method = RequestMethod.POST)
    public void addIdCardBlackList(@RequestParam("id") String id, HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        dubioustxnService.addIdCardBlackList(Integer.valueOf(id), auth);
    }

    @RequestMapping("_toAddCase")
    public ModelAndView toAddCase(String ids) {
        ModelAndView modelAndView = new ModelAndView("dubioustxn/caseAdd");
        modelAndView.addObject("ids", ids);
        return modelAndView;
    }

    @RequestMapping(value = "addCase", method = RequestMethod.POST)
    public void addCase(@ModelAttribute Case cases, String addIds, HttpServletRequest request) {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        dubioustxnService.addCase(cases, addIds, auth);
    }
}
