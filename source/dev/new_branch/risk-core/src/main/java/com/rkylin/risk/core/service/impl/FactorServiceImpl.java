package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.FactorDao;
import com.rkylin.risk.core.dao.ModuelChannelDao;
import com.rkylin.risk.core.dto.ChannelBean;
import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.entity.ModuelsChannel;
import com.rkylin.risk.core.service.FactorService;
import com.rkylin.risk.commons.entity.Amount;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508240185 on 2015/9/29.
 */
@Service("factorService")
public class FactorServiceImpl implements FactorService {
  @Resource
  private FactorDao factorDao;
  @Resource
  private ModuelChannelDao moduelChannelDao;

  @Override
  public List<Factor> queryFactorByType(String riskType, String customertype) {
    return factorDao.queryFactorByType(riskType, customertype);
  }

  @Override
  @Transactional
  public Factor addFactor(Factor factor, String riskType, String custType, Authorization auth,
      String addType, ChannelsBean channels) {

    if ("FACTOR".equals(addType)) {
      //添加风险因子时，转换权重
      factor.setWeight((factor.getWeight() == null) ? null
          : new Amount(factor.getWeight()).divide(100).toString());
    } else if ("DESC".equals(addType)) {
      //添加因子描述时，计算分值，并保存权重
      Factor father = factorDao.findById(factor.getFactorid());
      Factor grandfather = factorDao.findById(father.getFactorid());
      Amount weight =
          new Amount(father.getWeight()).multiply(Double.parseDouble(grandfather.getWeight()));
      factor.setAccount(weight.multiply(Double.parseDouble(factor.getScore())).toString());
      factor.setWeight(father.getWeight());
    } else if ("MODEL".equals(addType)) {
      //添加模型时

      //设置模型类型
      factor.setModuletype(factor.getCode());
      if (channels != null) {
        List<ChannelBean> list = channels.getChannels();
        //循环所有渠道
        for (ChannelBean channel : list) {
          List<String> mercs = channel.getMercCode();
          if (mercs == null || mercs.isEmpty()) {
            //如果渠道下面的商户为空，新增一条模型与渠道的关系
            ModuelsChannel moc = new ModuelsChannel();
            moc.setChannelcode(channel.getChannelCode());
            moc.setModuletype(factor.getModuletype());
            moc.setStatus("00");
            moduelChannelDao.insert(moc);
          } else {
            for (String mcod : mercs) {
              //渠道下的商户不为空，新建模型、渠道、商户关系
              ModuelsChannel moc = new ModuelsChannel();
              moc.setChannelcode(channel.getChannelCode());
              moc.setModuletype(factor.getModuletype());
              moc.setMercid(mcod);
              moc.setStatus("00");
              moduelChannelDao.insert(moc);
            }
          }
        }
      }
    }
    factor.setRisktype(riskType);
    factor.setCustomertype(custType);
    factor.setUserid(auth.getUserId());
    factor.setUsername(auth.getUsername());
    factor.setUpdatetime(DateTime.now());
    return factorDao.addFactor(factor);
  }

  @Override
  @Transactional
  public Factor modifyFactor(Factor factor, Authorization auth, String beforeWeight, String addType,
      ChannelsBean channels) {
    if ("DESC".equals(addType)) {
      //修改因子描述时，计算分值
      Factor father = factorDao.findById(factor.getFactorid());
      Factor grandfather = factorDao.findById(father.getFactorid());
      Amount weight =
          new Amount(father.getWeight()).multiply(Double.parseDouble(grandfather.getWeight()));
      factor.setAccount(weight.multiply(Double.parseDouble(factor.getScore())).toString());
    } else if ("MODEL".equals(addType)) {
      //修改模型时
      factor.setModuletype(factor.getCode());
      //设置模型类型
      //删除原有关系
      moduelChannelDao.deleteByModule(factor.getModuletype());
      //添加相应关系
      if (channels != null) {
        List<ChannelBean> list = channels.getChannels();
        for (ChannelBean channel : list) {
          //循环所有渠道
          List<String> mercs = channel.getMercCode();
          if (mercs == null || mercs.isEmpty()) {
            //如果渠道下面的商户为空，新增一条模型与渠道的关系
            ModuelsChannel moc = new ModuelsChannel();
            moc.setChannelcode(channel.getChannelCode());
            moc.setModuletype(factor.getModuletype());
            moc.setStatus("00");
            moduelChannelDao.insert(moc);
          } else {
            for (String mcod : mercs) {
              //渠道下的商户不为空，新建模型、渠道、商户关系
              ModuelsChannel moc = new ModuelsChannel();
              moc.setChannelcode(channel.getChannelCode());
              moc.setModuletype(factor.getModuletype());
              moc.setMercid(mcod);
              moc.setStatus("00");
              moduelChannelDao.insert(moc);
            }
          }
        }
      }
    }

    factor.setWeight(new Amount(factor.getWeight()).divide(100).toString());
    factor.setUpdatetime(DateTime.now());
    factor.setUserid(auth.getUserId());
    factor.setUsername(auth.getUsername());
    factor = factorDao.updateFactor(factor);

    if (StringUtils.equals(beforeWeight, factor.getWeight())) {
      //权重发生变化，修改其所有子节点的相关信息
      updateChildFactor(factor, auth);
    }
    return factor;
  }

  public void updateChildFactor(Factor factor, Authorization auth) {
    List<Factor> children = factorDao.queryByParentid(factor.getId());
    if (children == null || children.isEmpty()) {
      //如果此节点没有子节点结束递归调用
      return;
    }
    //循环子节点，并递归调用
    for (Factor child : children) {
      //子节点如果是模型节点，修改其权重
      if ("2".equals(child.getFactorlevel()) && !StringUtils.isEmpty(child.getModuletype())) {
        child.setWeight(factor.getWeight());
        child.setUpdatetime(DateTime.now());
        child.setUserid(auth.getUserId());
        child.setUsername(auth.getUsername());
        factorDao.updateFactor(child);
      } else if ("4".equals(child.getFactorlevel())
          || (("3".equals(child.getFactorlevel()) && StringUtils.isEmpty(
          factor.getModuletype())))) {
        //非模型的三级子节点或4级节点修改其权重和计算分值
        Factor grandfather = factorDao.findById(factor.getFactorid());
        Amount weight =
            new Amount(factor.getWeight()).multiply(Double.parseDouble(grandfather.getWeight()));

        child.setAccount(weight.multiply(Double.parseDouble(child.getScore())).toString());
        child.setWeight(factor.getWeight());
        child.setUpdatetime(DateTime.now());
        child.setUserid(auth.getUserId());
        child.setUsername(auth.getUsername());
        factorDao.updateFactor(child);
      }
      updateChildFactor(child, auth);
    }
  }

  @Override
  public Factor findById(int id) {
    return factorDao.findById(id);
  }

  @Override
  public Factor updateFactor(Factor factor) {
    return factorDao.updateFactor(factor);
  }

  @Override
  public String findFactorInTree(String riskType, String customertype) {

    List<Factor> list = factorDao.queryFactorByType(riskType, customertype);
    StringBuffer sbf = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        sbf.append("{id:"
            + list.get(i).getId()
            + ",pId:"
            + list.get(i).getFactorid()
            + ",name:'"
            + list.get(i).getName()
            + "',menulevel:'"
            + list.get(i).getFactorlevel()
            + "'}");
      } else {
        sbf.append("{id:"
            + list.get(i).getId()
            + ",pId:"
            + list.get(i).getFactorid()
            + ",name:'"
            + list.get(i).getName()
            + "',menulevel:'"
            + list.get(i).getFactorlevel()
            + "'},");
      }
    }
    return sbf.toString();
  }

  @Override
  public Factor findByCode(String code) {
    return factorDao.findByCode(code);
  }

  @Override
  public void deleteFactor(Integer id) {
    factorDao.deleteFactor(id);
  }
}
