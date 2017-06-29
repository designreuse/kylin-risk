package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.AmlDubiousTxn;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.AmlDubiousTxnService;
import com.rkylin.risk.core.service.OperatorLogService;
import com.rkylin.risk.core.service.OrderService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by 201507270241 on 2015/9/19.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/amlDubTxn")
public class AmlDubiousTxnRestAction {
  @Resource
  private AmlDubiousTxnService amlDubiousTxnService;
  @Resource
  private OrderService orderService;
  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping("addAmlDubiousTxn")
  public AmlDubiousTxn addAmlDubiousTxn(@ModelAttribute AmlDubiousTxn amlDubiousTxn,
      @RequestParam("fileUpload") CommonsMultipartFile file, String[] txnIds,
      HttpServletRequest request) throws IllegalStateException, IOException {
    // 判断文件是否存在
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    String path = "";
    if (!file.isEmpty()) {
      String filename = DateTime.now().toString("yyyyMMddhhmmss") + file.getOriginalFilename();
      path = Constants.FILE_PATH + filename;
      File localFile = new File(path);
      try {
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
      } catch (IllegalStateException e) {
        log.info(e.getMessage(), e);
      } catch (IOException e) {
        log.info(e.getMessage(), e);
      }
    }
    if (amlDubiousTxn != null) {
      amlDubiousTxn.setWarnnum(LocalDate.now().toString("yyyyMMdd")
          + amlDubiousTxn.getRuleid()
          + amlDubiousTxn.getCustomnum());
      amlDubiousTxn.setSource(Constants.SOURCE);
      amlDubiousTxn.setWarndate(LocalDate.now());
      amlDubiousTxn.setFilepath(path);
      amlDubiousTxnService.add(amlDubiousTxn);
      if (txnIds.length > 0 && !StringUtils.isEmpty(amlDubiousTxn.getId())) {
        List<String> txnIdlist = Arrays.asList(txnIds);
        for (String txnid : txnIdlist) {
          Order order = new Order();
          order.setId(Long.parseLong(txnid));
          order.setDubioustxnid(amlDubiousTxn.getId());
          orderService.update(order);
        }
      }
    }
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "AmlDubiousTxn,SimpleBill", "新增反洗钱可以交易");
    return new AmlDubiousTxn();
  }

  @RequestMapping("updateAmlDubTxn")
  public AmlDubiousTxn updateAmlDubTxn(@RequestParam Integer id, String dealopinion,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    AmlDubiousTxn amlDubiousTxn = new AmlDubiousTxn();
    amlDubiousTxn.setId(id);
    amlDubiousTxn.setDealopinion(dealopinion);
    amlDubiousTxnService.modify(amlDubiousTxn);
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "AmlDubiousTxn,SimpleBill", "新增反洗钱可以交易");
    return new AmlDubiousTxn();
  }
}

