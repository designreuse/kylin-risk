package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.MerchantbwgList;
import com.rkylin.risk.core.service.MerchantbwgService;
import com.rkylin.risk.core.service.OperatorLogService;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by 201508031790 on 2015/9/14.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/merchantbwg")
public class MerchantBwgRestAction {

  @Resource
  private MerchantbwgService merchantbwgService;
  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping(value = "addBWGfromMerc", method = RequestMethod.POST)
  public MerchantbwgList addBWG(@ModelAttribute MerchantbwgList mer, String addIds,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    merchantbwgService.addBWGFromMerchant(addIds, mer, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "MerchantbwgList", "更新");
    return mer;
  }

  @RequestMapping(value = "merchantbwgModify", method = RequestMethod.POST)
  public String merchantbwgModify(String ids, String opertype, String reason,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String mercIds = merchantbwgService.update(ids, opertype, reason, auth);
    return mercIds;
  }

  @RequestMapping(value = "deleteBWGfromMerc", method = RequestMethod.POST)
  public String delBwg(String deleteIds, HttpServletRequest request, String reason) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    merchantbwgService.delMerbwg(deleteIds, auth, reason);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "MerchantbwgList", "删除");
    return deleteIds;
  }

  @RequestMapping(value = "merchantbwgVerify", method = RequestMethod.POST)
  public String verifyBwg(@ModelAttribute MerchantbwgList bwg, String ids,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String mercIds = merchantbwgService.verifybwg(ids, bwg, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "MerchantbwgList", "修改");
    return mercIds;
  }

  @RequestMapping(value = "importMercFile", method = RequestMethod.POST)
  public String springUpload(@RequestParam("fileUpload") CommonsMultipartFile file)
      throws IllegalStateException, IOException {
    // 判断文件是否存在
    if (!file.isEmpty()) {
      String path = "D:/" + file.getOriginalFilename();
      File localFile = new File(path);
      try {
        file.transferTo(localFile);
      } catch (IllegalStateException e) {
        log.info(e.getMessage(), e);
      } catch (IOException e) {
        log.info(e.getMessage(), e);
      }
    }
    return "dataSuccess";
  }
}
