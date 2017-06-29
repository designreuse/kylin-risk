package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.AbnorCountryBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.AbnormalCountrycode;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.AbnorCountryService;
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
 * Created by 201507270241 on 2015/9/10.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/abnorCountry")
public class AbnorCountryRestAction {

  @Resource
  private AbnorCountryService abnorCountryService;
  @Resource
  private AbnorCountryBiz abnorCountryBiz;

  @RequestMapping("updateAbnorCountry")
  public AbnormalCountrycode update(@ModelAttribute AbnormalCountrycode abnormalCountrycode,
      HttpServletRequest request) {
    if (abnormalCountrycode != null) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      abnormalCountrycode.setLstmtausr(auth.getUserId());
      abnormalCountrycode.setUsername(auth.getUsername());
      abnorCountryService.update(abnormalCountrycode);
    }
    return new AbnormalCountrycode();
  }

  @RequestMapping(value = "importAbnorCountry", method = RequestMethod.POST)
  public AbnormalCountrycode importAbnorCountry(
      @RequestParam("fileUpload") CommonsMultipartFile file)
      throws IllegalStateException, IOException {

    // 判断文件是否存在
    if (!file.isEmpty()) {
      DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddhhmmss");
      String filename = DateTime.now().toString(fmt) + file.getOriginalFilename();
      String path = Constants.FILE_PATH + filename;
      File localFile = new File(path);

      try {
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        Map map = abnorCountryBiz.uploadFile(filename);
        List<String> errorlist = (List) map.get("errorlist");
        List<AbnormalCountrycode> abnormalCountrycodeList =
            (List<AbnormalCountrycode>) map.get("abnormalCountrycodeList");
        if (!errorlist.isEmpty()) {
          throw new RiskRestErrorException(errorlist.get(0));
        } else {
          if (!abnormalCountrycodeList.isEmpty()) {
            abnorCountryService.insert(abnormalCountrycodeList);
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
    return new AbnormalCountrycode();
  }
}
