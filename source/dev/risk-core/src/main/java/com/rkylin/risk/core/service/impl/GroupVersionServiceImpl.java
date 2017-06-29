package com.rkylin.risk.core.service.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.BaseRedisDao;
import com.rkylin.risk.core.dao.GroupDao;
import com.rkylin.risk.core.dao.GroupVersionDao;
import com.rkylin.risk.core.dao.RuleDao;
import com.rkylin.risk.core.dao.RuleHisDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.GroupVersion;
import com.rkylin.risk.core.entity.Rule;
import com.rkylin.risk.core.entity.RuleHis;
import com.rkylin.risk.core.service.GroupVersionService;
import com.rkylin.risk.core.utils.BeanMappper;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by lina on 2016-7-20.
 */
@Slf4j
@Service
public class GroupVersionServiceImpl implements GroupVersionService {
  @Resource
  private GroupVersionDao groupVersionDao;
  @Resource
  private GroupDao groupDao;
  @Resource
  private RuleDao ruleDao;
  @Resource
  private RuleHisDao ruleHisDao;

  @Resource
  private BaseRedisDao<String, String> baseRedisDao;

  private static final String RULE_VERSION_PREFIX = "$risk#";

  @Override public GroupVersion queryById(Short id) {
    return groupVersionDao.queryById(id);
  }

  @Override public GroupVersion insert(GroupVersion groupVersion) {
    return groupVersionDao.insert(groupVersion);
  }

  private String ga(String groupId, String artifactId) {
    return RULE_VERSION_PREFIX + groupId + ":" + artifactId;
  }

  @Override public void update(GroupVersion groupVersion) {
    GroupVersion dbGroupVersion = groupVersionDao.queryById(groupVersion.getId());
    Objects.requireNonNull(dbGroupVersion);
    //只有是发布规则才更新缓存
    boolean isSetCache = false;
    if (Constants.ACTIVE.equals(dbGroupVersion.getIsissue())) {
      isSetCache = true;
    }
    groupVersionDao.update(groupVersion);
    if (isSetCache) {
      updateRuleVersionCache(groupVersion);
    }
  }

  private void updateRuleVersionCache(GroupVersion groupVersion) {
    try {
      String key = ga(groupVersion.getIssuegroupid(), groupVersion.getIssueartifactid());
      log.info("update rule jar[{}] to the {} version", key, groupVersion);
      baseRedisDao.set(key, groupVersion.getVersion());
    } catch (Exception e) {
      log.error("redis保存失败", e);
    }
  }

  @Override
  public String loadRuleGroupVersionFromCache(String groupId, String artifactId) {
    String key = ga(groupId, artifactId);
    String version = null;
    try {
      version = baseRedisDao.get(key);
    } catch (Exception e) {
      log.error("connect redis error", e);
    }
    return version;
  }

  @Override
  public String queryRuleGroupVersion(String groupId, String artifactId) {
    Group group = groupDao.queryByTimeAndStatus(artifactId, groupId);
    if (group == null) {
      return null;
    }
    String jarVersion = group.getVersion();
    GroupVersion groupVersion = groupVersionDao.query(groupId, artifactId, jarVersion);
    if (groupVersion == null) {
      return null;
    }
    if (Constants.INACTIVE.equals(groupVersion.getIsissue())) {
      updateRuleVersionCache(groupVersion);
      return groupVersion.getVersion();
    }
    return null;
  }

  @Override public void insertGroupVersion(Group group, Authorization auth) {
    //规则组更新为不可执行
    group.setVersion(
        group.getVersion() == null ? "1"
            : Integer.toString(Integer.parseInt(group.getVersion()) + 1));
    group.setIsexecute(Constants.INACTIVE);
    groupDao.update(group);

    GroupVersion version = new GroupVersion();
    BeanMappper.fastCopy(group, version);
    version.setId(null);
    version.setGroupid(group.getId());
    version.setCreateoperid(auth.getUserId());
    version.setCreateopername(auth.getRealname());
    version.setIsissue(Constants.ACTIVE);
    GroupVersion addGroupVer = groupVersionDao.insert(version);

    //其他版本置为INACTIVE
    GroupVersion versionSta = new GroupVersion();
    versionSta.setGroupid(group.getId());
    versionSta.setVersion(group.getVersion());
    versionSta.setIsissue(Constants.INACTIVE);
    groupVersionDao.updateVersionSta(versionSta);

    //保存规则快照
    createRuleHisList(addGroupVer, auth);
  }

  /**
   * 保存历史信息：规则历史
   */
  private void createRuleHisList(GroupVersion group, Authorization auth) {
    List<Rule> rules = ruleDao.queryByGroupidAndSta(group.getGroupid(), Constants.ACTIVE);
    List<RuleHis> ruleHises = Lists.newArrayList();
    for (Rule rule : rules) {
      RuleHis ruleHis = new RuleHis();
      BeanMappper.fastCopy(rule, ruleHis);
      ruleHis.setGroupversionid(group.getId());
      ruleHis.setUpdateoperid(auth.getUserId());
      ruleHis.setUpdateopername(auth.getRealname());
      ruleHis.setId(null);
      ruleHises.add(ruleHis);
    }
    ruleHisDao.insertBatch(ruleHises);
  }
}
