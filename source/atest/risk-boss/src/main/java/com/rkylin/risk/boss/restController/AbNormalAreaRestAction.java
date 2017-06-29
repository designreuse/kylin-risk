package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.AbnormalAreaBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.AbnormalArea;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.AbnormalAreaService;
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
 * Created by 201508031790 on 2015/9/5.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/abnormalarea")
public class AbNormalAreaRestAction {

  @Resource
  private AbnormalAreaService abnormalAreaService;

  @Resource
  private AbnormalAreaBiz abnormalAreaBiz;

  @RequestMapping("abnormalareaModify")
  public AbnormalArea operatorModify(@ModelAttribute AbnormalArea entity) {
    AbnormalArea area = abnormalAreaService.updateAbnormalArea(entity);
    return area;
  }

  @RequestMapping(value = "importAbnormalArea", method = RequestMethod.POST)
  public AbnormalArea importAbnormalArea(@RequestParam("fileUpload") CommonsMultipartFile file)
      throws IllegalStateException, IOException {

    // 判断文件是否存在
    if (!file.isEmpty()) {
      DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddhhmmss");
      String filename = DateTime.now().toString(fmt) + file.getOriginalFilename();
      String path = Constants.FILE_PATH + filename;
      File localFile = new File(path);

      try {
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        Map map = abnormalAreaBiz.uploadFile(filename);
        List<String> errorlist = (List) map.get("errorlist");
        List<AbnormalArea> abnormalAreaList = (List<AbnormalArea>) map.get("abnormalAreaList");
        if (!errorlist.isEmpty()) {
          throw new RiskRestErrorException(errorlist.get(0));
        } else {
          if (!abnormalAreaList.isEmpty()) {
            abnormalAreaService.insert(abnormalAreaList);
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
    return new AbnormalArea();
  }
}
