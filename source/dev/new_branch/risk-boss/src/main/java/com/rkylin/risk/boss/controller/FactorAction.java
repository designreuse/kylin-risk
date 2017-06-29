package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.dto.DictionaryBean;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.entity.ModuelsChannel;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.FactorService;
import com.rkylin.risk.core.service.ModuelsChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 201508240185 on 2015/9/29.
 */
@Controller
@RequestMapping("factor")
public class FactorAction {
  @Resource
  private FactorService factorService;
  @Resource
  private DictionaryService dictionaryService;
  @Resource
  private ModuelsChannelService moduelsChannelService;

  @RequestMapping("toCustFactorQuery")
  public ModelAndView toCustFactorQuery() {
    return new ModelAndView("factor/custFactorQuery");
  }

  @RequestMapping("custFactorQuery")
  public ModelAndView loadMenu() {
    String sbf = factorService.findFactorInTree("00", "01");
    sbf = "[" + sbf + "]";
    ModelAndView view = new ModelAndView("factor/custFactorQueryResult");
    view.addObject("factorTreeJSONString", sbf);
    return view;
  }

  @RequestMapping("toModifyCustFactor")
  public ModelAndView toMenuModify(String id) {
    ModelAndView view = new ModelAndView("factor/custFactorModify");
    Factor factor = factorService.findById(Integer.parseInt(id));
    Factor father = null;
    if (factor.getFactorid() != null) {
      father = factorService.findById(factor.getFactorid());
    }
    String addType = getOperatorType(father);

    //查询设置模型渠道
    if ("MODEL".equals(addType)) {
      //已存在的模型渠道关系set
      Set<String> cset = new HashSet();
      //已存在的模型产品关系set
      Set<String> mset = new HashSet();
      //查找模型相关的渠道产品关系，并添加相应的set中
      List<ModuelsChannel> channels = moduelsChannelService.queryByModuel(factor.getModuletype());
      if (channels != null) {
        for (ModuelsChannel mod : channels) {
          if (mod.getChannelcode() != null) {
            cset.add(mod.getChannelcode());
          }
          if (mod.getMercid() != null) {
            mset.add(mod.getMercid());
          }
        }
      }

      //查找所有的渠道产品，并设置是否与模型相关联
      List<DictionaryBean> list = new ArrayList<DictionaryBean>();
      List<DictionaryCode> chans = dictionaryService.queryByDictCode("channel");
      for (DictionaryCode channel : chans) {
        DictionaryBean bean = new DictionaryBean();
        //如果渠道存在cset中，则设置1
        if (cset.contains(channel.getCode())) {
          channel.setDictname("1");
        }
        bean.setDictionaryCode(channel);

        List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
        if (mercs != null) {
          for (DictionaryCode merc : mercs) {
            //如果商户存在mset中，则设置1
            if (mset.contains(merc.getCode())) {
              merc.setDictname("1");
            }
          }
        }
        bean.setDictionaryCodes(mercs);

        list.add(bean);
      }
      view.addObject("list", list);
    }

    view.addObject("factor", factor);
    view.addObject("parentNodeName", father == null ? "" : father.getName());
    view.addObject("addType", addType);
    return view;
  }

  @RequestMapping("toAddCustFactor")
  public ModelAndView toAddMenu(String targetParentId) {
    ModelAndView modelAndView = new ModelAndView();
    Factor factor = factorService.findById(Short.valueOf(targetParentId));
    String addType = getOperatorType(factor);
    if ("MODEL".equals(addType)) {
      List<DictionaryBean> list = new ArrayList<DictionaryBean>();
      List<DictionaryCode> channels = dictionaryService.queryByDictCode("channel");
      for (DictionaryCode channel : channels) {
        DictionaryBean bean = new DictionaryBean();
        bean.setDictionaryCode(channel);

        List<DictionaryCode> mercs = dictionaryService.queryByDictCode(channel.getCode());
        bean.setDictionaryCodes(mercs);

        list.add(bean);
      }
      modelAndView.addObject("list", list);
    }

    modelAndView.addObject("parent", factor);
    modelAndView.addObject("addType", addType);
    modelAndView.setViewName("factor/custFactorAdd");
    return modelAndView;
  }

  @RequestMapping("addCustFactor")
  public ModelAndView addMenu(@ModelAttribute Factor factor) {
    ModelAndView view = new ModelAndView("factor/custFactorAddSuccess");
    view.addObject("factor", factor);
    return view;
  }

  @RequestMapping("modifyCustFactor")
  public ModelAndView menuModify(@ModelAttribute Factor factor) {
    if (factor != null) {
      factorService.updateFactor(factor);
    }
    ModelAndView view = new ModelAndView("factor/custFactorAddSuccess");
    view.addObject("factor", factor);
    return view;
  }

  public String getOperatorType(Factor factor) {
    String addType = "";

    if (factor == null || ("1".equals(factor.getFactorlevel()) && !"transfactor".equals(
        factor.getCode()))) {
      //父节点级别为1，且是客户风险评分或账户风险评分，添加风险因子
      addType = "FACTOR";
    } else if ("1".equals(factor.getFactorlevel()) && "transfactor".equals(factor.getCode())) {
      //父节点级别为1，且交易风险评分，添加风险模型
      addType = "MODEL";
    } else if ("2".equals(factor.getFactorlevel()) && StringUtils.isEmpty(factor.getModuletype())) {
      //父节点级别为2，且风险模型为空，添加因子描述
      addType = "DESC";
    } else if ("2".equals(factor.getFactorlevel()) && !StringUtils.isEmpty(
        factor.getModuletype())) {
      //父节点级别为2，且风险模型不为空，添加风险因子
      addType = "FACTOR";
    } else if ("3".equals(factor.getFactorlevel())) {
      //父节点级别为3，添加因子描述
      addType = "DESC";
    }

    return addType;
  }
}
