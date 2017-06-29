package com.rkylin.risk.service.biz.impl;

import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileurlGetResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.KezhanBiz;
import com.rkylin.risk.service.utils.MulThreadRSAUtils;
import com.rkylin.risk.service.utils.ROPUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qiuxian
 * @create 2016-11-28 16:44
 **/
@Component("kezhanBiz")
@Slf4j
public class KezhanBizImpl implements KezhanBiz {
  @Resource
  private ROPUtil fileROP;
  @Resource
  private IdentifyBiz identifyBiz;

  @Value("${kezhan.decode.privateKey}")
  private String kezhan_privateKey;
  @Value("${bangbang.decode.privateKey}")
  private String bangbang_privateKey;
  private static Cache<String, Key> cache = CacheBuilder.newBuilder().maximumSize(5).build();
  @Override
  public String downloadFile(PersonFactor personFactor) {
    //下载
    StringBuffer sbf = new StringBuffer();
    String fileUrl = "";
    FsFileurlGetRequest fileurlRequest = new FsFileurlGetRequest();
    fileurlRequest.setUrl_key(personFactor.getUrlkey());
    try {
      FsFileurlGetResponse filrUrlResponse = fileROP.getResponse(fileurlRequest, "json");
      if (filrUrlResponse != null && filrUrlResponse.getFile_url() != null) {
        fileUrl = filrUrlResponse.getFile_url();
        log.info("['" + personFactor.getUrlkey() + "'文件查询成功：] " + filrUrlResponse.getBody());
      } else {
        sbf.append("附件下载失败；");
        log.info("['" + personFactor.getUrlkey() + "'文件下载失败：] " + filrUrlResponse);
        return sbf.toString();
      }
    } catch (Exception e) {
      log.info("附件下载异常，异常信息:{}", e);
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
      log.info("开始解密");
      if (RuleConstants.BANGBANG_CHANNEL_ID.equals(personFactor.getConstid())) {
        Key rsaPrivateKey = cache.get(bangbang_privateKey, new Callable<Key>() {
          @Override public Key call() throws Exception {
            return MulThreadRSAUtils.generatePrivatekey(bangbang_privateKey);
          }
        });
        dencodedDate = MulThreadRSAUtils.multiThreadDecryp(encodedData, 5, rsaPrivateKey);
      } else if ("M000004".equals(personFactor.getConstid())) {
        Key rsaPrivateKey = cache.get(kezhan_privateKey, new Callable<Key>() {
          @Override public Key call() throws Exception {
            return MulThreadRSAUtils.generatePrivatekey(kezhan_privateKey);
          }
        });
        dencodedDate = MulThreadRSAUtils.multiThreadDecryp(encodedData, 5, rsaPrivateKey);
      }
      log.info("解密完成");
      Map<String, Object> imageEntityMap = unzip(dencodedDate);
      if (imageEntityMap.get("idcardmd5check") == null
          || imageEntityMap.get("idcardBackmd5check") == null
          || imageEntityMap.get("bankCardmd5check") == null
          || imageEntityMap.get("idcardPersonPicmd5check") == null) {
        log.info("存在照片的MD5校验码为空]");
        sbf.append("照片数量不足，请重新上传");
        return sbf.toString();
      } else {
        String riskmessage = identifyBiz.requestFaceresult(imageEntityMap, personFactor);
        sbf.append(riskmessage);
      }
    } catch (Exception e) {
      log.info("['" + personFactor.getUrlkey() + "'解析照片异常]");
      e.printStackTrace();
      sbf.append("解析照片异常;");
      return sbf.toString();
    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (Exception e2) {
        log.info(e2.toString());
      }
    }
    return sbf.toString();
  }

  //解压文件
  public Map<String, Object> unzip(byte[] zipdatas) throws IOException {
    Map<String, Object> imageResult = new HashMap<>();
    ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipdatas));
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    try {
      while (zipEntry != null) {
        byte[] encodedData;
        if (!zipEntry.isDirectory()) {
          encodedData = IOUtils.toByteArray(zipInputStream);
          String md5CheckSum = DigestUtils.md5Hex(encodedData);

          ImageEntity entity = new ImageEntity();
          entity.setType(ImageEntity.Type.BYTES);
          entity.setData(encodedData);
          String name = zipEntry.getName();
          log.info(name + "大小：" + new Amount(zipEntry.getSize()).divide(1024) + "kb");
          name = name.substring(0, name.indexOf("."));

          switch (name) {
            case "idcard":
              imageResult.put("idcard", entity);
              imageResult.put("idcardmd5check", md5CheckSum);
              imageResult.put("idcardsuffix", "jpg");
              break;
            case "idcard_back":
              imageResult.put("idcardBack", entity);
              imageResult.put("idcardBackmd5check", md5CheckSum);
              imageResult.put("idcardBacksuffix", "jpg");

              break;
            case "idcard_person_pic":
              imageResult.put("idcardPersonPic", entity);
              imageResult.put("idcardPersonPicmd5check", md5CheckSum);
              imageResult.put("idcardPersonPicsuffix", "jpg");

              break;
            case "bankcard":
              imageResult.put("bankCard", entity);
              imageResult.put("bankCardmd5check", md5CheckSum);
              break;
            default:
              log.info("no photo");
          }
        }
        zipEntry = zipInputStream.getNextEntry();
      }
    } catch (IOException e) {
      log.info("解压出现问题, 异常信息:{}", e);
    }
    return imageResult;
  }
}
