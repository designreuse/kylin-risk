package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.dto.DictionaryBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.GroupChannel;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.GroupChannelService;
import com.rkylin.risk.core.service.GroupService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lina on 2016-7-11.
 */
@Controller
@RequestMapping("group")
public class GroupAction {
  private static final String CHANNEL = "channel";
  @Resource
  private GroupService groupService;
  @Resource
  private GroupChannelService groupChannelService;
  @Resource
  private DictionaryService dictionaryService;

  @RequestMapping("toQueryGroup")
  public ModelAndView toQueryGroup(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("group/groupQuery");
    view.addObject("rolename", RuleAction.getRuleManageRole(auth));
    return view;
  }

  @RequestMapping("toAjaxAddGroup")
  public ModelAndView toAddGroup(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    ModelAndView view = new ModelAndView("group/groupAdd");
    List<DictionaryBean> list = new ArrayList<>();
    String rolename = RuleAction.getRuleManageRole(auth);
    if ("SUPERMODIFIER".equals(rolename)) {
      List<DictionaryCode> channels = dictionaryService.queryByDictCode(CHANNEL);
      for (DictionaryCode channel : channels) {
        DictionaryBean bean = new DictionaryBean();
        bean.setDictionaryCode(channel);
        List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
        bean.setDictionaryCodes(mercs);

        list.add(bean);
      }
    } else if ("MODIFIER".equals(rolename)) {
      List<DictionaryCode> products =
          dictionaryService.queryConnByDicAndCode(CHANNEL, auth.getProducts());
      DictionaryCode channel = null;
      if (!products.isEmpty()) {
        channel = dictionaryService.queryByDictAndCode(CHANNEL,
            products.get(0).getDictcode());
      }
      DictionaryBean bean = new DictionaryBean();
      bean.setDictionaryCode(channel);
      bean.setDictionaryCodes(products);
      list.add(bean);
    }
    view.addObject("list", list);
    return view;
  }

  @RequestMapping("toAjaxqueryGroupDetail")
  public ModelAndView queryGroupDetail(@RequestParam String id, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("group/groupModify");
    Group group = groupService.queryById(Short.parseShort(id));
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String rolename = RuleAction.getRuleManageRole(auth);
    //已存在的模型渠道关系set
    Set<String> pset = new HashSet();
    List<GroupChannel> groupChannels = groupChannelService.queryByGroupid(group.getId());
    if (groupChannels != null) {
      for (GroupChannel groupChannel : groupChannels) {
        pset.add(groupChannel.getProductcode());
      }
    }
    List<DictionaryBean> dicList = new ArrayList<>();
    if ("SUPERMODIFIER".equals(rolename)) {
      //查找所有的渠道，并设置是否与模型相关联
      List<DictionaryCode> chans = dictionaryService.queryByDictCode(CHANNEL);
      for (DictionaryCode channel : chans) {
        DictionaryBean bean = new DictionaryBean();
        bean.setDictionaryCode(channel);
        List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
        if (mercs != null) {
          for (DictionaryCode merc : mercs) {
            //如果商户存在mset中，则设置1
            if (pset.contains(merc.getCode())) {
              merc.setDictname("1");
            }
          }
        }
        bean.setDictionaryCodes(mercs);
        dicList.add(bean);
      }
    } else if ("MODIFIER".equals(rolename)) {
      List<DictionaryCode> products =
          dictionaryService.queryConnByDicAndCode(CHANNEL, auth.getProducts());
      DictionaryBean bean = new DictionaryBean();
      if (products != null) {
        for (DictionaryCode merc : products) {
          //如果商户存在mset中，则设置1
          if (pset.contains(merc.getCode())) {
            merc.setDictname("1");
          }
        }
        DictionaryCode channel = null;
        if (!products.isEmpty()) {
          channel = dictionaryService.queryByDictAndCode(CHANNEL,
              products.get(0).getDictcode());
        }
        bean.setDictionaryCode(channel);
        bean.setDictionaryCodes(products);
      }

      dicList.add(bean);
    }
    view.addObject("list", dicList);
    view.addObject("rolename", rolename);
    view.addObject("group", group);

    return view;
  }
}
