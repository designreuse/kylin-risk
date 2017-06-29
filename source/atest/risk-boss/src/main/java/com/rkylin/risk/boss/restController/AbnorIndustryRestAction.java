package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.AbnorIndustryBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.AbnorIndustry;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.AbnorIndustryService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
@RequestMapping("/api/1/abnorIndustry")
public class AbnorIndustryRestAction {
  @Resource
  private AbnorIndustryService abnorIndustryService;
  @Resource
  private AbnorIndustryBiz abnorIndustryBiz;

  @RequestMapping("updateAbnorIndustry")
  public AbnorIndustry update(@ModelAttribute AbnorIndustry abnorIndustry) {
    if (abnorIndustry != null) {
      abnorIndustryService.update(abnorIndustry);
    }
    return new AbnorIndustry();
  }

  @RequestMapping(value = "importAbnorIndustry", method = RequestMethod.POST)
  public AbnorIndustry importAbnorIndustry(@RequestParam("fileUpload") CommonsMultipartFile file)
      throws IllegalStateException, IOException {

    // 判断文件是否存在
    if (!file.isEmpty()) {
      DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddhhmmss");
      String filename = DateTime.now().toString(fmt) + file.getOriginalFilename();
      String path = Constants.FILE_PATH + filename;
      File localFile = new File(path);

      try {
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        Map map = abnorIndustryBiz.uploadFile(filename);
        List<String> errorlist = (List) map.get("errorlist");
        List<AbnorIndustry> abnorIndustryList = (List<AbnorIndustry>) map.get("abnorIndustryList");
        if (!errorlist.isEmpty()) {
          throw new RiskRestErrorException(errorlist.get(0));
        } else {
          if (!abnorIndustryList.isEmpty()) {
            abnorIndustryService.insert(abnorIndustryList);
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
    return new AbnorIndustry();
  }
}
