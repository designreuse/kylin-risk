package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.IdCardBlackBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.IdCardBlackList;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.IdCardBlackService;
import com.rkylin.risk.core.service.OperatorLogService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by 201508031790 on 2015/9/19.
 */
@Slf4j
@RestController
@RequestMapping("api/1/idcardblack")
public class IdcardBlackRestAction {

  @Resource
  private IdCardBlackService idCardBlackService;

  @Resource
  private IdCardBlackBiz idCardBlackBiz;

  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping(value = "importFile", method = RequestMethod.POST)
  public String springUpload(@RequestParam("fileUpload") CommonsMultipartFile file,
      HttpServletRequest request) throws IllegalStateException, IOException {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");

    // 判断文件是否存在
    if (!file.isEmpty()) {
      DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddhhmmss");
      String filename = DateTime.now().toString(fmt) + file.getOriginalFilename();
      String path = Constants.UPLOAD_PATH + filename;
      File localFile = new File(path);

      try {
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        Map map = idCardBlackBiz.uploadFile(filename);
        List<String> errorlist = (List) map.get("errorlist");
        List<IdCardBlackList> blacklist = (List<IdCardBlackList>) map.get("bwglistlist");
        if (!errorlist.isEmpty()) {
          throw new RiskRestErrorException(errorlist.get(0));
        } else {
          if (!blacklist.isEmpty()) {
            idCardBlackService.addBatch(blacklist, auth);
            operatorLogService.insert(auth.getUserId(), auth.getUsername(), "IdCardBlackList",
                "批量添加");
          } else {
            throw new RiskRestErrorException("上传文件为空，请填写数据重新上传");
          }
        }
      } catch (IllegalStateException e) {
        log.info(e.getMessage(), e);
      } catch (IOException e) {
        log.info(e.getMessage(), e);
      }
    }
    return "dataSuccess";
  }

  @RequestMapping("idcardblackVerify")
  public String verifyBwg(@ModelAttribute IdCardBlackList entity, String ids,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String cardIds = idCardBlackService.verifyBlackcard(ids, entity, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "IdCardBlackList", "更新");
    return cardIds;
  }

  @RequestMapping("idcardblackModify")
  public String removeBlackcard(String reason, String opertype, String ids,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String cardIds = idCardBlackService.modifyBatch(reason, opertype, ids, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "IdCardBlackList", "移除");
    return cardIds;
  }

  @RequestMapping("deleteIdCardBlack")
  public String deleteBlackcard(String reason, String opertype, String ids,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String cardIds = idCardBlackService.modifyBatch(reason, opertype, ids, auth);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "IdCardBlackList", "删除");
    return cardIds;
  }
}
