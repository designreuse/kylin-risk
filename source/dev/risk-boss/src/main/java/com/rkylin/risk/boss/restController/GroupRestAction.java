package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.RuleBiz;
import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.GroupVersion;
import com.rkylin.risk.core.service.GroupService;
import com.rkylin.risk.core.service.GroupVersionService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lina on 2016-7-11.
 */

@RestController
@RequestMapping("api/1/group")
public class GroupRestAction {

  @Resource
  private GroupService groupService;
  @Resource
  private RuleBiz ruleBiz;
  @Resource
  private GroupVersionService groupVersionService;

  @RequestMapping("deleteGroup")
  public Group deleteGroup(@RequestParam String id) {
    groupService.delGroup(id);
    return new Group();
  }

  @RequestMapping("addGroup")
  public Group addGroup(@ModelAttribute Group group, ChannelsBean channels,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    groupService.addGroup(group, channels, auth);
    return new Group();
  }

  @RequestMapping("updateGroup")
  public Group updateGroup(@ModelAttribute Group group, ChannelsBean channels,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    groupService.updateGroup(group, channels, auth);
    return new Group();
  }

  @RequestMapping("generatingRule")
  public GroupVersion generatingRule(@RequestParam String id, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    GroupVersion groupVersion = groupVersionService.queryById(Short.parseShort(id));
    ruleBiz.creategeneratingRule(groupVersion, auth);
    return new GroupVersion();
  }

  @RequestMapping("generatingVersion")
  public Group generatingVersion(@RequestParam String id, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    Group group = groupService.queryById(Short.parseShort(id));
    groupVersionService.insertGroupVersion(group, auth);
    return new Group();
  }

  @RequestMapping("queryAllGroup")
  public List<Group> queryAllGroup() {
    return groupService.queryAllGroup();
  }
}
