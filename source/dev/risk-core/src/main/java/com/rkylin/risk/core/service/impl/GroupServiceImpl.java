package com.rkylin.risk.core.service.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.DictionaryDao;
import com.rkylin.risk.core.dao.GroupChannelDao;
import com.rkylin.risk.core.dao.GroupChannelHisDao;
import com.rkylin.risk.core.dao.GroupDao;
import com.rkylin.risk.core.dao.GroupHisDao;
import com.rkylin.risk.core.dto.ChannelBean;
import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.GroupChannel;
import com.rkylin.risk.core.entity.GroupChannelHis;
import com.rkylin.risk.core.entity.GroupHis;
import com.rkylin.risk.core.service.GroupService;
import com.rkylin.risk.core.utils.BeanMappper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lina on 2016-7-11.
 */
@Service
public class GroupServiceImpl implements GroupService {
  @Resource
  private GroupDao groupDao;
  @Resource
  private GroupChannelDao groupChannelDao;
  @Resource
  private DictionaryDao dictionaryDao;
  @Resource
  private GroupHisDao groupHisDao;
  @Resource
  private GroupChannelHisDao groupChannelHisDao;
  @Value("${maven.groupPath}")
  private String mavenGroupPath;

  @Override
  public void delGroup(String ids) {
    if (!StringUtils.isEmpty(ids)) {
      String[] idArr = ids.split(",");
      for (String id : idArr) {
        groupDao.delete(Short.parseShort(id));
        groupChannelDao.delByGroupId(Short.parseShort(id));
      }
    }
  }

  @Override
  public List<Group> queryAllGroup() {
    return groupDao.queryAllGroup();
  }

  @Override
  public Group queryById(Short groupid) {
    return groupDao.queryById(groupid);
  }

  @Override
  public void updateGroup(Group group, ChannelsBean channels, Authorization auth) {
    createGroupHis(group, auth);

    group.setIsexecute(Constants.ACTIVE);
    groupDao.update(group);

    //删除并生成规则渠道关系
    groupChannelDao.delByGroupId(group.getId());

    List<GroupChannel> groupChannels = createArtifactinfo(group, channels);
    groupChannelDao.insertBatch(groupChannels);
  }

  @Override
  public Group addGroup(Group group, ChannelsBean channels, Authorization auth) {
    List<GroupChannel> groupChannels = createArtifactinfo(group, channels);
    group.setIsexecute(Constants.ACTIVE);
    group.setCreateoperid(auth.getUserId());
    group.setCreateopername(auth.getRealname());
    group.setVersion("0");
    Group addGroup = groupDao.addGroup(group);
    addGroup.setIssueartifactid(addGroup.getIssueartifactid() + addGroup.getId().toString());
    groupDao.update(addGroup);

    for (GroupChannel channel : groupChannels) {
      channel.setGroupid(addGroup.getId());
    }
    groupChannelDao.insertBatch(groupChannels);
    return null;
  }

  private List<GroupChannel> createArtifactinfo(Group group, ChannelsBean channels) {
    String artifactStr = "";
    String groupidStr = "";
    List<GroupChannel> groupChannels = Lists.newArrayList();
    if (channels != null) {
      List<ChannelBean> list = channels.getChannels();
      for (ChannelBean channel : list) {
        List<String> mercs = channel.getMercCode();
        if (mercs != null && !mercs.isEmpty()) {
          groupidStr += channel.getChannelCode();
          for (String mcod : mercs) {
            //渠道下的商户不为空，新建模型、渠道、产品关系
            DictionaryCode code =
                dictionaryDao.queryByDictAndCode(channel.getChannelCode(), mcod);
            GroupChannel groupChannel = new GroupChannel();
            if (code != null) {
              groupChannel.setGroupid(group.getId());
              groupChannel.setChannelname(code.getDictname());
              groupChannel.setChannelcode(code.getDictcode());
              groupChannel.setProductcode(code.getCode());
              groupChannel.setProductname(code.getName());
              groupChannels.add(groupChannel);

              artifactStr += code.getCode();
            }
          }
        }
      }
    }
    group.setIssueartifactid(artifactStr);
    group.setIssuegroupid(mavenGroupPath + groupidStr);
    return groupChannels;
  }

  /**
   * 保存规则组历史
   */
  private void createGroupHis(Group group, Authorization auth) {
    Group groupOld = groupDao.queryById(group.getId());
    GroupHis groupHis = new GroupHis();
    BeanMappper.fastCopy(groupOld, groupHis);
    groupHis.setId(null);
    groupHis.setUpdateopername(auth.getRealname());
    groupHis.setUpdateoperid(auth.getUserId());
    GroupHis hisAdd = groupHisDao.addGroupHis(groupHis);

    GroupChannelHis groupChannelHis = new GroupChannelHis();
    groupChannelHis.setId(hisAdd.getId());
    groupChannelHis.setGrouphisid(group.getId());
    groupChannelHisDao.insertFromGroupChannel(groupChannelHis);
  }

  @Override
  public Group queryByTimeAndStatus(String issueartifactid, String issuegroupid) {
    return groupDao.queryByTimeAndStatus(issueartifactid, issuegroupid);
  }

  @Override public Group queryByChannelAndServiceType(String channelCode, String serviceType) {
    return groupDao.queryByChannelAndServiceType(channelCode, serviceType);
  }

  @Override public Group queryByProductAndServiceType(String productCode, String serviceType) {
    return groupDao.queryByProductAndServiceType(productCode, serviceType);
  }
}
