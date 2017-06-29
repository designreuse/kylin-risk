package com.rkylin.risk.service.biz.impl;

import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileurlGetResponse;
import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.MitouRiskBiz;
import com.rkylin.risk.service.biz.YueshijueBiz;
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
@Component("yueshijueBiz")
@Slf4j
public class YueshijueBizImpl implements YueshijueBiz {
  @Resource
  private ROPUtil fileROP;

  @Resource
  private IdentifyBiz identifyBiz;

  @Resource
  private MitouRiskBiz mitouRiskBiz;

  @Value("${yueshijue.decode.privateKey}")
  private String yueshijue_privateKey;
  @Value("${xingren.PriKey}")
  private String xingren_privateKey;
  private static Cache<String, Key> cache = CacheBuilder.newBuilder().maximumSize(5).build();

  @Value("${mitou.platid}")
  private Integer platid;

  @Override
  public Map handleYueShijue(PersonFactor factor) {
    Map<String, String> result = new HashMap<>();
    factor.setUniversityName(factor.getUniversityName() == null ? factor.getCorporationname()
        : factor.getUniversityName());
    factor.setBankname(factor.getBankname() == null ? factor.getTaccountbank()
        : factor.getBankname());
    factor.setBankbranch(
        factor.getBankbranch() == null ? factor.getTaccountbranch()
            : factor.getBankbranch());
/*    if (isNotEmpty(factor.getUrlkey())) {
      String[] splitUrlkey = factor.getUrlkey().split("\\|");
      if (splitUrlkey.length < 3) {
        result.put("mtresult", "false");
        result.put("mtmsg", "urlkey个数必须为3");
        result.put("ocrmsg", "urlkey个数必须为3");
        return result;
      }
    }*/
    //目前只有美容分期提交米投风控
    if (RuleConstants.MEIRONG_CHANNEL_ID.equals(factor.getConstid())) {
      Map<String, Object> map = getImagemap(factor);
      if (map.get("message") != null) {
        result.put("mtresult", "validatefalse");
        result.put("mtmsg", map.get("message").toString());
        result.put("ocrmsg", map.get("message").toString());
        return result;
      }
      //解析照片
      Map ocrmap=identifyBiz.requestFaceresult(map, factor);
      String orcmsg = ocrmap.get("msg").toString();
      String simility=ocrmap.get("similarity")==null?"0":ocrmap.get("similarity").toString();
      result.put("ocrmsg", orcmsg);
      //提交米投前部分数据校验
/*      String validatemsg = personFactorValidate(factor);
      log.info("-推送米投数据校验-------validatemsg:" + validatemsg);
      if (isNotEmpty(validatemsg)) {
        result.put("mtresult", "validatefalse");
        result.put("mtmsg", validatemsg);
        return result;
      }*/
      Map<String, String> mitouresult = new HashMap();

      mitouresult = mitouRiskBiz.requestMitouRisk(map, factor ,simility);
      //success,exception,datafalse
      result.put("mtresult",mitouresult.get("result"));
      result.put("mtmsg",mitouresult.get("msg"));

    }
    //杏仁
    if (RuleConstants.XINGREN_CHANNEL_ID.equals(factor.getConstid())) {
      Map<String, Object> map = getYSJImagemap(factor);
      if (map.get("message") != null) {
        result.put("mtresult", "validatefalse");
        result.put("mtmsg", map.get("message").toString());
        result.put("ocrmsg", map.get("message").toString());
        return result;
      }
      //解析照片
      Map ocrmap= identifyBiz.requestYSJFaceresult(map, factor);
      String orcmsg = ocrmap.get("ocrmsg").toString();
      String simility=ocrmap.get("similarity")==null?"0":ocrmap.get("similarity").toString();
      result.put("ocrmsg", orcmsg);
      //提交米投前部分数据校验
/*      String validatemsg = personFactorValidate(factor);
      log.info("-推送米投数据校验-------validatemsg:" + validatemsg);
      if (isNotEmpty(validatemsg)) {
        result.put("mtresult", "validatefalse");
        result.put("mtmsg", validatemsg);
        return result;
      }*/
      Map<String, String> mitouresult = new HashMap();

      mitouresult = mitouRiskBiz.requestMitouRisk(map, factor ,simility);
      //success,exception,datafalse
      result.put("mtresult",mitouresult.get("result"));
      result.put("mtmsg",mitouresult.get("msg"));

    }

    //悦视觉目前只有身份证正反面
    if (RuleConstants.YUESHIJUE_CHANNEL_ID.equals(factor.getConstid())) {
      Map<String, Object> map = getYSJImagemap(factor);
      if (map.get("message") != null) {
        result.put("mtresult", "refuse");
        result.put("ocrmsg", map.get("message").toString());
        return result;
      }
      //解析照片
      Map ocrmap= identifyBiz.requestYSJFaceresult(map, factor);
      String orcmsg = ocrmap.get("ocrmsg").toString();
      result.put("ocrmsg", orcmsg);
      result.put("mtresult", "refuse");
    }
    return result;
  }

  public Map getImagemap(PersonFactor factor) {
    Map<String, Object> imageResult = new HashMap<>();
    if (isNotEmpty(factor.getUrlkey())) {
      StringBuffer sbf = new StringBuffer();
      String[] splitUrlkey = factor.getUrlkey().split("\\|");
      for (int i = 0; i < 3; i++) {
        String urlkey = splitUrlkey[i];
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
          //设置超时间为3秒
          conn.setConnectTimeout(3 * 1000);
          //防止屏蔽程序抓取而返回403错误
          conn.setRequestProperty("User-Agent",
              "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
          //得到输入流
          inputStream = conn.getInputStream();

          //获取自己数组
          log.info("开始读取数据流");
          byte[] encodedData = IOUtils.toByteArray(inputStream);
          log.info("附件大小：" + new Amount(encodedData.length).divide(1024) + "kb");
          log.info("读取数据流完成");
          byte[] dencodedDate = null;
          //私钥解密
          log.info(urlkey + "-->开始解密");
          dencodedDate = MulThreadRSAUtils.decryptByPublicKey(encodedData, yueshijue_privateKey);
          // Files.write(Paths.get("D:\\byte.123"),dencodedDate);
          log.info(urlkey + "-->解密完成");
          String md5CheckSum = DigestUtils.md5Hex(dencodedDate);
          String suffix = MagicNumberUtils.fileType(dencodedDate);
          log.info("-->图片类型：" + suffix);
          ImageEntity entity = new ImageEntity();
          entity.setType(ImageEntity.Type.BYTES);
          entity.setData(dencodedDate);
          switch (i) {
            case 0:
              entity.setFront(true);
              imageResult.put("idcard", entity);
              imageResult.put("idcardmd5check", md5CheckSum);
              imageResult.put("idcardsuffix", suffix);
              break;
            case 1:
              entity.setFront(false);
              imageResult.put("idcardBack", entity);
              imageResult.put("idcardBackmd5check", md5CheckSum);
              imageResult.put("idcardBacksuffix", suffix);
              break;
            case 2:
              imageResult.put("idcardPersonPic", entity);
              imageResult.put("idcardPersonPicmd5check", md5CheckSum);
              imageResult.put("idcardPersonPicsuffix", suffix);
              break;
            default:
              log.info("no photo");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else {
      imageResult.put("message", "urlkey为空");
      return imageResult;
    }

    if (imageResult.get("idcardmd5check") == null
        || imageResult.get("idcardBackmd5check") == null
        || imageResult.get("idcardPersonPicmd5check") == null) {
      log.info("OCR--存在照片的MD5校验码为空]");
      imageResult.put("message", "照片数量不足，请重新上传");
      return imageResult;
    }
    return imageResult;
  }

  private Map getYSJImagemap(PersonFactor factor) {
    Map<String, Object> imageResult = new HashMap<>();
    if (isNotEmpty(factor.getUrlkey())) {
      StringBuffer sbf = new StringBuffer();
      String[] splitUrlkey = factor.getUrlkey().split("\\|");
      for (int i = 0; i < 2; i++) {
        String urlkey = splitUrlkey[i];
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
          //设置超时间为3秒
          conn.setConnectTimeout(3 * 1000);
          //防止屏蔽程序抓取而返回403错误
          conn.setRequestProperty("User-Agent",
              "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
          //得到输入流
          inputStream = conn.getInputStream();

          //获取自己数组
          log.info("开始读取数据流");
          byte[] encodedData = IOUtils.toByteArray(inputStream);
          log.info("附件大小：" + new Amount(encodedData.length).divide(1024) + "kb");
          log.info("读取数据流完成");
          byte[] dencodedDate = null;
          //私钥解密
          log.info(urlkey + "-->开始解密");
          if(RuleConstants.XINGREN_CHANNEL_ID.equals(factor.getConstid())){
            Key rsaPrivateKey = cache.get(xingren_privateKey, new Callable<Key>() {
              @Override public Key call() throws Exception {
                return MulThreadRSAUtils.generatePrivatekey(xingren_privateKey);
              }
            });
            dencodedDate = MulThreadRSAUtils.multiThreadDecryp(encodedData, 5, rsaPrivateKey);
          }else{
            dencodedDate = MulThreadRSAUtils.decryptByPublicKey(encodedData, yueshijue_privateKey);
          }
          // Files.write(Paths.get("D:\\byte.123"),dencodedDate);
          log.info(urlkey + "-->解密完成");
          String md5CheckSum = DigestUtils.md5Hex(dencodedDate);
          String suffix = MagicNumberUtils.fileType(dencodedDate);
          log.info("-->图片类型：" + suffix);
          ImageEntity entity = new ImageEntity();
          entity.setType(ImageEntity.Type.BYTES);
          entity.setData(dencodedDate);
          switch (i) {
            case 0:
              entity.setFront(true);
              imageResult.put("idcard", entity);
              imageResult.put("idcardmd5check", md5CheckSum);
              imageResult.put("idcardsuffix", suffix);
              break;
            case 1:
              entity.setFront(false);
              imageResult.put("idcardBack", entity);
              imageResult.put("idcardBackmd5check", md5CheckSum);
              imageResult.put("idcardBacksuffix", suffix);
              break;
            default:
              log.info("no photo");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else {
      imageResult.put("message", "urlkey为空");
      return imageResult;
    }

    if (imageResult.get("idcardmd5check") == null
        || imageResult.get("idcardBackmd5check") == null) {
      log.info("OCR--存在照片的MD5校验码为空]");
      imageResult.put("message", "照片数量不足，请重新上传");
      return imageResult;
    }
    return imageResult;
  }

}
