package com.rkylin.risk.boss.restController;

import com.rkylin.risk.boss.biz.MerchantBiz;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Merchant;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by cuixiaofang on 2016-6-15.
 */
@RestController
@RequestMapping("/api/1/merchant")
@Slf4j
public class MerchantRestAction {
    @Resource
    private MerchantBiz merchantBiz;

    @Resource
    private MerchantService merchantService;

    @RequestMapping(value = "importMerchant", method = RequestMethod.POST)
    public Merchant importMerchant(@RequestParam("fileUpload") CommonsMultipartFile file) {
        try {
            if (!file.isEmpty()) {
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddhhmmss");
                String filename = DateTime.now().toString(fmt) + file.getOriginalFilename();
                String path = Constants.FILE_PATH + filename;
                File localFile = new File(path);
                FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
                Map map = merchantBiz.uploadFile(filename);
                List<String> errorlist = (List) map.get("errorlist");
                List<Merchant> merchants = (List<Merchant>) map.get("merchantList");
                if (!errorlist.isEmpty()) {
                    throw new RiskRestErrorException(errorlist.get(0));
                } else {
                    if (!merchants.isEmpty()) {
                        merchantService.addMerchantBatch(merchants);
                    } else {
                        throw new RiskRestErrorException("上传文件为空，请填写数据重新上传");
                    }
                }
            }
        } catch (IllegalStateException e) {
            log.info(e.getMessage(), e);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
        return new Merchant();
    }
}
