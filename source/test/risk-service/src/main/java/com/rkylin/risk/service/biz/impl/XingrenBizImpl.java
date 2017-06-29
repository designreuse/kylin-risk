package com.rkylin.risk.service.biz.impl;

import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileurlGetResponse;
import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.XingrenBiz;
import com.rkylin.risk.service.utils.MagicNumberUtils;
import com.rkylin.risk.service.utils.MulThreadRSAUtils;
import com.rkylin.risk.service.utils.ROPUtil;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author qiuxian
 * @create 2016-11-09 15:04
 **/
@Component("xingrenBiz")
@Slf4j
public class XingrenBizImpl implements XingrenBiz {
  @Resource
  private ROPUtil fileROP;

  @Resource
  private CustomerinfoService customerinfoService;

  @Resource
  private IdentifyBiz identifyBiz;

  @Value("${yueshijue.decode.privateKey}")
  private String yueshijue_privateKey;

  @Value("${xingren.PriKey}")
  private String xingren_privateKey;
  private static Cache<String, Key> cache = CacheBuilder.newBuilder().maximumSize(5).build();


  @Override
  public Map requestPhotoinfo(SimpleOrder order) {
    Map<String, Object> result=new HashMap<>();
    Map<String, Object> map = getImagemapforPolice(order.getSvcId());
    if (map.get("message") != null) {
      result.put("simitity","0");
      result.put("policemsg","获取活体照片异常");
      return result;
    }
    ImageEntity imageEntity = (ImageEntity) map.get("photo");
    String md5check=map.get("photomd5check").toString();
    Customerinfo customerinfo=customerinfoService.queryOne(order.getUserId());
    String svcid=order.getSvcId();
    String constid="";
    if(svcid!=null){
      if(svcid.contains("YLMR")){
        constid="M000028";
      } else if(svcid.contains("SYCP")){
        constid="M000025";
      }else if(svcid.contains("LYCP")){
        constid="M000027";
      }
    }
    if(customerinfo!=null){
      PersonFactor factor=new PersonFactor();
      factor.setUserid(customerinfo.getCustomerid());
      factor.setConstid(constid);
      Map ocrmap = identifyBiz.requestphotoMatchPolicePhoto(factor, customerinfo.getCertificateid(),
          customerinfo.getCustomername(), md5check, imageEntity,"photoMatchPolicePhoto");
      if(ocrmap.get("simitity")!=null){
        result.put("simitity",ocrmap.get("simitity"));
        result.put("policemsg",ocrmap.get("message"));
        return result;
      }else{
        log.info("---相似度为空");
        result.put("simitity","0");
        result.put("policemsg",ocrmap.get("message"));
        return result;
      }
    }else{
      log.info("---未找到客户数据");
      result.put("simitity","0");
      result.put("policemsg","");
      return result;
    }
  }

  public Map getImagemapforPolice(String urlkey) {
    Map<String, Object> imageResult = new HashMap<>();
    if (isNotEmpty(urlkey)) {
      StringBuffer sbf = new StringBuffer();
      String fileUrl = "";
      FsFileurlGetRequest fileurlRequest = new FsFileurlGetRequest();
      fileurlRequest.setUrl_key(urlkey);
      try {
        FsFileurlGetResponse filrUrlResponse = fileROP.getResponse(fileurlRequest, "json");
        if (filrUrlResponse != null && filrUrlResponse.getFile_url() != null) {
          fileUrl = filrUrlResponse.getFile_url();
          log.info("['" + urlkey + "'文件查询成功：] " + filrUrlResponse.getBody());
        } else {
          sbf.append("附件下载失败；");
          log.info("['" + urlkey + "'文件下载失败：] " + filrUrlResponse);
          imageResult.put("message", sbf.toString());
          return imageResult;
        }
      } catch (Exception e) {
        log.info(urlkey + "附件下载异常，异常信息:{}", Throwables.getStackTraceAsString(e));
        sbf.append("附件下载异常");
        imageResult.put("message", sbf.toString());
        return imageResult;
      }
      InputStream inputStream = null;
      try {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000);
        conn.setRequestProperty("User-Agent",
            "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        inputStream = conn.getInputStream();
        log.info("开始读取数据流");
        byte[] encodedData = IOUtils.toByteArray(inputStream);
        log.info("附件大小：" + new Amount(encodedData.length).divide(1024) + "kb");
        log.info("读取数据流完成");
        byte[] dencodedDate = null;
        //私钥解密
        log.info(urlkey + "-->开始解密");
        Key rsaPrivateKey = cache.get(xingren_privateKey, new Callable<Key>() {
          @Override public Key call() throws Exception {
            return MulThreadRSAUtils.generatePrivatekey(xingren_privateKey);
          }
        });
        dencodedDate = MulThreadRSAUtils.multiThreadDecryp(encodedData, 5, rsaPrivateKey);
        log.info(urlkey + "-->解密完成");
        String md5CheckSum = DigestUtils.md5Hex(dencodedDate);
        String suffix = MagicNumberUtils.fileType(dencodedDate);
        log.info("-->图片类型：" + suffix);
        ImageEntity entity = new ImageEntity();
        entity.setType(ImageEntity.Type.BYTES);
        entity.setData(dencodedDate);
        imageResult.put("photo", entity);
        imageResult.put("photomd5check", md5CheckSum);
        imageResult.put("photosuffix", suffix);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      imageResult.put("message", "urlkey为空");
      return imageResult;
    }
    return imageResult;
  }


}
